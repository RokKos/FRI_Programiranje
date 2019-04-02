/**
 * @author sliva 
 */
package compiler.phases.seman;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;
import compiler.data.type.*;
import compiler.data.type.property.*;

/**
 * Type resolving: the result is stored in {@link SemAn#declaresType},
 * {@link SemAn#isType}, and {@link SemAn#ofType}.
 * 
 * @author sliva
 */
public class TypeResolver extends AbsFullVisitor<SemType, TypeResolver.Phase> {

	/** Symbol tables of individual record types. */
	private final HashMap<SemRecType, SymbTable> symbTables = new HashMap<SemRecType, SymbTable>();

    // TODO

}
