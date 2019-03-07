/**
 * @author sliva
 */
package compiler.phases.lexan;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import compiler.common.report.*;
import compiler.data.symbol.*;
import compiler.data.symbol.Symbol.Term;
import compiler.phases.*;

/**
 * Lexical analysis.
 * 
 * @author sliva
 */
public class LexAn extends Phase {

	/** The name of the source file. */
	private final String srcFileName;

	/** The source file reader. */
	private final BufferedReader srcFile;

	/**
	 * Constructs a new phase of lexical analysis.
	 */
	public LexAn() {
		super("lexan");
		srcFileName = compiler.Main.cmdLineArgValue("--src-file-name");
		try {
			srcFile = new BufferedReader(new FileReader(srcFileName));
			line = 1;
			column = 1;
		} catch (IOException ___) {
			throw new Report.Error("Cannot open source file '" + srcFileName + "'.");
		}
	}

	@Override
	public void close() {
		try {
			srcFile.close();
		} catch (IOException ___) {
			Report.warning("Cannot close source file '" + this.srcFileName + "'.");
		}
		super.close();
	}

	/**
	 * The lexer.
	 * 
	 * This method returns the next symbol from the source file. To perform the
	 * lexical analysis of the entire source file, this method must be called until
	 * it returns EOF. This method calls {@link #lexify()}, logs its result if
	 * requested, and returns it.
	 * 
	 * @return The next symbol from the source file or EOF if no symbol is available
	 *         any more.
	 */
	public Symbol lexer() {
		Symbol symb = lexify();
		if (symb.token != Symbol.Term.EOF)
			symb.log(logger);
		return symb;
	}


	private boolean kDebugOn = true;
	private int line = 1;
	private int column = 1;

	private enum LexerState {
		kStart, kCharConst, kIntConst, kStrConst, kComment, kIdentifier, kSymbol, kLast
	}

	private Map<String, Term> kStringToSymbols = new HashMap<String, Term>(){{put("<", Term.LTH);}};

	/**
	 * Performs the lexical analysis of the source file.
	 * 
	 * This method returns the next symbol from the source file. To perform the
	 * lexical analysis of the entire source file, this method must be called until
	 * it returns EOF.
	 * 
	 * @return The next symbol from the source file or EOF if no symbol is available
	 *         any more.
	 */
	private Symbol lexify() {
		int value;
		int startLine = line;
		int startColumn = column;
		
		
		LexerState state = LexerState.kStart;
		String lexeme = "";

		try {
			srcFile.mark(1);
			while((value = srcFile.read()) != -1) {
				
				char c = (char)value;
				lexeme += c;

				switch (state) {
					case kStart:
						state = DecideStartingState(c);
						if (kDebugOn) {
							System.out.println("State: " + state);
						}
						break;

					case kCharConst:
						char singleQuote = (char)srcFile.read();
						if (singleQuote != kSingleQuote) {
							// TODO: Throw error
						}else {
							System.out.println("here2");
							lexeme += singleQuote;
							return new Symbol(Term.CHARCONST, lexeme, new Location(startLine, startColumn, line, column + 1));
						}
						break;

					case kIntConst:
						if (!IsNumber(c)){
							lexeme = lexeme.substring(0, lexeme.length() - 1);
							srcFile.reset();
							return new Symbol(Term.INTCONST, lexeme, new Location(startLine, startColumn, line, column));
						}
						break;
				
					default:
						System.out.println("Invalid state");	
						break;
				}

				srcFile.mark(1);
				MoveLocation(c);

				
				if (kDebugOn) {
					System.out.println("Read character: " + c + " in line:" + line + " collumn:" + column);
				}

				// TODO: Check for keyword
				//return new Symbol(Term.IDENTIFIER, lexeme, new Location(line, column));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		 
		 return new Symbol(Term.EOF, lexeme, new Location(line, column));
	}


	private final char kSingleQuote = '\'';
	private final char kDoubleQuote = '"';
	private final char kZeroChar = '0';
	private final char kNineChar = '9';
	private final char kHash = '#';
	private final char kUnderScore = '_';
	private final char kBigA = 'A';
	private final char kBigZ = 'Z';
	private final char kSmallA = 'a';
	private final char kSmallZ = 'z';
	private final char kSpace = ' ';
	private final char kTab = 9;
	private final char kNewLine = 10;
	private final char kCarrygaReturn = 13;

	private LexerState DecideStartingState(char c) {
		if (c == kSingleQuote) {
			System.out.println("here");
			return LexerState.kCharConst;
		} else if (IsNumber(c)) {
			return LexerState.kIntConst;
		} else if (c == kDoubleQuote) {
			return LexerState.kStrConst;
		} else if (c == kHash) {
			return LexerState.kComment;
		} else if (c == kUnderScore || (c >= kBigA && c <= kBigZ) || (c >= kSmallA && c <= kSmallZ)) {
			return LexerState.kIdentifier;
		} else if (c == kSpace || c == kTab || c == kNewLine || c == kCarrygaReturn) {
			return LexerState.kStart;
		}

		String sC = String.valueOf(c);
		for (String key : kStringToSymbols.keySet()){
			if (sC.equals(key)){
				return LexerState.kSymbol;
			}
		}

		System.out.println("Missing indetifier state");
		return LexerState.kStart;
	}

	private boolean IsNumber(char c){
		return c >= kZeroChar && c <= kNineChar;
	}

	private final int kTabLenght = 8;

	private void MoveLocation(char c) {
		if (c == kTab) {
			column += kTabLenght;
		} else if(c == kNewLine) {
			column = 1;
			line++;
		} else if (c == kCarrygaReturn) {
			column = 1;
		} else {
			column++;
		}
	}
}
