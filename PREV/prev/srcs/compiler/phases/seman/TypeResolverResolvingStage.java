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
public class TypeResolverResolvingStage extends TypeResolver {

    private SemType AtomTypeResolution(AbsAtomType atomType) {
        switch (atomType.type) {
        case VOID:
            return new SemVoidType();

        case INT:
            return new SemIntType();

        case CHAR:
            return new SemCharType();

        case BOOL:
            return new SemBoolType();

        default:
            throw new Report.Error(atomType.location(), "This type is not supported atomic type");
        }
    }

    private SemType AtomExpresionResolution(AbsAtomExpr atomExpr) {
        switch (atomExpr.type) {
        case VOID:
            return new SemVoidType();

        case INT:
            return new SemIntType();

        case CHAR:
            return new SemCharType();

        case BOOL:
            return new SemBoolType();

        default:
            throw new Report.Error(atomExpr.location(), "This type is not supported atomic type");
        }
    }

    private SemType LinkTypes(AbsType type) {
        if (type instanceof AbsAtomType) {
            AbsAtomType atomType = (AbsAtomType) type;
            SemType semAtomType = AtomTypeResolution(atomType);
            SemAn.isType.put(type, semAtomType);
            return semAtomType;
        } else if (type instanceof AbsTypName) {
            AbsTypName namedType = (AbsTypName) type;
            SemNamedType semNamedType = SemAn.declaresType.get((AbsTypDecl) SemAn.declaredAt.get(namedType));
            SemAn.isType.put(type, semNamedType);
            return semNamedType;
        } else if (type instanceof AbsArrType) {
            AbsArrType arrType = (AbsArrType) type;
            long len = 0;
            if (arrType.len instanceof AbsAtomExpr) {
                AbsAtomExpr lenExpr = (AbsAtomExpr) arrType.len;
                if (lenExpr.type == Type.INT) {
                    len = Long.parseLong(lenExpr.expr);
                    if (len < 0) {
                        throw new Report.Error(arrType.location(), "Len of array is negative");
                    }
                } else {
                    throw new Report.Error(arrType.location(),
                            "Atom expresion: " + lenExpr.expr + " is not of type INT");
                }
            } else {
                throw new Report.Error(arrType.location(), "Array expresion is not of type Atom Expresion");
            }

            SemType semElemType = LinkTypes(arrType.elemType);
            SemArrType semArrType = new SemArrType(len, semElemType);
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
                SemType semComponentType = LinkTypes(compDecl.type);
                semComponentTypes.add(semComponentType);
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
        SemNamedType semType = SemAn.declaresType.get(typDecl);
        semType.define(LinkTypes(typDecl.type));

        super.visit(typDecl, visArg);
        return null;
    }

    @Override
    public SemType visit(AbsAtomExpr atomExpr, Object visArg) {

        SemType atomType = AtomExpresionResolution(atomExpr);
        SemAn.ofType.put(atomExpr, atomType);

        super.visit(atomExpr, visArg);
        return null;
    }

    @Override
    public SemType visit(AbsAtomType atomType, Object visArg) {
        SemType semAtomType = AtomTypeResolution(atomType);
        SemAn.isType.put(atomType, semAtomType);

        super.visit(atomType, visArg);
        return null;
    }

    @Override
    public SemType visit(AbsTypName typName, Object visArg) {
        AbsTypDecl typeDeclaration = (AbsTypDecl) SemAn.declaredAt.get(typName);
        SemNamedType namedType = (SemNamedType) SemAn.declaresType.get(typeDeclaration);
        SemAn.isType.put(typName, namedType);

        super.visit(typName, visArg);
        return null;
    }
}
