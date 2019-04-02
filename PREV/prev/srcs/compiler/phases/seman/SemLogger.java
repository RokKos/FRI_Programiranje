/**
 * @author sliva
 */
package compiler.phases.seman;

import compiler.common.logger.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;
import compiler.data.type.*;

/**
 * The visitor that produces the log of the attributes computed during semantic
 * analysis.
 * 
 * This visitor does not traverses the abstract syntax tree. It is used as a
 * subvisitor of {@link compiler.phases.abstr.AbsLogger} and produces XML
 * representation of the computed attributes for each AST node separately (when
 * called by a methods of {@link compiler.phases.abstr.AbsLogger} each time they
 * reach an AST node).
 * 
 * @author sliva
 *
 */
public class SemLogger extends AbsNullVisitor<Object, Object> {

	/** The logger the log should be written to. */
	private final Logger logger;

	/**
	 * Construct a new visitor with a logger the log should be written to.
	 * 
	 * @param logger The logger the log should be written to.
	 */
	public SemLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public Object visit(AbsArrExpr arrExpr, Object visArg) {
		SemType type = SemAn.ofType.get(arrExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(arrExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsArrType arrType, Object visArg) {
		SemType type = SemAn.isType.get(arrType);
		if (type != null)
			type.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsAtomExpr atomExpr, Object visArg) {
		SemType type = SemAn.ofType.get(atomExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(atomExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsAtomType atomType, Object visArg) {
		SemType type = SemAn.isType.get(atomType);
		if (type != null)
			type.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsBinExpr binExpr, Object visArg) {
		SemType type = SemAn.ofType.get(binExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(binExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsBlockExpr blockExpr, Object visArg) {
		SemType type = SemAn.ofType.get(blockExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(blockExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsCastExpr castExpr, Object visArg) {
		SemType type = SemAn.ofType.get(castExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(castExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsDelExpr delExpr, Object visArg) {
		SemType type = SemAn.ofType.get(delExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(delExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsFunName funName, Object visArg) {
		AbsDecl decl = SemAn.declaredAt.get(funName);
		if (decl != null) {
			logger.begElement("declaredAt");
			logger.addAttribute("location", decl.toString());
			logger.endElement();
		}
		SemType type = SemAn.ofType.get(funName);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(funName);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsNewExpr newExpr, Object visArg) {
		SemType type = SemAn.ofType.get(newExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(newExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsPtrType ptrType, Object visArg) {
		SemType type = SemAn.isType.get(ptrType);
		if (type != null)
			type.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsRecExpr recExpr, Object visArg) {
		SemType type = SemAn.ofType.get(recExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(recExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsRecType recType, Object visArg) {
		SemType type = SemAn.isType.get(recType);
		if (type != null)
			type.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsTypDecl typDecl, Object visArg) {
		SemNamedType type = SemAn.declaresType.get(typDecl);
		if (type != null)
			type.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsTypName typName, Object visArg) {
		AbsDecl decl = SemAn.declaredAt.get(typName);
		if (decl != null) {
			logger.begElement("declaredAt");
			logger.addAttribute("location", decl.toString());
			logger.endElement();
		}
		SemType type = SemAn.isType.get(typName);
		if (type != null)
			type.log(logger);
		return null;
	}

	@Override
	public Object visit(AbsUnExpr unExpr, Object visArg) {
		SemType type = SemAn.ofType.get(unExpr);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(unExpr);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

	@Override
	public Object visit(AbsVarName varName, Object visArg) {
		AbsVarDecl decl = (AbsVarDecl) SemAn.declaredAt.get(varName);
		if (decl != null) {
			logger.begElement("declaredAt");
			logger.addAttribute("location", decl.toString());
			logger.endElement();
		}
		SemType type = SemAn.ofType.get(varName);
		if (type != null)
			type.log(logger);
		Boolean hasAddr = SemAn.isAddr.get(varName);
		if ((hasAddr != null) && (hasAddr == true)) {
			logger.begElement("addr");
			logger.endElement();
		}
		return null;
	}

}
