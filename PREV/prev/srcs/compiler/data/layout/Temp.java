/**
 * @author sliva
 */
package compiler.data.layout;

/**
 * A temporary variable.
 * 
 * @author sliva
 */
public class Temp {

	/** The name of a temporary variable. */
	public final long temp;

	/** Counter of temporary variables. */
	private static long count = 0;

	/** Creates a new temporary variable. */
	public Temp() {
		this.temp = count;
		count++;
	}

	@Override
	public String toString() {
		return "T" + temp;
	}

}
