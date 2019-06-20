/**
 * @author sliva
 */
package compiler.phases.seman;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.*;

/**
 * Symbol table.
 * 
 * @author sliva
 *
 */
public class SymbTable {

	/**
	 * A symbol table record denoting a declaration of a name within a certain
	 * scope.
	 * 
	 * @author sliva
	 *
	 */
	private class ScopedDecl {

		/** The depth of the scope the declaration belongs to. */
		public final int depth;

		/** The declaration. */
		public final AbsDecl decl;

		/**
		 * Constructs a new record denoting a declaration of a name within a certain
		 * scope.
		 * 
		 * @param depth The depth of the scope the declaration belongs to.
		 * @param decl  The declaration.
		 */
		public ScopedDecl(int depth, AbsDecl decl) {
			this.depth = depth;
			this.decl = decl;
		}

	}

	/**
	 * A mapping of names into lists of records denoting declarations at different
	 * scopes. At each moment during the lifetime of a symbol table, the declaration
	 * list corresponding to a particular name contains all declarations that name
	 * within currently active scopes: the declaration at the inner most scope is
	 * the first in the list and is visible, the other declarations are hidden.
	 */
	private final HashMap<String, LinkedList<ScopedDecl>> allDeclsOfAllNames;

	/**
	 * The list of scopes. Each scope is represented by a list of names declared
	 * within it.
	 */
	private final LinkedList<LinkedList<String>> scopes;

	/** The depth of the currently active scope. */
	private int currDepth;

	/** Whether the symbol table can no longer be modified or not. */
	private boolean lock;

	/**
	 * Constructs a new symbol table.
	 */
	public SymbTable() {
		allDeclsOfAllNames = new HashMap<String, LinkedList<ScopedDecl>>();
		scopes = new LinkedList<LinkedList<String>>();
		currDepth = 0;
		lock = false;
		newScope();
	}

	/**
	 * Returns the depth of the currently active scope.
	 * 
	 * @return The depth of the currently active scope.
	 */
	public int currDepth() {
		return currDepth;
	}

	/**
	 * Inserts a new declaration of a name within the currently active scope or
	 * throws an exception if this name has already been declared within this scope.
	 * Once the symbol table is locked, any attempt to insert further declarations
	 * results in an internal error.
	 * 
	 * @param name The name.
	 * @param decl The declaration.
	 * @throws CannotInsNameException Thrown if this name has already been declared
	 *                                within the currently active scope.
	 */
	public void ins(String name, AbsDecl decl) throws CannotInsNameException {
		if (lock)
			throw new Report.InternalError();

		LinkedList<ScopedDecl> allDeclsOfName = allDeclsOfAllNames.get(name);
		if (allDeclsOfName == null) {
			allDeclsOfName = new LinkedList<ScopedDecl>();
			allDeclsOfAllNames.put(name, allDeclsOfName);
		}

		if (!allDeclsOfName.isEmpty()) {
			ScopedDecl declOfName = allDeclsOfName.getFirst();
			if (declOfName.depth == currDepth)
				throw new CannotInsNameException();
		}

		allDeclsOfName.addFirst(new ScopedDecl(currDepth, decl));
		scopes.getFirst().addFirst(name);
	}

	/**
	 * Returns the currently visible declaration of the specified name. This
	 * declaration can belong either to the currently active scope or any scope
	 * enclosing it. If no declaration of the name exists within these scopes, an
	 * exception is thrown.
	 * 
	 * @param name The name.
	 * @return The declaration.
	 * @throws CannotFndNameException Thrown if the name is not declared within the
	 *                                currently active scope or any scope enclosing
	 *                                it.
	 */
	public AbsDecl fnd(String name) throws CannotFndNameException {
		LinkedList<ScopedDecl> allDeclsOfName = allDeclsOfAllNames.get(name);
		if (allDeclsOfName == null)
			throw new CannotFndNameException();

		if (allDeclsOfName.isEmpty())
			throw new CannotFndNameException();

		return allDeclsOfName.getFirst().decl;
	}

	/**
	 * Constructs a new scope within the currently active scope. The newly
	 * constructed scope becomes the currently active scope.
	 */
	public void newScope() {
		if (lock)
			throw new Report.InternalError();

		currDepth++;
		scopes.addFirst(new LinkedList<String>());
	}

	/**
	 * Destroys the currently active scope by removing all declarations belonging to
	 * it from the symbol table. Makes the enclosing scope the currently active
	 * scope.
	 */
	public void oldScope() {
		if (lock)
			throw new Report.InternalError();

		if (currDepth == 0)
			throw new Report.InternalError();

		for (String name : scopes.getFirst()) {
			allDeclsOfAllNames.get(name).removeFirst();
		}
		scopes.removeFirst();
		currDepth--;
	}

	/**
	 * Prevents further modification of this symbol table.
	 */
	public void lock() {
		lock = true;
	}

	/**
	 * An exception thrown when the name cannot be inserted into a symbol table.
	 * 
	 * @author sliva
	 *
	 */
	@SuppressWarnings("serial")
	public class CannotInsNameException extends Exception {

		/**
		 * Constructs a new exception.
		 */
		private CannotInsNameException() {
		}

	}

	/**
	 * An exception thrown when the name cannot be found in the symbol table.
	 * 
	 * @author sliva
	 *
	 */
	@SuppressWarnings("serial")
	public class CannotFndNameException extends Exception {

		/**
		 * Constructs a new exception.
		 */
		private CannotFndNameException() {
		}

	}

}
