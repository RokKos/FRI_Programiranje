/**
 * @author sliva
 */
package compiler.data.abstree;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsArgs extends Location implements AbsTree {

	private final Vector<AbsExpr> args;

	public AbsArgs(Locatable location, Vector<AbsExpr> args) {
		super(location);
		this.args = new Vector<AbsExpr>(args);
	}

	public Vector<AbsExpr> args() {
		return new Vector<AbsExpr>(args);
	}

	public AbsExpr arg(int index) {
		return args.elementAt(index);
	}
	
	public int numArgs() {
		return args.size();
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
