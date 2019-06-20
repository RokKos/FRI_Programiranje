/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsTypName extends AbsType implements AbsName {

	public final String name;

	public AbsTypName(Locatable location, String name) {
		super(location);
		this.name = name;
	}

	@Override
	public AbsType relocate(Locatable location) {
		return new AbsTypName(location, name);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
