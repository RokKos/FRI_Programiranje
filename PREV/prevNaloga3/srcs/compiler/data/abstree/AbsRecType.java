/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsRecType extends AbsType {

	public final AbsCompDecls compDecls;

	public AbsRecType(Locatable location, AbsCompDecls compDecls) {
		super(location);
		this.compDecls = compDecls;
	}

	@Override
	public AbsType relocate(Locatable location) {
		return new AbsRecType(location, compDecls);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
