/**
 * @author sliva
 */
package compiler.phases.synan;

import compiler.common.logger.*;
import compiler.data.dertree.*;
import compiler.data.dertree.visitor.*;

/**
 * The visitor that produces the log of the derivation tree.
 * 
 * @author sliva
 */
public class DerLogger implements DerVisitor<Object, Object> {

	/** The logger the log should be written to. */
	private final Logger logger;

	/**
	 * Construct a new visitor with a logger the log should be written to.
	 * 
	 * @param logger The logger the log should be written to.
	 */
	public DerLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public Object visit(DerLeaf leaf, Object visArg) {
		if (logger == null)
			return null;
		leaf.symb.log(logger);
		return null;
	}

	@Override
	public Object visit(DerNode node, Object visArg) {
		if (logger == null)
			return null;
		logger.begElement("nont");
		logger.addAttribute("label", node.label.toString());
		for (DerTree subTree : node.subtrees()) {
			subTree.accept(this, visArg);
		}
		if (node.location() != null)
			node.location().log(logger);
		logger.endElement();
		return null;
	}

}
