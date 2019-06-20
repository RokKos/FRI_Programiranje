/**
 * @author sliva
 */
package compiler.data.abstree;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsCompDecls extends Location implements AbsTree {

	private final Vector<AbsCompDecl> compDecls;

	public AbsCompDecls(Locatable location, Vector<AbsCompDecl> compDecls) {
		super(location);
		this.compDecls = new Vector<AbsCompDecl>(compDecls);
	}

	public Vector<AbsCompDecl> compDecls() {
		return new Vector<AbsCompDecl>(compDecls);
	}

	public AbsCompDecl compDecl(int index) {
		return compDecls.elementAt(index);
	}

	public int numCompDecls() {
		return compDecls.size();
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
