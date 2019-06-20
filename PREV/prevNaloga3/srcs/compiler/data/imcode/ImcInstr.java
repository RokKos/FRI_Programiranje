/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.imcode.visitor.*;

/**
 * Intermediate code instruction.
 * 
 * @author sliva
 */
public abstract class ImcInstr {

	public abstract <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg);

}
