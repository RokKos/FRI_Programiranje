/**
 * @author sliva
 */
package compiler.data.imcode;

import java.util.*;
import compiler.data.imcode.visitor.*;

/**
 * Statements.
 * 
 * Executes one statement after another.
 * 
 * @author sliva
 */
public class ImcSTMTS extends ImcStmt {

	private final Vector<ImcStmt> stmts;

	public ImcSTMTS(Vector<ImcStmt> stmts) {
		this.stmts = new Vector<ImcStmt>(stmts);
	}

	public Vector<ImcStmt> stmts() {
		return new Vector<ImcStmt>(stmts);
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
