/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsAssignStmt extends AbsStmt {

	public final AbsExpr dst;

	public final AbsExpr src;

	public AbsAssignStmt(Locatable location, AbsExpr dst, AbsExpr src) {
		super(location);
		this.dst = dst;
		this.src = src;
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
