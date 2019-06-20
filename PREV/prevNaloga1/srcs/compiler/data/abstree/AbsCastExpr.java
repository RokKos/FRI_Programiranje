/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsCastExpr extends AbsExpr {

	public final AbsExpr expr;

	public final AbsType type;

	public AbsCastExpr(Locatable location, AbsExpr expr, AbsType type) {
		super(location);
		this.expr = expr;
		this.type = type;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsCastExpr(location, expr, type);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
