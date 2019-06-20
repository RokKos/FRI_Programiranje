/**
 * @author sliva
 */
package compiler.phases.imcgen;

import compiler.common.logger.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;
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
public class ImcLogger implements AbsVisitor<Object, Object>, ImcVisitor<Object, Object> {

	/** The logger the log should be written to. */
	private final Logger logger;

	/**
	 * Construct a new visitor with a logger the log should be written to.
	 * 
	 * @param logger The logger the log should be written to.
	 */
	public ImcLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public Object visit(AbsArgs args, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsArrExpr arrExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(arrExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsArrType arrType, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsAssignStmt assignStmt, Object visArg) {
		ImcStmt imc = ImcGen.stmtImCode.get(assignStmt);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsAtomExpr atomExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(atomExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsAtomType atomType, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsBinExpr binExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(binExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsBlockExpr blockExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(blockExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsCastExpr castExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(castExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsCompDecl compDecl, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsCompDecls compDecls, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsDecls decls, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsDelExpr delExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(delExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsExprStmt exprStmt, Object visArg) {
		ImcStmt imc = ImcGen.stmtImCode.get(exprStmt);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsFunDecl funDecl, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsFunDef funDef, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsFunName funName, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(funName);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsIfStmt ifStmt, Object visArg) {
		ImcStmt imc = ImcGen.stmtImCode.get(ifStmt);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsNewExpr newExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(newExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsParDecl parDecl, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsParDecls parDecls, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsPtrType ptrType, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsRecExpr recExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(recExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsRecType recType, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsSource source, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsStmts stmts, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsTypDecl typDecl, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsTypName typeName, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsUnExpr unExpr, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(unExpr);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsVarDecl varDecl, Object visArg) {
		return null;
	}

	@Override
	public Object visit(AbsVarName varName, Object visArg) {
		ImcExpr imc = ImcGen.exprImCode.get(varName);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AbsWhileStmt whileStmt, Object visArg) {
		ImcStmt imc = ImcGen.stmtImCode.get(whileStmt);
		if (imc != null)
			imc.accept(this, null);
		return null;
	}

	//

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
