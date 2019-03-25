/**
 * @author sliva
 */
package compiler.phases.seman;

import compiler.data.abstree.*;
import compiler.data.abstree.attribute.*;
import compiler.data.type.*;
import compiler.phases.*;

/**
 * Semantic analysis.
 * 
 * @author sliva
 */
public class SemAn extends Phase {

	/** Maps names (except component names) to declarations. */
	public static final AbsAttribute<AbsName, AbsDecl> declaredAt = new AbsAttribute<AbsName, AbsDecl>();

	/** Maps type declarations to a {@link SemNamedType} types. */
	public static final AbsAttribute<AbsTypDecl, SemNamedType> declaresType = new AbsAttribute<AbsTypDecl, SemNamedType>();

	/** Maps type expressions to types. */
	public static final AbsAttribute<AbsType, SemType> isType = new AbsAttribute<AbsType, SemType>();

	/** Maps value expressions to types. */
	public static final AbsAttribute<AbsExpr, SemType> isOfType = new AbsAttribute<AbsExpr, SemType>();

	/** Denotes whether a value expression can denote an address. */
	public static final AbsAttribute<AbsExpr, Boolean> isAddr = new AbsAttribute<AbsExpr, Boolean>();
	
	/**
	 * Constructs a new phase of semantic analysis.
	 */
	public SemAn() {
		super("seman");
	}

}
