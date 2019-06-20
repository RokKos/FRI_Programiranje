/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsNewExpr extends AbsExpr {

	public final AbsType type;

	public AbsNewExpr(Locatable location, AbsType type) {
		super(location);
		this.type = type;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsNewExpr(location, type);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
