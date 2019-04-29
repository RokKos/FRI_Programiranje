/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;
import compiler.data.type.property.*;

/**
 * A pointer type.
 * 
 * @author sliva
 */
public class SemPtrType extends SemType
		implements AssignType, BinCompOperType, BinEquOperType, CastOperType, FunParameterType, FunResultType {

	/** The type of a data a pointer points to. */
	public final SemType ptdType;

	/**
	 * Constructs a new pointer type.
	 * 
	 * @param ptdType The type of a data a pointer points to.
	 */
	public SemPtrType(SemType ptdType) {
		this.ptdType = ptdType;
	}

	@Override
	public long size() {
		return 8;
	}

	@Override
	public SemType actualType() {
		return this;
	}

	@Override
	public boolean matches(SemType that, SemType.TypeMatching matched) {
		SemType actThat = that.actualType();
		if (!(actThat instanceof SemPtrType))
			return false;

		if (matched.contains(this, actThat))
			return true;
		matched.add(this, actThat);

		SemPtrType ptrThat = (SemPtrType) actThat;
		if (!((this.ptdType instanceof SemVoidType) || (this.ptdType.matches(ptrThat.ptdType, matched))))
			return false;

		return true;
	}

	@Override
	protected boolean isInfinite(HashMap<SemNamedType, SemType.Infinite> namedTypes) {
		return false;
	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("type");
		logger.addAttribute("label", "PTR");
		if (ptdType != null)
			ptdType.log(logger);
		logger.endElement();
	}

	@Override
	public String toString() {
		return "ptr(" + ptdType + ")";
	}

}
