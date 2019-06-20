/**
 * @author sliva
 */
package compiler.phases.abstr;

import compiler.data.abstree.*;
import compiler.phases.*;

/**
 * Abstract syntax.
 * 
 * @author sliva
 */
public class Abstr extends Phase {

	/** The abstract syntax tree of the program being compiled. */
	public static AbsTree absTree = null;

	/**
	 * Constructs a new phase of abstract syntax.
	 */
	public Abstr() {
		super("abstr");
	}

}
