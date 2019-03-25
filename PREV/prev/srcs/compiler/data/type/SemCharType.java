/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;
import compiler.data.type.property.*;

/**
 * Type char.
 * 
 * @author sliva
 */
public class SemCharType extends SemType implements AssignType, BinArithOperType, BinCompOperType, BinEquOperType,
		CastOperType, FunParameterType, FunResultType {

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
		return that.actualType() instanceof SemCharType;
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
		logger.addAttribute("label", "CHAR");
		logger.endElement();
	}

	@Override
	public String toString() {
		return "char";
	}

}
