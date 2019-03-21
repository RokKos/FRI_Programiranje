/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;

public abstract class AbsExpr extends Location implements AbsTree {

	public AbsExpr(Locatable location) {
		super(location);
	}

	public abstract AbsExpr relocate(Locatable location);

}
