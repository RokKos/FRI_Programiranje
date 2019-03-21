/**
 * @author sliva
 */
package compiler.data.abstree;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsStmts extends Location implements AbsTree {

	private final Vector<AbsStmt> stmts;

	public AbsStmts(Locatable location, Vector<AbsStmt> stmts) {
		super(location);
		this.stmts = new Vector<AbsStmt>(stmts);
	}

	public Vector<AbsStmt> stmts() {
		return stmts;
	}

	public AbsStmt stmt(int index) {
		return stmts.elementAt(index);
	}
	
	public int numStmts() {
		return stmts.size();
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
