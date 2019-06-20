/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;

/**
 * An array type.
 * 
 * @author sliva
 */
public class SemArrType extends SemType {

	/** The number of elements in an array. */
	public final long len;

	/** The type of an array element. */
	public final SemType elemType;

	/**
	 * Constructs a new array type.
	 * 
	 * @param len      The number of elements in an array.
	 * @param elemType The type of an array element.
	 */
	public SemArrType(long len, SemType elemType) {
		this.len = len;
		this.elemType = elemType;
	}

	@Override
	public long size() {
		return len * elemType.size();
	}

	@Override
	public SemType actualType() {
		return this;
	}

	@Override
	public boolean matches(SemType that, SemType.TypeMatching matched) {
		SemType actThat = that.actualType();
		if (!(actThat instanceof SemArrType))
			return false;

		if (matched.contains(this, actThat))
			return true;
		matched.add(this, actThat);

		SemArrType arrThat = (SemArrType) actThat;
		if (!(this.len == arrThat.len))
			return false;
		if (!this.elemType.matches(arrThat.elemType, matched))
			return false;

		return true;
	}

	@Override
	protected boolean isInfinite(HashMap<SemNamedType, SemType.Infinite> namedTypes) {
		return elemType.isInfinite(namedTypes);
	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("type");
		logger.addAttribute("label", "ARR(" + len + ")");
		logger.addAttribute("len", Long.toString(len));
		elemType.log(logger);
		logger.endElement();
	}

	@Override
	public String toString() {
		return "arr[" + len + "]" + elemType;
	}

}
