/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.layout.*;
import compiler.data.imcode.visitor.*;

/**
 * Conditional jump.
 * 
 * Evaluates the condition, jumps to the positive label if the condition is
 * nonzero or to the negative label if the condition is zero.
 * 
 * @author sliva
 */
public class ImcCJUMP extends ImcStmt {

	public ImcExpr cond;

	public Label posLabel;

	public Label negLabel;

	public ImcCJUMP(ImcExpr cond, Label posLabel, Label negLabel) {
		this.cond = cond;
		this.posLabel = posLabel;
		this.negLabel = negLabel;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
