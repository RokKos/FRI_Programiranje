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
public class FunNameDeclaration<Result, Arg> extends NameResolver<Result, Arg> {

    @Override
    public Result visit(AbsFunDecl funDecl, Arg visArg) {
        try {
            System.out.println("FunDecl deck:" + funDecl.name);
            symbTable.ins(funDecl.name, funDecl);
        } catch (Exception e) {
            throw new Report.Error(funDecl.location(), "Fun " + funDecl.name + " defined twice in same scope");
        }

        super.visit(funDecl, visArg);
        return null;
    }

    @Override
    public Result visit(AbsFunDef funDef, Arg visArg) {
        try {
            System.out.println("FunDef decl:" + funDef.name);
            symbTable.ins(funDef.name, funDef);

            symbTable.newScope();
            System.out.println("New scope:" + funDef.name + " d: " + symbTable.currDepth());
            funDef.parDecls.accept(this, visArg);
            funDef.value.accept(new VarNameDeclaration(), null);
            funDef.value.accept(this, visArg);
            symbTable.oldScope();
            System.out.println("old scope:" + funDef.name);

        } catch (Exception e) {
            throw new Report.Error(funDef.location(), "Fun " + funDef.name + " defined twice in same scope");
        }
        return null;
    }

    @Override
    public Result visit(AbsVarName varName, Arg visArg) {
        try {
            System.out.println("varName:" + varName.name + " d: " + symbTable.currDepth());
            AbsDecl varDeclaration = symbTable.fnd(varName.name);
            SemAn.declaredAt.put(varName, varDeclaration);
        } catch (Exception e) {
            throw new Report.Error(varName.location(), "This var: " + varName.name + " does not exist in this scope");
        }
        super.visit(varName, visArg);
        return null;
    }

    @Override
    public Result visit(AbsParDecl parDecl, Arg visArg) {
        try {
            System.out.println("ParDecl:" + parDecl.name);
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
        System.out.println("Block expr" + " d: " + symbTable.currDepth());
        blockExpr.decls.accept(new VarNameDeclaration(), null);
        blockExpr.decls.accept(this, visArg);
        blockExpr.decls.accept(new FunNameResolution(), null);
        blockExpr.stmts.accept(this, visArg);
        blockExpr.stmts.accept(new FunNameResolution(), null);
        blockExpr.expr.accept(this, visArg);
        blockExpr.expr.accept(new FunNameResolution(), null);
        symbTable.oldScope();
        System.out.println("End expr name:");
        return null;
    }

}
