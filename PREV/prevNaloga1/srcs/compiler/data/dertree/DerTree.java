/**
 * @author sliva
 */
package compiler.data.dertree;

import compiler.common.report.*;
import compiler.data.dertree.visitor.*;

/**
 * Derivation tree.
 * 
 * @author sliva
 */
public abstract class DerTree implements Locatable {

	/**
	 * The method implementing the acceptor functionality.
	 * 
	 * @param         <Result> The type of result the visitor produces.
	 * @param         <Arg> The optional argument that the visitor can pass around.
	 * @param visitor The accepted visitor.
	 * @param accArg  The acceptor's argument.
	 * @return The acceptor's result.
	 */
	public abstract <Result, Arg> Result accept(DerVisitor<Result, Arg> visitor, Arg accArg);

}
