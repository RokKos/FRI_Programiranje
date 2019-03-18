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
		Source, Decls, DeclsRest, Decl, ParDeclsEps, ParDecls, ParDeclsRest, ParDecl, BodyEps, Type, CompDecls, CompDeclsRest, CompDecl, Expr,
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
