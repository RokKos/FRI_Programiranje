/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;

public abstract class AbsStmt extends Location implements AbsTree {

	public AbsStmt(Locatable location) {
		super(location);
	}

}
