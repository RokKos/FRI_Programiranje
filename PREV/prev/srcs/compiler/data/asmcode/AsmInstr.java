/**
 * @author sliva
 */
package compiler.data.asmcode;

import java.util.*;
import compiler.data.layout.*;

/**
 * An assembly instruction (operation or label).
 * 
 * @author sliva
 *
 */
public abstract class AsmInstr {

	/**
	 * A list of temporaries used by this instruction.
	 * 
	 * @return The list of temporaries used by this instruction.
	 */
	public abstract Vector<Temp> uses();

	/**
	 * Returns the list of temporaries defined by this instruction.
	 * 
	 * @return The list of temporaries defined by this instruction.
	 */
	public abstract Vector<Temp> defs();

	/**
	 * Returns the list of labels this instruction can jump to.
	 * 
	 * @returnThe list of labels this instruction can jump to.
	 */
	public abstract Vector<Label> jumps();

	/**
	 * Returns a string representing this instruction with temporaries.
	 * 
	 * @return A string representing this instruction with temporaries.
	 */
	public abstract String toString();

	/**
	 * Returns the set of temporaries that are live in the control flow graph edges
	 * leading to this instruction.
	 * 
	 * @return The set of temporaries that are live in the control flow graph edges
	 *         leading to this instruction.
	 */
	public abstract HashSet<Temp> in();

	/**
	 * Returns the set of temporaries that are live in the control flow graph edges
	 * leading from this instruction.
	 * 
	 * @return The set of temporaries that are live in the control flow graph edges
	 *         leading from this instruction.
	 */
	public abstract HashSet<Temp> out();

	/**
	 * Adds a set of temporaries to the set of temporaries that are live in the
	 * control flow graph edges leading to this instruction.
	 * 
	 * @param in A set of temporaries to be added.
	 */
	public abstract void addInTemps(HashSet<Temp> in);

	/**
	 * Adds a set of temporaries to the set of temporaries that are live in the
	 * control flow graph edges leading from this instruction.
	 * 
	 * @param out A set of temporaries to be added.
	 */
	public abstract void addOutTemp(HashSet<Temp> out);

	/**
	 * Returns a string representing this instruction with registers.
	 * 
	 * @param regs A mapping of temporaries to registers.
	 * @return A a string representing this instruction with registers.
	 */
	public abstract String toString(HashMap<Temp, Integer> regs);

}
