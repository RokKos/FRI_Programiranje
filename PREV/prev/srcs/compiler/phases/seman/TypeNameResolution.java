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
public class TypeNameResolution<Result, Arg> extends NameResolver<Result, Arg> {

    @Override
    public Result visit(AbsTypName typName, Arg visArg) {
        try {
            if (Main.kDebugOn) {
                System.out.println("TypeName:" + typName.name);
            }
            AbsDecl typeDeclaration = symbTable.fnd(typName.name);

            // Not the best way to do this.. enter can surpass this statement
            if (typeDeclaration.location().GetLine() == typName.location().GetLine()) {
                throw new Report.Error(typName.location(), "Type : " + typName.name + " references itself");
            }

            SemAn.declaredAt.put(typName, typeDeclaration);
        } catch (Exception e) {
            throw new Report.Error(typName.location(),
                    "This type: " + typName.name + " does not exist in this scope. Message: " + e.toString());
        }

        super.visit(typName, visArg);
        return null;
    }

    @Override
    public Result visit(AbsBlockExpr blockExpr, Arg visArg) {
        return null;
    }
}
