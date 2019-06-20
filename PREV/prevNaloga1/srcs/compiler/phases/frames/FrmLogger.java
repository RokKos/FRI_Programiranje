/**
 * @author sliva
 */
package compiler.phases.frames;

import compiler.common.logger.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;
import compiler.data.layout.*;

/**
 * The visitor that produces the log of frames and accesses.
 * 
 * This visitor does not traverses the abstract syntax tree. It is used as a
 * subvisitor of {@link compiler.phases.abstr.AbsLogger} and produces XML
 * representation of the computed attributes for each AST node separately (when
 * called by a methods of {@link compiler.phases.abstr.AbsLogger} each time they
 * reach an AST node).
 * 
 * @author sliva
 */
public class FrmLogger extends AbsNullVisitor<Object, Object> {

	/** The logger the log should be written to. */
	private final Logger logger;

	/**
	 * Construct a new visitor with a logger the log should be written to.
	 * 
	 * @param logger The logger the log should be written to.
	 */
	public FrmLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public Object visit(AbsAtomExpr atomExpr, Object visArg) {
		switch (atomExpr.type) {
		case STR: {
			AbsAccess access = Frames.strings.get(atomExpr);
			if (access != null)
				access.log(logger);
			break;
		}
		default:
			break;
		}
		return null;
	}

	@Override
	public Object visit(AbsCompDecl compDecl, Object visArg) {
		Access access = Frames.accesses.get(compDecl);
		if (access != null)
			access.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsFunDef funDef, Object visArg) {
		Frame frame = Frames.frames.get(funDef);
		if (frame != null)
			frame.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsParDecl parDecl, Object visArg) {
		Access access = Frames.accesses.get(parDecl);
		if (access != null)
			access.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsVarDecl varDecl, Object visArg) {
		Access access = Frames.accesses.get(varDecl);
		if (access != null)
			access.log(logger);
		return null;
	}

}
