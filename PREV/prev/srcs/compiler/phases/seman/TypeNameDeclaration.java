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
public class TypeNameDeclaration<Result, Arg> extends NameResolver<Result, Arg> {

    @Override
    public Result visit(AbsTypDecl typDecl, Arg visArg) {
        try {
            // System.out.println("TypeDecl:" + typDecl.name);
            symbTable.ins(typDecl.name, typDecl);
        } catch (Exception e) {
            throw new Report.Error(typDecl.location(), "Type defined twice in same scope");
        }

        super.visit(typDecl, visArg);
        return null;
    }

    @Override
    public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
        // System.out.println("TypeNameDeclaration new scope currDepth: " +
        // symbTable.currDepth());
        symbTable.newScope();
        super.visit(blockExpr, visArg);
        blockExpr.decls.accept(new TypeNameResolution(), null);
        blockExpr.decls.accept(new VarNameDeclaration(), null);
        blockExpr.stmts.accept(new TypeNameResolution(), null);
        blockExpr.stmts.accept(new VarNameDeclaration(), null);
        blockExpr.expr.accept(new TypeNameResolution(), null);
        blockExpr.expr.accept(new VarNameDeclaration(), null);
        symbTable.oldScope();
        // System.out.println("TypeNameDeclaration old scope currDepth: " +
        // symbTable.currDepth());
        return null;
    }

}
