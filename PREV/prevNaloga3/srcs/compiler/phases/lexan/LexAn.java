/**
 * @author sliva
 */
package compiler.phases.lexan;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import compiler.Main;
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

	private int line = 1;
	private int column = 1;

	private enum LexerState {
		kStart, kCharConst, kIntConst, kStrConst, kComment, kIdentifier, kSymbol, kLast
	}

	private Map<String, Term> kStringToSymbols = new HashMap<String, Term>() {
		{
			put("|", Term.IOR);
			put("^", Term.XOR);
			put("&", Term.AND);
			put("==", Term.EQU);
			put("!=", Term.NEQ);
			put("<", Term.LTH);
			put(">", Term.GTH);
			put("<=", Term.LEQ);
			put(">=", Term.GEQ);
			put("+", Term.ADD);
			put("-", Term.SUB);
			put("*", Term.MUL);
			put("/", Term.DIV);
			put("%", Term.MOD);
			put("!", Term.NOT);
			put("$", Term.ADDR);
			put("@", Term.DATA);
			put("=", Term.ASSIGN);
			put(":", Term.COLON);
			put(",", Term.COMMA);
			put(".", Term.DOT);
			put(";", Term.SEMIC);
			put("{", Term.LBRACE);
			put("}", Term.RBRACE);
			put("[", Term.LBRACKET);
			put("]", Term.RBRACKET);
			put("(", Term.LPARENTHESIS);
			put(")", Term.RPARENTHESIS);
		}
	};
	private Map<String, Term> kStringToKeyWords = new HashMap<String, Term>() {
		{
			put("new", Term.NEW);
			put("del", Term.DEL);
			put("none", Term.VOIDCONST);
			put("true", Term.BOOLCONST);
			put("false", Term.BOOLCONST);
			put("null", Term.PTRCONST);
			put("void", Term.VOID);
			put("bool", Term.BOOL);
			put("char", Term.CHAR);
			put("int", Term.INT);
			put("ptr", Term.PTR);
			put("arr", Term.ARR);
			put("rec", Term.REC);
			put("do", Term.DO);
			put("else", Term.ELSE);
			put("end", Term.END);
			put("fun", Term.FUN);
			put("if", Term.IF);
			put("then", Term.THEN);
			put("typ", Term.TYP);
			put("var", Term.VAR);
			put("where", Term.WHERE);
			put("while", Term.WHILE);
		}
	};

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
			while ((value = srcFile.read()) != -1) {

				CheckAsciiValue(value, 0, 127);

				char c = (char) value;
				lexeme += c;

				switch (state) {
				case kStart:
					state = DecideStartingState(c);
					// TODO: Make this reseting better
					lexeme = "" + c;
					startLine = line;
					startColumn = column;

					if (Main.kDebugOn) {
						System.out.println("State: " + state);
					}
					break;

				case kCharConst:
					CheckAsciiValue(value, 32, 127);
					char singleQuote = (char) srcFile.read();
					// Because we read two characters and we don't go to MoveLocation
					column += 2;
					if (singleQuote != kSingleQuote) {
						if (Main.kDebugOn) {
							System.out.println("Lexer error: " + lexeme + " pos: " + line + " " + column);
						}
						throw new Report.Error(new Location(startLine, startColumn, line, column),
								"Character constant is more than one character long. Are you missing ' ?");
					} else {
						lexeme += singleQuote;
						return new Symbol(Term.CHARCONST, lexeme,
								new Location(startLine, startColumn, line, column - 1));
					}

				case kIntConst:
					if (!IsNumber(c)) {
						lexeme = lexeme.substring(0, lexeme.length() - 1);
						srcFile.reset();
						return new Symbol(Term.INTCONST, lexeme,
								new Location(startLine, startColumn, line, column - 1));
					}
					break;

				case kStrConst:
					CheckAsciiValue(value, 32, 127);
					if (c == kDoubleQuote) {
						return new Symbol(Term.STRCONST, lexeme, new Location(startLine, startColumn, line, column));
					}
					break;

				case kComment:
					if (c == kNewLine) {
						state = LexerState.kStart;
					}
					break;

				case kIdentifier:
					if (!(IsLetter(c) || IsNumber(c))) {
						// Hacky but only this simbols is not caught
						// Others are handled by others states
						// If more symbols like this come up change this
						if (c == '~') {
							throw new Report.Error(new Location(startLine, startColumn, line, column),
									"Character ~ is prohibited in identiier");
						} else {
							lexeme = lexeme.substring(0, lexeme.length() - 1);
							srcFile.reset();
							return new Symbol(ReturnKeywordIfPossible(lexeme), lexeme,
									new Location(startLine, startColumn, line, column - 1));
						}
					}
					break;

				case kSymbol:

					Term possibleSymbol = ReturnSymbolIfPossible(lexeme);
					if (possibleSymbol != Term.EOF) {
						column++;
						return new Symbol(possibleSymbol, lexeme,
								new Location(startLine, startColumn, line, column - 1));
					} else {
						lexeme = lexeme.substring(0, lexeme.length() - 1);
						srcFile.reset();
						return new Symbol(ReturnSymbolIfPossible(lexeme), lexeme,
								new Location(startLine, startColumn, startLine, startColumn));
					}

				case kLast:
				default:
					if (Main.kDebugOn) {
						System.out.println("Invalid state");
					}
					break;
				}

				srcFile.mark(1);
				MoveLocation(c);

				if (Main.kDebugOn) {
					System.out.println("Read character: " + c + " in line:" + line + " collumn:" + column);
				}

				// TODO: Check for keyword
				// return new Symbol(Term.IDENTIFIER, lexeme, new Location(line, column));
			}

			// Handle EOF with somethign in
			switch (state) {
			case kStart:
				return new Symbol(Term.EOF, lexeme, new Location(line, column));
			case kCharConst:
				throw new Report.Error(new Location(startLine, startColumn, line, column),
						"Character constant is not closed. Are you missing ' ?");
			case kIntConst:
				return new Symbol(Term.INTCONST, lexeme, new Location(startLine, startColumn, line, column - 1));
			case kStrConst:
				throw new Report.Error(new Location(startLine, startColumn, line, column),
						"String constant is not closed. Are you missing \" ?");
			case kComment:
				return new Symbol(Term.EOF, lexeme, new Location(line, column));
			case kIdentifier:
				return new Symbol(ReturnKeywordIfPossible(lexeme), lexeme,
						new Location(startLine, startColumn, line, column - 1));
			case kSymbol:
				return new Symbol(ReturnSymbolIfPossible(lexeme), lexeme,
						new Location(startLine, startColumn, line, column - 1));
			case kLast:
				if (Main.kDebugOn) {
					System.out.println("Error wrong state");
				}
				throw new Report.Error(new Location(startLine, startColumn, line, column), "Wrong state: kLast");
			default:
				throw new Report.Error(new Location(startLine, startColumn, line, column), "Unhandled state");
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
			return LexerState.kCharConst;
		} else if (IsNumber(c)) {
			return LexerState.kIntConst;
		} else if (c == kDoubleQuote) {
			return LexerState.kStrConst;
		} else if (c == kHash) {
			return LexerState.kComment;
		} else if (IsLetter(c)) {
			return LexerState.kIdentifier;
		} else if (c == kSpace || c == kTab || c == kNewLine || c == kCarrygaReturn) {
			return LexerState.kStart;
		}

		String sC = String.valueOf(c);
		Term possibleSymbol = ReturnSymbolIfPossible(sC);
		if (possibleSymbol != Term.EOF) {
			return LexerState.kSymbol;
		}

		if (Main.kDebugOn) {
			System.out.println("Missing indetifier state");
		}
		return LexerState.kStart;
	}

	private final int kTabLenght = 8;

	private void MoveLocation(char c) {
		if (c == kTab) {
			column += kTabLenght;
		} else if (c == kNewLine) {
			column = 1;
			line++;
		} else if (c == kCarrygaReturn) {
			column = 1;
		} else {
			column++;
		}
	}

	// --- Helpers ---
	private boolean IsNumber(char c) {
		return c >= kZeroChar && c <= kNineChar;
	}

	private boolean IsLetter(char c) {
		return c == kUnderScore || (c >= kBigA && c <= kBigZ) || (c >= kSmallA && c <= kSmallZ);
	}

	private Term ReturnKeywordIfPossible(String lexeme) {
		if (kStringToKeyWords.containsKey(lexeme)) {
			return kStringToKeyWords.get(lexeme);
		}

		return Term.IDENTIFIER;
	}

	private Term ReturnSymbolIfPossible(String lexeme) {
		if (kStringToSymbols.containsKey(lexeme)) {
			return kStringToSymbols.get(lexeme);
		}

		return Term.EOF;
	}

	private void CheckAsciiValue(int value, int low, int high) {
		if (value < low || value > high) {
			throw new Report.Error(new Location(line, column, line, column),
					"Character in input file is not part of standart ASCII table. Are you using weird characters in your code?");
		}
	}
}
