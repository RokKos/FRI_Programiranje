/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsIfStmt extends AbsStmt {

	public final AbsExpr cond;

	public final AbsStmts thenStmts;

	public final AbsStmts elseStmts;

	public AbsIfStmt(Locatable location, AbsExpr cond, AbsStmts thenStmts, AbsStmts elseStmts) {
		super(location);
		this.cond = cond;
		this.thenStmts = thenStmts;
		this.elseStmts = elseStmts;
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
