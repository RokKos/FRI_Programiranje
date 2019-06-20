/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.layout.*;
import compiler.data.imcode.visitor.*;

/**
 * Temporary variable.
 * 
 * Returns the value of a temporary variable.
 * 
 * @author sliva
 */
public class ImcTEMP extends ImcExpr {

	public final Temp temp;

	public ImcTEMP(Temp temp) {
		this.temp = temp;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
