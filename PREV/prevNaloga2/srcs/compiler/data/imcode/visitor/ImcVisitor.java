/**
 * @author sliva
 */
package compiler.data.imcode.visitor;

import compiler.common.report.*;
import compiler.data.imcode.*;

/**
 * An abstract visitor of the intermediate code.
 * 
 * @author sliva
 *
 * @param <Result> The result the visitor produces.
 * @param <Arg> The argument the visitor carries around.
 */
public interface ImcVisitor<Result, Arg> {

	public default Result visit(ImcBINOP binOp, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcCALL call, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcCJUMP cjump, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcCONST constant, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcESTMT eStmt, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcJUMP jump, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcLABEL label, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcMEM mem, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcMOVE move, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcNAME name, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcSEXPR sExpr, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcSTMTS stmts, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcTEMP temp, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(ImcUNOP unOp, Arg visArg) {
		throw new Report.InternalError();
	}

}
