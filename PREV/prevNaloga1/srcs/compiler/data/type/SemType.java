/**
 * @author sliva
 */
package compiler.data.type;

import java.util.*;
import compiler.common.logger.*;

/**
 * An abstract class for describing types.
 * 
 * @author sliva
 */
public abstract class SemType implements Loggable {

	/**
	 * Returns the size of this type.
	 * 
	 * @return The size of this type.
	 */
	public abstract long size();

	/**
	 * Returns the actual representation of this type.
	 * 
	 * Alongside nodes that describe the actual type a representation of a type can
	 * include nodes describing type synonyms (represented by objects of class
	 * {@link SemNamedType}). This function returns the top-most node describing the
	 * actual type by skipping all top-most synonym nodes.
	 * 
	 * @return The top-most non-synonym node of this type representation.
	 */
	public abstract SemType actualType();

	/**
	 * Checks whether this type matches another type (wrapper for
	 * {@link matches(SemType, TypeMatching)}).
	 * 
	 * @param that A type to be matched.
	 * @return {@code true} if types match, {@code false} otherwise.
	 */
	public final boolean matches(SemType that) {
		return matches(that, new TypeMatching());
	}

	/**
	 * Checks whether this type matches another type.
	 * 
	 * @param that    A type to be matched.
	 * @param matched Types matched so far.
	 * @return {@code true} if types match, {@code false} otherwise.
	 */
	protected abstract boolean matches(SemType that, TypeMatching matched);

	/**
	 * A class for keeping a history of type matching operations within a single
	 * type matching query. An object of this class contains pairs of types which
	 * have been assumed matching.
	 * 
	 * @author sliva
	 */
	protected class TypeMatching {

		private final HashMap<SemType, HashSet<SemType>> matching = new HashMap<SemType, HashSet<SemType>>();

		/**
		 * Stores a pair of matching types in the type matching history.
		 * 
		 * @param type1 The first type.
		 * @param type2 The second type.
		 */
		public void add(SemType type1, SemType type2) {
			HashSet<SemType> types1 = matching.get(type1);
			if (types1 == null) {
				types1 = new HashSet<SemType>();
				matching.put(type1, types1);
			}
			types1.add(type2);

			HashSet<SemType> types2 = matching.get(type2);
			if (types2 == null) {
				types2 = new HashSet<SemType>();
				matching.put(type2, types2);
			}
			types2.add(type1);
		}

		/**
		 * Checks whether the type matching history contains a pair of types.
		 * 
		 * @param type1 The first pair.
		 * @param type2 The second pair.
		 * @return {@code true} if the two types , {@code false} otherwise.
		 */
		public boolean contains(SemType type1, SemType type2) {
			HashSet<SemType> types1 = matching.get(type1);
			return (types1 != null) && (types1.contains(type2));
		}

	}

	/**
	 * Checks whether a type is infinite (wrapper for
	 * {@link isInfinite(HashSet)}.
	 * 
	 * @return {@code true} if the type is infinite, {@code false} otherwise.
	 */
	public final boolean isInfinite() {
		return isInfinite(new HashSet<SemNamedType>());
	}

	/**
	 * Checks whether a type is infinite.
	 * 
	 * @param namedTypes Named types already encountered during the traversal.
	 * @return {@code true} if the type is infinite, {@code false} otherwise.
	 */
	protected abstract boolean isInfinite(HashSet<SemNamedType> namedTypes);

}
