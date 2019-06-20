package compiler.phases.abstr;

import java.util.*;
import compiler.common.logger.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;

/**
 * The visitor that produces the log of the abstract syntax tree.
 * 
 * @author sliva
 *
 */
public class AbsLogger implements AbsVisitor<Object, Object> {

	/** A list of subvisitors for logging results of the subsequent phases. */
	private final LinkedList<AbsVisitor<Object, Object>> subvisitors;

	/** The logger the log should be written to. */
	private final Logger logger;

	/**
	 * Construct a new visitor with a logger the log should be written to.
	 * 
	 * @param logger The logger the log should be written to.
	 */
	public AbsLogger(Logger logger) {
		this.logger = logger;
		this.subvisitors = new LinkedList<AbsVisitor<Object, Object>>();
	}

	/**
	 * Adds a new subvisitor to this visitor.
	 * 
	 * @param subvisitor The subvisitor.
	 * @return This visitor.
	 */
	public AbsLogger addSubvisitor(AbsVisitor<Object, Object> subvisitor) {
		subvisitors.addLast(subvisitor);
		return this;
	}

	@Override
	public Object visit(AbsArgs args, Object visArg) {
		if (logger == null)
			return null;
		if (!args.args().isEmpty() || true) {
			logger.begElement("node");
			logger.addAttribute("label", "Args");
			for (AbsExpr arg : args.args()) {
				arg.accept(this, null);
			}
			args.log(logger);
			for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
				args.accept(subvisitor, null);
			}
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsArrExpr arrExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "ArrExpr");
		arrExpr.array.accept(this, visArg);
		arrExpr.index.accept(this, visArg);
		arrExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			arrExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsArrType arrType, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "ArrType");
		arrType.len.accept(this, null);
		arrType.elemType.accept(this, null);
		arrType.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			arrType.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsAssignStmt assignStmt, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "AssignStmt");
		assignStmt.dst.accept(this, null);
		assignStmt.src.accept(this, null);
		assignStmt.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			assignStmt.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsAtomExpr atomExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "AtomExpr");
		logger.addAttribute("spec", atomExpr.type.toString());
		logger.addAttribute("lexeme", atomExpr.expr);
		atomExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			atomExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsAtomType atomType, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "AtomType");
		logger.addAttribute("spec", atomType.type.toString());
		atomType.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			atomType.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsBinExpr binExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "BinExpr");
		logger.addAttribute("spec", binExpr.oper.toString());
		binExpr.fstExpr.accept(this, null);
		binExpr.sndExpr.accept(this, null);
		binExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			binExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsBlockExpr blockExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "BlockExpr");
		blockExpr.decls.accept(this, null);
		blockExpr.stmts.accept(this, null);
		blockExpr.expr.accept(this, null);
		blockExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			blockExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsCastExpr castExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "CastExpr");
		castExpr.expr.accept(this, null);
		castExpr.type.accept(this, null);
		castExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			castExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsCompDecl compDecl, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "CompDecl");
		logger.addAttribute("lexeme", compDecl.name);
		compDecl.type.accept(this, visArg);
		compDecl.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			compDecl.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsCompDecls compDecls, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "CompDecls");
		for (AbsCompDecl compDecl : compDecls.compDecls())
			compDecl.accept(this, visArg);
		compDecls.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			compDecls.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsDecls decls, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "Decls");
		for (AbsDecl decl : decls.decls()) {
			decl.accept(this, null);
		}
		decls.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			decls.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsDelExpr delExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "DelExpr");
		delExpr.expr.accept(this, null);
		delExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			delExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsExprStmt exprStmt, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "ExprStmt");
		exprStmt.expr.accept(this, null);
		exprStmt.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			exprStmt.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsFunDecl funDecl, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "FunDecl");
		logger.addAttribute("lexeme", funDecl.name);
		funDecl.parDecls.accept(this, null);
		funDecl.type.accept(this, null);
		funDecl.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			funDecl.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsFunDef funDeclBody, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "FunDef");
		logger.addAttribute("lexeme", funDeclBody.name);
		funDeclBody.parDecls.accept(this, null);
		funDeclBody.type.accept(this, null);
		funDeclBody.value.accept(this, null);
		funDeclBody.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			funDeclBody.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsFunName funName, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "FunName");
		logger.addAttribute("lexeme", funName.name);
		funName.args.accept(this, null);
		funName.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			funName.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsIfStmt ifStmt, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "IfStmt");
		ifStmt.cond.accept(this, null);
		ifStmt.thenStmts.accept(this, null);
		ifStmt.elseStmts.accept(this, null);
		ifStmt.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			ifStmt.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsNewExpr newExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "NewExpr");
		newExpr.type.accept(this, null);
		newExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			newExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsParDecl parDecl, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "ParDecl");
		logger.addAttribute("lexeme", parDecl.name);
		parDecl.type.accept(this, null);
		parDecl.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			parDecl.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsParDecls parDecls, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "ParDecls");
		for (AbsParDecl parDecl : parDecls.parDecls()) {
			parDecl.accept(this, null);
		}
		parDecls.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			parDecls.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsPtrType ptrType, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "PtrType");
		ptrType.ptdType.accept(this, null);
		ptrType.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			ptrType.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsRecExpr recExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "RecExpr");
		recExpr.record.accept(this, visArg);
		recExpr.comp.accept(this, visArg);
		recExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			recExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsRecType recType, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "RecType");
		recType.compDecls.accept(this, visArg);
		recType.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			recType.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsSource source, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "Source");
		source.decls.accept(this, visArg);
		source.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			source.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsStmts stmts, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "Stmts");
		for (AbsStmt stmt : stmts.stmts()) {
			stmt.accept(this, null);
		}
		stmts.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			stmts.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsTypDecl typDecl, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "TypDecl");
		logger.addAttribute("lexeme", typDecl.name);
		typDecl.type.accept(this, null);
		typDecl.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			typDecl.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsTypName typName, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "TypeName");
		logger.addAttribute("lexeme", typName.name);
		typName.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			typName.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsUnExpr unExpr, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "UnExpr");
		logger.addAttribute("spec", unExpr.oper.toString());
		unExpr.subExpr.accept(this, null);
		unExpr.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			unExpr.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsVarDecl varDecl, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "VarDecl");
		logger.addAttribute("lexeme", varDecl.name);
		varDecl.type.accept(this, null);
		varDecl.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			varDecl.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsVarName varName, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "VarName");
		logger.addAttribute("lexeme", varName.name);
		varName.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			varName.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

	@Override
	public Object visit(AbsWhileStmt whileStmt, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("node");
		logger.addAttribute("label", "WhileStmt");
		whileStmt.cond.accept(this, null);
		whileStmt.stmts.accept(this, null);
		whileStmt.log(logger);
		for (AbsVisitor<Object, Object> subvisitor : subvisitors) {
			whileStmt.accept(subvisitor, null);
		}
		logger.endElement();
		return null;
	}

}
