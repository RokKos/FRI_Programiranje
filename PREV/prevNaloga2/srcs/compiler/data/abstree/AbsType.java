/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;

public abstract class AbsType extends Location implements AbsTree {

	public AbsType(Locatable location) {
		super(location);
	}

	public abstract AbsType relocate(Locatable location);

}
