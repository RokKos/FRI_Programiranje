/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsVarName extends AbsExpr implements AbsName {

	public final String name;

	public AbsVarName(Locatable location, String name) {
		super(location);
		this.name = name;
	}

	@Override
	public AbsExpr relocate(Locatable location) {
		return new AbsVarName(location, name);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
