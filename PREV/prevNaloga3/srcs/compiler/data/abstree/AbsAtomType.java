/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsAtomType extends AbsType {

	public enum Type {
		VOID, BOOL, CHAR, INT,
	}

	public final Type type;

	public AbsAtomType(Locatable location, Type type) {
		super(location);
		this.type = type;
	}
	
	@Override
	public AbsType relocate(Locatable location) {
		return new AbsAtomType(location, type);
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
