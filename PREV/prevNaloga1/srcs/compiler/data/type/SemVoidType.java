/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;
import compiler.common.report.*;
import compiler.data.type.property.*;

/**
 * Type void.
 * 
 * @author sliva
 */
public class SemVoidType extends SemType implements FunResultType {

	@Override
	public long size() {
		throw new Report.InternalError();
	}

	@Override
	public SemType actualType() {
		return this;
	}

	@Override
	public boolean matches(SemType that, SemType.TypeMatching matched) {
		return that.actualType() instanceof SemVoidType;
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
		logger.addAttribute("label", "VOID");
		logger.endElement();
	}

	@Override
	public String toString() {
		return "void";
	}

}
