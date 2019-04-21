/**
 * @author sliva
 */
package compiler.phases.frames;

import compiler.data.abstree.*;
import compiler.data.abstree.AbsAtomExpr.Type;
import compiler.data.abstree.visitor.*;
import compiler.data.type.*;
import compiler.data.layout.*;
import compiler.phases.seman.*;

/**
 * Computing function frames and accesses.
 * 
 * @author sliva
 */
public class FrmEvaluator extends AbsFullVisitor<Object, FrmEvaluator.Context> {

	/**
	 * The context {@link FrmEvaluator} uses while computing function frames and
	 * variable accesses.
	 * 
	 * @author sliva
	 */
	protected abstract class Context {
	}

	/**
	 * Functional context, i.e., used when traversing function and building a new
	 * frame, parameter acceses and variable acceses.
	 * 
	 * @author sliva
	 */
	private class FunContext extends Context {
		public int depth = 0;
		public long locsSize = 0; // Size already alocated for local vars
		public long argsSize = 0;
		public long parsSize = new SemPtrType(new SemVoidType()).size();
		public AbsFunDef funDef;
	}

	/**
	 * Record context, i.e., used when traversing record definition and computing
	 * record component acceses.
	 * 
	 * @author sliva
	 */
	private class RecContext extends Context {
		public long compsSize = 0;
		public int depth = 0;
	}

	@Override
	public Object visit(AbsFunDecl funDecl, FrmEvaluator.Context visArg) {
		FunContext funContext = new FunContext();
		// To prevernt crash in ParDecls
		funDecl.parDecls.accept(this, funContext);
		funDecl.type.accept(this, funContext);
		return null;
	}

	@Override
	public Object visit(AbsFunDef funDef, FrmEvaluator.Context visArg) {
		FunContext funContext = new FunContext();
		funContext.funDef = funDef;
		FunContext outherFunContext = (FunContext) visArg;

		funContext.depth = 1;
		if (outherFunContext != null) {
			funContext.depth = outherFunContext.depth + 1;
		}

		// Calculate acceses for paramters declarations
		funDef.parDecls.accept(this, funContext);

		// Parse Local variables and function calls
		funDef.value.accept(this, funContext);

		Label funLabel = new Label(funDef.name);
		if (outherFunContext != null && (funDef.name.equals(outherFunContext.funDef.name))) {
			funLabel = new Label();
		}

		Frame funFrame = new Frame(funLabel, funContext.depth, funContext.locsSize, funContext.argsSize);
		Frames.frames.put(funDef, funFrame);

		return null;
	}

	@Override
	public Object visit(AbsParDecl parDecl, Context visArg) {
		FunContext funContext = (FunContext) visArg;
		long parameterSize = TypeSize(SemAn.isType.get(parDecl.type));
		Frames.accesses.put(parDecl, new RelAccess(parameterSize, funContext.parsSize, funContext.depth));
		funContext.parsSize += parameterSize;
		return null;
	}

	@Override
	public Object visit(AbsVarDecl varDecl, Context visArg) {
		FunContext funContext = (FunContext) visArg;
		long varSize = TypeSize(SemAn.isType.get(varDecl.type));
		if (funContext != null) {
			funContext.locsSize += varSize;
			Frames.accesses.put(varDecl, new RelAccess(varSize, -funContext.locsSize, funContext.depth));

		} else {

			Frames.accesses.put(varDecl, new AbsAccess(varSize, new Label(varDecl.name), null));
		}

		varDecl.type.accept(this, null);
		return null;
	}

	private long TypeSize(SemType type) {
		if (type instanceof SemVoidType) {
			return 0;
		}
		return type.size();
	}

	@Override
	public Object visit(AbsFunName funName, Context visArg) {
		AbsFunDecl funDecl = (AbsFunDecl) SemAn.declaredAt.get(funName);

		long argsSize = new SemPtrType(new SemVoidType()).size();

		SemType returnType = SemAn.isType.get(funDecl.type);
		long returnSize = TypeSize(returnType);

		for (AbsParDecl parDecl : funDecl.parDecls.parDecls()) {
			argsSize += TypeSize(SemAn.isType.get(parDecl.type));
		}

		long calledFunArgsSize = Math.max(argsSize, returnSize);

		FunContext funContext = (FunContext) visArg;
		funContext.argsSize = Math.max(funContext.argsSize, calledFunArgsSize);

		funName.args.accept(this, visArg);
		return null;
	}

	@Override
	public Object visit(AbsBlockExpr blockExpr, Context visArg) {
		FunContext funContext = (FunContext) visArg;

		blockExpr.decls.accept(this, visArg);
		blockExpr.stmts.accept(this, visArg);
		blockExpr.expr.accept(this, visArg);

		return null;
	}

	@Override
	public Object visit(AbsRecType recType, Context visArg) {
		RecContext recContext = new RecContext();
		recContext.depth = 1;

		if (visArg instanceof RecContext) {
			RecContext outherRecContext = (RecContext) visArg;
			if (outherRecContext != null) {
				recContext.depth = outherRecContext.depth + 1;
			}
		}

		recType.compDecls.accept(this, recContext);
		return null;
	}

	@Override
	public Object visit(AbsCompDecl compDecl, Context visArg) {
		RecContext recContext = (RecContext) visArg;

		long recSize = TypeSize(SemAn.isType.get(compDecl.type));
		Frames.accesses.put(compDecl, new RelAccess(recSize, recContext.compsSize, recContext.depth));
		recContext.compsSize += recSize;

		compDecl.type.accept(this, visArg);
		return null;
	}

	@Override
	public Object visit(AbsAtomExpr atomExpr, Context visArg) {
		if (atomExpr.type == Type.STR) {
			long strSize = atomExpr.expr.length() - 1; // Because we need to include \0 and we have double quotes so it
														// comes -1
			Frames.strings.put(atomExpr, new AbsAccess(strSize, new Label(), atomExpr.expr));
		}
		return null;
	}

}
