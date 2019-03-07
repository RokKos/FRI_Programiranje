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

	private Map<String, Term> kStringToSymbols = new HashMap<String, Term>(){{
		/*
		put("", Term.EOF),
		put("", Term.IOR),
		put("", Term.XOR),
		put("", Term.AND),
		put("", Term.EQU),
		put("", Term.NEQ),
		put("", Term.LTH),
		put("", Term.GTH),
		put("", Term.LEQ),
		put("", Term.GEQ),
		put("", Term.ADD),
		put("", Term.SUB),
		put("", Term.MUL),
		put("", Term.DIV),
		put("", Term.MOD),
		put("", Term.NOT),
		put("", Term.ADDR),
		put("", Term.DATA),
		put("", Term.ASSIGN),
		put("", Term.COLON),
		put("", Term.COMMA),
		put("", Term.DOT),
		put("", Term.SEMIC),
		put("", Term.LBRACE),
		put("", Term.RBRACE),
		put("", Term.LBRACKET),
		put("", Term.RBRACKET),
		put("", Term.LPARENTHESIS),
		put("", Term.RPARENTHESIS),
		*/
}};
	private Map<String, Term> kStringToKeyWords = new HashMap<String, Term>(){{
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
	}};

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
						// TODO: Make this reseting better
						lexeme = "" + c;
						startLine = line;
						startColumn = column;

						if (kDebugOn) {
							System.out.println("State: " + state);
						}
						break;

					case kCharConst:
						char singleQuote = (char)srcFile.read();
						if (singleQuote != kSingleQuote) {
							// TODO: Throw error
							if (kDebugOn) {
								System.out.println("Lexer error: " + lexeme + " pos: " + line + " " + column);
								return new Symbol(Term.EOF, lexeme, new Location(startLine, startColumn, line, column + 1));	
							}	
						} else {
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

					case kStrConst:
						if (c == kDoubleQuote){
							return new Symbol(Term.STRCONST, lexeme, new Location(startLine, startColumn, line, column));
						}
						break;

					case kComment:
						if (c == kNewLine) {
							state = LexerState.kStart;
						}
						break;
					

					case kIdentifier:
						if (!(IsLetter(c) || IsNumber(c))){
							lexeme = lexeme.substring(0, lexeme.length() - 1);
							srcFile.reset();
							return new Symbol(ReturnKeywordIfPossible(lexeme), lexeme, new Location(startLine, startColumn, line, column));
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
		for (String key : kStringToSymbols.keySet()){
			if (sC.equals(key)){
				return LexerState.kSymbol;
			}
		}

		System.out.println("Missing indetifier state");
		return LexerState.kStart;
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


	// --- Helpers ---
	private boolean IsNumber(char c){
		return c >= kZeroChar && c <= kNineChar;
	}

	private boolean IsLetter(char c){
		return c == kUnderScore || (c >= kBigA && c <= kBigZ) || (c >= kSmallA && c <= kSmallZ);
	}

	private Term ReturnKeywordIfPossible(String lexeme) {
		if (kStringToKeyWords.containsKey(lexeme)){
			return kStringToKeyWords.get(lexeme);
		}

		return Term.IDENTIFIER;
	}
}
