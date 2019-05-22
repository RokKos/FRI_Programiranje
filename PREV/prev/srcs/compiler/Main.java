/**
 * @author sliva
 */
package compiler;

import java.util.*;
import compiler.common.report.*;
import compiler.phases.lexan.*;
import compiler.phases.synan.*;
import compiler.phases.abstr.*;
import compiler.phases.asmcode.*;
import compiler.phases.seman.*;
import compiler.phases.frames.*;
import compiler.phases.imcgen.*;
import compiler.phases.chunks.*;
import compiler.phases.livean.*;
import compiler.phases.ralloc.*;

/**
 * The compiler.
 * 
 * @author sliva
 */
public class Main {

	/** All valid phases of the compiler. */
	private static final String phases = "lexan|synan|abstr|seman|frames|imcgen|chunks|asmgen|livean|ralloc";

	/** Values of command line arguments. */
	private static HashMap<String, String> cmdLine = new HashMap<String, String>();

	/** The number of general-purpose registers. */
	public final int numOfRegs = 8;

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

				// Syntax analysis.
				try (SynAn synan = new SynAn()) {
					synan.parser();
					SynAn.derTree.accept(new DerLogger(synan.logger), null);
				}
				if (cmdLine.get("--target-phase").equals("synan"))
					break;

				// Abstract syntax.
				try (Abstr abstr = new Abstr()) {
					Abstr.absTree = SynAn.derTree.accept(new AbsTreeConstructor(), null);
					AbsLogger logger = new AbsLogger(abstr.logger);
					Abstr.absTree.accept(logger, null);
				}
				if (cmdLine.get("--target-phase").equals("abstr"))
					break;

				// Semantic analysis.
				try (SemAn seman = new SemAn()) {
					// Name resolution
					Abstr.absTree.accept(new GlobalTypeNameDeclaration(), null);
					Abstr.absTree.accept(new TypeNameDeclaration(), null);
					Abstr.absTree.accept(new TypeNameResolution(), null);
					Abstr.absTree.accept(new VarNameDeclaration(), null);
					Abstr.absTree.accept(new FunNameDeclaration(), null);
					Abstr.absTree.accept(new FunNameResolution(), null);

					// Type resolution
					Abstr.absTree.accept(new TypeResolverDeclarationStage(), null);
					Abstr.absTree.accept(new TypeResolverResolvingStage(), null);
					Abstr.absTree.accept(new TypeResolverCheckingStage(), null);

					Abstr.absTree.accept(new AddrResolver(), null);
					Abstr.absTree.accept(new LValueChecker(), null);
					SemAn.declaredAt.lock();
					SemAn.declaresType.lock();
					SemAn.isType.lock();
					SemAn.ofType.lock();
					SemAn.isAddr.lock();

					AbsLogger logger = new AbsLogger(seman.logger);
					logger.addSubvisitor(new SemLogger(seman.logger));
					Abstr.absTree.accept(logger, null);
				}
				if (cmdLine.get("--target-phase").equals("seman"))
					break;

				// Memory layout, i.e., frames and accesses.
				try (Frames frames = new Frames()) {
					Abstr.absTree.accept(new FrmEvaluator(), null);
					Frames.frames.lock();
					Frames.accesses.lock();
					Frames.strings.lock();

					AbsLogger logger = new AbsLogger(frames.logger);
					logger.addSubvisitor(new SemLogger(frames.logger));
					logger.addSubvisitor(new FrmLogger(frames.logger));
					Abstr.absTree.accept(logger, null);
				}
				if (cmdLine.get("--target-phase").equals("frames"))
					break;

				// Intermediate code generation.
				try (ImcGen imcGen = new ImcGen()) {
					Abstr.absTree.accept(new CodeGenerator(), new Stack<compiler.data.layout.Frame>());
					ImcGen.stmtImCode.lock();
					ImcGen.exprImCode.lock();

					AbsLogger logger = new AbsLogger(imcGen.logger);
					logger.addSubvisitor(new SemLogger(imcGen.logger));
					logger.addSubvisitor(new FrmLogger(imcGen.logger));
					logger.addSubvisitor(new ImcLogger(imcGen.logger));
					Abstr.absTree.accept(logger, null);
				}
				if (cmdLine.get("--target-phase").equals("imcgen"))
					break;

				// Chunks.
				try (Chunks chunks = new Chunks()) {
					Abstr.absTree.accept(new ChunkGenerator(), null);
					chunks.log();

					// Interpreter interpreter = new Interpreter(Chunks.dataChunks,
					// Chunks.codeChunks);
					// System.out.println("EXIT CODE: " + interpreter.run("_main"));
				}
				if (cmdLine.get("--target-phase").equals("chunks"))
					break;

				// Code generation.
				try (AsmGen asmgen = new AsmGen()) {
					asmgen.genAsmCodes();
					asmgen.log();
				}
				if (cmdLine.get("--target-phase").equals("asmgen"))
					break;

				// Liveness analysis.
				try (LiveAn livean = new LiveAn()) {
					livean.chunksLiveness();
					livean.log();
				}
				if (cmdLine.get("--target-phase").equals("livean"))
					break;

				// Register allocation.
				try (RAlloc ralloc = new RAlloc()) {
					ralloc.tempsToRegs();
					ralloc.log();
				}
				if (cmdLine.get("--target-phase").equals("ralloc"))
					break;

				int endWarnings = Report.numOfWarnings();
				if (begWarnings != endWarnings)
					throw new Report.Error("Compilation stopped.");

			} while (false);

			Report.info("Done.");
		} catch (Report.Error __) {
		}
	}

}
