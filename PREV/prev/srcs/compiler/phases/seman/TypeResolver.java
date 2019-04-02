/**
 * @author sliva 
 */
package compiler.phases.seman;

import java.util.*;
import compiler.data.abstree.visitor.*;
import compiler.data.type.*;

/**
 * Type resolving: the result is stored in {@link SemAn#declaresType},
 * {@link SemAn#isType}, and {@link SemAn#ofType}.
 * 
 * @author sliva
 */
public class TypeResolver<SemType, Arg> extends AbsFullVisitor<SemType, Arg> {

	/** Symbol tables of individual record types. */
	protected static final HashMap<SemRecType, SymbTable> symbTables = new HashMap<SemRecType, SymbTable>();

	// TODO

}
