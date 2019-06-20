/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsUnExpr extends AbsExpr {

	public enum Oper {
		NOT, ADD, SUB, ADDR, DATA,
	}

	public final Oper oper;

	public final AbsExpr subExpr;

	public AbsUnExpr(Locatable location, Oper oper, AbsExpr subExpr) {
		super(location);
		this.oper = oper;
		this.subExpr = subExpr;
	}
	
	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsUnExpr(location, oper, subExpr);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
