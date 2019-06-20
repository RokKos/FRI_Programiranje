/**
 * @author sliva
 */
package compiler.phases.chunks;

import java.util.*;
import compiler.common.report.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;
import compiler.data.chunk.*;

/**
 * @author sliva
 */
public class Interpreter {

	private boolean debug = false;

	private Random random;

	private HashMap<Long, Byte> memory;

	private HashMap<Temp, Long> temps;

	private HashMap<Label, Long> dataLabels;

	private HashMap<Label, Integer> jumpLabels;

	private HashMap<Label, CodeChunk> callLabels;

	private Temp SP;

	private Temp FP;

	private Temp RV;

	private Temp HP;

	public Interpreter(Vector<DataChunk> dataChunks, Vector<CodeChunk> codeChunks) {
		random = new Random();

		this.memory = new HashMap<Long, Byte>();
		this.temps = new HashMap<Temp, Long>();

		SP = new Temp();
		tempST(SP, 0xFFFFFFFFFFFFFFF0l);
		HP = new Temp();
		tempST(HP, 0x2000000000000000l);

		this.dataLabels = new HashMap<Label, Long>();
		for (DataChunk dataChunk : dataChunks) {
			if (debug) {
				System.out.printf("### %s @ %d\n", dataChunk.label.name, tempLD(HP, false));
			}
			this.dataLabels.put(dataChunk.label, tempLD(HP, false));
			if (dataChunk.init != null) {
				for (int c = 0; c < dataChunk.init.length() - 2; c++)
					memST(tempLD(HP, false) + 8 * c, (long) dataChunk.init.charAt(c + 1), false);
				memST(tempLD(HP, false) + 8 * (dataChunk.init.length() - 2), 0L, false);
			}
			tempST(HP, tempLD(HP, false) + dataChunk.size, true);
		}
		if (debug)
			System.out.printf("###\n");

		this.jumpLabels = new HashMap<Label, Integer>();
		this.callLabels = new HashMap<Label, CodeChunk>();
		for (CodeChunk codeChunk : codeChunks) {
			this.callLabels.put(codeChunk.frame.label, codeChunk);
			Vector<ImcStmt> stmts = codeChunk.stmts();
			for (int stmtOffset = 0; stmtOffset < stmts.size(); stmtOffset++) {
				if (stmts.get(stmtOffset) instanceof ImcLABEL)
					jumpLabels.put(((ImcLABEL) stmts.get(stmtOffset)).label, stmtOffset);
			}
		}
	}

	private void memST(Long address, Long value) {
		memST(address, value, debug);
	}

	private void memST(Long address, Long value, boolean debug) {
		if (debug)
			System.out.printf("### [%d] <- %d\n", address, value);
		for (int b = 0; b <= 7; b++) {
			long longval = value % 0x100;
			byte byteval = (byte) longval;
			memory.put(address + b, byteval);
			value = value >> 8;
		}
	}

	private Long memLD(Long address) {
		return memLD(address, debug);
	}

	private Long memLD(Long address, boolean debug) {
		Long value = 0L;
		for (int b = 7; b >= 0; b--) {
			Byte byteval = memory.get(address + b);
			if (byteval == null) {
				byteval = (byte) (random.nextLong() / 0x100);
				throw new Report.Error("INTERPRETER: Uninitialized memory location " + (address + b) + ".");
			}
			long longval = (long) byteval;
			value = (value * 0x100) + (longval < 0 ? longval + 0x100 : longval);
		}
		if (debug)
			System.out.printf("### %d <- [%d]\n", value, address);
		return value;
	}

	private void tempST(Temp temp, Long value) {
		tempST(temp, value, debug);
	}

	private void tempST(Temp temp, Long value, boolean debug) {
		temps.put(temp, value);
		if (debug) {
			if (temp == SP) {
				System.out.printf("### SP <- %d\n", value);
				return;
			}
			if (temp == FP) {
				System.out.printf("### FP <- %d\n", value);
				return;
			}
			if (temp == RV) {
				System.out.printf("### RV <- %d\n", value);
				return;
			}
			if (temp == HP) {
				System.out.printf("### HP <- %d\n", value);
				return;
			}
			System.out.printf("### T%d <- %d\n", temp.temp, value);
			return;
		}
	}

	private Long tempLD(Temp temp) {
		return tempLD(temp, debug);
	}

	private Long tempLD(Temp temp, boolean debug) {
		Long value = temps.get(temp);
		if (value == null) {
			value = random.nextLong();
			throw new Report.Error("Uninitialized temporary variable T" + temp.temp + ".");
		}
		if (debug) {
			if (temp == SP) {
				System.out.printf("### %d <- SP\n", value);
				return value;
			}
			if (temp == FP) {
				System.out.printf("### %d <- FP\n", value);
				return value;
			}
			if (temp == RV) {
				System.out.printf("### %d <- RV\n", value);
				return value;
			}
			if (temp == HP) {
				System.out.printf("### %d <- HP\n", value);
				return value;
			}
			System.out.printf("### %d <- T%d\n", value, temp.temp);
			return value;
		}
		return value;
	}

	private class ExprInterpreter implements ImcVisitor<Long, Object> {

		@Override
		public Long visit(ImcBINOP imcBinop, Object arg) {
			Long fstExpr = imcBinop.fstExpr.accept(this, null);
			Long sndExpr = imcBinop.sndExpr.accept(this, null);
			switch (imcBinop.oper) {
			case IOR:
				return (fstExpr != 0) | (sndExpr != 0) ? 1L : 0L;
			case XOR:
				return (fstExpr != 0) ^ (sndExpr != 0) ? 1L : 0L;
			case AND:
				return (fstExpr != 0) & (sndExpr != 0) ? 1L : 0L;
			case EQU:
				return (fstExpr == sndExpr) ? 1L : 0L;
			case NEQ:
				return (fstExpr != sndExpr) ? 1L : 0L;
			case LEQ:
				return (fstExpr <= sndExpr) ? 1L : 0L;
			case GEQ:
				return (fstExpr >= sndExpr) ? 1L : 0L;
			case LTH:
				return (fstExpr < sndExpr) ? 1L : 0L;
			case GTH:
				return (fstExpr > sndExpr) ? 1L : 0L;
			case ADD:
				return fstExpr + sndExpr;
			case SUB:
				return fstExpr - sndExpr;
			case MUL:
				return fstExpr * sndExpr;
			case DIV:
				return fstExpr / sndExpr;
			case MOD:
				return fstExpr % sndExpr;
			}
			throw new Report.InternalError();
		}

		@Override
		public Long visit(ImcCALL imcCall, Object arg) {
			throw new Report.InternalError();
		}

		@Override
		public Long visit(ImcCONST imcConst, Object arg) {
			return imcConst.value;
		}

		@Override
		public Long visit(ImcMEM imcMem, Object arg) {
			if (debug) {
				System.out.printf("###ROK Mem acc: %s\n", imcMem.addr);
			}
			return memLD(imcMem.addr.accept(this, null));
		}

		@Override
		public Long visit(ImcNAME imcName, Object arg) {
			return dataLabels.get(imcName.label);
		}

		@Override
		public Long visit(ImcSEXPR imcSExpr, Object arg) {
			throw new Report.InternalError();
		}

		@Override
		public Long visit(ImcTEMP imcTemp, Object arg) {
			return tempLD(imcTemp.temp);
		}

		@Override
		public Long visit(ImcUNOP imcUnop, Object arg) {
			Long subExpr = imcUnop.subExpr.accept(this, null);
			switch (imcUnop.oper) {
			case NOT:
				return (subExpr == 0) ? 1L : 0L;
			case NEG:
				return -subExpr;
			}
			throw new Report.InternalError();
		}

	}

	private class StmtInterpreter implements ImcVisitor<Label, Object> {

		@Override
		public Label visit(ImcCJUMP imcCJump, Object arg) {
			if (debug)
				System.out.println(imcCJump);
			Long cond = imcCJump.cond.accept(new ExprInterpreter(), null);
			return (cond != 0) ? imcCJump.posLabel : imcCJump.negLabel;
		}

		@Override
		public Label visit(ImcESTMT imcEStmt, Object arg) {
			if (debug)
				System.out.println(imcEStmt);
			if (imcEStmt.expr instanceof ImcCALL) {
				if (debug) {
					ImcCALL funCall = (ImcCALL) imcEStmt.expr;
					System.out.println("###ROK Function Call: " + funCall.label.name);
				}
				call((ImcCALL) imcEStmt.expr);
				return null;
			}
			imcEStmt.expr.accept(new ExprInterpreter(), null);
			return null;
		}

		@Override
		public Label visit(ImcJUMP imcJump, Object arg) {
			if (debug)
				System.out.println(imcJump);
			return imcJump.label;
		}

		@Override
		public Label visit(ImcLABEL imcLabel, Object arg) {
			if (debug)
				System.out.println(imcLabel);
			return null;
		}

		@Override
		public Label visit(ImcMOVE imcMove, Object arg) {
			if (debug)
				System.out.println(imcMove);
			if (imcMove.dst instanceof ImcMEM) {
				Long dst = ((ImcMEM) (imcMove.dst)).addr.accept(new ExprInterpreter(), null);
				Long src;
				if (imcMove.src instanceof ImcCALL) {
					call((ImcCALL) imcMove.src);
					if (debug) {
						System.out.printf("###ROK Src acc: %s\n", imcMove.src.toString());
					}
					src = memLD(tempLD(SP));
				} else
					src = imcMove.src.accept(new ExprInterpreter(), null);
				memST(dst, src);
				return null;
			}
			if (imcMove.dst instanceof ImcTEMP) {
				ImcTEMP dst = (ImcTEMP) (imcMove.dst);
				Long src;
				if (imcMove.src instanceof ImcCALL) {
					call((ImcCALL) imcMove.src);
					if (debug) {
						System.out.printf("###ROK Dest acc: %s\n", imcMove.dst.toString());
					}
					src = memLD(tempLD(SP));
				} else
					src = imcMove.src.accept(new ExprInterpreter(), null);
				tempST(dst.temp, src);
				return null;
			}
			throw new Report.InternalError();
		}

		@Override
		public Label visit(ImcSTMTS imcStmts, Object arg) {
			if (debug)
				System.out.println(imcStmts);
			throw new Report.InternalError();
		}

		private void call(ImcCALL imcCall) {
			Long offset = 0L;
			for (ImcExpr callArg : imcCall.args()) {
				Long callValue = callArg.accept(new ExprInterpreter(), null);
				memST(tempLD(SP) + offset, callValue);
				offset += 8;
			}
			if (imcCall.label.name.equals("_new")) {
				if (debug) {
					System.out.println("###ROK Call new allocation");
				}

				Long size = memLD(tempLD(SP, false) + 1 * 8, false);
				Long addr = tempLD(HP);
				tempST(HP, addr + size);
				memST(tempLD(SP), addr, false);
				return;
			}
			if (imcCall.label.name.equals("_del")) {
				return;
			}
			if (imcCall.label.name.equals("_putInt")) {
				Long i = memLD(tempLD(SP, false) + 1 * 8, false);
				System.out.printf("%d", i);
				return;
			}
			if (imcCall.label.name.equals("_putChar")) {
				Long c = memLD(tempLD(SP, false) + 1 * 8, false);
				System.out.printf("%c", (char) ((long) c) % 0x100);
				return;
			}
			if (imcCall.label.name.equals("_putString")) {
				if (debug) {
					System.out.println("###ROK Put String");
				}

				Long addr = memLD(tempLD(SP, false) + 1 * 8, false);
				do {
					long c = memLD(addr, false);
					addr += 8;
					if (c == 0)
						break;
					System.out.printf("%c", (char) c);
				} while (true);
				return;
			}
			funCall(imcCall.label);
		}

	}

	public void funCall(Label entryLabel) {

		HashMap<Temp, Long> storedTemps;
		Temp storedFP = null;
		Temp storedRV = null;

		CodeChunk chunk = callLabels.get(entryLabel);
		Frame frame = chunk.frame;
		Vector<ImcStmt> stmts = chunk.stmts();
		int stmtOffset;

		/* PROLOGUE */
		{
			if (debug)
				System.out.printf("###\n### CALL: %s\n", entryLabel.name);

			// Store registers and FP.
			storedTemps = temps;
			temps = new HashMap<Temp, Long>(temps);
			// Store RA.
			// Create a stack frame.
			FP = frame.FP;
			RV = frame.RV;
			tempST(frame.FP, tempLD(SP));
			tempST(SP, tempLD(SP) - frame.size);
			// Jump to the body.
			stmtOffset = jumpLabels.get(chunk.entryLabel);
		}

		/* BODY */
		{
			int pc = 0;
			Label label = null;

			while (label != chunk.exitLabel) {
				if (debug) {
					pc++;
					System.out.printf("### %s (%d):\n", chunk.frame.label.name, pc);
					if (pc == 1000000)
						break;
				}

				if (label != null) {
					Integer offset = jumpLabels.get(label);
					if (offset == null)
						throw new Report.InternalError();
					stmtOffset = offset;
				}

				label = stmts.get(stmtOffset).accept(new StmtInterpreter(), null);

				stmtOffset += 1;
			}
		}

		/* EPILOGUE */
		{
			// Store the result.
			memST(tempLD(frame.FP), tempLD(frame.RV));
			// Destroy a stack frame.
			tempST(SP, tempLD(SP) + frame.size);
			// Restore registers and FP.
			FP = storedFP;
			RV = storedRV;
			Long hp = tempLD(HP);
			temps = storedTemps;
			tempST(HP, hp);
			// Restore RA.
			// Return.

			if (debug)
				System.out.printf("### RETURN: %s\n###\n", entryLabel.name);
		}

	}

	public long run(String entryLabel) {
		for (Label label : callLabels.keySet()) {
			if (label.name.equals(entryLabel)) {
				funCall(label);
				return memLD(tempLD(SP));
			}
		}
		throw new Report.InternalError();
	}

}
