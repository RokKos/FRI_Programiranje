/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.imcode.visitor.*;

/**
 * Move operation.
 * 
 * Evaluates the destination, evaluates the source, and moves the source to the
 * destination. If the root node of the destination is {@link ImcMEM}, then the
 * source is stored to the memory address denoted by the subtree of that
 * {@link ImcMEM} node. If the root node of the destination is {@link ImcTEMP},
 * the source is stored in the temporary variable.
 * 
 * @author sliva
 */
public class ImcMOVE extends ImcStmt {

	public final ImcExpr dst;

	public final ImcExpr src;

	public ImcMOVE(ImcExpr dst, ImcExpr src) {
		this.dst = dst;
		this.src = src;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
