package compiler.data.abstree.visitor;

import compiler.data.abstree.*;

/**
 * A visitor that does nothing.
 * 
 * @author sliva
 *
 * @param <Result> The result the visitor produces.
 * @param <Arg> The argument the visitor carries around.
 */
public class AbsNullVisitor<Result, Arg> implements AbsVisitor<Result, Arg> {

	@Override
	public Result visit(AbsArgs args, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsArrExpr arrExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsArrType arrType, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsAssignStmt assignStmt, Arg visArg) {
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
		return null;
	}

	@Override
	public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsCastExpr castExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsCompDecl compDecl, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsCompDecls compDecls, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsDecls decls, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsDelExpr delExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsExprStmt exprStmt, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsFunDecl funDecl, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsFunDef funDef, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsFunName funName, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsIfStmt ifStmt, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsNewExpr newExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsParDecl parDecl, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsParDecls pars, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsPtrType ptrType, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsRecExpr recExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsRecType recType, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsSource source, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsStmts stmts, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsTypDecl typDecl, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsTypName typName, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsUnExpr unExpr, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsVarDecl varDecl, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsVarName varName, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(AbsWhileStmt whileStmt, Arg visArg) {
		return null;
	}

}
