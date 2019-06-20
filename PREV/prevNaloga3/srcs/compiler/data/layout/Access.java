/**
 * @author sliva
 */
package compiler.data.layout;

import compiler.common.logger.*;

/**
 * Access to a variable.
 * 
 * @author sliva
 */
public abstract class Access implements Loggable {

	/** The size of the variable. */
	public final long size;

	/**
	 * Creates a new access to a variable.
	 * 
	 * @param size The size of the variable.
	 */
	public Access(long size) {
		this.size = size;
	}

}
