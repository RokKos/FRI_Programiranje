/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsDelExpr extends AbsExpr {

	public final AbsExpr expr;

	public AbsDelExpr(Locatable location, AbsExpr expr) {
		super(location);
		this.expr = expr;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsDelExpr(location, expr);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
