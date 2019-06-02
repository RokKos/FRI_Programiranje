/**
 * @author kos
 */
package compiler.phases.finalphase;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Vector;

import compiler.Main;
import compiler.common.report.Report;
import compiler.data.asmcode.AsmInstr;
import compiler.data.asmcode.AsmLABEL;
import compiler.data.asmcode.AsmOPER;
import compiler.data.asmcode.Code;
import compiler.data.chunk.DataChunk;
import compiler.data.layout.Frame;
import compiler.data.layout.Label;
import compiler.data.layout.Temp;
import compiler.phases.*;
import compiler.phases.asmcode.AsmGen;
import compiler.phases.chunks.Chunks;

public class FinalPhase extends Phase {

	private final String kJump = "JMP ";
	private final String kSetConstPrePendix = "SET $0,";
	private final String kINCLow = "INCML $0,";
	private final String kINCMidHigh = "INCMH $0,";
	private final String kINCHigh = "INCH $0,";
	private final String kFP = "FP";// "$253";
	private final String kSP = "SP";// "$254";

	public FinalPhase() {
		super("final");
	}

	private Vector<String> AsmInstructionToString(Vector<AsmInstr> instructions) {
		Vector<String> strings = new Vector<>();

		for (AsmInstr instr : instructions) {
			if (instr instanceof AsmLABEL) {
				strings.add(instr.toAsemblerCode(null));
			} else {
				strings.add(instr.toAsemblerCode(null) + '\n');
			}
		}

		return strings;
	}

	private Vector<String> CreatePutChar() {
		Vector<String> putCharCode = new Vector<>();
		Label putCharLabel = new Label("putChar");
		Label entryLabel = new Label();
		Label exitLabel = new Label();
		Code code = new Code(new Frame(putCharLabel, 0, 0, 8), entryLabel, exitLabel, new Vector<>(), null, 0);

		putCharCode.add("% Code for function: _putChar\n");
		putCharCode.addAll(AsmInstructionToString(CreateProlog(code)));

		putCharCode.add(entryLabel.name + "\tSET\t$0,14\n");
		putCharCode.add("\tADD\t$0,FP,$0\n");

		putCharCode.add("\t%Putting char one position in front\n");
		putCharCode.add("\t%so that we put end char at the end\n");
		putCharCode.add("\tLDB\t$1,$0,1\n");
		putCharCode.add("\tSTB\t$1,$0,0\n");

		putCharCode.add("\tSET\t$1,0\n");
		putCharCode.add("\tSTB\t$1,$0,1\n");

		putCharCode.add("\tSET\t$255,$0\n");
		putCharCode.add("\tTRAP\t0,Fputs,StdOut\n");

		putCharCode.addAll(AsmInstructionToString(CreateEpilogue(code)));

		return putCharCode;
	}

	private Vector<String> CreatePutString() {
		Vector<String> putStringCode = new Vector<>();
		Label putStringLabel = new Label("putString");
		Label entryLabel = new Label();
		Label exitLabel = new Label();
		Code code = new Code(new Frame(putStringLabel, 0, 0, 8), entryLabel, exitLabel, new Vector<>(), null, 0);

		putStringCode.add("% Code for function: _putString\n");
		putStringCode.addAll(AsmInstructionToString(CreateProlog(code)));

		putStringCode.add(entryLabel.name + "\tSET\t$0,8\n");
		putStringCode.add("\tADD\t$0,FP,$0\n");
		putStringCode.add("\tLDO\t$1,$0,0\n");
		putStringCode.add("\tSET\t$255,$1\n");
		putStringCode.add("\tTRAP\t0,Fputs,StdOut\n");

		putStringCode.addAll(AsmInstructionToString(CreateEpilogue(code)));

		return putStringCode;
	}

	private Vector<String> GetFunctionLocalVariableAddress(int offset) {
		Vector<String> varCode = new Vector<>();

		varCode.add("\tSET\t$0," + offset + "\n");
		varCode.add("\tSUB\t$0,FP,$0\n");

		return varCode;
	}

	private Vector<String> GetFunctionArgVariableAddress(int offset) {
		Vector<String> varCode = new Vector<>();

		varCode.add("\tSET\t$0," + offset + "\n");
		varCode.add("\tADD\t$0,$0,FP\n");

		return varCode;
	}

	private Vector<String> CompareWith(int comparator) {
		Vector<String> varCode = new Vector<>();
		varCode.add("\tSET\t$1," + comparator + "\n");
		varCode.add("\tCMP\t$0,$0,$1\n");
		varCode.add("\tZSP\t$0,$0,1\n");

		return varCode;

	}

	private Vector<String> CreatePutInt() {
		Vector<String> putIntCode = new Vector<>();
		Label putIntLabel = new Label("putInt");
		Label entryLabel = new Label();
		Label exitLabel = new Label();
		Code code = new Code(new Frame(putIntLabel, 1, 16, 16), entryLabel, exitLabel, new Vector<>(), null, 0);

		putIntCode.add("% Code for function: _putInt\n");
		putIntCode.addAll(AsmInstructionToString(CreateProlog(code)));

		putIntCode.add("% Storing inverse number\n");
		putIntCode.add(entryLabel.name);
		putIntCode.addAll(GetFunctionLocalVariableAddress(16));
		putIntCode.add("\tSET\t$1,0\n");
		putIntCode.add("\tSTO\t$1,$0,0\n");

		putIntCode.add("% While condition of inverse loop\n");
		putIntCode.add("_putInt_Inverse_Loop_");
		putIntCode.addAll(GetFunctionArgVariableAddress(8));
		putIntCode.add("\tLDO\t$0,$0,0\n");

		CompareWith(0);

		putIntCode.add("\tBZ\t$0,_putInt_Print_out_loop\n");

		putIntCode.add("% While loop of inverse loop\n");
		putIntCode.addAll(GetFunctionLocalVariableAddress(16));
		putIntCode.add("\tLDO\t$2,$0,0\n");
		putIntCode.add("\tMUL\t$2,$2,10 % Multipling inverse num\n");

		putIntCode.addAll(GetFunctionArgVariableAddress(8));
		putIntCode.add("\tLDO\t$3,$0,0\n");
		putIntCode.add("\tDIV\t$3,$3,10\n");
		putIntCode.add("\tSTO\t$3,$0,0 % Storing N\n");

		putIntCode.add("\tGET\t$1,rR\n");
		putIntCode.add("\tADD\t$2,$2,$1\n");

		putIntCode.addAll(GetFunctionLocalVariableAddress(16));
		putIntCode.add("\tSTO\t$2,$0,0\n");
		putIntCode.add("\tJMP\t_putInt_Inverse_Loop_\n");

		putIntCode.add("% While condition of print loop\n");
		putIntCode.add("_putInt_Print_out_loop");
		putIntCode.addAll(GetFunctionLocalVariableAddress(16));
		putIntCode.add("\tLDO\t$0,$0,0\n");

		CompareWith(0);

		putIntCode.add("\tBZ\t$0,_putInt_Print_out_end\n");

		putIntCode.addAll(GetFunctionLocalVariableAddress(16));
		putIntCode.add("\tLDO\t$1,$0,0\n");
		putIntCode.add("\tDIV\t$1,$1,10\n");
		putIntCode.add("\tGET\t$2,rR\n");

		putIntCode.add("\tSTO\t$1,$0,0\n");

		putIntCode.add("\tADD\t$2,$2,48\n");
		putIntCode.add("\tSTO\t$2,$254,8\n");
		putIntCode.add("\tPUSHJ\t$8,_putChar\n");
		putIntCode.add("\tJMP\t_putInt_Print_out_loop\n");
		putIntCode.add("_putInt_Print_out_end\tJMP\t" + exitLabel.name);

		putIntCode.addAll(AsmInstructionToString(CreateEpilogue(code)));

		return putIntCode;
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

	private boolean IsMain(Code code) {
		return code.frame.label.name.equals("_main");
	}

	private Vector<AsmInstr> CreateProlog(Code code) {
		Vector<AsmInstr> instr = new Vector<>();
		instr.add(new AsmOPER("% --- Prolog ---", null, null, null));
		instr.add(new AsmLABEL(code.frame.label));

		// Storing FP
		instr.addAll(SetConstant(code.frame.locsSize + 2 * 8));
		instr.add(new AsmOPER("% Storing FP ", null, null, null));
		instr.add(new AsmOPER("SUB $0," + kSP + ",$0", null, null, null));
		instr.add(new AsmOPER("STO " + kFP + ",$0,0", null, null, null));

		// STORING RA
		instr.add(new AsmOPER("% STORING RA ", null, null, null));
		instr.add(new AsmOPER("GET $1,rJ", null, null, null));
		instr.add(new AsmOPER("STO $1,$0,8", null, null, null));

		instr.add(new AsmOPER("% Lowering FP ", null, null, null));
		instr.add(new AsmOPER("SET " + kFP + "," + kSP, null, null, null));

		instr.add(new AsmOPER("% Lowering SP ", null, null, null));
		instr.addAll(SetConstant(code.frame.size + code.tempSize));
		instr.add(new AsmOPER("SUB " + kSP + "," + kSP + ",$0", null, null, null));

		Vector<Label> jumps = new Vector<>();
		jumps.add(code.entryLabel);
		instr.add(new AsmOPER(kJump + code.entryLabel.name, null, null, jumps));
		return instr;
	}

	private Vector<AsmInstr> CreateEpilogue(Code code) {
		Vector<AsmInstr> instr = new Vector<>();
		instr.add(new AsmOPER("% --- Epilogue ---", null, null, null));
		instr.add(new AsmLABEL(code.exitLabel));

		// Save return value
		instr.add(new AsmOPER("STO $0," + kFP + ",0  % Save return value ", null, null, null));

		// Highering Stack pointer
		instr.add(new AsmOPER("% Highering Stack pointer ", null, null, null));
		instr.add(new AsmOPER("SET " + kSP + "," + kFP, null, null, null));
		// instr.addAll(SetConstant(code.frame.size + code.tempSize));
		// instr.add(new AsmOPER("ADD " + kSP + "," + kSP + ",$0", null, null, null));

		instr.add(new AsmOPER("% Getting RA ", null, null, null));
		instr.addAll(SetConstant(code.frame.locsSize + 2 * 8));
		instr.add(new AsmOPER("SUB $0," + kSP + ",$0", null, null, null));

		instr.add(new AsmOPER("LDO $1,$0,8", null, null, null));
		instr.add(new AsmOPER("PUT rJ,$1", null, null, null));

		instr.add(new AsmOPER("% Getting old FP ", null, null, null));
		instr.add(new AsmOPER("LDO " + kFP + ",$0,0", null, null, null));
		instr.add(new AsmOPER("POP " + Main.numOfRegs + ",0", null, null, null));

		return instr;

	}

	public void Finish() {
		for (Code code : AsmGen.codes) {
			code.instrs.addAll(0, CreateProlog(code));
			code.instrs.addAll(CreateEpilogue(code));
		}

		Vector<String> bootstrapCode = new Vector<>();
		bootstrapCode.add("% Code generated by PREV compiler");

		bootstrapCode.add("SP\tGREG\tStack_Segment");
		bootstrapCode.add("FP\tGREG\t#6100000000000000");
		bootstrapCode.add("HP\tGREG\tData_Segment");

		bootstrapCode.add("\tLOC\tData_Segment");
		for (DataChunk data : Chunks.dataChunks) {
			String string_data = data.init != null ? data.init + ",0" : "0";
			bootstrapCode.add(data.label.name + "\tBYTE\t" + string_data);
		}

		bootstrapCode.add("% Code Segment");
		bootstrapCode.add("\tLOC\t#500");
		bootstrapCode.add("Main\tPUSHJ\t$" + Main.numOfRegs + ",_main");

		bootstrapCode.add("% STOPPING PROGRAM");
		bootstrapCode.add("\tTRAP\t0,Halt,0");

		try {
			OutputStream os = new FileOutputStream(new File(Main.cmdLineArgValue("--dst-file-name")));

			for (String line : bootstrapCode) {
				os.write((line + "\n").getBytes());
			}

			boolean previousLabel = false;
			for (Code code : AsmGen.codes) {
				os.write(("% Code for function: " + code.frame.label.name + "\n").getBytes());
				for (AsmInstr instr : code.instrs) {
					if (instr instanceof AsmLABEL) {
						if (previousLabel) {
							os.write(("\tSWYM\t0,4,2 %Two labels one after another\n").getBytes());
							os.write((instr.toAsemblerCode(code.regs)).getBytes());
						} else {
							os.write((instr.toAsemblerCode(code.regs)).getBytes());
						}

						previousLabel = true;
					} else {
						os.write((instr.toAsemblerCode(code.regs) + "\n").getBytes());
						previousLabel = false;
					}
				}

			}

			for (String putCharInstruction : CreatePutChar()) {
				os.write(putCharInstruction.getBytes());
			}

			for (String putCharInstruction : CreatePutString()) {
				os.write(putCharInstruction.getBytes());
			}

			for (String putCharInstruction : CreatePutInt()) {
				os.write(putCharInstruction.getBytes());
			}

			os.close();
		} catch (IOException e) {
			throw new Report.Error(e.toString());
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
