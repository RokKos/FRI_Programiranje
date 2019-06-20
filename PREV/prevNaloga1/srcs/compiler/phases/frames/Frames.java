/**
 * @author sliva
 */
package compiler.phases.frames;

import compiler.data.abstree.*;
import compiler.data.abstree.attribute.*;
import compiler.data.layout.*;
import compiler.phases.*;

/**
 * Computing memory layout, i.e., frames and accesses.
 * 
 * @author sliva
 */
public class Frames extends Phase {

	/** Maps function declarations to frames. */
	public static final AbsAttribute<AbsFunDecl, Frame> frames = new AbsAttribute<AbsFunDecl, Frame>();

	/** Maps variable declarations to accesses. */
	public static final AbsAttribute<AbsVarDecl, Access> accesses = new AbsAttribute<AbsVarDecl, Access>();

	/** Maps string constants to accesses. */
	public static final AbsAttribute<AbsAtomExpr, AbsAccess> strings = new AbsAttribute<AbsAtomExpr, AbsAccess>();
	
	/**
	 * Constructs a new phase for computing layout.
	 */
	public Frames() {
		super("frames");
	}

}
