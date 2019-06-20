/**
 * @author sliva
 */
package compiler.phases.seman;

import compiler.Main;
import compiler.common.report.*;
import compiler.data.abstree.*;
import compiler.data.abstree.visitor.*;

/**
 * Name resolving: the result is stored in {@link SemAn#declaredAt}.
 * 
 * @author sliva
 */
public class GlobalTypeNameDeclaration<Result, Arg> extends NameResolver<Result, Arg> {

    @Override
    public Result visit(AbsTypDecl typDecl, Arg visArg) {
        try {
            if (Main.kDebugOn) {
                System.out.println("TypeDecl:" + typDecl.name);
            }
            symbTable.ins(typDecl.name, typDecl);
        } catch (Exception e) {
            throw new Report.Error(typDecl.location(), "Type defined twice in same scope");
        }

        super.visit(typDecl, visArg);
        return null;
    }
}
