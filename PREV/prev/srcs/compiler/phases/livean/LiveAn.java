/**
 * @author sliva
 */
package compiler.phases.livean;

import java.util.*;
import compiler.data.asmcode.*;
import compiler.data.layout.*;
import compiler.phases.*;
import compiler.phases.asmcode.*;

/**
 * @author sliva
 */
public class LiveAn extends Phase {

	public LiveAn() {
		super("livean");
	}

	public void chunkLiveness(Code code) {
		boolean stable = false;
		while (!stable) {
			stable = true;
			for (int i = 0; i < code.instrs.size(); ++i) {
				AsmInstr instruction = code.instrs.get(i);
				HashSet<Temp> _in = new HashSet<>();
				_in.addAll(instruction.in());
				HashSet<Temp> _out = new HashSet<>();
				_out.addAll(instruction.out());

				HashSet<Temp> new_in = new HashSet<Temp>();
				new_in.addAll(instruction.uses());
				HashSet<Temp> temp_out = new HashSet<Temp>();

				// out(n) \ def(n)
				temp_out.addAll(instruction.out());
				temp_out.removeAll(instruction.defs());

				new_in.addAll(temp_out);

				instruction.addInTemps(new_in);

				HashSet<Temp> new_out = new HashSet<Temp>();
				if (i < code.instrs.size() - 1) {
					new_out.addAll(code.instrs.get(i + 1).in());
				}

				instruction.addOutTemp(new_out);

				if (!(_in.equals(new_in) && _out.equals(new_out))) {
					stable = false;
				}

			}
		}

	}

	public void chunksLiveness() {
		for (Code code : AsmGen.codes) {
			chunkLiveness(code);
		}
	}

	public void log() {
		if (logger == null)
			return;
		for (Code code : AsmGen.codes) {
			{
				logger.begElement("code");
				logger.addAttribute("entrylabel", code.entryLabel.name);
				logger.addAttribute("exitlabel", code.exitLabel.name);
				logger.addAttribute("tempsize", Long.toString(code.tempSize));
				code.frame.log(logger);
				logger.begElement("instructions");
				for (AsmInstr instr : code.instrs) {
					logger.begElement("instruction");
					logger.addAttribute("code", instr.toString());
					logger.begElement("temps");
					logger.addAttribute("name", "use");
					for (Temp temp : instr.uses()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.begElement("temps");
					logger.addAttribute("name", "def");
					for (Temp temp : instr.defs()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.begElement("temps");
					logger.addAttribute("name", "in");
					for (Temp temp : instr.in()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.begElement("temps");
					logger.addAttribute("name", "out");
					for (Temp temp : instr.out()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.endElement();
				}
				logger.endElement();
				logger.endElement();
			}
		}
	}

}
