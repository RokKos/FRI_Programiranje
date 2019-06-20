/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.layout.*;
import compiler.data.imcode.visitor.*;

/**
 * Label.
 * 
 * Does nothing.
 * 
 * @author sliva
 */
public class ImcLABEL extends ImcStmt {

	public Label label;

	public ImcLABEL(Label label) {
		this.label = label;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
