/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.imcode.visitor.*;

/**
 * Binary operation (logical, relational, and arithmetic).
 * 
 * Evaluates the first subexpression, evaluates the second expression, and
 * performs the selected binary operation and returns it result.
 * 
 * @author sliva
 */
public class ImcBINOP extends ImcExpr {

	public enum Oper {
		IOR, XOR, AND, EQU, NEQ, LTH, GTH, LEQ, GEQ, ADD, SUB, MUL, DIV, MOD,
	}

	public final Oper oper;

	public final ImcExpr fstExpr;

	public final ImcExpr sndExpr;

	public ImcBINOP(Oper oper, ImcExpr fstExpr, ImcExpr sndExpr) {
		this.oper = oper;
		this.fstExpr = fstExpr;
		this.sndExpr = sndExpr;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
