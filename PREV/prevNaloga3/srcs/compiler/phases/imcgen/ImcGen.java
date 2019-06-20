/**
 * @author sliva
 */
package compiler.phases.imcgen;

import compiler.data.abstree.*;
import compiler.data.abstree.attribute.*;
import compiler.data.imcode.*;
import compiler.phases.*;

/**
 * Intermediate code generation.
 * 
 * @author sliva
 */
public class ImcGen extends Phase {
	
	/** Maps statements to intermediate code. */
	public static final AbsAttribute<AbsStmt, ImcStmt> stmtImCode = new AbsAttribute<AbsStmt, ImcStmt>();

	/** Maps expressions to intermediate code. */
	public static final AbsAttribute<AbsExpr, ImcExpr> exprImCode = new AbsAttribute<AbsExpr, ImcExpr>();
	
	/**
	 * Constructs a new phase of intermediate code generation.
	 */
	public ImcGen() {
		super("imcgen");
	}

}
