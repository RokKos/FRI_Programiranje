/**
 * @author sliva
 */
package compiler.common.report;

/**
 * Reporting to the user.
 * 
 * @author sliva
 */
public class Report {

	/** Counter of information messages printed out. */
	private static int numOfInfos = 0;

	/**
	 * Returns the number of information messages printed out.
	 * 
	 * @return The number of information messages printed out.
	 */
	public static int numOfInfos() {
		return numOfInfos;
	}

	/**
	 * Prints out an information message.
	 * 
	 * @param message The information message to be printed.
	 */
	public static void info(String message) {
		numOfInfos++;
		System.out.print(":-) ");
		System.out.println(message);
	}

	/**
	 * Prints out an information message relating to the specified part of the
	 * source file.
	 * 
	 * @param location Location the information message is related to.
	 * @param message  The information message to be printed.
	 */
	public static void info(Locatable location, String message) {
		numOfInfos++;
		System.out.print(":-) ");
		System.out.print("[" + location.location() + "] ");
		System.out.println(message);
	}

	/** Counter of warnings printed out. */
	private static int numOfWarnings = 0;

	/**
	 * Returns the number of warnings printed out.
	 * 
	 * @return The number of warnings printed out.
	 */
	public static int numOfWarnings() {
		return numOfWarnings;
	}

	/**
	 * Prints out a warning.
	 * 
	 * @param message The warning message.
	 */
	public static void warning(String message) {
		numOfWarnings++;
		System.err.print(":-o ");
		System.err.println(message);
	}

	/**
	 * Prints out a warning relating to the specified part of the source file.
	 * 
	 * @param location Location the warning message is related to.
	 * @param message  The warning message to be printed.
	 */
	public static void warning(Locatable location, String message) {
		numOfWarnings++;
		System.err.print(":-o ");
		System.err.print("[" + location.location() + "] ");
		System.err.println(message);
	}

	/**
	 * An error.
	 * 
	 * Thrown whenever the program reaches a situation where any further computing
	 * makes no sense any more because of the erroneous input.
	 * 
	 * @author sliva
	 *
	 */
	@SuppressWarnings("serial")
	public static class Error extends java.lang.Error {

		/**
		 * Constructs a new error.
		 * 
		 * @param message The error message.
		 */
		public Error(String message) {
			System.err.print(":-( ");
			System.err.println(message);
		}

		/**
		 * Constructs a new error relating to the specified part of the source file.
		 * 
		 * @param location Location the error message is related to.
		 * @param message  The error message.
		 */
		public Error(Locatable location, String message) {
			System.err.print(":-( ");
			System.err.print("[" + location.location() + "] ");
			System.err.println(message);
		}

	}

	/**
	 * An internal error.
	 * 
	 * Thrown whenever the program encounters internal error.
	 * 
	 * @author sliva
	 *
	 */
	@SuppressWarnings("serial")
	public static class InternalError extends Error {

		/**
		 * Constructs a new internal error.
		 */
		public InternalError() {
			super("Internal error.");
			this.printStackTrace();
		}

	}
}
