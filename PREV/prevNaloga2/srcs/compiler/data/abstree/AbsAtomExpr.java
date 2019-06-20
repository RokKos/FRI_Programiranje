/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsAtomExpr extends AbsExpr {

	public enum Type {
		VOID, BOOL, CHAR, INT, PTR, STR,
	}

	public final Type type;

	public final String expr;

	public AbsAtomExpr(Locatable location, Type type, String expr) {
		super(location);
		this.type = type;
		this.expr = expr;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsAtomExpr(location, type, expr);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
