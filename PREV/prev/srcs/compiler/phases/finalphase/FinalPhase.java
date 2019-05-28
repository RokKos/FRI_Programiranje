/**
 * @author kos
 */
package compiler.phases.finalphase;

import java.util.Vector;

import compiler.Main;
import compiler.data.asmcode.AsmInstr;
import compiler.data.asmcode.AsmLABEL;
import compiler.data.asmcode.AsmOPER;
import compiler.data.asmcode.Code;
import compiler.data.layout.Label;
import compiler.data.layout.Temp;
import compiler.phases.*;
import compiler.phases.asmcode.AsmGen;

public class FinalPhase extends Phase {

	private final String kJump = "JMP ";
	private final String kSetConstPrePendix = "SET $0, ";
	private final String kINCLow = "INCML $0, ";
	private final String kINCMidHigh = "INCMH $0, ";
	private final String kINCHigh = "INCH $0, ";
	private final String kFP = "$253";
	private final String kSP = "$254";

	public FinalPhase() {
		super("final");
	}

	private Vector<AsmInstr> SetConstant(long value) {
		Vector<AsmInstr> instructions = new Vector<>();
		int bits = ((short) value & 0xffff);
		instructions.add(new AsmOPER(kSetConstPrePendix + bits, null, null, null));
		value >>= 16;

		if (value > 0) {
			bits = ((short) value & 0xffff);
			instructions.add(new AsmOPER(kINCLow + bits, null, null, null));
			value >>= 16;
		}

		if (value > 0) {
			bits = ((short) value & 0xffff);
			instructions.add(new AsmOPER(kINCMidHigh + bits, null, null, null));
			value >>= 16;
		}

		if (value > 0) {
			bits = ((short) value & 0xffff);
			instructions.add(new AsmOPER(kINCHigh + bits, null, null, null));
			value >>= 16;
		}

		return instructions;
	}

	private Vector<AsmInstr> CreateProlog(Code code) {
		Vector<AsmInstr> instr = new Vector<>();
		instr.add(new AsmLABEL(code.frame.label));

		// Storing FP
		instr.addAll(SetConstant(code.frame.locsSize));
		instr.add(new AsmOPER("SUB $0, " + kFP + ", $0", null, null, null));
		instr.add(new AsmOPER("STO " + kFP + ", $0, 0", null, null, null));

		// STORING RA
		instr.add(new AsmOPER("GET $1, rJ", null, null, null));
		instr.add(new AsmOPER("STO " + kFP + ", $0, 8", null, null, null));

		instr.add(new AsmOPER("OR " + kFP + ", " + kSP + ", 0", null, null, null));

		instr.addAll(SetConstant(code.frame.size));
		instr.add(new AsmOPER("SUB " + kSP + "," + kSP + ", $0", null, null, null));

		Vector<Label> jumps = new Vector<>();
		jumps.add(code.entryLabel);
		instr.add(new AsmOPER(kJump + code.entryLabel.name, null, null, jumps));
		return instr;
	}

	public void Finish() {
		for (Code code : AsmGen.codes) {
			code.instrs.addAll(0, CreateProlog(code));
		}
	}

	public void log() {
		if (logger == null)
			return;
		for (Code code : AsmGen.codes) {
			logger.begElement("code");
			logger.addAttribute("entrylabel", code.entryLabel.name);
			logger.addAttribute("exitlabel", code.exitLabel.name);
			logger.addAttribute("tempsize", Long.toString(code.tempSize));
			code.frame.log(logger);
			logger.begElement("instructions");
			for (AsmInstr instr : code.instrs) {
				logger.begElement("instruction");
				logger.addAttribute("code", instr.toString(code.regs));
				logger.endElement();
			}
			logger.endElement();
			logger.endElement();
		}
	}

}
