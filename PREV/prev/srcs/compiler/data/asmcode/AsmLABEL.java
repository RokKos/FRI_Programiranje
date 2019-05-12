/**
 * @author sliva
 */
package compiler.data.asmcode;

import java.util.*;
import compiler.data.layout.*;

/**
 * An assembly label.
 * 
 * @author sliva
 */
public class AsmLABEL extends AsmOPER {

	/** The label. */
	private final Label label;

	public AsmLABEL(Label label) {
		super("", null, null, null);
		this.label = label;
	}

	@Override
	public String toString() {
		return label.name;
	}

	@Override
	public String toString(HashMap<Temp, Integer> regs) {
		return label.name;
	}

	public Label GetLabel() {
		return label;
	}

}
