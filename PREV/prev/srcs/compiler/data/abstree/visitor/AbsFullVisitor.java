package compiler.data.abstree.visitor;

import compiler.data.abstree.*;

/**
 * A visitor that visits every node.
 * 
 * @author sliva
 *
 * @param <Result> The result the visitor produces.
 * @param <Arg> The argument the visitor carries around.
 */
public class AbsFullVisitor<Result, Arg> implements AbsVisitor<Result, Arg> {

	@Override
	public Result visit(AbsArgs args, Arg visArg) {
		for (AbsExpr arg : args.args())
			arg.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsArrExpr arrExpr, Arg visArg) {
		arrExpr.array.accept(this, visArg);
		arrExpr.index.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsArrType arrType, Arg visArg) {
		arrType.len.accept(this, visArg);
		arrType.elemType.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsAssignStmt assignStmt, Arg visArg) {
		assignStmt.dst.accept(this, visArg);
		assignStmt.src.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsAtomExpr atomExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsAtomType atomType, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsBinExpr binExpr, Arg visArg) {
		binExpr.fstExpr.accept(this, visArg);
		binExpr.sndExpr.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
		blockExpr.decls.accept(this, visArg);
		blockExpr.stmts.accept(this, visArg);
		blockExpr.expr.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsCastExpr castExpr, Arg visArg) {
		castExpr.type.accept(this, visArg);
		castExpr.expr.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsCompDecl compDecl, Arg visArg) {
		compDecl.type.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsCompDecls compDecls, Arg visArg) {
		for (AbsCompDecl compDecl : compDecls.compDecls())
			compDecl.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsDecls decls, Arg visArg) {
		for (AbsDecl decl : decls.decls())
			decl.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsDelExpr delExpr, Arg visArg) {
		delExpr.expr.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsExprStmt exprStmt, Arg visArg) {
		exprStmt.expr.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsFunDecl funDecl, Arg visArg) {
		funDecl.parDecls.accept(this, visArg);
		funDecl.type.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsFunDef funDef, Arg visArg) {
		funDef.parDecls.accept(this, visArg);
		funDef.type.accept(this, visArg);
		funDef.value.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsFunName funName, Arg visArg) {
		funName.args.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsIfStmt ifStmt, Arg visArg) {
		ifStmt.cond.accept(this, visArg);
		ifStmt.thenStmts.accept(this, visArg);
		ifStmt.elseStmts.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsNewExpr newExpr, Arg visArg) {
		newExpr.type.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsParDecl parDecl, Arg visArg) {
		parDecl.type.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsParDecls parDecls, Arg visArg) {
		for (AbsParDecl parDecl : parDecls.parDecls())
			parDecl.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsPtrType ptrType, Arg visArg) {
		ptrType.ptdType.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsRecExpr recExpr, Arg visArg) {
		recExpr.record.accept(this, visArg);
		recExpr.comp.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsRecType recType, Arg visArg) {
		recType.compDecls.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsSource source, Arg visArg) {
		source.decls.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsStmts stmts, Arg visArg) {
		for (AbsStmt stmt : stmts.stmts())
			stmt.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsTypDecl typDecl, Arg visArg) {
		typDecl.type.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsTypName typName, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsUnExpr unExpr, Arg visArg) {
		unExpr.subExpr.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsVarDecl varDecl, Arg visArg) {
		varDecl.type.accept(this, visArg);
		return null;
	}

	@Override
	public Result visit(AbsVarName varName, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsWhileStmt whileStmt, Arg visArg) {
		whileStmt.cond.accept(this, visArg);
		whileStmt.stmts.accept(this, visArg);
		return null;
	}

}
