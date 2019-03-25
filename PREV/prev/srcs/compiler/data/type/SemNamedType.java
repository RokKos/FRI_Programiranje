/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;
import compiler.common.report.*;

/**
 * A type synonym.
 * 
 * @author sliva
 */
public class SemNamedType extends SemType {

	/** The name of this type. */
	public final String name;

	/** The type this type is a synonym for. */
	public SemType type;

	/**
	 * Constructs a new representation of a type synonym.
	 * 
	 * @param name The name of this synonim.
	 */
	public SemNamedType(String name) {
		this.name = name;
		this.type = null;
	}

	/**
	 * Defines this type synonym by pointing to the type this type is a synonym for.
	 * 
	 * @param type The type this type is a synonym for.
	 */
	public void define(SemType type) {
		if (this.type != null)
			throw new Report.InternalError();
		this.type = type;
	}

	@Override
	public long size() {
		return type.size();
	}

	@Override
	public SemType actualType() {
		return type.actualType();
	}

	@Override
	public boolean matches(SemType that, SemType.TypeMatching matched) {
		return this.actualType().matches(that, matched);
	}

	@Override
	protected boolean isInfinite(HashSet<SemNamedType> namedTypes) {
		if (namedTypes.contains(this))
			return true;
		else {
			namedTypes.add(this);
			return type.isInfinite(namedTypes);
		}

	}

	@Override
	public void log(Logger logger) {
		if (logger == null)
			return;
		logger.begElement("type");
		logger.addAttribute("label", "TYPE");
		logger.addAttribute("name", name);
		logger.endElement();
	}

	@Override
	public String toString() {
		return name;
	}

}
