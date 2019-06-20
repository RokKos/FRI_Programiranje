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
            // System.out.println("FunDecl deck:" + funDecl.name);
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
            // System.out.println("FunDef decl:" + funDef.name);
            symbTable.ins(funDef.name, funDef);

        } catch (Exception e) {
            throw new Report.Error(funDef.location(), "Fun " + funDef.name + " defined twice in same scope");
        }

        return null;
    }

}
