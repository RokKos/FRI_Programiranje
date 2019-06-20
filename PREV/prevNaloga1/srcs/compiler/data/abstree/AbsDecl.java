/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;

public abstract class AbsDecl extends Location implements AbsTree {

	public final String name;

	public final AbsType type;

	public AbsDecl(Locatable location, String name, AbsType type) {
		super(location);
		this.name = name;
		this.type = type;
	}

}
