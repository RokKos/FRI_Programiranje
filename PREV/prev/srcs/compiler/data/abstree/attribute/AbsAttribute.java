/**
 * @author sliva
 */
package compiler.data.abstree.attribute;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.*;

/**
 * An attribute of the abstract syntax tree node.
 * 
 * @author sliva
 *
 * @param <Node> Nodes that values are associated with.
 * @param <Value> Values associated with nodes.
 */
public class AbsAttribute<Node extends AbsTree, Value> {

	/** Mapping of nodes to values. */
	private HashMap<Node, Value> mapping;

	/** Whether this attribute can no longer be modified or not. */
	private boolean lock;

	/** Constructs a new attribute. */
	public AbsAttribute() {
		mapping = new HashMap<Node, Value>();
		lock = false;
	}

	/**
	 * Associates a value with the specified abstract syntax tree node.
	 * 
	 * @param node  The specified abstract syntax tree node.
	 * @param value The value.
	 * @return The value.
	 */
	public Value put(Node node, Value value) {
		if (lock)
			throw new Report.InternalError();
		mapping.put(node, value);
		return value;
	}

	/**
	 * Returns a value associated with the specified abstract syntax tree node.
	 * 
	 * @param node The specified abstract syntax tree node.
	 * @return The value.
	 */
	public Value get(Node node) {
		return mapping.get(node);
	}

	/**
	 * Prevents further modification of this attribute.
	 */
	public void lock() {
		lock = true;
	}

}
