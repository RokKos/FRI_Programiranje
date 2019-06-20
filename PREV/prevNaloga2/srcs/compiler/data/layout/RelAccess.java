/**
 * @author sliva
 */
package compiler.data.layout;

import compiler.common.logger.*;

/**
 * An access to a variable relative to an (unspecified) base address.
 * 
 * @author sliva
 */
public class RelAccess extends Access {

	/** Offset of a variable relative to a base address. */
	public final long offset;

	/** The variable's static depth (0 for record components). */
	public final int depth;

	/**
	 * Constructs a new relative access.
	 * 
	 * @param size   The size of the variable.
	 * @param offset Offset of a variable relative to a base address.
	 * @param depth  The variable's static depth (0 for record components).
	 */
	public RelAccess(long size, long offset, int depth) {
		super(size);
		this.offset = offset;
		this.depth = depth;
	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("access");
		logger.addAttribute("size", Long.toString(size));
		logger.addAttribute("offset", Long.toString(offset));
		if (depth > 0)
			logger.addAttribute("depth", Integer.toString(depth));
		logger.endElement();
	}

}
