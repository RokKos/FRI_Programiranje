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
	private final String DOT_ERR_STRING = EXPECTED_SYMBOLS_STR + "'.'" + GOT_STR;
	private final String ASSIGN_ERR_STRING = EXPECTED_SYMBOLS_STR + "'='" + GOT_STR;
	private final String IF_ERR_STRING = EXPECTED_SYMBOLS_STR + "'IF'" + GOT_STR;
	private final String ELSE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'ELSE'" + GOT_STR;
	private final String THEN_ERR_STRING = EXPECTED_SYMBOLS_STR + "'THEN'" + GOT_STR;
	private final String DO_ERR_STRING = EXPECTED_SYMBOLS_STR + "'DO'" + GOT_STR;
	private final String WHILE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'WHILE'" + GOT_STR;
	private final String END_ERR_STRING = EXPECTED_SYMBOLS_STR + "'END'" + GOT_STR;
	private final String WHERE_ERR_STRING = EXPECTED_SYMBOLS_STR + "'WHERE'" + GOT_STR;
	private final String IDENTIFIER_ERR_STRING = EXPECTED_SYMBOLS_STR + "IDENTIFIER" + GOT_STR;
	private final String PARAMETER_DECLARATION_ERR_STR = "Expected IDENTIFIER or ) when parsing parameters";
	private final String PARAMETER_DECLARATION_REST_ERR_STR = "Expected , or ) when parsing parameters";
	private final String PARAMETER_DECLARATION_ONE_ERR_STR = "Expected IDENTIFIER when parsing function parameters";
	private final String COMPONENT_DECLARATION_ONE_ERR_STR = "Expected IDENTIFIER when parsing component parameters";
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
	private final String EXPR_CAST_ERR_STR = "Expected : or ) while parsing cast expresion.";
	private final String EXPR_PSTF_ERR_STR = "Expected identifier or literal while parsing postfix expresion.";
	private final String EXPR_PSTF_REST_ERR_STR = "Expected [ or . while parsing postfix expresion.";
	private final String EXPR_CALL_ERR_STR = "Expected ( while parsing call of function arguments.";
	private final String EXPR_ATOM_ERR_STR = "Expected identifier or literal while parsing atom expresion.";
	private final String EXPR_LITERAL_ERR_STR = "Expected PTRCONST, BOOOLCONST, VOIDCONST, CHARCONST, STRCONST, INTCONST while parsing literal.";
	private final String EXPR_ARGS_ERR_STR = "Expected literal or identifier or parenthesis or unary operator while parsing arguments.";
	private final String EXPR_ASS_ERR_STR = "Expected ; or == operator while parsing assigment expresion.";
	private final String EXPR_ELSE_ERR_STR = "Expected ELSE or END operator while parsing else expresion.";
	private final String EXPR_WHERE_ERR_STR = "Expected END or } operator while parsing where expresion.";
	private final String EXPR_STMTS_ERR_STR = "Expected start of statements while parsing statements.";

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
			throw new Report.Error(currSymb.location(), SOURCE_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(), SOURCE_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(), SOURCE_ERR_STR + GOT_STR + currSymb.token.toString());

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
			break;
		case VAR:
			add(node, Symbol.Term.VAR, EXPECTED_SYMBOLS_STR + "'var'" + GOT_STR + currSymb.token.toString());
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
			throw new Report.Error(currSymb.location(), SOURCE_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(),
					PARAMETER_DECLARATION_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(),
					PARAMETER_DECLARATION_REST_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(),
					PARAMETER_DECLARATION_ONE_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(), TYPE_ERR_STR + GOT_STR + currSymb.token.toString());

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
			throw new Report.Error(currSymb.location(), BODY_ERR_STR + GOT_STR + currSymb.token.toString());

		}

		return node;
	}

	private DerNode parseCompDecl() {
		DerNode node = new DerNode(DerNode.Nont.CompDecl);
		switch (currSymb.token) {
		case IDENTIFIER:
			add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
			CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
			node.add(parseType());
			break;

		default:
			throw new Report.Error(currSymb.location(),
					COMPONENT_DECLARATION_ONE_ERR_STR + GOT_STR + currSymb.token.toString());

		}

		return node;
	}

	private DerNode parseCompDecls() {
		DerNode node = new DerNode(DerNode.Nont.CompDecls);
		switch (currSymb.token) {
		case IDENTIFIER:
			node.add(parseCompDecl());
			node.add(parseCompDeclsRest());
			break;

		default:
			throw new Report.Error(currSymb.location(), RECORD_DECL_ERR_STR + GOT_STR + currSymb.token.toString());

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
			node.add(parseCompDecl());
			node.add(parseCompDeclsRest());
			break;

		default:
			throw new Report.Error(currSymb.location(), RECORD_DECL_ERR_STR + GOT_STR + currSymb.token.toString());

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
		case LBRACE:
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
			throw new Report.Error(currSymb.location(), EXPR_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private DerNode parseDisjExpr() {
		DerNode node = new DerNode(DerNode.Nont.DisjExpr);
		if (IsOperator()) {
			node.add(parseConjExpr());
			node.add(parseConjExprRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_DISJ_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private boolean IsEndOfExpresion() {
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
				throw new Report.Error(currSymb.location(),
						EXPR_DISJ_REST_ERR_STR + GOT_STR + currSymb.token.toString());
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
			throw new Report.Error(currSymb.location(), EXPR_CONJ_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private boolean IsDisjExpresion() {
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
				throw new Report.Error(currSymb.location(),
						EXPR_CONJ_REST_ERR_STR + GOT_STR + currSymb.token.toString());
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
			throw new Report.Error(currSymb.location(), EXPR_REL_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private boolean IsConjExpresion() {
		return IsDisjExpresion() || currSymb.token == Symbol.Term.AND;
	}

	private DerNode parseRelExprRest() {
		DerNode node = new DerNode(DerNode.Nont.RelExprRest);
		if (!IsConjExpresion()) {
			switch (currSymb.token) {
			case EQU:
				add(node, Symbol.Term.EQU, EXPECTED_SYMBOLS_STR + "==" + GOT_STR + currSymb.token.toString());
				node.add(parseRelExpr());
				break;

			case NEQ:
				add(node, Symbol.Term.NEQ, EXPECTED_SYMBOLS_STR + "!=" + GOT_STR + currSymb.token.toString());
				node.add(parseRelExpr());
				break;

			case LEQ:
				add(node, Symbol.Term.LEQ, EXPECTED_SYMBOLS_STR + "<=" + GOT_STR + currSymb.token.toString());
				node.add(parseRelExpr());
				break;

			case GEQ:
				add(node, Symbol.Term.GEQ, EXPECTED_SYMBOLS_STR + ">=" + GOT_STR + currSymb.token.toString());
				node.add(parseRelExpr());
				break;

			case LTH:
				add(node, Symbol.Term.LTH, EXPECTED_SYMBOLS_STR + "<" + GOT_STR + currSymb.token.toString());
				node.add(parseRelExpr());
				break;

			case GTH:
				add(node, Symbol.Term.GTH, EXPECTED_SYMBOLS_STR + ">" + GOT_STR + currSymb.token.toString());
				node.add(parseRelExpr());
				break;

			default:
				throw new Report.Error(currSymb.location(),
						EXPR_REL_REST_ERR_STR + GOT_STR + currSymb.token.toString());
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
			throw new Report.Error(currSymb.location(), EXPR_ADD_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private boolean IsRelExpresion() {
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
				throw new Report.Error(currSymb.location(),
						EXPR_ADD_REST_ERR_STR + GOT_STR + currSymb.token.toString());
			}
		}

		return node;
	}

	private DerNode parseMulExpr() {
		DerNode node = new DerNode(DerNode.Nont.MulExpr);
		if (IsOperator()) {
			node.add(parsePrefExpr());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_MUL_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private boolean IsAddExpresion() {
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
				throw new Report.Error(currSymb.location(),
						EXPR_MUL_REST_ERR_STR + GOT_STR + currSymb.token.toString());
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
		case LBRACE:
			node.add(parsePstfExpr());
			node.add(parsePstfExprRest());
			break;

		default:
			throw new Report.Error(currSymb.location(), EXPR_PREF_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private DerNode parseCastEps() {
		DerNode node = new DerNode(DerNode.Nont.CastEps);
		switch (currSymb.token) {
		case RPARENTHESIS:
			break;

		case COLON:
			CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
			node.add(parseType());
			break;

		default:
			throw new Report.Error(currSymb.location(), EXPR_CAST_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private boolean IsLiteral() {
		switch (currSymb.token) {
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

	private DerNode parsePstfExpr() {
		DerNode node = new DerNode(DerNode.Nont.PstfExpr);
		if (IsLiteral() || currSymb.token == Symbol.Term.IDENTIFIER || currSymb.token == Symbol.Term.LBRACE) {
			node.add(parseAtomExpr());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_PSTF_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private DerNode parsePstfExprRest() {
		DerNode node = new DerNode(DerNode.Nont.PstfExprRest);

		switch (currSymb.token) {
		case SEMIC:
		case RPARENTHESIS:
		case COLON:
		case COMMA:
		case ASSIGN:
		case RBRACKET:
		case RBRACE:
		case IOR:
		case XOR:
		case AND:
		case EQU:
		case NEQ:
		case GTH:
		case LTH:
		case LEQ:
		case GEQ:
		case ADD:
		case SUB:
		case MUL:
		case DIV:
		case MOD:
		case THEN:
		case DO:
		case WHERE:
			break;

		case LBRACKET:
			CheckAndSkip(Symbol.Term.LBRACKET, LBRACKET_ERR_STRING + currSymb.token.toString());
			node.add(parseExpr());
			CheckAndSkip(Symbol.Term.RBRACKET, RBRACKET_ERR_STRING + currSymb.token.toString());
			node.add(parsePstfExprRest());
			break;

		case DOT:
			CheckAndSkip(Symbol.Term.DOT, DOT_ERR_STRING + currSymb.token.toString());
			add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
			node.add(parsePstfExprRest());
			break;
		default:
			throw new Report.Error(currSymb.location(), EXPR_PSTF_REST_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private DerNode parseAtomExpr() {
		DerNode node = new DerNode(DerNode.Nont.AtomExpr);
		if (currSymb.token == Symbol.Term.IDENTIFIER) {
			add(node, Symbol.Term.IDENTIFIER, IDENTIFIER_ERR_STRING + currSymb.token.toString());
			node.add(parseCallEps());
		} else if (IsLiteral()) {
			node.add(parseLiteral());
		} else if (currSymb.token == Term.LBRACE) {
			CheckAndSkip(Symbol.Term.LBRACE, LBRACE_ERR_STRING + currSymb.token.toString());
			node.add(parseStmts());
			CheckAndSkip(Symbol.Term.COLON, COLON_ERR_STRING + currSymb.token.toString());
			node.add(parseExpr());
			node.add(parseWhereEps());
			CheckAndSkip(Symbol.Term.RBRACE, RBRACE_ERR_STRING + currSymb.token.toString());

		} else {
			throw new Report.Error(currSymb.location(), EXPR_ATOM_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private DerNode parseLiteral() {
		DerNode node = new DerNode(DerNode.Nont.Literal);
		switch (currSymb.token) {
		case PTRCONST:
			add(node, Symbol.Term.PTRCONST, EXPECTED_SYMBOLS_STR + "PTRCONST" + GOT_STR + currSymb.token.toString());
			break;
		case BOOLCONST:
			add(node, Symbol.Term.BOOLCONST, EXPECTED_SYMBOLS_STR + "BOOLCONST" + GOT_STR + currSymb.token.toString());
			break;
		case VOIDCONST:
			add(node, Symbol.Term.VOIDCONST, EXPECTED_SYMBOLS_STR + "VOIDCONST" + GOT_STR + currSymb.token.toString());
			break;
		case CHARCONST:
			add(node, Symbol.Term.CHARCONST, EXPECTED_SYMBOLS_STR + "CHARCONST" + GOT_STR + currSymb.token.toString());
			break;
		case STRCONST:
			add(node, Symbol.Term.STRCONST, EXPECTED_SYMBOLS_STR + "STRCONST" + GOT_STR + currSymb.token.toString());
			break;
		case INTCONST:
			add(node, Symbol.Term.INTCONST, EXPECTED_SYMBOLS_STR + "INTCONST" + GOT_STR + currSymb.token.toString());
			break;
		default:
			throw new Report.Error(currSymb.location(), EXPR_LITERAL_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private DerNode parseCallEps() {
		DerNode node = new DerNode(DerNode.Nont.CallEps);
		switch (currSymb.token) {
		case SEMIC:
		case RPARENTHESIS:
		case COLON:
		case COMMA:
		case ASSIGN:
		case RBRACKET:
		case RBRACE:
		case IOR:
		case XOR:
		case AND:
		case EQU:
		case NEQ:
		case GTH:
		case LTH:
		case LEQ:
		case GEQ:
		case ADD:
		case SUB:
		case MUL:
		case DIV:
		case MOD:
		case THEN:
		case DO:
		case WHERE:
		case DOT:
		case LBRACKET:
			break;
		case LPARENTHESIS:
			CheckAndSkip(Symbol.Term.LPARENTHESIS, LPARENTHESIS_ERR_STRING + currSymb.token.toString());
			node.add(parseArgs());
			CheckAndSkip(Symbol.Term.RPARENTHESIS, RPARENTHESIS_ERR_STRING + currSymb.token.toString());
			break;
		default:
			throw new Report.Error(currSymb.location(), EXPR_CALL_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private boolean IsArgs() {
		switch (currSymb.token) {
		case IDENTIFIER:
		case LPARENTHESIS:
		case LBRACE:
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

	private DerNode parseArgs() {
		DerNode node = new DerNode(DerNode.Nont.Args);
		if (IsArgs()) {
			node.add(parseArgsEps());
		} else if (currSymb.token == Symbol.Term.RPARENTHESIS) {
			// Do nothing
		} else {
			throw new Report.Error(currSymb.location(), EXPR_ARGS_ERR_STR + GOT_STR + currSymb.token.toString());
		}

		return node;
	}

	private DerNode parseArgsEps() {
		DerNode node = new DerNode(DerNode.Nont.ArgsEps);
		if (IsArgs()) {
			node.add(parseExpr());
			node.add(parseArgsRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_ARGS_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private DerNode parseArgsRest() {
		DerNode node = new DerNode(DerNode.Nont.ArgsRest);
		switch (currSymb.token) {
		case RPARENTHESIS:
			break;

		case COMMA:
			CheckAndSkip(Symbol.Term.COMMA, COMMA_ERR_STRING + currSymb.token.toString());
			node.add(parseExpr());
			node.add(parseArgsRest());
			break;

		default:
			throw new Report.Error(currSymb.location(), EXPR_ARGS_ERR_STR + GOT_STR + currSymb.token.toString());

		}
		return node;
	}

	private boolean IsStatement() {
		return IsArgs() || currSymb.token == Term.IF || currSymb.token == Term.WHILE;
	}

	private DerNode parseStmts() {
		DerNode node = new DerNode(DerNode.Nont.Stmts);
		if (IsStatement()) {
			node.add(parseStmt());
			node.add(parseStmtsRest());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_STMTS_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private DerNode parseStmtsRest() {
		DerNode node = new DerNode(DerNode.Nont.StmtsRest);
		if (IsStatement()) {
			node.add(parseStmt());
			node.add(parseStmtsRest());
		} else if (currSymb.token == Term.END || currSymb.token == Term.ELSE || currSymb.token == Term.COLON) {
			// Do nothing
		} else {
			throw new Report.Error(currSymb.location(), EXPR_STMTS_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private DerNode parseStmt() {
		DerNode node = new DerNode(DerNode.Nont.Stmt);
		if (IsArgs()) {
			node.add(parseExpr());
			node.add(parseAssignEps());
			CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
		} else if (currSymb.token == Term.IF) {
			CheckAndSkip(Symbol.Term.IF, IF_ERR_STRING + currSymb.token.toString());
			node.add(parseExpr());
			CheckAndSkip(Symbol.Term.THEN, THEN_ERR_STRING + currSymb.token.toString());
			node.add(parseStmts());
			node.add(parseElseEps());
			CheckAndSkip(Symbol.Term.END, END_ERR_STRING + currSymb.token.toString());
			CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
		} else if (currSymb.token == Term.WHILE) {
			CheckAndSkip(Symbol.Term.WHILE, WHILE_ERR_STRING + currSymb.token.toString());
			node.add(parseExpr());
			CheckAndSkip(Symbol.Term.DO, DO_ERR_STRING + currSymb.token.toString());
			node.add(parseStmts());
			CheckAndSkip(Symbol.Term.END, END_ERR_STRING + currSymb.token.toString());
			CheckAndSkip(Symbol.Term.SEMIC, SEMIC_ERR_STRING + currSymb.token.toString());
		} else {
			throw new Report.Error(currSymb.location(), EXPR_STMTS_ERR_STR + GOT_STR + currSymb.token.toString());
		}
		return node;
	}

	private DerNode parseAssignEps() {
		DerNode node = new DerNode(DerNode.Nont.AssignEps);
		switch (currSymb.token) {
		case SEMIC:
			break;
		case ASSIGN:
			CheckAndSkip(Symbol.Term.ASSIGN, ASSIGN_ERR_STRING + currSymb.token.toString());
			node.add(parseExpr());
			break;

		default:
			throw new Report.Error(currSymb.location(), EXPR_ASS_ERR_STR + GOT_STR + currSymb.token.toString());

		}
		return node;
	}

	private DerNode parseElseEps() {
		DerNode node = new DerNode(DerNode.Nont.ElseEps);
		switch (currSymb.token) {
		case END:
			break;
		case ELSE:
			CheckAndSkip(Symbol.Term.ELSE, ELSE_ERR_STRING + currSymb.token.toString());
			node.add(parseStmts());
			break;

		default:
			throw new Report.Error(currSymb.location(), EXPR_ELSE_ERR_STR + GOT_STR + currSymb.token.toString());

		}
		return node;
	}

	private DerNode parseWhereEps() {
		DerNode node = new DerNode(DerNode.Nont.WhereEps);
		switch (currSymb.token) {
		case RBRACE:
			break;
		case WHERE:
			CheckAndSkip(Symbol.Term.WHERE, WHERE_ERR_STRING + currSymb.token.toString());
			node.add(parseDecls());
			break;

		default:
			throw new Report.Error(currSymb.location(), EXPR_WHERE_ERR_STR + GOT_STR + currSymb.token.toString());

		}
		return node;
	}
}