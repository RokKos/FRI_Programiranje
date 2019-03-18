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
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. XOR stage");
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
			case COMMA:
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
				add(node, Symbol.Term.IOR, EXPECTED_SYMBOLS_STR + "| got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_XOR());
				node.add(parse_exprOR());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. OR stage");
		}

		return node;
	}


	private DerNode parseNEXT_expr_AND() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_AND);
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
				node.add(parseNEXT_expr_EQ());
				node.add(parse_exprAND());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. AND stage");
		}

		return node;
	}
	
	private DerNode parse_exprXOR() {
		DerNode node = new DerNode(DerNode.Nont.exprXOR);
		switch (currSymb.token) {
			case SEMIC:
			case IDENTIFIER:
			case LPARENTHESIS:
			case RPARENTHESIS:
			case COMMA:
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
			case IOR:
				break;

			case XOR:
				add(node, Symbol.Term.XOR, EXPECTED_SYMBOLS_STR + "^ got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_AND());
				node.add(parse_exprXOR());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. XOR stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_EQ() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_EQ);
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
				node.add(parseNEXT_expr_NEQ());
				node.add(parse_exprEQ());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. EQ stage");
		}

		return node;
	}
	

	private DerNode parse_exprAND() {
		DerNode node = new DerNode(DerNode.Nont.exprAND);
		switch (currSymb.token) {
			case SEMIC:
			case IDENTIFIER:
			case LPARENTHESIS:
			case RPARENTHESIS:
			case COMMA:
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
			case IOR:
			case XOR:
				break;

			case AND:
				add(node, Symbol.Term.AND, EXPECTED_SYMBOLS_STR + "& got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_EQ());
				node.add(parse_exprAND());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. AND stage");
		}

		return node;
	}

	private DerNode parse_exprEQ() {
		DerNode node = new DerNode(DerNode.Nont.exprEQ);
		switch (currSymb.token) {
			case SEMIC:
			case IDENTIFIER:
			case LPARENTHESIS:
			case RPARENTHESIS:
			case COMMA:
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
			case IOR:
			case XOR:
			case AND:
				break;

			case EQU:
				add(node, Symbol.Term.EQU, EXPECTED_SYMBOLS_STR + "== got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_NEQ());
				node.add(parse_exprEQ());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. EQU stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_NEQ() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_NEQ);
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
				node.add(parseNEXT_expr_LESS());
				node.add(parse_exprNEQ());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. NEQ stage");
		}

		return node;
	}


	private DerNode parse_exprNEQ() {
		DerNode node = new DerNode(DerNode.Nont.exprNEQ);
		switch (currSymb.token) {
			case SEMIC:
			case IDENTIFIER:
			case LPARENTHESIS:
			case RPARENTHESIS:
			case COMMA:
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
			case IOR:
			case XOR:
			case AND:
			case EQU:
				break;

			case NEQ:
				add(node, Symbol.Term.EQU, EXPECTED_SYMBOLS_STR + "!= got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_LESS());
				node.add(parse_exprNEQ());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. NEQ stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_LESS() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_LESS);
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
				node.add(parseNEXT_expr_GREAT());
				node.add(parse_expr_LESS());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. LESS stage");
		}

		return node;
	}


	private DerNode parse_expr_LESS() {
		DerNode node = new DerNode(DerNode.Nont.exprLESS);
		switch (currSymb.token) {
			
			case LTH:
				node.add(parse_expr_LESS_EQ());
				node.add(parseNEXT_expr_GREAT());
				node.add(parse_expr_LESS());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. LESSs stage");
		}

		return node;
	}

	private DerNode parse_expr_LESS_EQ() {
		DerNode node = new DerNode(DerNode.Nont.exprLESS_EQ);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
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

			case ASSIGN:
				add(node, Symbol.Term.ASSIGN, EXPECTED_SYMBOLS_STR + "= got: " + currSymb.token.toString());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. LESS_EQ stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_GREAT() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_GREAT);
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
				node.add(parseNEXT_expr_BIN_PLUS());
				node.add(parse_expr_GREAT());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. GREAT stage");
		}

		return node;
	}

	private DerNode parse_expr_GREAT() {
		DerNode node = new DerNode(DerNode.Nont.exprGREAT);
		switch (currSymb.token) {
			
			case GTH:
				node.add(parse_expr_GREAT_EQ());
				node.add(parseNEXT_expr_GREAT());
				node.add(parse_expr_GREAT());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. GREAT stage");
		}

		return node;
	}

	private DerNode parse_expr_GREAT_EQ() {
		DerNode node = new DerNode(DerNode.Nont.exprGREAT_EQ);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
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

			case ASSIGN:
				CheckAndSkip(Symbol.Term.ASSIGN, EXPECTED_SYMBOLS_STR + "= got: " + currSymb.token.toString());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. GREAT_EQ stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_BIN_PLUS() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_BIN_PLUS);
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
				node.add(parseNEXT_expr_BIN_MINUS());
				node.add(parse_expr_PLUS());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. BIN_PLUS stage");
		}

		return node;
	}


	private DerNode parse_expr_PLUS() {
		DerNode node = new DerNode(DerNode.Nont.exprPLUS);
		switch (currSymb.token) {
			case GTH:
				break;
			case ADD:
				add(node, Symbol.Term.ADD, EXPECTED_SYMBOLS_STR + "+ got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_BIN_MINUS());
				node.add(parse_expr_PLUS());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or +. PLUS stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_BIN_MINUS() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_BIN_MINUS);
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
				node.add(parseNEXT_expr_TIMES());
				node.add(parse_expr_MINUS());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. BIN_MINUS stage");
		}

		return node;
	}

	private DerNode parse_expr_MINUS() {
		DerNode node = new DerNode(DerNode.Nont.exprMINUS);
		switch (currSymb.token) {
			case GTH:
			case ADD:
				break;
			case SUB:
				add(node, Symbol.Term.SUB, EXPECTED_SYMBOLS_STR + "- got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_TIMES());
				node.add(parse_expr_MINUS());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or +. MINUS stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_TIMES() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_TIMES);
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
				node.add(parseNEXT_expr_DIV());
				node.add(parse_expr_TIMES());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. TIMES stage");
		}

		return node;
	}

	private DerNode parse_expr_TIMES() {
		DerNode node = new DerNode(DerNode.Nont.exprTIMES);
		switch (currSymb.token) {
			case GTH:
			case ADD:
			case SUB:
				break;
			case MUL:
				add(node, Symbol.Term.MUL, EXPECTED_SYMBOLS_STR + "* got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_DIV());
				node.add(parse_expr_TIMES());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or -. TIMES stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_DIV() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_DIV);
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
				node.add(parseNEXT_expr_DIV());
				node.add(parse_expr_DIV());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. DIV stage");
		}

		return node;
	}

	private DerNode parse_expr_DIV() {
		DerNode node = new DerNode(DerNode.Nont.exprDIV);
		switch (currSymb.token) {
			case GTH:
			case ADD:
			case SUB:
			case MUL:
				break;
			case DIV:
				add(node, Symbol.Term.DIV, EXPECTED_SYMBOLS_STR + "/ got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_MOD());
				node.add(parse_expr_DIV());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or - or *. DIV stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_MOD() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_MOD);
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
				node.add(parseNEXT_expr_UNARY_NOT());
				node.add(parse_expr_MOD());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. MOD stage");
		}

		return node;
	}

	private DerNode parse_expr_MOD() {
		DerNode node = new DerNode(DerNode.Nont.exprMOD);
		switch (currSymb.token) {
			case GTH:
			case ADD:
			case SUB:
			case MUL:
			case DIV:
				break;
			case MOD:
				add(node, Symbol.Term.MOD, EXPECTED_SYMBOLS_STR + "/ got: " + currSymb.token.toString());
				node.add(parseNEXT_expr_UNARY_NOT());
				node.add(parse_expr_MOD());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or - or * or /. MOD stage");
		}

		return node;
	}
	

	private DerNode parseNEXT_expr_UNARY_NOT() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_UNARY_NOT);
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
				node.add(parse_expr_UNARY_NOT());
				node.add(parseNEXT_expr_UNARY_PLUS());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. UNARY_NOT stage");
		}

		return node;
	}

	private DerNode parse_expr_UNARY_NOT() {
		DerNode node = new DerNode(DerNode.Nont.exprUNARY_NOT);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				break;
			case NOT:
				add(node, Symbol.Term.NOT, EXPECTED_SYMBOLS_STR + "! got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or - or * or /. UNARY_NOT stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_UNARY_PLUS() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_UNARY_PLUS);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				node.add(parse_expr_UNARY_PLUS());
				node.add(parseNEXT_expr_UNARY_MINUS());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. UNARY_PLUS stage");
		}

		return node;
	}

	private DerNode parse_expr_UNARY_PLUS() {
		DerNode node = new DerNode(DerNode.Nont.exprUNARY_PLUS);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				break;
			case ADD:
				add(node, Symbol.Term.ADD, EXPECTED_SYMBOLS_STR + "+ got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or - or * or /. UNARY_PLUS stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_UNARY_MINUS() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_UNARY_MINUS);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				node.add(parse_expr_UNARY_MINUS());
				node.add(parseNEXT_expr_ADDR());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. UNARY_MINUS stage");
		}

		return node;
	}

	private DerNode parse_expr_UNARY_MINUS() {
		DerNode node = new DerNode(DerNode.Nont.exprUNARY_MINUS);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				break;
			case SUB:
				add(node, Symbol.Term.SUB, EXPECTED_SYMBOLS_STR + "- got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or - or * or /. UNARY_MINUS stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_ADDR() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_ADDR);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
				node.add(parse_expr_ADDR());
				node.add(parseNEXT_expr_DATA());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. ADDR stage");
		}

		return node;
	}

	private DerNode parse_expr_ADDR() {
		DerNode node = new DerNode(DerNode.Nont.exprADDR);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case DATA:
			case NEW:
			case DEL:
				break;
			case ADDR:
				add(node, Symbol.Term.ADDR, EXPECTED_SYMBOLS_STR + "$ got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected >  or + or - or * or /. ADDR stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_DATA() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_DATA);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case DATA:
			case NEW:
			case DEL:
				node.add(parse_expr_DATA());
				node.add(parseNEXT_expr_NEW());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. DATA stage");
		}

		return node;
	}

	private DerNode parse_expr_DATA() {
		DerNode node = new DerNode(DerNode.Nont.exprDATA);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case NEW:
			case DEL:
				break;
			case DATA:
				add(node, Symbol.Term.DATA, EXPECTED_SYMBOLS_STR + "$ got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected $ ( or identifier. DATA stage");
		}

		return node;
	}

	private DerNode parse_expr_NEW() {
		DerNode node = new DerNode(DerNode.Nont.exprNEW);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case DEL:
				break;
			case NEW:
				add(node, Symbol.Term.NEW, EXPECTED_SYMBOLS_STR + "new got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected $ ( or identifier. NEW stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_NEW() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_NEW);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case NEW:
			case DEL:
				node.add(parse_expr_NEW());
				node.add(parseNEXT_expr_DEL());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. NEW stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_DEL() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_DEL);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case DEL:
				node.add(parse_expr_DEL());
				node.add(parseNEXT_expr_TYPE_CAST());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis or operator. DEL stage");
		}

		return node;
	}

	private DerNode parse_expr_DEL() {
		DerNode node = new DerNode(DerNode.Nont.exprDEL);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
				break;
			case DEL:
				add(node, Symbol.Term.DEL, EXPECTED_SYMBOLS_STR + "DEL got: " + currSymb.token.toString());
				node.add(parseExpr());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected $ ( or identifier. DEL stage");
		}

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
