/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsFunDef extends AbsFunDecl {

	public final AbsExpr value;

	public AbsFunDef(Locatable location, String name, AbsParDecls parDecls, AbsType type, AbsExpr value) {
		super(location, name, parDecls, type);
		this.value = value;
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
