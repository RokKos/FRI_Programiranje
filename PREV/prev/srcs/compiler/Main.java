/**
 * @author sliva
 */
package compiler;

import java.util.*;
import compiler.common.report.*;
import compiler.phases.lexan.*;

/**
 * The compiler.
 * 
 * @author sliva
 */
public class Main {

	/** All valid phases of the compiler. */
	private static final String phases = "lexan";

	/** Values of command line arguments. */
	private static HashMap<String, String> cmdLine = new HashMap<String, String>();

	/**
	 * Returns the value of a command line argument.
	 * 
	 * @param cmdLineArgName The name of the command line argument.
	 * @return The value of the specified command line argument or {@code null} if
	 *         the specified command line argument has not been used.
	 */
	public static String cmdLineArgValue(String cmdLineArgName) {
		return cmdLine.get(cmdLineArgName);
	}

	/**
	 * The compiler's {@code main} method.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		try {
			Report.info("This is PREV compiler:");

			// Scan the command line.
			for (int argc = 0; argc < args.length; argc++) {
				if (args[argc].startsWith("--")) {
					// Command-line switch.
					if (args[argc].matches("--target-phase=(" + phases + "|all)")) {
						if (cmdLine.get("--target-phase") == null) {
							cmdLine.put("--target-phase", args[argc].replaceFirst("^[^=]*=", ""));
							continue;
						}
					}
					if (args[argc].matches("--logged-phase=(" + phases + "|all)")) {
						if (cmdLine.get("--logged-phase") == null) {
							cmdLine.put("--logged-phase", args[argc].replaceFirst("^[^=]*=", ""));
							continue;
						}
					}
					if (args[argc].matches("--xml=.*")) {
						if (cmdLine.get("--xml") == null) {
							cmdLine.put("--xml", args[argc].replaceFirst("^[^=]*=", ""));
							continue;
						}
					}
					if (args[argc].matches("--xsl=.*")) {
						if (cmdLine.get("--xsl") == null) {
							cmdLine.put("--xsl", args[argc].replaceFirst("^[^=]*=", ""));
							continue;
						}
					}
					Report.warning("Command line argument '" + args[argc] + "' ignored.");
				} else {
					// Source file name.
					if (cmdLine.get("--src-file-name") == null) {
						cmdLine.put("--src-file-name", args[argc]);
					} else {
						Report.warning("Source file '" + args[argc] + "' ignored.");
					}
				}
			}
			if (cmdLine.get("--src-file-name") == null) {
				throw new Report.Error("Source file not specified.");
			}
			if (cmdLine.get("--dst-file-name") == null) {
				cmdLine.put("--dst-file-name", cmdLine.get("--src-file-name").replaceFirst("\\.[^./]*$", "") + ".asm");
			}
			if (cmdLine.get("--target-phase") == null) {
				cmdLine.put("--target-phase", phases.replaceFirst("^.*\\|", ""));
			}

			// Compile, phase by phase.
			do {
				int begWarnings = Report.numOfWarnings();

				// Lexical analysis.
				if (cmdLine.get("--target-phase").equals("lexan")) {
					try (LexAn lexan = new LexAn()) {
						while (lexan.lexer().token != compiler.data.symbol.Symbol.Term.EOF)
							;
					}
					break;
				}

				int endWarnings = Report.numOfWarnings();
				if (begWarnings != endWarnings)
					throw new Report.Error("Compilation stopped.");

			} while (false);

			Report.info("Done.");
		} catch (Report.Error __) {
		}
	}

}
