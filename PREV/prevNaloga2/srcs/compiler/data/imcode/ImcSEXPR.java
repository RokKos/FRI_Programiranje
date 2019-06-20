/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.imcode.visitor.*;

/**
 * Statement expression.
 * 
 * Executes the statement, evaluates the expression and returns its value.
 * 
 * @author sliva
 */
public class ImcSEXPR extends ImcExpr {

	public final ImcStmt stmt;

	public final ImcExpr expr;

	public ImcSEXPR(ImcStmt stmt, ImcExpr expr) {
		this.stmt = stmt;
		this.expr = expr;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
