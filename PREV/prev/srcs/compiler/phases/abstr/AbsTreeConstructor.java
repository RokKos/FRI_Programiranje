/**
 * @author sliva
 */
package compiler.phases.abstr;

import java.util.*;
import compiler.common.report.*;
import compiler.data.dertree.*;
import compiler.data.dertree.visitor.*;
import compiler.data.symbol.Symbol.Term;
import compiler.data.abstree.*;

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

			case Decls: {
				Vector<AbsDecl> allDecls = new Vector<AbsDecl>();
				AbsDecl decl = (AbsDecl) node.subtree(0).accept(this, null);
				allDecls.add(decl);
				AbsDecls decls = (AbsDecls) node.subtree(1).accept(this, null);
				if (decls != null)
					allDecls.addAll(decls.decls());
				return new AbsDecls(new Location(decl, decls == null ? decl : decls), allDecls);
			}

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

					
					default:
						// TODO: Error
						break;
				}
			}

			case ParDecl: {
				DerLeaf identifier = (DerLeaf) node.subtree(0);
				AbsType type = (AbsType) node.subtree(1).accept(this, null);
				Location loc = new Location(identifier, type);
				return new AbsParDecl(loc, identifier.symb.lexeme, type);

			}

			case Type: {
				if (node.numSubtrees() == 1) {

					// This is only check for ( Type )
					if (node.subtree(0) instanceof DerNode) {
						return node.subtree(0).accept(this,null);
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
					DerLeaf ptr = (DerLeaf)node.subtree(0);
					if (ptr.symb.token != Term.PTR) {
						throw new Report.Error(ptr.location(), PTR_NODE + GOT_STR + ptr.symb.token.toString());
					}

					AbsType subType = (AbsType) node.subtree(1).accept(this, null);
					Location loc = new Location(ptr, subType);
					return new AbsPtrType(loc, subType);

				} else if (node.numSubtrees() == 3) {
					DerLeaf arr = (DerLeaf)node.subtree(0);
					if (arr.symb.token != Term.ARR) {
						throw new Report.Error(arr.location(), ARR_NODE + GOT_STR + arr.symb.token.toString());
					}

					AbsExpr length = (AbsExpr) node.subtree(1).accept(this, null);
					AbsType elemType = (AbsType) node.subtree(2).accept(this, null);
					Location loc = new Location (arr, elemType);
					return new AbsArrType(loc, length, elemType);

				} else {
					// TODO: Error
					throw new Report.Error(node.location(), TOO_MANY_NODES + GOT_STR + node.numSubtrees());
				}

			}


		}
		// TODO

		return visArg;
	}

}
