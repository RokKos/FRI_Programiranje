/**
 * @author sliva 
 */
package compiler.phases.seman;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsAtomExpr.Type;
import compiler.data.abstree.visitor.*;
import compiler.data.type.*;
import compiler.data.type.property.*;

/**
 * Type resolving: the result is stored in {@link SemAn#declaresType},
 * {@link SemAn#isType}, and {@link SemAn#ofType}.
 * 
 * @author sliva
 */
public class TypeResolverCheckingStage extends TypeResolver {
    @Override
    public SemType visit(AbsTypDecl typDecl, Object visArg) {
        SemNamedType semType = SemAn.declaresType.get(typDecl);
        System.out.println("Checking type:" + semType.name);
        if (semType.type instanceof SemArrType) {
            SemArrType arrType = (SemArrType) semType.type;
            if (arrType.elemType.actualType() instanceof SemVoidType) {
                throw new Report.Error(typDecl.location(), "This array element type is void");
            }
        } else if (semType.type instanceof SemRecType) {
            SemRecType recType = (SemRecType) semType.type;
            for (SemType compType : recType.compTypes()) {
                if (compType.actualType() instanceof SemVoidType) {
                    throw new Report.Error(typDecl.location(), "This component type of record is void");
                }
            }
        }
        super.visit(typDecl, visArg);
        return null;
    }
}