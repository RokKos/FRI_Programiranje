/**
 * @author sliva
 */
package compiler.data.dertree.visitor;

import compiler.data.dertree.*;

/**
 * A visitor that does nothing.
 * 
 * @author sliva
 */
public class DerNullVisitor<Result, Arg> implements DerVisitor<Result, Arg> {

	@Override
	public Result visit(DerLeaf leaf, Arg visArg) {
		return null;
	}

	@Override
	public Result visit(DerNode node, Arg visArg) {
		return null;
	}

}
