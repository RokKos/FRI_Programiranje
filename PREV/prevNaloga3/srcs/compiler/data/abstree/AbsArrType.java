/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsArrType extends AbsType {

	public final AbsExpr len;

	public final AbsType elemType;

	public AbsArrType(Locatable location, AbsExpr len, AbsType elemType) {
		super(location);
		this.len = len;
		this.elemType = elemType;
	}

	@Override
	public AbsType relocate(Locatable location) {
		return new AbsArrType(location, len, elemType);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
