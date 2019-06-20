/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsBinExpr extends AbsExpr {

	public enum Oper {
		IOR, XOR, AND, EQU, NEQ, LTH, GTH, LEQ, GEQ, ADD, SUB, MUL, DIV, MOD,
	}

	public final Oper oper;

	public final AbsExpr fstExpr;

	public final AbsExpr sndExpr;

	public AbsBinExpr(Locatable location, Oper oper, AbsExpr fstExpr, AbsExpr sndExpr) {
		super(location);
		this.oper = oper;
		this.fstExpr = fstExpr;
		this.sndExpr = sndExpr;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsBinExpr(location, oper, fstExpr, sndExpr);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
