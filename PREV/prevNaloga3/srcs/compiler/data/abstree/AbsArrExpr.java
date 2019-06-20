/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsArrExpr extends AbsExpr {

	public final AbsExpr array;

	public final AbsExpr index;

	public AbsArrExpr(Locatable location, AbsExpr array, AbsExpr index) {
		super(location);
		this.array = array;
		this.index = index;
	}

	public AbsExpr relocate(Locatable location) {
		return new AbsArrExpr(location, array, index);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
