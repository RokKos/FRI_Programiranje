/**
 * @author sliva
 */
package compiler.data.layout;

import compiler.common.logger.*;

/**
 * An access to a variable at a fixed address. (Also used for string constants.)
 * 
 * @author sliva
 */
public class AbsAccess extends Access {

	/** Label denoting a fixed address. */
	public final Label label;

	/** Initial value. */
	public final String init;

	/**
	 * Constructs a new absolute access.
	 * 
	 * @param size  The size of a variable.
	 * @param label Offset of a variable at an absolute address.
	 * @param init  Initial value (or {@code null}).
	 */
	public AbsAccess(long size, Label label, String init) {
		super(size);
		this.label = label;
		this.init = init;
	}

	/**
	 * Constructs a new absolute access.
	 * 
	 * @param size  The size of a variable.
	 * @param label Offset of a variable at an absolute address.
	 */
	public AbsAccess(long size, Label label) {
		super(size);
		this.label = label;
		this.init = null;
	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("access");
		logger.addAttribute("size", Long.toString(size));
		logger.addAttribute("label", label.name);
		if (init != null)
			logger.addAttribute("init", init);
		logger.endElement();
	}

}
