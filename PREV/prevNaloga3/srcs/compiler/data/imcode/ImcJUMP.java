/**
 * @author sliva
 */
package compiler.data.imcode;

import compiler.data.layout.*;
import compiler.data.imcode.visitor.*;

/**
 * Unconditional jump.
 * 
 * Jumps to the label provided.
 * 
 * @author sliva
 */
public class ImcJUMP extends ImcStmt {

	public Label label;

	public ImcJUMP(Label label) {
		this.label = label;
	}

	@Override
	public <Result, Arg> Result accept(ImcVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
