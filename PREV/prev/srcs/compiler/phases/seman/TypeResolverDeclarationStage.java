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
public class TypeResolverDeclarationStage extends TypeResolver {

    @Override
    public SemType visit(AbsTypDecl typDecl, Object visArg) {

        SemNamedType semType = new SemNamedType(typDecl.name);

        SemAn.declaresType.put(typDecl, semType);
        super.visit(typDecl, visArg);
        return null;
    }
}
