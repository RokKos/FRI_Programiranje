/**
 * @author sliva
 */
package compiler.phases.seman;

import compiler.common.report.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;

/**
 * Name resolving: the result is stored in {@link SemAn#declaredAt}.
 * 
 * @author sliva
 */
public class NameResolver extends AbsFullVisitor<Object, Object> {

	/** Symbol table. */
	private final SymbTable symbTable = new SymbTable();

	// TODO

}
