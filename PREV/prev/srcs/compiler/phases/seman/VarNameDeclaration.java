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
public class VarNameDeclaration<Result, Arg> extends NameResolver<Result, Arg> {

    @Override
    public Result visit(AbsVarDecl varDecl, Arg visArg) {
        try {
            // System.out.println("VarDecl:" + varDecl.name);
            symbTable.ins(varDecl.name, varDecl);
        } catch (Exception e) {
            throw new Report.Error(varDecl.location(), "Type defined twice in same scope");
        }

        super.visit(varDecl, visArg);
        return null;
    }

    @Override
    public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
        return null;
    }

}
