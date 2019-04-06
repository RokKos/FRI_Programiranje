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
public class FunNameResolution<Result, Arg> extends NameResolver<Result, Arg> {

    @Override
    public Result visit(AbsFunName funName, Arg visArg) {
        try {
            AbsDecl typeDeclaration = symbTable.fnd(funName.name);
            SemAn.declaredAt.put(funName, typeDeclaration);
        } catch (Exception e) {
            throw new Report.Error(funName.location(), "This fun: " + funName.name + " does not exist in this scope");
        }

        super.visit(funName, visArg);
        return null;
    }

    @Override
    public Result visit(AbsFunDef funDef, Arg visArg) {
        // System.out.println("FunDef res:" + funDef.name + " d: " +
        // symbTable.currDepth());

        symbTable.newScope();
        funDef.value.accept(new FunNameDeclaration(), null);
        funDef.value.accept(new VarNameDeclaration(), null);

        funDef.parDecls.accept(this, visArg);

        funDef.value.accept(this, visArg);

        symbTable.oldScope();
        return null;
    }

    @Override
    public Result visit(AbsVarName varName, Arg visArg) {
        try {
            // System.out.println("varName:" + varName.name);
            AbsDecl varDeclaration = symbTable.fnd(varName.name);
            SemAn.declaredAt.put(varName, varDeclaration);
        } catch (Exception e) {
            throw new Report.Error(varName.location(), "This var: " + varName.name + " does not exist in this scope");
        }
        super.visit(varName, visArg);
        return null;
    }

    @Override
    public Result visit(AbsRecExpr recExpr, Arg visArg) {
        recExpr.record.accept(this, visArg);
        return null;
    }

    @Override
    public Result visit(AbsParDecl parDecl, Arg visArg) {
        try {
            // System.out.println("ParDecl:" + parDecl.name);
            symbTable.ins(parDecl.name, parDecl);
        } catch (Exception e) {
            throw new Report.Error(parDecl.location(), "Parameter: " + parDecl.name + " defined twice in same scope");
        }
        super.visit(parDecl, visArg);
        return null;
    }

    @Override
    public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
        symbTable.newScope();
        // System.out.println("Block expr" + " d: " + symbTable.currDepth());
        blockExpr.decls.accept(new VarNameDeclaration(), null);
        blockExpr.decls.accept(new FunNameDeclaration(), null);
        blockExpr.decls.accept(this, visArg);

        blockExpr.stmts.accept(this, visArg);
        blockExpr.expr.accept(this, visArg);
        symbTable.oldScope();
        // System.out.println("End expr name:");
        return null;
    }
}
