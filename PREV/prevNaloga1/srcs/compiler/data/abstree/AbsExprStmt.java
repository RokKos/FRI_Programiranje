/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsExprStmt extends AbsStmt {

	public final AbsExpr expr;

	public AbsExprStmt(Locatable location, AbsExpr expr) {
		super(location);
		this.expr = expr;
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
