/**
 * @author sliva
 */
package compiler.phases.chunks;

import compiler.common.logger.*;
import compiler.data.chunk.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;

/**
 * The visitor that produces the log of the intermediate code.
 * 
 * This visitor does not traverses the abstract syntax tree. It is used as a
 * subvisitor of {@link compiler.phases.abstr.AbsLogger} and produces XML
 * representation of the computed attributes for each AST node separately (when
 * called by a methods of {@link compiler.phases.abstr.AbsLogger} each time they
 * reach an AST node).
 * 
 * @author sliva
 */
public class ChunkLogger implements ImcVisitor<Object, Object> {

	/** The logger the log should be written to. */
	private final Logger logger;

	/**
	 * Construct a new visitor with a logger the log should be written to.
	 * 
	 * @param logger The logger the log should be written to.
	 */
	public ChunkLogger(Logger logger) {
		this.logger = logger;
	}

	public void log(DataChunk dataChunk) {
		if (logger == null)
			return;
		logger.begElement("datachunk");
		logger.addAttribute("label", dataChunk.label.name);
		logger.addAttribute("size", Long.toString(dataChunk.size));
		logger.addAttribute("init", dataChunk.init);
		logger.endElement();
	}

	public void log(CodeChunk codeChunk) {
		if (logger == null)
			return;
		logger.begElement("codechunk");
		logger.addAttribute("entrylabel", codeChunk.entryLabel.name);
		logger.addAttribute("exitlabel", codeChunk.exitLabel.name);
		codeChunk.frame.log(logger);
		for (ImcStmt stmt : codeChunk.stmts()) {
			logger.begElement("stmt");
			stmt.accept(new ChunkLogger(logger), null);
			logger.endElement();
		}
		logger.endElement();
	}

	@Override
	public Object visit(ImcBINOP binOp, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "BINOP");
		logger.addAttribute("value", binOp.oper.toString());
		binOp.fstExpr.accept(this, logger);
		binOp.sndExpr.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcCALL call, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "CALL");
		logger.addAttribute("value", call.label.name);
		for (ImcExpr arg : call.args())
			arg.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcCJUMP cjump, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "CJUMP");
		logger.addAttribute("value", cjump.posLabel.name + ":" + cjump.negLabel.name);
		cjump.cond.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcCONST constant, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "CONST");
		logger.addAttribute("value", Long.toString(constant.value));
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcESTMT eStmt, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "ESTMT");
		eStmt.expr.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcJUMP jump, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "JUMP");
		logger.addAttribute("value", jump.label.name);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcLABEL label, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "LABEL");
		logger.addAttribute("value", label.label.name);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcMEM mem, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "MEM");
		mem.addr.accept(this, logger);
		logger.endElement();

		return null;
	}

	@Override
	public Object visit(ImcMOVE move, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "MOVE");
		move.dst.accept(this, logger);
		move.src.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcNAME name, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "NAME");
		logger.addAttribute("value", name.label.name);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcSEXPR sExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "SEXPR");
		sExpr.stmt.accept(this, logger);
		sExpr.expr.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcSTMTS stmts, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "STMTS");
		for (ImcStmt stmt : stmts.stmts())
			stmt.accept(this, logger);
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcTEMP temp, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "TEMP");
		logger.addAttribute("value", Long.toString(temp.temp.temp));
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(ImcUNOP unOp, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("imc");
		logger.addAttribute("name", "UNOP");
		logger.addAttribute("value", unOp.oper.toString());
		unOp.subExpr.accept(this, logger);
		logger.endElement();
		return null;
	}

}
