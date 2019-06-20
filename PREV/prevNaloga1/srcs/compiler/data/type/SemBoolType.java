/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;
import compiler.data.type.property.*;

/**
 * Type bool.
 * 
 * @author sliva
 */
public class SemBoolType extends SemType
		implements AssignType, BinEquOperType, BinLogOperType, FunParameterType, FunResultType, UnLogOperType {

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
		return that.actualType() instanceof SemBoolType;
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
		logger.addAttribute("label", "BOOL");
		logger.endElement();
	}

	@Override
	public String toString() {
		return "bool";
	}

}
