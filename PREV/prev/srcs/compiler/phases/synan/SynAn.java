/**
 * @author sliva
 */
package compiler.phases.synan;

import compiler.common.report.*;
import compiler.data.symbol.*;
import compiler.data.symbol.Symbol.Term;
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


	// --- Helper functions ---

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

	// --- String consts ---

	private final String EXPECTED_SYMBOLS_STR = "Expected symbol ";
	private final String GOT_STR = " got: ";
	private final String SOURCE_ERR_STR = "Program doesn't start with declaration. Each declaration starts with 'typ', 'var' or 'fun'";
	private final String LPARENTHESIS_ERR_STRING = EXPECTED_SYMBOLS_STR + "'('" + GOT_STR;
	private final String RPARENTHESIS_ERR_STRING = EXPECTED_SYMBOLS_STR + "')'" + GOT_STR;
	private final String LBRACKET_ERR_STRING = EXPECTED_SYMBOLS_STR + "'['" + GOT_STR;
	private final String RBRACKET_ERR_STRING = EXPECTED_SYMBOLS_STR + "']'" + GOT_STR;
	private final String LBRACE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'{'" + GOT_STR;
	private final String RBRACE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'}'" + GOT_STR;
	private final String SEMIC_ERR_STRING = EXPECTED_SYMBOLS_STR + "';'" + GOT_STR;
	private final String COLON_ERR_STRING = EXPECTED_SYMBOLS_STR + "':'" + GOT_STR;
	private final String COMMA_ERR_STRING = EXPECTED_SYMBOLS_STR + "','" + GOT_STR;
	private final String IDENTIFIER_ERR_STRING = EXPECTED_SYMBOLS_STR + "IDENTIFIER" + GOT_STR;
	private final String PARAMETER_DECLARATION_ERR_STR = "Expected IDENTIFIER or ) when parsing parameters";
	private final String PARAMETER_DECLARATION_REST_ERR_STR = "Expected , or ) when parsing parameters";
	private final String TYPE_ERR_STR = "Parsing type and expected IDENTIFIER, VOID, CHAR, ARR, INT, BOOL";
	private final String BODY_ERR_STR = "Expected ; or EXPR when parsing function declaration";
	private final String RECORD_DECL_ERR_STR = "Expected IDENTIFIER when parsing declarations of record";
	private final String EXPR_ERR_STR = "Expected operator or left brace while parsing expresion.";
	private final String EXPR_DISJ_ERR_STR = "Expected operator while parsing disjunct expresion.";
	private final String EXPR_DISJ_REST_ERR_STR = "Expected | or ^ or end of expresion while parsing disjunct expresion.";
	private final String EXPR_CONJ_ERR_STR = "Expected operator while parsing conjuct expresion.";
	private final String EXPR_CONJ_REST_ERR_STR = "Expected & or | or ^ or end of expresion while parsing conjuct expresion.";
	private final String EXPR_REL_ERR_STR = "Expected operator while parsing relation expresion.";
	private final String EXPR_REL_REST_ERR_STR = "Expected ==, !=, <=, >=, < or > while parsing relation expresion.";
	private final String EXPR_ADD_ERR_STR = "Expected operator while parsing additive expresion.";
	private final String EXPR_ADD_REST_ERR_STR = "Expected + or - while parsing additive expresion.";
	private final String EXPR_MUL_ERR_STR = "Expected operator while parsing multiplicative expresion.";
	private final String EXPR_MUL_REST_ERR_STR = "Expected *, / or % while parsing multiplicative expresion.";
	private final String EXPR_PREF_ERR_STR = "Expected unary operator while parsing prefix expresion.";


	// --- Parsing part ---
	private DerNode parseSource() {
		DerNode node = new DerNode(DerNode.Nont.Source);
		switch (currSymb.token) {
			case TYP:
			case VAR:
			case FUN:
				node.add(parseDecls());
				break;
		
			default:
				throw new Report.Error(currSymb.location(), SOURCE_ERR_STR);
				
		}
		return node;
	}

	private DerNode parseDecls() {
		DerNode node = new DerNode(DerNode.Nont.Decls);
		switch (currSymb.token) {
			case TYP:
			case VAR:
			case FUN:
				node.add(parseDecl());
				node.add(parseDeclsRest());
				break;
		
			default:
				throw new Report.Error(currSymb.location(), SOURCE_ERR_STR);
				
		}
		return node;
	}

	private DerNode parseDeclsRest() {
		DerNode node = new DerNode(DerNode.Nont.DeclsRest);
		switch (currSymb.token) {
			case TYP:
			case VAR:
			case FUN:
				node.add(parseDecl());
				node.add(parseDeclsRest());
				break;

			case RBRACE:
			case EOF:
				break;
		
			default:
				throw new Report.Error(currSymb.location(), SOURCE_ERR_STR);
				
		}
		return node;
	}


	private DerNode parseDecl() {
		DerNode node = new DerNode(DerNode.Nont.Decl);
		switch (currSymb.token) {
			case TYP:
				add(node, Symbol.Term.TYP, EXPECTED_SYMBOLS_STR + "'typ'" + GOT_STR + currSymb.token.toString());
				node.add(parseParDecl());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;EXPR_ADD_REST_ERR_STR
			case VAR:
				add(node, Symbol.Term.VAR, EXPECTED_SYMBOLS_STR + "'var'" + GOT_STR  + currSymb.token.toString());
				node.add(parseParDecl());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
			case FUN:
				add(node, Symbol.Term.FUN, EXPECTED_SYMBOLS_STR + "'fun'" + GOT_STR + currSymb.token.toString());
				add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseParDecls());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				node.add(parseBodyEps());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
		
			default:
				throw new Report.Error(currSymb.location(), SOURCE_ERR_STR);
				
		}
		
		return node;
	}

	private DerNode parseParDecls() {
		DerNode node = new DerNode(DerNode.Nont.ParDecls);
		switch (currSymb.token) {
			case IDENTIFIER:		
				node.add(parseParDecl());
				node.add(parseParDeclsRest());
				break;
			case RPARENTHESIS:
					break;
			default:
				throw new Report.Error(currSymb.location(), PARAMETER_DECLARATION_ERR_STR);

		}

		return node;
	}

	private DerNode parseParDeclsRest() {
		DerNode node = new DerNode(DerNode.Nont.ParDeclsRest);
		switch (currSymb.token) {
			case COMMA:
				CheckAndSkip(Symbol.Term.COMMA, EXPECTED_SYMBOLS_STR + "','" + GOT_STR + currSymb.token.toString());	
				node.add(parseParDecl());
				node.add(parseParDeclsRest());
				break;
			case RPARENTHESIS:
					break;
			default:
				throw new Report.Error(currSymb.location(), PARAMETER_DECLARATION_REST_ERR_STR);

		}

		return node;
	}


	private DerNode parseParDecl() {
		DerNode node = new DerNode(DerNode.Nont.ParDecl);
		switch (currSymb.token) {
			case IDENTIFIER:
				add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), PARAMETER_DECLARATION_REST_ERR_STR);

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
				add(node, Symbol.Term.VOID, EXPECTED_SYMBOLS_STR + "VOID" + GOT_STR + currSymb.token.toString());
				break;

			case BOOL:
				add(node, Symbol.Term.BOOL, EXPECTED_SYMBOLS_STR + "BOOL" + GOT_STR + currSymb.token.toString());
				break;

			case CHAR:
				add(node, Symbol.Term.CHAR, EXPECTED_SYMBOLS_STR + "CHAR" + GOT_STR + currSymb.token.toString());
				break;

			case INT:
				add(node, Symbol.Term.INT, EXPECTED_SYMBOLS_STR + "INT" + GOT_STR + currSymb.token.toString());
				break;
			
			case PTR:
				add(node, Symbol.Term.PTR, EXPECTED_SYMBOLS_STR + "PTR" + GOT_STR + currSymb.token.toString());
				node.add(parseType());
				break;

			case ARR:
				add(node, Symbol.Term.ARR, EXPECTED_SYMBOLS_STR + "ARR" + GOT_STR + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LBRACKET, LBRACKET_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				CheckAndSkip(Symbol.Term.RBRACKET, RBRACKET_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				break;

			case REC:
				add(node, Symbol.Term.REC, EXPECTED_SYMBOLS_STR + "REC" + GOT_STR + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseCompDecls());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;

			default:
				throw new Report.Error(currSymb.location(), TYPE_ERR_STR);

		}

		return node;
	}

	private DerNode parseBodyEps() {
		DerNode node = new DerNode(DerNode.Nont.BodyEps);
		switch (currSymb.token) {
			case SEMIC:
				break;

			case ASSIGN:
				add(node, Symbol.Term.ASSIGN, EXPECTED_SYMBOLS_STR + "=" + GOT_STR + currSymb.token.toString());
				node.add(parseExpr());
				break;

			default:
				throw new Report.Error(currSymb.location(), BODY_ERR_STR);

		}

		return node;
	}

	private DerNode parseCompDecls() {
		DerNode node = new DerNode(DerNode.Nont.CompDecls);
		switch (currSymb.token) {
			case IDENTIFIER:
				node.add(parseParDecl());
				node.add(parseCompDeclsRest());
				break;

			default:
				throw new Report.Error(currSymb.location(), RECORD_DECL_ERR_STR);

		}

		return node;
	}

	private DerNode parseCompDeclsRest() {
		DerNode node = new DerNode(DerNode.Nont.CompDeclsRest);
		switch (currSymb.token) {
			case RPARENTHESIS:
				break;

			case COMMA:
				CheckAndSkip(Symbol.Term.COMMA, COMMA_ERR_STRING + currSymb.token.toString());
				node.add(parseParDecl());
				node.add(parseCompDeclsRest());
				break;

			default:
				throw new Report.Error(currSymb.location(), RECORD_DECL_ERR_STR);

		}

		return node;
	}


	private boolean IsOperator() {
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
			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
				return true;
			default:
				return false;
		}
	}

	private DerNode parseExpr() {
		DerNode node = new DerNode(DerNode.Nont.Expr);
		if (IsOperator()) {
			node.add(parseDisjExpr());
			node.add(parseDisjExprRest());
		} else {
			switch (currSymb.token) {
				case LBRACE:
					CheckAndSkip(Symbol.Term.LBRACE, LBRACE_ERR_STRING + currSymb.token.toString());
					node.add(parseStmts());
					CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
					node.add(parseExpr());
					node.add(parseWhereEps());
					CheckAndSkip(Symbol.Term.RBRACE, RBRACE_ERR_STRING + currSymb.token.toString());
				default:
					throw new Report.Error(currSymb.location(), EXPR_ERR_STR);
			}
		}

		return node;
	}

	private DerNode parseDisjExpr() {
		DerNode node = new DerNode(DerNode.Nont.DisjExpr);
		if (IsOperator()) {
			node.add(parseConjExpr());
			node.add(parseConjExprRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_DISJ_ERR_STR);
		}

		return node;
	}

	private boolean IsEndOfExpresion(){
		switch (currSymb.token) {
			case SEMIC:
			case RPARENTHESIS:
			case COLON:
			case COMMA:
			case ASSIGN:
			case RBRACKET:
			case RBRACE:
			case THEN:
			case DO:
			case WHERE:
				return true;
			default:
				return false;
		}
	}

	private DerNode parseDisjExprRest() {
		DerNode node = new DerNode(DerNode.Nont.DisjExprRest);
		if (!IsEndOfExpresion()) {
			switch (currSymb.token) {
				case IOR:
					add(node, Symbol.Term.IOR, EXPECTED_SYMBOLS_STR + "|" + GOT_STR + currSymb.token.toString());
					node.add(parseDisjExpr());
					node.add(parseDisjExprRest());
					break;
				case XOR:
					add(node, Symbol.Term.XOR, EXPECTED_SYMBOLS_STR + "^" + GOT_STR + currSymb.token.toString());
					node.add(parseDisjExpr());
					node.add(parseDisjExprRest());
					break;

				default:
					throw new Report.Error(currSymb.location(), EXPR_DISJ_REST_ERR_STR);
			}
		}

		return node;
	}

	private DerNode parseConjExpr() {
		DerNode node = new DerNode(DerNode.Nont.ConjExpr);
		if (IsOperator()) {
			node.add(parseRelExpr());
			node.add(parseRelExprRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_CONJ_ERR_STR);
		}

		return node;
	}

	private boolean IsDisjExpresion(){
		return IsEndOfExpresion() || currSymb.token == Symbol.Term.IOR || currSymb.token == Symbol.Term.XOR;
	}

	private DerNode parseConjExprRest() {
		DerNode node = new DerNode(DerNode.Nont.ConjExprRest);
		if (!IsDisjExpresion()) {
			switch (currSymb.token) {
				case AND:
					add(node, Symbol.Term.AND, EXPECTED_SYMBOLS_STR + "&" + GOT_STR + currSymb.token.toString());
					node.add(parseConjExpr());
					node.add(parseConjExprRest());
					break;

				default:
					throw new Report.Error(currSymb.location(), EXPR_CONJ_REST_ERR_STR);
			}
		}

		return node;
	}

	private DerNode parseRelExpr() {
		DerNode node = new DerNode(DerNode.Nont.RelExpr);
		if (IsOperator()) {
			node.add(parseAddExpr());
			node.add(parseAddExprRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_REL_ERR_STR);
		}

		return node;
	}

	private boolean IsConjExpresion(){
		return IsDisjExpresion() || currSymb.token == Symbol.Term.AND;
	}

	private DerNode parseRelExprRest() {
		DerNode node = new DerNode(DerNode.Nont.RelExprRest);
		if (!IsConjExpresion()) {
			switch (currSymb.token) {	
				case EQU:
					add(node, Symbol.Term.EQU, EXPECTED_SYMBOLS_STR + "==" + GOT_STR + currSymb.token.toString());
					node.add(parseRelExpr());
					node.add(parseRelExprRest());
					break;

				case NEQ:
					add(node, Symbol.Term.NEQ, EXPECTED_SYMBOLS_STR + "!=" + GOT_STR + currSymb.token.toString());
					node.add(parseRelExpr());
					node.add(parseRelExprRest());
					break;

				case LEQ:
					add(node, Symbol.Term.LEQ, EXPECTED_SYMBOLS_STR + "<=" + GOT_STR + currSymb.token.toString());
					node.add(parseRelExpr());
					node.add(parseRelExprRest());
					break;
				
				case GEQ:
					add(node, Symbol.Term.GEQ, EXPECTED_SYMBOLS_STR + ">=" + GOT_STR + currSymb.token.toString());
					node.add(parseRelExpr());
					node.add(parseRelExprRest());
					break;

				case LTH:
					add(node, Symbol.Term.LTH, EXPECTED_SYMBOLS_STR + "<" + GOT_STR + currSymb.token.toString());
					node.add(parseRelExpr());
					node.add(parseRelExprRest());
					break;

				case GTH:
					add(node, Symbol.Term.GTH, EXPECTED_SYMBOLS_STR + ">" + GOT_STR + currSymb.token.toString());
					node.add(parseRelExpr());
					node.add(parseRelExprRest());
					break;

				default:
					throw new Report.Error(currSymb.location(), EXPR_REL_REST_ERR_STR);
			}
		}

		return node;
	}

	private DerNode parseAddExpr() {
		DerNode node = new DerNode(DerNode.Nont.AddExpr);
		if (IsOperator()) {
			node.add(parseMulExpr());
			node.add(parseMulExprRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_ADD_ERR_STR);
		}

		return node;
	}

	private boolean IsRelExpresion(){
		if (IsConjExpresion()) {
			return true;
		}

		switch (currSymb.token) {
			case EQU:
			case NEQ:
			case LEQ:
			case GEQ:
			case LTH:
			case GTH:
				return true;
			default:
				return false;
		}
	}

	private DerNode parseAddExprRest() {
		DerNode node = new DerNode(DerNode.Nont.AddExprRest);
		if (!IsRelExpresion()) {
			switch (currSymb.token) {	
				case ADD:
					add(node, Symbol.Term.ADD, EXPECTED_SYMBOLS_STR + "+" + GOT_STR + currSymb.token.toString());
					node.add(parseAddExpr());
					node.add(parseAddExprRest());
					break;

				case SUB:
					add(node, Symbol.Term.SUB, EXPECTED_SYMBOLS_STR + "-" + GOT_STR + currSymb.token.toString());
					node.add(parseAddExpr());
					node.add(parseAddExprRest());
					break;

				default:
					throw new Report.Error(currSymb.location(), EXPR_ADD_REST_ERR_STR);
			}
		}

		return node;
	}

	private DerNode parseMulExpr() {
		DerNode node = new DerNode(DerNode.Nont.MulExpr);
		if (IsOperator()) {
			node.add(parsePrefExpr());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_MUL_ERR_STR);
		}

		return node;
	}

	private boolean IsAddExpresion(){
		return IsRelExpresion() || currSymb.token == Symbol.Term.ADD || currSymb.token == Symbol.Term.SUB;
	}


	private DerNode parseMulExprRest() {
		DerNode node = new DerNode(DerNode.Nont.MulExprRest);
		if (!IsAddExpresion()) {
			switch (currSymb.token) {	
				case MUL:
					add(node, Symbol.Term.MUL, EXPECTED_SYMBOLS_STR + "*" + GOT_STR + currSymb.token.toString());
					node.add(parseMulExpr());
					node.add(parseMulExprRest());
					break;

				case DIV:
					add(node, Symbol.Term.DIV, EXPECTED_SYMBOLS_STR + "/" + GOT_STR + currSymb.token.toString());
					node.add(parseMulExpr());
					node.add(parseMulExprRest());
					break;

				case MOD:
					add(node, Symbol.Term.MOD, EXPECTED_SYMBOLS_STR + "%" + GOT_STR + currSymb.token.toString());
					node.add(parseMulExpr());
					node.add(parseMulExprRest());
					break;

				default:
					throw new Report.Error(currSymb.location(), EXPR_MUL_REST_ERR_STR);
			}
		}

		return node;
	}


	private DerNode parsePrefExpr() {
		DerNode node = new DerNode(DerNode.Nont.PrefExpr);
		switch (currSymb.token) {
			case IDENTIFIER:
				node.add(parsePstfExpr());
				node.add(parsePstfExprRest());
				break;
			case LPARENTHESIS:
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				node.add(parseCastEps());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;
			case ADD:
				add(node, Symbol.Term.ADD, EXPECTED_SYMBOLS_STR + "+" + GOT_STR + currSymb.token.toString());
				node.add(parsePrefExpr());
				break;
			case SUB:
				add(node, Symbol.Term.SUB, EXPECTED_SYMBOLS_STR + "-" + GOT_STR + currSymb.token.toString());
				node.add(parsePrefExpr());
				break;
			case NOT:
				add(node, Symbol.Term.NOT, EXPECTED_SYMBOLS_STR + "!" + GOT_STR + currSymb.token.toString());
				node.add(parsePrefExpr());
				break;
			case ADDR:
				add(node, Symbol.Term.ADDR, EXPECTED_SYMBOLS_STR + "$" + GOT_STR + currSymb.token.toString());
				node.add(parsePrefExpr());
				break;
			case DATA:
				add(node, Symbol.Term.DATA, EXPECTED_SYMBOLS_STR + "@" + GOT_STR + currSymb.token.toString());
				node.add(parsePrefExpr());
				break;
			case NEW:
				add(node, Symbol.Term.NEW, EXPECTED_SYMBOLS_STR + "NEW" + GOT_STR + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;
			case DEL:
				add(node, Symbol.Term.DEL, EXPECTED_SYMBOLS_STR + "DEL" + GOT_STR + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;

			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
				node.add(parsePstfExpr());
				node.add(parsePstfExprRest());
				break;
				
			default:
				throw new Report.Error(currSymb.location(), EXPR_PREF_ERR_STR);
		}

		return node;
	}

	// TODO Cast

	/*

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
				//System.out.println("before " + currSymb.token.toString());
				node.add(parseExpr());
				//System.out.println("here " + currSymb.token.toString());
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
				//System.out.println("tukaj");
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
				node.add(parseNEXT_expr_MOD());
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
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
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
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected $ ( or identifier. DEL stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_TYPE_CAST() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_TYPE_CAST);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
				node.add(parse_expr_TYPE_CAST());
				node.add(parseNEXT_expr_ARR_ACC());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected indentifier or left parenthesis. TYPE_CAST stage");
		}

		return node;
	}

	private DerNode parse_expr_TYPE_CAST() {
		DerNode node = new DerNode(DerNode.Nont.exprTYPE_CAST);
		switch (currSymb.token) {
			case IDENTIFIER:
				break;
			case LPARENTHESIS:
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseExprIDE());
				node.add(parse_expr_ENCLOSE_TYPE_CAST());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected ( or identifier. TYPE_CAST stage");
		}

		return node;
	}

	private DerNode parse_expr_ENCLOSE_TYPE_CAST() {
		DerNode node = new DerNode(DerNode.Nont.exprENCLOSE_TYPE_CAST);
		switch (currSymb.token) {
			
			case RPARENTHESIS:
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;

			case COLON:
				CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
				node.add(parseType());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected ( or identifier. TYPE_CAST stage");
		}

		return node;
	}

	private DerNode parseNEXT_expr_ARR_ACC() {
		DerNode node = new DerNode(DerNode.Nont.NEXT_expr_ARR_ACC);
		switch (currSymb.token) {
			case IDENTIFIER:
				node.add(parseExprIDE());
				node.add(parse_expr_ARR_COMP_ACC());
				break;

			default:
				throw new Report.Error(currSymb.location(), "Expected IDENTIFIER. ARR_ACC stage");
		}

		return node;
	}

	private DerNode parse_expr_ARR_COMP_ACC() {
		DerNode node = new DerNode(DerNode.Nont.exprARR_COMP_ACC);
		switch (currSymb.token) {
			
			case LBRACKET:
				CheckAndSkip(Symbol.Term.LBRACKET, LBRACKET_ERR_STRING + currSymb.token.toString());
				node.add(parseExprIDE());
				CheckAndSkip(Symbol.Term.RBRACKET, RBRACKET_ERR_STRING + currSymb.token.toString());
				break;

			case GTH:
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case MOD:
				break;

			case DOT:
				add(node, Symbol.Term.DOT, EXPECTED_SYMBOLS_STR + "DOT got: " + currSymb.token.toString());
				add(node, Symbol.Term.IDENTIFIER, EXPECTED_SYMBOLS_STR + "IDENTIFIER got: " + currSymb.token.toString());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected ( or identifier. ARR_COMP_ACC stage");
		}

		return node;
	}

	private DerNode parseExprIDE() {
		DerNode node = new DerNode(DerNode.Nont.exprIDE);
		switch (currSymb.token) {
			case IDENTIFIER:
				add(node, Symbol.Term.IDENTIFIER, EXPECTED_SYMBOLS_STR + "IDENTIFIER got: " + currSymb.token.toString());
				node.add(parseExprFunction());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected identifier. exprIDE stage");
		}

		return node;
	}


	private DerNode parseExprFunction() {
		DerNode node = new DerNode(DerNode.Nont.exprFunction);
		switch (currSymb.token) {
			case LPARENTHESIS:
				CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
				node.add(parseExprs());
				CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
				break;
			
			case RPARENTHESIS:
			case COLON:
			case LBRACKET:
			case RBRACKET:
			case GTH:
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case MOD:
			case DOT:
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected identifier. ExprFunction( stage " +  currSymb.token.toString());
		}

		return node;
	}

	private DerNode parseExprs() {
		DerNode node = new DerNode(DerNode.Nont.exprs);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
			case NOT:
			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
				node.add(parseExpr());
				node.add(parseExprsEps());
				break;
			
			case RPARENTHESIS:
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected identifier. Exprs stage");
		}

		return node;
	}

	private DerNode parseExprsEps() {
		DerNode node = new DerNode(DerNode.Nont.exprsEps);
		switch (currSymb.token) {
			case COMMA:
				CheckAndSkip(Symbol.Term.COMMA, COMMA_ERR_STRING + currSymb.token.toString());
				node.add(parseExpr());
				node.add(parseExprsEps());
				break;
			
			case RPARENTHESIS:
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected Comma or RPARENTHESIS. ExprsEps stage");
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


	private DerNode parseStatements() {
		DerNode node = new DerNode(DerNode.Nont.Stmts);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
			case NOT:
			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
			case IF:
			case WHILE:
				node.add(parseStatement());
				node.add(parseStatementsEps());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected statement got: " + currSymb.token.toString());
		}

		return node;

	}

	private DerNode parseStatementsEps() {
		DerNode node = new DerNode(DerNode.Nont.StmtsEps);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
			case NOT:
			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
			case IF:
			case WHILE:
				node.add(parseStatement());
				node.add(parseStatementsEps());
				break;

			case COLON:
			case END:
			case ELSE:
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected statement got: " + currSymb.token.toString());
		}

		return node;

	}

	private DerNode parseStatement() {
		DerNode node = new DerNode(DerNode.Nont.Stmt);
		switch (currSymb.token) {
			case IDENTIFIER:
			case LPARENTHESIS:
			case ADD:
			case SUB:
			case ADDR:
			case DATA:
			case NEW:
			case DEL:
			case NOT:
			case PTRCONST:
			case BOOLCONST:
			case VOIDCONST:
			case CHARCONST:
			case STRCONST:
			case INTCONST:
				node.add(parseExpr());
				node.add(parseStatementAssigment());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
			
			
			case IF:
				add(node, Symbol.Term.IF, EXPECTED_SYMBOLS_STR + "IF got: " + currSymb.token.toString());
				node.add(parseExpr());
				add(node, Symbol.Term.THEN, EXPECTED_SYMBOLS_STR + "THEN got: " + currSymb.token.toString());
				node.add(parseStatements());
				node.add(parseStatementELSE());
				add(node, Symbol.Term.END, EXPECTED_SYMBOLS_STR + "END got: " + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
			case WHILE:
				add(node, Symbol.Term.WHILE, EXPECTED_SYMBOLS_STR + "WHILE got: " + currSymb.token.toString());
				node.add(parseExpr());
				add(node, Symbol.Term.DO, EXPECTED_SYMBOLS_STR + "DO got: " + currSymb.token.toString());
				node.add(parseStatements());
				add(node, Symbol.Term.END, EXPECTED_SYMBOLS_STR + "END got: " + currSymb.token.toString());
				CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
				break;
			
			default:
				throw new Report.Error(currSymb.location(), "Expected statement got: " + currSymb.token.toString());
		}

		return node;

	}

	private DerNode parseStatementAssigment() {
		DerNode node = new DerNode(DerNode.Nont.StmtASS);
		switch (currSymb.token) {
			case SEMIC:
				break;

			case ASSIGN:
				add(node, Symbol.Term.ASSIGN, EXPECTED_SYMBOLS_STR + "ASSIGN got: " + currSymb.token.toString());
				node.add(parseExpr());

			default:
				throw new Report.Error(currSymb.location(), "Expected statement got: " + currSymb.token.toString());
		}

		return node;
	}

	private DerNode parseStatementELSE() {
		DerNode node = new DerNode(DerNode.Nont.StmtELSE);
		switch (currSymb.token) {
			case END:
				break;

			case ELSE:
				add(node, Symbol.Term.ELSE, EXPECTED_SYMBOLS_STR + "ELSE got: " + currSymb.token.toString());
				node.add(parseStatements());

			default:
				throw new Report.Error(currSymb.location(), "Expected statement got: " + currSymb.token.toString());
		}

		return node;
	}

	
	private DerNode parseWhere_o() {
		DerNode node = new DerNode(DerNode.Nont.Where_o);
		switch (currSymb.token) {
			case RBRACE:
				break;
		
			case WHERE:
				add(node, Symbol.Term.WHERE, EXPECTED_SYMBOLS_STR + "WHERE got: " + currSymb.token.toString());
				node.add(parseDecl());
				node.add(parseDeclEps());
			break;

			default:
				throw new Report.Error(currSymb.location(), "Expected where got: " + currSymb.token.toString());
		}
		return node;
	}
	*/
}
