/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsBlockExpr extends AbsExpr {

	public final AbsDecls decls;

	public final AbsStmts stmts;

	public final AbsExpr expr;

	public AbsBlockExpr(Locatable location, AbsDecls decls, AbsStmts stmts, AbsExpr expr) {
		super(location);
		this.decls = decls;
		this.stmts = stmts;
		this.expr = expr;
	}
	
	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsBlockExpr(location, decls, stmts, expr);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
