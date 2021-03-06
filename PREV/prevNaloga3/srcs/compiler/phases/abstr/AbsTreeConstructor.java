/**
 * @author sliva
 */
package compiler.phases.abstr;

import java.util.*;
import compiler.common.report.*;
import compiler.data.dertree.*;
import compiler.data.dertree.DerNode.Nont;
import compiler.data.dertree.visitor.*;
import compiler.data.symbol.Symbol.Term;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsBinExpr.Oper;

/**
 * Transforms a derivation tree to an abstract syntax tree.
 * 
 * @author sliva
 */
public class AbsTreeConstructor implements DerVisitor<AbsTree, AbsTree> {

	private final String PTR_NODE = "Expected PTR node";
	private final String ARR_NODE = "Expected ARR node";
	private final String GOT_STR = " got: ";
	private final String TOO_MANY_NODES = "There are zore or more than 3 nodes";
	private final String DECL_NODE = "Declaration node doesn't start with TYP, FUN or VAR";
	private final String WRONG_BINARY_NODE = "This binary operator doesn't exist.";
	private final Location kNULL_LOCATION = new Location(0, 0);

	@Override
	public AbsTree visit(DerLeaf leaf, AbsTree visArg) {
		throw new Report.InternalError();
	}

	@Override
	public AbsTree visit(DerNode node, AbsTree visArg) {
		switch (node.label) {

		case Source: {
			AbsDecls decls = (AbsDecls) node.subtree(0).accept(this, null);
			return new AbsSource(decls, decls);
		}

		case Decls:
		case DeclsRest: {
			if (node.numSubtrees() == 0)
				return null;
			Vector<AbsDecl> allDecls = new Vector<AbsDecl>();
			AbsDecl decl = (AbsDecl) node.subtree(0).accept(this, null);
			allDecls.add(decl);
			AbsDecls decls = (AbsDecls) node.subtree(1).accept(this, null);
			if (decls != null)
				allDecls.addAll(decls.decls());
			return new AbsDecls(new Location(decl, decls == null ? decl : decls), allDecls);
		}

		case Decl: {
			DerLeaf typeOfDecleration = (DerLeaf) node.subtree(0);
			switch (typeOfDecleration.symb.token) {
			case VAR: {
				AbsParDecl parDecl = (AbsParDecl) node.subtree(1).accept(this, null);
				Location loc = new Location(typeOfDecleration, parDecl);
				return new AbsVarDecl(loc, parDecl.name, parDecl.type);
			}

			case TYP: {
				AbsParDecl parDecl = (AbsParDecl) node.subtree(1).accept(this, null);
				Location loc = new Location(typeOfDecleration, parDecl);
				return new AbsTypDecl(loc, parDecl.name, parDecl.type);
			}

			case FUN: {
				DerLeaf funName = (DerLeaf) node.subtree(1);
				AbsParDecls parDecls = (AbsParDecls) node.subtree(2).accept(this, null);
				AbsType type = (AbsType) node.subtree(3).accept(this, null);
				AbsExpr expr = (AbsExpr) node.subtree(4).accept(this, null);

				if (expr.location().equals(kNULL_LOCATION)) {
					Location loc = new Location(typeOfDecleration, type);
					return new AbsFunDecl(loc, funName.symb.lexeme, parDecls, type);
				} else {
					Location loc = new Location(typeOfDecleration, expr);
					return new AbsFunDef(loc, funName.symb.lexeme, parDecls, type, expr);
				}

			}

			default:
				throw new Report.Error(typeOfDecleration.location(),
						DECL_NODE + GOT_STR + typeOfDecleration.symb.token.toString());
			}
		}

		case ParDecl: {
			return DeformParDecl(node);
		}

		case Type: {
			if (node.numSubtrees() == 1) {

				// This is only check for ( Type )
				if (node.subtree(0) instanceof DerNode) {
					return node.subtree(0).accept(this, null);
				} else if (node.subtree(0) instanceof DerLeaf) {
					DerLeaf primitiveTypeNode = (DerLeaf) node.subtree(0);
					AbsAtomType.Type primitiveType;
					switch (primitiveTypeNode.symb.token) {
					case VOID:
						primitiveType = AbsAtomType.Type.VOID;
						break;

					case BOOL:
						primitiveType = AbsAtomType.Type.BOOL;
						break;

					case CHAR:
						primitiveType = AbsAtomType.Type.CHAR;
						break;

					case INT:
						primitiveType = AbsAtomType.Type.INT;
						break;

					default:
						// TODO: Error (Maybe)
						return new AbsTypName(primitiveTypeNode.location(), primitiveTypeNode.symb.lexeme);
					}

					return new AbsAtomType(primitiveTypeNode.location(), primitiveType);
				}
			} else if (node.numSubtrees() == 2) {
				DerLeaf ptr = (DerLeaf) node.subtree(0);
				if (ptr.symb.token == Term.PTR) {
					AbsType subType = (AbsType) node.subtree(1).accept(this, null);
					Location loc = new Location(ptr, subType);
					return new AbsPtrType(loc, subType);
				} else if (ptr.symb.token == Term.REC) {
					AbsCompDecls compDecls = (AbsCompDecls) node.subtree(1).accept(this, null);
					Location loc = new Location(ptr, compDecls);
					return new AbsRecType(loc, compDecls);
				} else {
					throw new Report.Error(ptr.location(), PTR_NODE + GOT_STR + ptr.symb.token.toString());
				}

			} else if (node.numSubtrees() == 3) {
				DerLeaf arr = (DerLeaf) node.subtree(0);
				if (arr.symb.token != Term.ARR) {
					throw new Report.Error(arr.location(), ARR_NODE + GOT_STR + arr.symb.token.toString());
				}

				AbsExpr length = (AbsExpr) node.subtree(1).accept(this, null);
				AbsType elemType = (AbsType) node.subtree(2).accept(this, null);
				Location loc = new Location(arr, elemType);
				return new AbsArrType(loc, length, elemType);

			} else {
				throw new Report.Error(node.location(), TOO_MANY_NODES + GOT_STR + node.numSubtrees());
			}
		}

		case ParDecls: {
			return DeformParDecls(node);
		}

		case ParDeclsRest: {
			return DeformParDecls(node);
		}

		case BodyEps: {
			if (node.numSubtrees() == 0) {
				// Hacky try to find other expr that is not abstrac and has less field
				return Epsilon();
			}

			return node.subtree(1).accept(this, null);
		}

		case CompDecls:
		case CompDeclsRest: {
			if (node.numSubtrees() == 0) {
				return null;
			}
			Vector<AbsCompDecl> allCompDecls = new Vector<AbsCompDecl>();
			AbsCompDecl compDecl = (AbsCompDecl) node.subtree(0).accept(this, null);
			allCompDecls.add(compDecl);
			AbsCompDecls compDecls = (AbsCompDecls) node.subtree(1).accept(this, null);
			if (compDecls != null) {
				allCompDecls.addAll(compDecls.compDecls());
			}
			Location loc = new Location(compDecl, compDecls == null ? compDecl : compDecls);
			return new AbsCompDecls(loc, allCompDecls);
		}

		case CompDecl: {
			return DeformCompDecl(node);
		}

		case Expr:
		case DisjExpr:
		case ConjExpr:
		case RelExpr:
		case AddExpr:
		case MulExpr: {
			return ExpressionTransform(node, visArg);
		}

		case DisjExprRest:
		case ConjExprRest:
		case AddExprRest:
		case MulExprRest: {
			if (node.numSubtrees() == 0) {
				return visArg;
			}

			DerLeaf operatorNode = (DerLeaf) node.subtree(0);
			AbsBinExpr.Oper oper = kTermToBinOper.get(operatorNode.symb.token);
			if (oper == null) {
				throw new Report.Error(node.location(), WRONG_BINARY_NODE + GOT_STR + node.numSubtrees());
			}

			AbsExpr leftOperand = (AbsExpr) node.subtree(1).accept(this, null);
			Location loc = new Location(visArg, leftOperand);
			AbsBinExpr binExpr = new AbsBinExpr(loc, oper, (AbsExpr) visArg, leftOperand);

			AbsExpr rightOperand = (AbsExpr) node.subtree(2).accept(this, binExpr);
			// rightOperand.location());
			return rightOperand;

		}

		case RelExprRest: {
			if (node.numSubtrees() == 0) {
				return visArg;
			}

			DerLeaf operatorNode = (DerLeaf) node.subtree(0);
			AbsBinExpr.Oper oper = kTermToBinOper.get(operatorNode.symb.token);
			if (oper == null) {
				throw new Report.Error(node.location(), WRONG_BINARY_NODE + GOT_STR + node.numSubtrees());
			}

			AbsExpr leftOperand = (AbsExpr) node.subtree(1).accept(this, null);
			Location loc = new Location(visArg, leftOperand);
			return new AbsBinExpr(loc, oper, (AbsExpr) visArg, leftOperand);
		}

		case PrefExpr: {
			if (node.subtree(0) instanceof DerLeaf) {
				DerLeaf operatorNode = (DerLeaf) node.subtree(0);
				AbsUnExpr.Oper oper = kTermToUnarOper.get(operatorNode.symb.token);

				if (oper != null) {
					AbsExpr subExpr = (AbsExpr) node.subtree(1).accept(this, null);
					Location loc = new Location(operatorNode, subExpr);
					return new AbsUnExpr(loc, oper, subExpr);
				}

				if (operatorNode.symb.token == Term.NEW) {
					AbsType type = (AbsType) node.subtree(1).accept(this, null);
					Location loc = new Location(operatorNode, type);
					return new AbsNewExpr(loc, type);
				}

				if (operatorNode.symb.token == Term.DEL) {
					AbsExpr expr = (AbsExpr) node.subtree(1).accept(this, null);
					Location loc = new Location(operatorNode, expr);
					return new AbsDelExpr(loc, expr);
				}
			} else {
				DerNode exprNode = (DerNode) node.subtree(0);
				AbsExpr expr = (AbsExpr) exprNode.accept(this, null);
				if (exprNode.label == Nont.Expr) {
					AbsExpr cast = (AbsExpr) node.subtree(1).accept(this, expr);

					if (node.subtrees().size() >= 3) {
						return node.subtree(2).accept(this, cast);
					}

					return cast;
				} else if (exprNode.label == Nont.PstfExpr) {
					return node.subtree(1).accept(this, expr);
				}
			}

		}

		case PstfExprRest: {
			if (node.numSubtrees() == 0) {
				return visArg;
			}

			if (node.subtree(0) instanceof DerLeaf) {
				DerLeaf varNode = (DerLeaf) node.subtree(0);
				AbsVarName varName = new AbsVarName(varNode, varNode.symb.lexeme);
				Location loc = new Location(visArg, varName);
				AbsRecExpr recordExpr = new AbsRecExpr(loc, (AbsExpr) visArg, varName);
				return node.subtree(1).accept(this, recordExpr);
			} else {
				AbsExpr index = (AbsExpr) node.subtree(0).accept(this, null);
				Location loc = new Location(visArg, index);
				AbsArrExpr arrExpr = new AbsArrExpr(loc, (AbsExpr) visArg, index);
				return node.subtree(1).accept(this, arrExpr);
			}
		}

		case PstfExpr: {
			return node.subtree(0).accept(this, null);
		}

		case AtomExpr: {
			if (node.numSubtrees() == 1) {
				return node.subtree(0).accept(this, null);
			}

			if (node.numSubtrees() == 2) {
				boolean isNull = node.subtree(1).accept(this, null) instanceof AbsAtomExpr
						&& node.subtree(1).accept(this, null).location().equals(kNULL_LOCATION);
				if (isNull) {
					DerLeaf varName = (DerLeaf) node.subtree(0);
					Location loc = new Location(varName, varName);
					return new AbsVarName(loc, varName.symb.lexeme);
				} else {
					AbsArgs funArgs = (AbsArgs) node.subtree(1).accept(this, null);
					DerLeaf funName = (DerLeaf) node.subtree(0);
					Location loc = new Location(funName, funArgs.args().size() != 0 ? funArgs : funName);
					return new AbsFunName(loc, funName.symb.lexeme, funArgs);
				}
			}

			if (node.numSubtrees() == 3) {
				AbsStmts statements = (AbsStmts) node.subtree(0).accept(this, null);
				AbsExpr expr = (AbsExpr) node.subtree(1).accept(this, null);
				AbsDecls decls = (AbsDecls) node.subtree(2).accept(this, null);

				if (decls == null) {
					Location loc = new Location(statements, expr);
					return new AbsBlockExpr(loc, EpsilonDecls(), statements, expr);
				}

				Location loc = new Location(statements, decls);
				return new AbsBlockExpr(loc, decls, statements, expr);
			}
		}
		case Literal: {
			DerLeaf literal = (DerLeaf) node.subtree(0);
			return new AbsAtomExpr(literal, kTermToLitType.get(literal.symb.token), literal.symb.lexeme);
		}

		case CastEps: {
			if (node.numSubtrees() == 0) {
				return visArg;
			}

			AbsType type = (AbsType) node.subtree(0).accept(this, null);
			// Double check this for location
			Location loc = new Location(visArg, type);
			return new AbsCastExpr(loc, (AbsExpr) visArg, type);
		}

		case CallEps:
			if (node.numSubtrees() == 0) {
				return Epsilon();
			}

			return node.subtree(0).accept(this, null);
		case Args: {
			if (node.numSubtrees() == 0) {
				return EpsilonArgs();
			}

			return node.subtree(0).accept(this, null);
		}

		case ArgsEps:
		case ArgsRest: {
			if (node.numSubtrees() == 0) {
				return null;
			}
			Vector<AbsExpr> allExpr = new Vector<AbsExpr>();
			AbsExpr expr = (AbsExpr) node.subtree(0).accept(this, null);
			allExpr.add(expr);
			AbsArgs args = (AbsArgs) node.subtree(1).accept(this, null);
			if (args != null) {
				allExpr.addAll(args.args());
			}
			Location loc = new Location(expr, args == null ? expr : args);
			return new AbsArgs(loc, allExpr);
		}

		case Stmts:
		case StmtsRest: {
			if (node.numSubtrees() == 0) {
				return null;
			}
			Vector<AbsStmt> allStmts = new Vector<AbsStmt>();
			AbsStmt stmt = (AbsStmt) node.subtree(0).accept(this, null);
			allStmts.add(stmt);
			AbsStmts stmts = (AbsStmts) node.subtree(1).accept(this, null);
			if (stmts != null) {
				allStmts.addAll(stmts.stmts());
			}
			Location loc = new Location(stmt, stmts == null ? stmt : stmts);
			return new AbsStmts(loc, allStmts);

		}
		case Stmt: {
			if (node.numSubtrees() == 2) {
				AbsExpr expr = (AbsExpr) node.subtree(0).accept(this, null);
				return node.subtree(1).accept(this, expr);
			}

			if (node.numSubtrees() == 3) {
				AbsExpr condition = (AbsExpr) node.subtree(1).accept(this, null);
				AbsStmts doStatements = (AbsStmts) node.subtree(2).accept(this, null);
				Location loc = new Location((DerLeaf) node.subtree(0), doStatements);
				return new AbsWhileStmt(loc, condition, doStatements);
			}

			if (node.numSubtrees() == 4) {
				AbsExpr condition = (AbsExpr) node.subtree(1).accept(this, null);
				AbsStmts thenStatements = (AbsStmts) node.subtree(2).accept(this, null);
				AbsStmts elseStatements = (AbsStmts) node.subtree(3).accept(this, null);

				if (elseStatements == null) {
					Location loc = new Location((DerLeaf) node.subtree(0), thenStatements);
					return new AbsIfStmt(loc, condition, thenStatements, EpsilonStmts());
				}

				Location loc = new Location((DerLeaf) node.subtree(0), elseStatements);
				return new AbsIfStmt(loc, condition, thenStatements, elseStatements);
			}

		}

		case AssignEps: {
			if (node.numSubtrees() == 0) {
				return new AbsExprStmt(visArg, (AbsExpr) visArg);
			}
			AbsExpr rightSide = (AbsExpr) node.subtree(0).accept(this, null);

			Location loc = new Location(visArg, rightSide);
			return new AbsAssignStmt(loc, (AbsExpr) visArg, rightSide);
		}

		case ElseEps:
		case WhereEps: {
			if (node.numSubtrees() == 0) {
				return null;
			}

			return node.subtree(0).accept(this, null);
		}

		} // End Switch

		return visArg;

	}

	// Helper function
	private AbsTree DeformParDecls(DerNode node) {
		if (node.numSubtrees() == 0) {
			return new AbsParDecls(kNULL_LOCATION, new Vector<AbsParDecl>());
		}

		Vector<AbsParDecl> allParDecls = new Vector<AbsParDecl>();
		AbsParDecl parDecl = (AbsParDecl) node.subtree(0).accept(this, null);
		allParDecls.add(parDecl);
		AbsParDecls parDecls = (AbsParDecls) node.subtree(1).accept(this, null);
		if (parDecls != null) {
			allParDecls.addAll(parDecls.parDecls());
		}

		Location loc = new Location(parDecl, parDecls.equals(kNULL_LOCATION) ? parDecl : parDecls);
		return new AbsParDecls(loc, allParDecls);
	}

	private AbsTree DeformParDecl(DerNode node) {
		DerLeaf identifier = (DerLeaf) node.subtree(0);
		AbsType type = (AbsType) node.subtree(1).accept(this, null);
		Location loc = new Location(identifier, type);
		return new AbsParDecl(loc, identifier.symb.lexeme, type);
	}

	// Same function just other class
	private AbsTree DeformCompDecl(DerNode node) {
		DerLeaf identifier = (DerLeaf) node.subtree(0);
		AbsType type = (AbsType) node.subtree(1).accept(this, null);
		Location loc = new Location(identifier, type);
		return new AbsCompDecl(loc, identifier.symb.lexeme, type);
	}

	private AbsTree ExpressionTransform(DerNode node, AbsTree visArg) {
		if (node.numSubtrees() == 0) {
			return visArg;
		}

		if (node.numSubtrees() == 1) {
			return node.subtree(0).accept(this, null);
		}
		AbsExpr leftOperand = (AbsExpr) node.subtree(0).accept(this, null);
		AbsExpr rightOperand = (AbsExpr) node.subtree(1).accept(this, leftOperand);
		return rightOperand;
	}

	private AbsTree Epsilon() {
		// Hacky try to find other expr that is not abstrac and has less field
		return new AbsAtomExpr(kNULL_LOCATION, AbsAtomExpr.Type.VOID, "");
	}

	private AbsArgs EpsilonArgs() {
		return new AbsArgs(kNULL_LOCATION, new Vector<AbsExpr>());
	}

	private AbsDecls EpsilonDecls() {
		return new AbsDecls(kNULL_LOCATION, new Vector<AbsDecl>());
	}

	private AbsStmts EpsilonStmts() {
		return new AbsStmts(kNULL_LOCATION, new Vector<AbsStmt>());
	}

	private Map<Term, AbsBinExpr.Oper> kTermToBinOper = new HashMap<Term, AbsBinExpr.Oper>() {
		{
			put(Term.IOR, AbsBinExpr.Oper.IOR);
			put(Term.XOR, AbsBinExpr.Oper.XOR);
			put(Term.AND, AbsBinExpr.Oper.AND);
			put(Term.EQU, AbsBinExpr.Oper.EQU);
			put(Term.NEQ, AbsBinExpr.Oper.NEQ);
			put(Term.LTH, AbsBinExpr.Oper.LTH);
			put(Term.GTH, AbsBinExpr.Oper.GTH);
			put(Term.LEQ, AbsBinExpr.Oper.LEQ);
			put(Term.GEQ, AbsBinExpr.Oper.GEQ);
			put(Term.ADD, AbsBinExpr.Oper.ADD);
			put(Term.SUB, AbsBinExpr.Oper.SUB);
			put(Term.MUL, AbsBinExpr.Oper.MUL);
			put(Term.DIV, AbsBinExpr.Oper.DIV);
			put(Term.MOD, AbsBinExpr.Oper.MOD);

		}
	};

	private Map<Term, AbsUnExpr.Oper> kTermToUnarOper = new HashMap<Term, AbsUnExpr.Oper>() {
		{
			put(Term.NOT, AbsUnExpr.Oper.NOT);
			put(Term.ADDR, AbsUnExpr.Oper.ADDR);
			put(Term.DATA, AbsUnExpr.Oper.DATA);
			put(Term.ADD, AbsUnExpr.Oper.ADD);
			put(Term.SUB, AbsUnExpr.Oper.SUB);

		}
	};

	private Map<Term, AbsAtomExpr.Type> kTermToLitType = new HashMap<Term, AbsAtomExpr.Type>() {
		{
			put(Term.VOIDCONST, AbsAtomExpr.Type.VOID);
			put(Term.BOOLCONST, AbsAtomExpr.Type.BOOL);
			put(Term.PTRCONST, AbsAtomExpr.Type.PTR);
			put(Term.INTCONST, AbsAtomExpr.Type.INT);
			put(Term.CHARCONST, AbsAtomExpr.Type.CHAR);
			put(Term.STRCONST, AbsAtomExpr.Type.STR);

		}
	};
}
