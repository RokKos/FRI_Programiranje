/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;

/**
 * A record type.
 * 
 * @author sliva
 */
public class SemRecType extends SemType {

	/** The types of all components. */
	private final Vector<SemType> compTypes;

	/**
	 * Constructs a new record type.
	 * 
	 * @param compTypes The types of all components.
	 */
	public SemRecType(Vector<SemType> compTypes) {
		this.compTypes = new Vector<SemType>(compTypes);
	}

	/**
	 * Returns the types of all components.
	 * 
	 * @return The types of all components.
	 */
	public Vector<SemType> compTypes() {
		return new Vector<SemType>(compTypes);
	}

	/**
	 * Returns the type of the specified component.
	 * 
	 * @param index The index of a component.
	 * @return The type of the specified component,
	 */
	public SemType compType(int index) {
		return compTypes.elementAt(index);
	}

	/**
	 * Returns the number of all component types.
	 * 
	 * @return the number of all component types.
	 */
	public int numCompTypes() {
		return compTypes.size();
	}

	@Override
	public long size() {
		long size = 0;
		for (int comp = 0; comp < compTypes.size(); comp++) {
			size = size + compTypes.elementAt(comp).size();
		}
		return size;
	}

	@Override
	public SemType actualType() {
		return this;
	}

	@Override
	public boolean matches(SemType that, SemType.TypeMatching matched) {
		SemType actThat = that.actualType();
		if (!(actThat instanceof SemRecType))
			return false;

		if (matched.contains(this, actThat))
			return true;
		matched.add(this, actThat);

		SemRecType recThat = (SemRecType) actThat;
		if (this.compTypes.size() != recThat.compTypes.size())
			return false;
		for (int comp = 0; comp < this.compTypes.size(); comp++)
			if (!this.compTypes.elementAt(comp).matches(recThat.compTypes.elementAt(comp), matched))
				return false;

		return true;
	}

	@Override
	protected boolean isInfinite(HashSet<SemNamedType> namedTypes) {
		boolean isInfinite = false;
		for (SemType compType : compTypes)
			isInfinite |= compType.isInfinite(namedTypes);
		return isInfinite;
	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("type");
		logger.addAttribute("label", "REC");
		for (SemType compType : compTypes)
			compType.log(logger);
		logger.endElement();
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("rec");
		str.append("(");
		for (int comp = 0; comp < compTypes.size(); comp++) {
			if (comp > 0)
				str.append(",");
			str.append(compTypes.elementAt(comp).toString());
		}
		str.append(")");
		return str.toString();
	}

}
