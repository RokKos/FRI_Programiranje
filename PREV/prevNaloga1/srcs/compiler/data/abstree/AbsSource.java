/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

/**
 * @author sliva
 */
public class AbsSource extends Location implements AbsTree {

	public final AbsDecls decls;

	public AbsSource(Locatable location, AbsDecls decls) {
		super(location);
		this.decls = decls;
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}
}
