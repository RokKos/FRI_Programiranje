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
            // System.out.println("TypeName:" + funName.name);
            AbsDecl typeDeclaration = symbTable.fnd(funName.name);
            SemAn.declaredAt.put(funName, typeDeclaration);
        } catch (Exception e) {
            throw new Report.Error(funName.location(), "This type: " + funName.name + " does not exist in this scope");
        }

        super.visit(funName, visArg);
        return null;
    }

    @Override
    public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
        return null;
    }
}
