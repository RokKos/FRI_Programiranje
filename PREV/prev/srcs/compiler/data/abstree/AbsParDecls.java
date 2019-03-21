/**
 * @author sliva
 */
package compiler.data.abstree;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsParDecls extends Location implements AbsTree {

	private final Vector<AbsParDecl> parDecls;

	public AbsParDecls(Locatable location, Vector<AbsParDecl> parDecls) {
		super(location);
		this.parDecls = new Vector<AbsParDecl>(parDecls);
	}

	public Vector<AbsParDecl> parDecls() {
		return new Vector<AbsParDecl>(parDecls);
	}

	public AbsParDecl parDecl(int index) {
		return parDecls.elementAt(index);
	}

	public int numParDecls() {
		return parDecls.size();
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
