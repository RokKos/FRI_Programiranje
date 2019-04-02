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

    private SemType LinkTypes(AbsType type) {
        if (type instanceof AbsAtomType) {
            AbsAtomType atomType = (AbsAtomType) type;
            switch (atomType.type) {
            case VOID:
                SemVoidType voidType = new SemVoidType();
                SemAn.isType.put(type, voidType);
                return voidType;

            case INT:
                SemIntType intType = new SemIntType();
                SemAn.isType.put(type, intType);
                return new SemIntType();

            case CHAR:
                SemCharType charType = new SemCharType();
                SemAn.isType.put(type, charType);
                return new SemCharType();

            case BOOL:
                SemBoolType boolType = new SemBoolType();
                SemAn.isType.put(type, boolType);
                return new SemBoolType();

            default:
                throw new Report.Error(atomType.location(), "This type is not supported atomic type");
            }
        } else if (type instanceof AbsTypName) {
            AbsTypName namedType = (AbsTypName) type;
            SemNamedType semNamedType = new SemNamedType(namedType.name);
            SemAn.isType.put(type, semNamedType);
            return semNamedType;
        } else if (type instanceof AbsArrType) {
            AbsArrType arrType = (AbsArrType) type;
            long len = 0;
            if (arrType.len instanceof AbsAtomExpr) {
                AbsAtomExpr lenExpr = (AbsAtomExpr) arrType.len;
                if (lenExpr.type == Type.INT) {
                    len = Long.parseLong(lenExpr.expr);
                } else {
                    throw new Report.Error(arrType.location(),
                            "Atom expresion: " + lenExpr.expr + " is not of type INT");
                }
            } else {
                throw new Report.Error(arrType.location(), "Array expresion is not of type Atom Expresion");
            }

            SemArrType semArrType = new SemArrType(len, LinkTypes(arrType.elemType));
            SemAn.isType.put(type, semArrType);
            return semArrType;
        } else if (type instanceof AbsPtrType) {
            AbsPtrType ptrType = (AbsPtrType) type;
            SemPtrType semPtrType = new SemPtrType(LinkTypes(ptrType.ptdType));
            SemAn.isType.put(type, semPtrType);
            return semPtrType;
        } else if (type instanceof AbsRecType) {
            AbsRecType recType = (AbsRecType) type;
            AbsCompDecls componentDeclarations = (AbsCompDecls) recType.compDecls;
            Vector<AbsCompDecl> vComponentDeclarations = componentDeclarations.compDecls();
            Vector<SemType> semComponentTypes = new Vector<SemType>();
            SymbTable recSymTab = new SymbTable();
            for (AbsCompDecl compDecl : vComponentDeclarations) {
                try {
                    recSymTab.ins(compDecl.name, compDecl);
                } catch (Exception e) {
                    throw new Report.Error(compDecl.location(),
                            "This component name: " + compDecl.name + " declerad twice in one record");
                }
                semComponentTypes.add(LinkTypes(compDecl.type));
            }

            SemRecType semRecType = new SemRecType(semComponentTypes);

            symbTables.put(semRecType, recSymTab);
            SemAn.isType.put(type, semRecType);

            return semRecType;

        }

        throw new Report.Error(type.location(), "Not link for this type was found");
    }

    @Override
    public SemType visit(AbsTypDecl typDecl, Object visArg) {

        SemNamedType semType = new SemNamedType(typDecl.name);
        semType.define(LinkTypes(typDecl.type));

        SemAn.declaresType.put(typDecl, semType);
        super.visit(typDecl, visArg);
        return null;
    }
}
