/**
 * @author sliva
 */
package compiler.data.abstree;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

public class AbsDecls extends Location implements AbsTree {

	private final Vector<AbsDecl> decls;

	public AbsDecls(Locatable location, Vector<AbsDecl> decls) {
		super(location);
		this.decls = new Vector<AbsDecl>(decls);
	}

	public Vector<AbsDecl> decls() {
		return new Vector<AbsDecl>(decls);
	}

	public AbsDecl decl(int index) {
		return decls.elementAt(index);
	}

	public int numDecls() {
		return decls.size();
	}

	@Override
	public <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg) {
		return visitor.visit(this, accArg);
	}

}
