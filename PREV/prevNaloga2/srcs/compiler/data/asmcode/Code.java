/**
 * @author sliva
 */
package compiler.data.asmcode;

import java.util.*;
import compiler.data.layout.*;

/**
 * A fragment of code.
 * 
 * @author sliva
 */
public class Code {

	/** The funtion's frame. */
	public final Frame frame;

	/**
	 * The function's body entry label, i.e., the label the prologue jumps to.
	 */
	public final Label entryLabel;

	/**
	 * The function's body exit label, i.e., the label at which the epilogue starts.
	 */
	public final Label exitLabel;

	/** Assembly instructions representing the bofy of the function. */
	public final Vector<AsmInstr> instrs;

	/**
	 * Mapping of temporary variables to registers.
	 */
	public final HashMap<Temp, Integer> regs;

	/**
	 * The size of all temporaries in the frame.
	 */
	public final long tempSize;

	/**
	 * Creates a new fragment of code.
	 * 
	 * @param frame      The funtion's frame.
	 * @param entryLabel The label the prologue jumps to.
	 * @param exitLabel  The label at which the epilogue starts.
	 * @param instrs     Assembly instructions representing the body of the
	 *                   function.
	 */
	public Code(Frame frame, Label entryLabel, Label exitLabel, Vector<AsmInstr> instrs) {
		this.frame = frame;
		this.entryLabel = entryLabel;
		this.exitLabel = exitLabel;
		this.instrs = new Vector<AsmInstr>(instrs);
		this.regs = null;
		this.tempSize = 0;
	}

	/**
	 * Creates a new fragment of code.
	 * 
	 * @param frame      The funtion's frame.
	 * @param entryLabel The label the prologue jumps to.
	 * @param exitLabel  The label at which the epilogue starts.
	 * @param instrs     Assembly instructions representing the body of the
	 *                   function.
	 * @param regs       Mapping of temporary variables to registers.
	 * @param tempSize   The size of all temporaries in the frame.
	 */
	public Code(Frame frame, Label entryLabel, Label exitLabel, Vector<AsmInstr> instrs, HashMap<Temp, Integer> regs,
			long tempSize) {
		this.frame = frame;
		this.entryLabel = entryLabel;
		this.exitLabel = exitLabel;
		this.instrs = new Vector<AsmInstr>(instrs);
		this.regs = regs;
		this.tempSize = tempSize;
	}

}
