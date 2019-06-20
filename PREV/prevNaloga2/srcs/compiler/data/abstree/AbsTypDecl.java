/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsTypDecl extends AbsDecl {

	public AbsTypDecl(Locatable location, String name, AbsType type) {
		super(location, name, type);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
