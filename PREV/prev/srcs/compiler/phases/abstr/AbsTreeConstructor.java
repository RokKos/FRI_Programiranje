/**
 * @author sliva
 */
package compiler.phases.abstr;

import java.util.*;
import compiler.common.report.*;
import compiler.data.dertree.*;
import compiler.data.dertree.visitor.*;
import compiler.data.abstree.*;

/**
 * Transforms a derivation tree to an abstract syntax tree.
 * 
 * @author sliva
 */
public class AbsTreeConstructor implements DerVisitor<AbsTree, AbsTree> {

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
					

					
					default:
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
							return new AbsTypName(primitiveTypeNode.location(), primitiveTypeNode.symb.lexeme);
					}

					return new AbsAtomType(primitiveTypeNode.location(), primitiveType);
				} else {
					// TODO: multiple types
				}
			}


		}
		// TODO

		return visArg;
	}

}
