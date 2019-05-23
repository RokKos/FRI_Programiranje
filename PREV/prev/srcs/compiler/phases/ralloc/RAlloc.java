/**
 * @author sliva
 */
package compiler.phases.ralloc;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import compiler.Main;
import compiler.data.asmcode.*;
import compiler.data.layout.Frame;
import compiler.data.layout.Temp;
import compiler.phases.*;
import compiler.phases.asmcode.*;

/**
 * Register allocation phase.
 * 
 * @author sliva
 */
public class RAlloc extends Phase {

	public RAlloc() {
		super("ralloc");
	}

	private final String kSetConst = "SET `d0, ";
	private final String kSub = "SUB `d0, `s0, `s1";
	private final String kStore = "STO `s0, `s1, 0";
	private final String kLoad = "LDO `d0, `s0, 0";

	private class Node {
		private Temp nodeName;
		private Vector<Temp> connections;
		private boolean isPotencialSpill;
		private boolean isActualSpill;
		private int color;

		Node(Temp _nodeName, Vector<Temp> _connnections, boolean _isPotencialSpill) {
			nodeName = _nodeName;
			connections = _connnections;
			isPotencialSpill = _isPotencialSpill;
			color = Integer.MAX_VALUE;
			isActualSpill = false;
		}

		public Temp getNodeName() {
			return nodeName;
		}

		public Vector<Temp> getConnections() {
			return connections;
		}

		public boolean getIsPotencialSpill() {
			return isPotencialSpill;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int _color) {
			color = _color;
		}

		public boolean getIsActualSpill() {
			return isActualSpill;
		}

		public void setIsActualSpill(boolean _isActualSpill) {
			isActualSpill = _isActualSpill;
		}
	}

	private class InterferenceGraph {

		private HashMap<Temp, Vector<Temp>> graph = new HashMap<Temp, Vector<Temp>>();

		private void addEdgeToNode(Temp node1, Temp node2) {
			if (!graph.containsKey(node1)) {
				graph.put(node1, new Vector<Temp>());
				graph.get(node1).add(node2);
			} else {
				graph.get(node1).add(node2);
			}
		}

		public void addEdge(Temp node1, Temp node2) {
			addEdgeToNode(node1, node2);
			addEdgeToNode(node2, node1);
		}

		public Vector<Temp> getEdgesFromNode(Temp node) {
			return graph.get(node);
		}

		public Set<Temp> getAllNodes() {
			return graph.keySet();
		}

		public void RemoveNodeFromGraph(Temp node) {
			graph.remove(node);
			for (Temp n : getAllNodes()) {
				getEdgesFromNode(n).remove(node);
			}
		}

		public int getNodesInGraph() {
			return graph.size();
		}

	}

	private InterferenceGraph BuildPhase(Code code) {
		InterferenceGraph interGraph = new InterferenceGraph();

		for (AsmInstr instr : code.instrs) {

			for (Temp defTemp : instr.defs()) {

				for (Temp outTemp : instr.out()) {
					if (defTemp != outTemp) {
						interGraph.addEdge(defTemp, outTemp);
					}
				}
			}
		}

		return interGraph;
	}

	private Stack<Node> nodeStack = new Stack<>();

	private boolean SimplifyPhase(InterferenceGraph interGraph) {
		boolean change = false;
		Set<Temp> allNodes = interGraph.getAllNodes();

		Vector<Temp> removedNodes = new Vector<>();

		for (Temp node : allNodes) {
			Vector<Temp> connections = interGraph.getEdgesFromNode(node);
			if (connections.size() < Main.numOfRegs) {
				nodeStack.add(new Node(node, connections, false));
				removedNodes.add(node);
				change = true;
			}
		}

		for (Temp node : removedNodes) {
			interGraph.RemoveNodeFromGraph(node);
		}

		return change;
	}

	private void SpillPhase(InterferenceGraph interGraph) {
		Set<Temp> allNodes = interGraph.getAllNodes();
		for (Temp node : allNodes) {
			Vector<Temp> connections = interGraph.getEdgesFromNode(node);
			if (connections.size() >= Main.numOfRegs) {
				nodeStack.add(new Node(node, connections, true));
				interGraph.RemoveNodeFromGraph(node);
			}
		}
	}

	public int GetColor(Node node, Vector<Node> reconstructedGraph) {
		Vector<Boolean> colors = new Vector<Boolean>(Main.numOfRegs);
		for (int i = 0; i < Main.numOfRegs; ++i) {
			colors.add(true);
		}

		for (Temp neigbour : node.connections) {
			int ngbColor = Integer.MAX_VALUE;
			for (Node fNode : reconstructedGraph) {
				if (fNode.getNodeName() == neigbour) {
					ngbColor = fNode.getColor();
				}
			}

			if (ngbColor != Integer.MAX_VALUE) {
				colors.set(ngbColor, false);
			}
		}

		for (int i = 0; i < Main.numOfRegs; ++i) {
			if (colors.get(i)) {
				return i;
			}
		}

		return Integer.MAX_VALUE;
	}

	private Vector<Node> SelectPhase() {
		Vector<Node> reconstructedGraph = new Vector<>();

		while (nodeStack.size() > 0) {
			Node node = nodeStack.pop();
			if (node.getIsPotencialSpill()) {
				int color = GetColor(node, reconstructedGraph);
				node.setColor(color);
				reconstructedGraph.add(node);

				if (color != Integer.MAX_VALUE) {
					// Marking as a spill
					node.setIsActualSpill(true);
				}

			} else {
				int color = GetColor(node, reconstructedGraph);
				node.setColor(color);
				reconstructedGraph.add(node);
			}
		}
		return reconstructedGraph;
	}

	private Code RemapTempToRegs(Code code, Vector<Node> reconstructedGraph) {
		HashMap<Temp, Integer> mapping = new HashMap<>();
		for (Node node : reconstructedGraph) {
			mapping.put(node.nodeName, node.color);
		}

		for (Temp temp : mapping.keySet()) {
			System.out.println("m :" + temp);
		}

		return new Code(code.frame, code.entryLabel, code.exitLabel, code.instrs, mapping, code.tempSize);
	}

	private Code SpillRegs(Code code, Vector<Node> reconstructedGraph) {
		Vector<AsmInstr> newInstructions = new Vector<>();
		for (AsmInstr instr : code.instrs) {
			newInstructions.add(instr);
		}

		long temps = code.tempSize;
		long offset = code.frame.locsSize;
		for (Node node : reconstructedGraph) {
			if (node.isActualSpill) {
				for (AsmInstr instr : code.instrs) {
					if (instr.defs().contains(node.nodeName)) {
						int ind = newInstructions.indexOf(instr);
						newInstructions.get(ind).defs().remove(node.nodeName);

						Temp newTemp = new Temp();
						newInstructions.get(ind).defs().add(newTemp);

						Temp address = new Temp();
						Vector<Temp> defs = new Vector<>();
						defs.add(address);
						newInstructions.insertElementAt(new AsmOPER(kSetConst + ", " + offset, null, defs, null),
								ind + 1);

						offset += 8;
						temps += 1;

						Vector<Temp> uses = new Vector<>();
						uses.add(address);
						uses.add(code.frame.FP);

						newInstructions.insertElementAt(new AsmOPER(kSub, uses, defs, null), ind + 2);

						Vector<Temp> usesStore = new Vector<>();
						uses.add(newTemp);
						uses.add(address);

						newInstructions.insertElementAt(new AsmOPER(kStore, uses, null, null), ind + 3);
					} else if (instr.uses().contains(node.nodeName)) {
						int ind = newInstructions.indexOf(instr);
						newInstructions.get(ind).defs().remove(node.nodeName);

						Temp newTemp = new Temp();
						newInstructions.get(ind).defs().add(newTemp);

						Temp address = new Temp();
						Vector<Temp> defs = new Vector<>();
						defs.add(address);
						newInstructions.insertElementAt(new AsmOPER(kSetConst + ", " + offset, null, defs, null), ind);

						Vector<Temp> uses = new Vector<>();
						uses.add(address);
						uses.add(code.frame.FP);

						newInstructions.insertElementAt(new AsmOPER(kSub, uses, defs, null), ind);

						Vector<Temp> defsLoad = new Vector<>();
						defsLoad.add(newTemp);

						Vector<Temp> usesLoad = new Vector<>();
						usesLoad.add(address);

						newInstructions.insertElementAt(new AsmOPER(kLoad, usesLoad, defsLoad, null), ind);
					}
				}
			}
		}

		Frame newFrame = new Frame(code.frame.label, code.frame.depth, offset, code.frame.argsSize);
		return new Code(newFrame, code.entryLabel, code.exitLabel, newInstructions, code.regs, temps);
	}

	/**
	 * Computes the mapping of temporary variables to registers for each function.
	 * If necessary, the code of each function is modified.
	 */
	public void tempsToRegs() {

		for (int i = 0; i < AsmGen.codes.size(); i++) {
			Code code = AsmGen.codes.get(i);
			while (true) {
				InterferenceGraph interGraph = BuildPhase(code);

				nodeStack = new Stack<>();

				while (true) {

					// Simplify until you can
					while (SimplifyPhase(interGraph)) {
					}

					if (interGraph.getNodesInGraph() > 0) {
						SpillPhase(interGraph);
					}

					if (interGraph.getNodesInGraph() == 0) {
						break;
					}
				}

				Vector<Node> reconstructedGraph = SelectPhase();
				boolean success = true;
				for (Node node : reconstructedGraph) {
					if (node.isActualSpill) {
						success = false;
						break;
					}
				}

				if (success) {
					code = RemapTempToRegs(code, reconstructedGraph);
					AsmGen.codes.set(i, code);

					System.out.println("here");
					for (Temp temp : code.regs.keySet()) {
						System.out.println("h :" + temp);
					}
					System.out.println("ent :" + code.entryLabel.name);
					break;
				} else {
					code = SpillRegs(code, reconstructedGraph);
				}
			}

		}
	}

	public void log() {
		if (logger == null)
			return;
		for (Code code : AsmGen.codes) {
			logger.begElement("code");
			logger.addAttribute("entrylabel", code.entryLabel.name);
			System.out.println("entrylabel :" + code.entryLabel.name);
			logger.addAttribute("exitlabel", code.exitLabel.name);
			logger.addAttribute("tempsize", Long.toString(code.tempSize));
			code.frame.log(logger);
			logger.begElement("instructions");
			for (AsmInstr instr : code.instrs) {
				logger.begElement("instruction");
				for (Temp temp : code.regs.keySet()) {
					System.out.println("s :" + temp);
				}
				logger.addAttribute("code", instr.toString(code.regs));
				logger.endElement();
			}
			logger.endElement();
			logger.endElement();
		}
	}

}
