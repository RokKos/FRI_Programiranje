/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;
import compiler.data.type.property.*;

/**
 * Type int.
 * 
 * @author sliva
 */
public class SemIntType extends SemType implements AssignType, BinArithOperType, BinCompOperType, BinEquOperType,
		CastOperType, FunParameterType, FunResultType, UnArithOperType {

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
		return that.actualType() instanceof SemIntType;
	}

	@Override
	protected boolean isInfinite(HashSet<SemNamedType> namedTypes) {
		return false;
	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("type");
		logger.addAttribute("label", "INT");
		logger.endElement();
	}

	@Override
	public String toString() {
		return "int";
	}

}
