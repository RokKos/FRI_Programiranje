/**
 * @author sliva
 */
package compiler.phases.synan;

import compiler.common.report.*;
import compiler.data.symbol.*;
import compiler.data.dertree.*;
import compiler.phases.*;
import compiler.phases.lexan.*;	

/**
 * Syntax analysis.
 * 
 * @author sliva
 */
public class SynAn extends Phase {

	/** The derivation tree of the program being compiled. */
	public static DerTree derTree = null;

	/** The lexical analyzer used by this syntax analyzer. */
	private final LexAn lexAn;

	/**
	 * Constructs a new phase of syntax analysis.
	 */
	public SynAn() {
		super("synan");
		lexAn = new LexAn();
	}

	@Override
	public void close() {
		lexAn.close();
		super.close();
	}

	/**
	 * The parser.
	 * 
	 * This method constructs a derivation tree of the program in the source file.
	 * It calls method {@link #parseSource()} that starts a recursive descent parser
	 * implementation of an LL(1) parsing algorithm.
	 */
	public void parser() {
		currSymb = lexAn.lexer();
		derTree = parseSource();
		if (currSymb.token != Symbol.Term.EOF)
			throw new Report.Error(currSymb, "Unexpected '" + currSymb + "' at the end of a program.");
	}

	/** The lookahead buffer (of length 1). */
	private Symbol currSymb = null;

	/**
	 * Appends the current symbol in the lookahead buffer to a derivation tree node
	 * (typically the node of the derivation tree that is currently being expanded
	 * by the parser) and replaces the current symbol (just added) with the next
	 * input symbol.
	 * 
	 * @param node The node of the derivation tree currently being expanded by the
	 *             parser.
	 */
	private void add(DerNode node) {
		if (currSymb == null)
			throw new Report.InternalError();
		node.add(new DerLeaf(currSymb));
		currSymb = lexAn.lexer();
	}

	/**
	 * If the current symbol is the expected terminal, appends the current symbol in
	 * the lookahead buffer to a derivation tree node (typically the node of the
	 * derivation tree that is currently being expanded by the parser) and replaces
	 * the current symbol (just added) with the next input symbol. Otherwise,
	 * produces the error message.
	 * 
	 * @param node     The node of the derivation tree currently being expanded by
	 *                 the parser.
	 * @param token    The expected terminal.
	 * @param errorMsg The error message.
	 */
	private void add(DerNode node, Symbol.Term token, String errorMsg) {
		if (currSymb == null)
			throw new Report.InternalError();
		if (currSymb.token == token) {
			node.add(new DerLeaf(currSymb));
			currSymb = lexAn.lexer();
		} else
			throw new Report.Error(currSymb, errorMsg);
	}

	private void CheckAndSkip(Symbol.Term token, String errorMsg) {
		if (currSymb.token == token) {
			currSymb = lexAn.lexer();
		} else {
			throw new Report.Error(currSymb, errorMsg);
		}
	}

	private DerNode parseSource() {
		DerNode node = new DerNode(DerNode.Nont.Source);
		switch (currSymb.token) {
			case TYP:
			case VAR:
			case FUN:
				node.add(parseDecl());
				node.add(parseDeclEps());		
				break;
		
			default:
				throw new Report.Error(currSymb.location(), "Program doesn't start with declaration. Each declaration starts with 'typ', 'var' or 'fun'");
				
		}
		return node;
	}


	private final String EXPECTED_SYMBOLS_STR = "Expected symbol ";
	private final String LPARENTHESIS_ERR_STRING = EXPECTED_SYMBOLS_STR + "'(' got: ";
	private final String RPARENTHESIS_ERR_STRING = EXPECTED_SYMBOLS_STR + "')' got: ";
	private final String LBRACKET_ERR_STRING = EXPECTED_SYMBOLS_STR + "'[' got: ";
	private final String RBRACKET_ERR_STRING = EXPECTED_SYMBOLS_STR + "']' got: ";
	private final String LBRACE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'{' got: ";
	private final String RBRACE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'}' got: ";
	private final String SEMIC_ERR_STRING = EXPECTED_SYMBOLS_STR + "';' got: ";
	private final String COLON_ERR_STRING = EXPECTED_SYMBOLS_STR + "':' got: ";
	private final String IDENTIFIER_ERR_STRING = EXPECTED_SYMBOLS_STR + "IDENTIFIER got: ";


	private DerNode parseDecl() {
		DerNode node = new DerNode(DerNode.Nont.Decl);
		switch (currSymb.token) {
			case TYP:
				add(node, Symbol.Term.TYP, EXPECTED_SYMBOLS_STR + "'typ' got: " + currSymb.token.toString());
				node.add(parseArg());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
			case VAR:
				add(node, Symbol.Term.VAR, EXPECTED_SYMBOLS_STR + "'var' got: " + currSymb.token.toString());
				node.add(parseArg());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
			case FUN:
				add(node, Symbol.Term.FUN, EXPECTED_SYMBOLS_STR + "'fun' got: " + currSymb.token.toString());
				add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseArgs());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				node.add(parseProxyExpr());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
		
			default:
				throw new Report.Error(currSymb.location(), "Program doesn't start with declaration. Each declaration starts with 'typ', 'var' or 'fun'");
				
		}
		
		return node;
	}

	private DerNode parseDeclEps() {
		DerNode node = new DerNode(DerNode.Nont.DeclsRest);
		switch (currSymb.token) {
			case TYP:
			case VAR:
			case FUN:
				node.add(parseDecl());
				node.add(parseDeclEps());		
				break;
			case RBRACE:
			case EOF:
				break;
				
			default:
				throw new Report.Error(currSymb.location(), "Program doesn't start with declaration. Each declaration starts with 'typ', 'var' or 'fun'");
				
		}
		return node;
	}

	private DerNode parseArgs() {
		DerNode node = new DerNode(DerNode.Nont.Args);
		switch (currSymb.token) {
			case IDENTIFIER:
				node.add(parseArg());
				node.add(parseArgsEps());
				break;
			case RPARENTHESIS:
				break;

			default:
				throw new Report.Error(currSymb.location(), "Mistake in arguments of function call.");
		}

		return node;

	}

	private DerNode parseArgsEps() {
		DerNode node = new DerNode(DerNode.Nont.ArgsEps);
		switch (currSymb.token) {
			case COMMA:
				CheckAndSkip(Symbol.Term.COMMA, EXPECTED_SYMBOLS_STR + "',' got: " + currSymb.token.toString());
				node.add(parseArg());
				node.add(parseArgsEps());
				break;
			case RPARENTHESIS:
				break;

			default:
				throw new Report.Error(currSymb.location(), "Mistake in arguments of function call.");
		}

		return node;
	}

	private DerNode parseArg() {
		DerNode node = new DerNode(DerNode.Nont.Arg);
		switch (currSymb.token) {
			case IDENTIFIER:
				add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				break;
			case RPARENTHESIS:
				break;

			default:
				throw new Report.Error(currSymb.location(), "Mistake in arguments of function call.");
		}

		return node;
	}

	private DerNode parseType() {
		DerNode node = new DerNode(DerNode.Nont.Type);
		switch (currSymb.token) {
			case IDENTIFIER:
				add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
				break;
			case LPARENTHESIS:
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;

			case VOID:
				add(node, Symbol.Term.VOID, EXPECTED_SYMBOLS_STR + "VOID got: " + currSymb.token.toString());
				break;

			case BOOL:
				add(node, Symbol.Term.BOOL, EXPECTED_SYMBOLS_STR + "BOOL got: " + currSymb.token.toString());
				break;

			case CHAR:
				add(node, Symbol.Term.CHAR, EXPECTED_SYMBOLS_STR + "CHAR got: " + currSymb.token.toString());
				break;

			case INT:
				add(node, Symbol.Term.INT, EXPECTED_SYMBOLS_STR + "INT got: " + currSymb.token.toString());
				break;

			case ARR:
				add(node, Symbol.Term.ARR, EXPECTED_SYMBOLS_STR + "ARR got: " + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LBRACKET, LBRACKET_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				CheckAndSkip(Symbol.Term.RBRACKET, RBRACKET_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				break;

			case REC:
				add(node, Symbol.Term.REC, EXPECTED_SYMBOLS_STR + "REC got: " + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseArgs());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;

			case PTR:
				add(node, Symbol.Term.PTR, EXPECTED_SYMBOLS_STR + "PTR got: " + currSymb.token.toString());
				node.add(parseType());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected type.");
		}

		return node;
	}


	private DerNode parseProxyExpr() {
		DerNode node = new DerNode(DerNode.Nont.P_expr);
		switch (currSymb.token) {
			case SEMIC:
				break;
			case ASSIGN:
				add(node, Symbol.Term.ASSIGN, EXPECTED_SYMBOLS_STR + "= got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected semicolon or = and then expresion. But instead got: " + currSymb.token.toString());
		}
		
		return node;
	}

	private DerNode parseExpr() {
		DerNode node = new DerNode(DerNode.Nont.Expr);

		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case NOT:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				node.add(parseNEXT_expr_XOR());
				node.add(parse_exprOR());
				break;

			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
				node.add(parseLiteral());
				break;

			case LBRACE:
				CheckAndSkip(Symbol.Term.LBRACE, LBRACE_ERR_STRING + currSymb.token.toString());
				
				node.add(parseStatements());
				CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				node.add(parseWhere_o());

				CheckAndSkip(Symbol.Term.RBRACE, RBRACE_ERR_STRING + currSymb.token.toString());
		
			default:
				throw new Report.Error(currSymb.location(), "Expected literal or identifier or operator or left brace.");
		}

		return node;
	}
	
	private DerNode parseNEXT_expr_XOR() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_XOR);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case NOT:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				node.add(parseNEXT_expr_AND());
				node.add(parse_exprXOR());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator");
		}

		return node;
	}
	
	private DerNode parse_exprOR() {
		DerNode node = new DerNode(DerNode.Nont.exprOR);

		switch (currSymb.token) {
			case SEMIC:
			case IDENTIFIER:
			case LPARENTHESIS:
			case RPARENTHESIS:
			case COLON:
			case ASSIGN:
			case RBRACKET:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
			case RBRACE:
			case WHERE:
			case THEN:
			case DO:
				break;

			case IOR:
				CheckAndSkip(Symbol.Term.IOR, EXPECTED_SYMBOLS_STR + "| got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_XOR());
				node.add(parse_exprOR());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator");
		}

		return node;
	}


	private DerNode parseNEXT_expr_AND() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_AND);
		return node;
	}
	
	private DerNode parse_exprXOR() {
		DerNode node = new DerNode(DerNode.Nont.exprXOR);
		return node;
	}
	

	
	private DerNode parseLiteral() {
		DerNode node = new DerNode(DerNode.Nont.Literal);
		
		switch (currSymb.token) {
			case PTRCONST:
				add(node, Symbol.Term.PTRCONST, EXPECTED_SYMBOLS_STR + "PTRCONST got: " + currSymb.token.toString());
				break;
			case BOOLCONST:
				add(node, Symbol.Term.BOOLCONST, EXPECTED_SYMBOLS_STR + "BOOLCONST got: " + currSymb.token.toString());
				break;
			case VOIDCONST:
				add(node, Symbol.Term.VOIDCONST, EXPECTED_SYMBOLS_STR + "VOIDCONST got: " + currSymb.token.toString());
				break;
			case CHARCONST:
				add(node, Symbol.Term.CHARCONST, EXPECTED_SYMBOLS_STR + "CHARCONST got: " + currSymb.token.toString());
				break;
			case STRCONST:
				add(node, Symbol.Term.STRCONST, EXPECTED_SYMBOLS_STR + "STRCONST got: " + currSymb.token.toString());
				break;
			case INTCONST:
				add(node, Symbol.Term.INTCONST, EXPECTED_SYMBOLS_STR + "INTCONST got: " + currSymb.token.toString());
				break;
		
			default:
				throw new Report.Error(currSymb.location(), "Expected literal got: " + currSymb.token.toString());
				
		}


		return node;
	}


	// TODO: Finish
	private DerNode parseStatements() {
		return new DerNode(DerNode.Nont.Stmts);
	}
	
	private DerNode parseWhere_o() {
		return new DerNode(DerNode.Nont.Where_o);
	}
}
