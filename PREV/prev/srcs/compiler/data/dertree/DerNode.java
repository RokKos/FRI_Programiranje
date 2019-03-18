/**
 * @author sliva
 */
package compiler.data.dertree;

import java.util.*;
import compiler.common.report.*;
import compiler.data.dertree.visitor.*;

/**
 * An internal node of the derivation tree.
 * 
 * @author sliva
 */
public class DerNode extends DerTree {

	/**
	 * CFG nonterminals.
	 * 
	 * @author sliva
	 */
	public enum Nont {
		Source, Decls, DeclsRest, Decl, ParDeclsEps, ParDecls, ParDeclsRest, ParDecl, BodyEps, Type, CompDecls, CompDeclsRest, CompDecl, Expr, P_expr, NEXT_expr_XOR, exprOR, NEXT_expr_AND, NEXT_expr_EQ, exprXOR, NEXT_expr_NEQ, NEXT_expr_LESS, NEXT_expr_GREAT, NEXT_expr_BIN_PLUS, NEXT_expr_BIN_MINUS, NEXT_expr_TIMES, NEXT_expr_DIV, NEXT_expr_MOD, NEXT_expr_UNARY_NOT, exprAND, exprEQ, exprNEQ, exprLESS, exprLESS_EQ, exprGREAT, exprGREAT_EQ, exprPLUS, exprMINUS, exprTIMES, exprDIV, exprMOD, exprUNARY_NOT, NEXT_expr_UNARY_PLUS, exprUNARY_PLUS, NEXT_expr_UNARY_MINUS, exprUNARY_MINUS, NEXT_expr_ADDR, exprADDR, NEXT_expr_DATA, exprDATA, NEXT_expr_NEW, exprNEW, NEXT_expr_DEL, exprDEL, NEXT_expr_TYPE_CAST, exprTYPE_CAST, exprENCLOSE_TYPE_CAST, NEXT_expr_ARR_ACC, exprARR_COMP_ACC, exprIDE, exprFunction, exprs,exprsEps, StmtsEps, StmtASS, StmtELSE, Literal,Where_o,
		DisjExpr, DisjExprRest, ConjExpr, ConjExprRest, RelExpr, RelExprRest, AddExpr, AddExprRest, MulExpr,
		MulExprRest, PrefExpr, PstfExpr, PstfExprRest, AtomExpr, CallEps, ArgsEps, Args, Arg, ArgsRest, CastEps,
		WhereEps, Stmts, StmtsRest, Stmt, AssignEps, ElseEps,
	};

	/** The CFG nonterminal this node represents. */
	public final Nont label;

	/** A list of subtrees (from left to right, ordered). */
	private final Vector<DerTree> subtrees;

	/** Location of a part of the program represented by this node. */
	private Location location;

	/**
	 * Constructs a new internal node of the derivation tree. Immediately after
	 * construction, the list of subtrees is empty as no subtrees have been appended
	 * yet.
	 * 
	 * @param label The CFG nonterminal this node represents.
	 */
	public DerNode(Nont label) {
		this.label = label;
		this.subtrees = new Vector<DerTree>();
	}

	/**
	 * Add a new subtree to this node. Subtrees are always added from left to right.
	 * 
	 * @param subtree The subtree to be added to this node.
	 * @return This node.
	 */
	public DerNode add(DerTree subtree) {
		subtrees.addElement(subtree);
		Location location = subtree.location();
		this.location = (this.location == null) ? location
				: ((location == null) ? this.location : new Location(this.location, location));
		return this;
	}

	/**
	 * Returns the list of subtrees.
	 * 
	 * @return The list of subtrees.
	 */
	public Vector<DerTree> subtrees() {
		return new Vector<DerTree>(subtrees);
	}

	/**
	 * Returns the specified subtree.
	 * 
	 * @param index The index of the subtree (from left to right).
	 * @return The specified subtree.
	 */
	public DerTree subtree(int index) {
		return subtrees.elementAt(index);
	}

	/**
	 * Returns the number of subtrees of this node.
	 * 
	 * @return The number of subtrees of this node.
	 */
	public int numSubtrees() {
		return subtrees.size();
	}

	@Override
	public Location location() {
		return location;
	}

	@Override
	public <Result, Arg> Result accept(DerVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
