/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.imcode.visitor.*;

/**
 * Memory access.
 * 
 * Evaluates the address, reads from this address in the memory and returns the
 * value read (but see {@link ImcMOVE} as well.)
 * 
 * @author sliva
 */
public class ImcMEM extends ImcExpr {

	public final ImcExpr addr;

	public ImcMEM(ImcExpr addr) {
		this.addr = addr;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
