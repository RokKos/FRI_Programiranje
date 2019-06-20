/**
 * @author sliva
 */
package compiler.data.dertree.visitor;

import compiler.data.dertree.*;

/**
 * A visitor that visits all nodes of a derivation tree.
 * 
 * @author sliva
 */
public class DerFullVisitor<Result, Arg> implements DerVisitor<Result, Arg> {

	@Override
	public Result visit(DerLeaf leaf, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(DerNode node, Arg visArg) {
		for (DerTree subtree : node.subtrees())
			subtree.accept(this, visArg);
		return null;
	}

}
