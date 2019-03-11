/**
 * @author sliva
 */
package compiler.data.dertree.visitor;

import compiler.common.report.*;
import compiler.data.dertree.*;

/**
 * An abstract visitor of the derivation tree.
 * 
 * @author sliva
 *
 * @param <Result> The result the visitor produces.
 * @param <Arg> The argument the visitor carries around.
 */
public interface DerVisitor<Result, Arg> {

	public default Result visit(DerLeaf leaf, Arg visArg) {
		throw new Report.InternalError();
	}

	public default Result visit(DerNode node, Arg visArg) {
		throw new Report.InternalError();
	}

}
