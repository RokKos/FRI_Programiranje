/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsPtrType extends AbsType {

	public final AbsType ptdType;

	public AbsPtrType(Locatable location, AbsType subType) {
		super(location);
		this.ptdType = subType;
	}
	
	@Override
	public AbsType relocate(Locatable location) {
		return new AbsPtrType(location, ptdType);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
