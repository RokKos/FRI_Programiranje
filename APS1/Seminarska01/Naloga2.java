import java.util.*;
import java.io.*;

public class Naloga2 {
	public static void main(String[] args) {
		BufferedReader br = null;
		PrintWriter pw = null;

		// Preveri ce je dovolj argumentov za branje
		if (args.length < 2) {
			System.err.println("Ni dovolj argumentov za braje");
			return;
		}

		try{
			// Probaj odpreti datoteko
			br = new BufferedReader(new FileReader(args[0]));
			pw =  new PrintWriter(new FileWriter(args[1]));

			StreamTokenizer st = new StreamTokenizer(br);

			ExpressionTree root = CreateExpresionTree(st);

			PreOrderOutputStart(root, pw);
			pw.println();
			pw.println(TreeHeight(root));

		}
		catch (IOException ex) {System.err.println("I/O exception");}
		finally {
			// Ko koncas vse zapri

			try {
				if (br != null) {br.close();}
				if (pw != null) {pw.close();}
			}
			catch (IOException ex) {System.err.println("I/O exception");}
		}
	}


	private static ExpressionTree CreateExpresionTree (StreamTokenizer st) throws IOException{
		st.nextToken();
		ExpressionTree root = Izraz(st);
		if (st.ttype == StreamTokenizer.TT_EOF) {
			//System.out.println("End of File encountered.");

		}

		return root;
	}


	private static ExpressionTree Izraz (StreamTokenizer st) throws IOException{
		ExpressionTree root = InGramatika(st);

		boolean isOr = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals(OPERATOR_OR);
		if (isOr) {
			return OrGramatika(st, root);
		}

		return root;
	}

	private static ExpressionTree OrGramatika (StreamTokenizer st, ExpressionTree left) throws IOException{
		ExpressionTree root = new ExpressionTree(OPERATOR_OR, null, left, null, null);

		st.nextToken();
		root.rightChild = InGramatika(st);

		boolean isOr = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals(OPERATOR_OR);
		if (isOr) {
			return OrGramatika(st, root);
		}

		return root;
	}

	private static ExpressionTree InGramatika (StreamTokenizer st) throws IOException{
		ExpressionTree root = Vrednost(st);

		boolean isAnd = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals(OPERATOR_AND);
		if (isAnd) {
			return AndGramatika(st, root);
		}

		return root;
	}

	private static ExpressionTree AndGramatika (StreamTokenizer st, ExpressionTree left) throws IOException{
		ExpressionTree root = new ExpressionTree(OPERATOR_AND, null, left, null, null);

		st.nextToken();
		root.rightChild = Vrednost(st);

		boolean isAnd = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals(OPERATOR_AND);
		if (isAnd) {
			return AndGramatika(st, root);
		}

		return root;
	}

	private static ExpressionTree Vrednost (StreamTokenizer st) throws IOException{
		boolean isNotBracket = st.ttype != OPERATOR_L_BRACKET &&
							   st.ttype != OPERATOR_R_BRACKET;

		if (isNotBracket) {
			ExpressionTree root;

			boolean isNot = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals(OPERATOR_NOT);
			if (isNot) {
				root = new ExpressionTree(st.sval, null, null, null, null);
				st.nextToken();
				root.leftChild = Vrednost(st);
			} else {
				root = new ExpressionTree(null, st.sval, null, null, null);
				st.nextToken();
			}

			return root;
		}

		boolean isNotLeftBracket = st.ttype != OPERATOR_L_BRACKET;
		if (isNotLeftBracket) {
			System.out.println("error left bracket");
			return null;
		}

		st.nextToken();
		ExpressionTree root = Izraz(st);


		boolean isNotRightBracket = st.ttype != OPERATOR_R_BRACKET;
		if (isNotRightBracket) {
			System.out.println("error right bracket");
			return null;
		}

		st.nextToken();
		return root;

	}


	private static void write(ExpressionTree node) {
		if (node != null) {
			System.out.print("(");
			write(node.leftChild);
			if (node.value == null) {
				System.out.print(", " + node.operator + ", ");
			} else {
				System.out.print(", " + node.value + ", ");
			}
			write(node.rightChild);
			System.out.print(")");
		}
		else
			System.out.print("null");
	}

	private static void PreOrderOutputStart(ExpressionTree node, PrintWriter pw) {
		if (node == null) { return; }

		if (node.value == null) {
			//System.out.print(node.operator);
			pw.print(node.operator);
		} else {
			//System.out.print(node.value);
			pw.print(node.value);
		}

		PreOrderOutput(node.leftChild, pw);
		PreOrderOutput(node.rightChild, pw);
	}

	private static void PreOrderOutput(ExpressionTree node, PrintWriter pw) {
		if (node == null) { return; }

		if (node.value == null) {
			//System.out.print("," + node.operator);
			pw.print("," + node.operator);
		} else {
			//System.out.print("," + node.value);
			pw.print("," + node.value);
		}

		PreOrderOutput(node.leftChild, pw);
		PreOrderOutput(node.rightChild, pw);
	}


	private static int TreeHeight(ExpressionTree node) {
		if (node == null) { return 0; }
		return Math.max(TreeHeight(node.leftChild), TreeHeight(node.rightChild)) + 1;
	}

	private static final String OPERATOR_AND = "AND";
	private static final String OPERATOR_OR = "OR";
	private static final String OPERATOR_NOT = "NOT";
	private static final String CONSTANT_TRUE = "TRUE";
	private static final String CONSTANT_FALSE = "FALSE";
	private static final int OPERATOR_L_BRACKET = '(';
	private static final int OPERATOR_R_BRACKET = ')';
}

class ExpressionTree {
	public String operator;
	public String value;

	public ExpressionTree leftChild;
	public ExpressionTree rightChild;
	public ExpressionTree parent;

	ExpressionTree(String _operator, String _value, ExpressionTree _left, ExpressionTree _right, ExpressionTree _parent) {
		operator = _operator;
		value = _value;
		leftChild = _left;
		rightChild = _right;
		parent = _parent;
	}

}