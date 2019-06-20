/**
 * @author sliva
 */
package compiler.common.logger;

/**
 * Implemented by classes of objects that a log should be produced for.
 * 
 * @author sliva
 */
public interface Loggable {

	/**
	 * Produces a log of this object.
	 * 
	 * @param logger The logger a log of this object is dumped to.
	 */
	public void log(Logger logger);

}
