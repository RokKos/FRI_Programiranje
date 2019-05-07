/**
 * @author sliva
 */
package compiler.data.asmcode;

import java.util.*;
import compiler.common.report.*;
import compiler.data.layout.*;

/**
 * An assembly move.
 * 
 * @author sliva
 *
 */
public class AsmMOVE extends AsmOPER {

	public AsmMOVE(String instr, Vector<Temp> uses, Vector<Temp> defs) {
		super(instr, uses, defs, null);
		if (uses.size() != 1 || defs.size() != 1)
			throw new Report.InternalError();
	}

}
