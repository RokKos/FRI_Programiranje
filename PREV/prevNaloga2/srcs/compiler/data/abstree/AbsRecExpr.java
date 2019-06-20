/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsRecExpr extends AbsExpr {

	public final AbsExpr record;

	public final AbsVarName comp;

	public AbsRecExpr(Locatable location, AbsExpr record, AbsVarName comp) {
		super(location);
		this.record = record;
		this.comp = comp;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsRecExpr(location, record, comp);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
