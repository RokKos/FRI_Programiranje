/**
 * @author sliva 
 */
package compiler.phases.seman;

import java.util.*;
import compiler.common.report.*;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsAtomExpr.Type;
import compiler.data.abstree.AbsUnExpr.Oper;
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
    public SemType visit(AbsUnExpr unExpr, Object visArg) {
        SemType semUnaryType;

        switch (unExpr.oper) {
        case ADD:
        case SUB: {
            SemType unaryType = (SemType) unExpr.subExpr.accept(this, visArg);
            if (unaryType instanceof SemIntType) {
                semUnaryType = unaryType;
            } else {
                throw new Report.Error(unExpr.location(),
                        "Unary + or - are infront of expresion that is not of type INT");
            }
        }
            break;

        case NOT: {
            SemType unaryType = (SemType) unExpr.subExpr.accept(this, visArg);
            if (unaryType instanceof SemBoolType) {
                semUnaryType = unaryType;
            } else {
                throw new Report.Error(unExpr.location(), "NOT is  infront of expresion that is not of type BOOL");
            }
        }
            break;

        case ADDR: {
            SemType unaryType = (SemType) unExpr.subExpr.accept(this, visArg);
            if (!(unaryType instanceof SemVoidType)) {
                semUnaryType = new SemPtrType(unaryType);
            } else {
                throw new Report.Error(unExpr.location(), "Address cannot be reached with type VOID");
            }
        }
            break;

        case DATA: {
            SemType unaryType = (SemType) unExpr.subExpr.accept(this, visArg);
            if (unaryType instanceof SemPtrType) {
                SemPtrType ptrType = (SemPtrType) unaryType;
                if (!(ptrType.ptdType instanceof SemVoidType)) {
                    semUnaryType = ptrType.ptdType;
                } else {
                    throw new Report.Error(unExpr.location(),
                            "Trying to get to location of expresion that is of type VOID");
                }
            } else {
                throw new Report.Error(unExpr.location(),
                        "Trying to get to location of expresion that is not of type PTR");
            }

        }
            break;

        default:
            throw new Report.Error(unExpr.location(), "Unhandlet Unary operator");
        }

        SemAn.ofType.put(unExpr, semUnaryType);

        return semUnaryType;
    }

    @Override
    public SemType visit(AbsAtomExpr atomExpr, Object visArg) {
        return SemAn.ofType.get(atomExpr);
    }

    @Override
    public SemType visit(AbsVarName varName, Object visArg) {
        AbsDecl varDeclaration = SemAn.declaredAt.get(varName);
        if (varDeclaration.type instanceof AbsTypName) {
            AbsTypName typeName = (AbsTypName) varDeclaration.type;
            AbsTypDecl typeDecl = (AbsTypDecl) SemAn.declaredAt.get(typeName);
            SemNamedType namedType = SemAn.declaresType.get(typeDecl);
            return namedType.actualType();

        }
        SemType varType = SemAn.isType.get(varDeclaration.type);
        SemAn.ofType.put(varName, varType);
        return varType;
    }

    @Override
    public SemType visit(AbsFunName funName, Object visArg) {
        AbsDecl funDecl = SemAn.declaredAt.get(funName);
        if (funDecl.type instanceof AbsTypName) {
            AbsTypName typeName = (AbsTypName) funDecl.type;
            AbsTypDecl typeDecl = (AbsTypDecl) SemAn.declaredAt.get(typeName);
            SemNamedType namedType = SemAn.declaresType.get(typeDecl);
            return namedType.actualType();

        }

        visit(funName.args, visArg);
        SemType funType = SemAn.isType.get(funDecl.type);
        SemAn.ofType.put(funName, funType);

        return funType;
    }

    @Override
    public SemType visit(AbsBlockExpr blockExpr, Object visArg) {
        super.visit(blockExpr, visArg);

        SemType blockExprType = (SemType) blockExpr.expr.accept(this, visArg);
        SemAn.ofType.put(blockExpr, blockExprType);
        return blockExprType;
    }

    @Override
    public SemType visit(AbsBinExpr binExpr, Object visArg) {
        SemType firstType = (SemType) binExpr.fstExpr.accept(this, visArg);
        SemType secondType = (SemType) binExpr.sndExpr.accept(this, visArg);

        SemType binType;

        switch (binExpr.oper) {
        case AND:
        case IOR:
        case XOR:
            if (firstType instanceof SemBoolType && secondType instanceof SemBoolType) {
                binType = firstType;
                break;
            } else {
                throw new Report.Error(binExpr.location(),
                        "Binary operator &, |, ^  is inbetween two expresions that are not of type BOOL");
            }

        case ADD:
        case SUB:
        case MUL:
        case DIV:
        case MOD:
            if ((firstType instanceof SemIntType && secondType instanceof SemIntType)
                    || (firstType instanceof SemCharType && secondType instanceof SemCharType)) {
                binType = firstType;
                break;
            } else {
                throw new Report.Error(binExpr.location(),
                        "Binary operator +, -, *, /, %  is inbetween two expresions that are not of type INT or CHAR");
            }

        case EQU:
        case NEQ:
            if (firstType.getClass().equals(secondType.getClass()) && (firstType instanceof SemIntType
                    || firstType instanceof SemBoolType || firstType instanceof SemCharType)) {
                binType = new SemBoolType();
                break;
            } else if (firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemPtrType) {
                SemPtrType firstPtrType = (SemPtrType) firstType;
                SemPtrType secondPtrType = (SemPtrType) secondType;
                if (!firstPtrType.ptdType.getClass().equals(secondPtrType.ptdType.getClass())) {
                    throw new Report.Error(binExpr.location(),
                            "Binary operator ==, !=  is inbetween two expresions that don't have same PTD TYPE");
                }
                binType = new SemBoolType();
                break;
            } else {
                throw new Report.Error(binExpr.location(),
                        "Binary operator ==, !=  is inbetween two expresions that are not of type INT, CHAR, BOOL or PTR");
            }

        case LTH:
        case GTH:
        case LEQ:
        case GEQ:
            if (firstType.getClass().equals(secondType.getClass())
                    && (firstType instanceof SemIntType || firstType instanceof SemCharType)) {
                binType = new SemBoolType();
                break;
            } else if (firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemPtrType) {
                SemPtrType firstPtrType = (SemPtrType) firstType;
                SemPtrType secondPtrType = (SemPtrType) secondType;
                if (!firstPtrType.ptdType.getClass().equals(secondPtrType.ptdType.getClass())) {
                    throw new Report.Error(binExpr.location(),
                            "Binary operator <=, >=, <, >  is inbetween two expresions that don't have same PTD TYPE");
                }
                binType = new SemBoolType();
                break;
            } else {
                throw new Report.Error(binExpr.location(),
                        "Binary operator <=, >=, <, >  is inbetween two expresions that are not of type INT, CHAR, BOOL or PTR");
            }

        default:
            throw new Report.Error(binExpr.location(), "Unhadelt binary operator");
        }

        SemAn.ofType.put(binExpr, binType);

        return binType;
    }

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

    @Override
    public SemType visit(AbsNewExpr newExpr, Object visArg) {
        SemType type = (SemType) newExpr.type.accept(this, visArg);
        if (!(type instanceof SemVoidType)) {
            return new SemPtrType(type);
        } else {
            throw new Report.Error(newExpr.location(), "New type cannot be VOID");
        }
    }

    @Override
    public SemType visit(AbsDelExpr delExpr, Object visArg) {
        SemType type = (SemType) delExpr.expr.accept(this, visArg);
        if (type instanceof SemPtrType) {
            SemPtrType ptrType = (SemPtrType) type;
            if (!(ptrType.ptdType instanceof SemVoidType)) {
                return ptrType.ptdType;
            } else {
                throw new Report.Error(delExpr.location(),
                        "Trying to delete location of expresion that is of type VOID");
            }
        } else {
            throw new Report.Error(delExpr.location(),
                    "Trying to delete location of expresion that is not of type PTR");
        }
    }

    @Override
    public SemType visit(AbsArrExpr arrExpr, Object visArg) {
        SemType arrType = (SemType) arrExpr.array.accept(this, visArg);
        if (!(arrType instanceof SemArrType)) {
            throw new Report.Error(arrExpr.location(), "Arr expresion is not of type SemArrType");
        }
        SemArrType semArrType = (SemArrType) arrType;

        SemType indexType = (SemType) arrExpr.index.accept(this, visArg);
        if (!(indexType instanceof SemIntType)) {
            throw new Report.Error(arrExpr.location(), "Index expresion is not of type SemIntType");
        }
        SemIntType semIntType = (SemIntType) indexType;

        SemType wholeArrType = semArrType.elemType;
        SemAn.ofType.put(arrExpr, wholeArrType);

        return wholeArrType;
    }

    @Override
    public SemType visit(AbsRecExpr recExpr, Object visArg) {
        SemType varType = (SemType) recExpr.record.accept(this, visArg);
        System.out.println(varType == null);
        if (!(varType instanceof SemRecType)) {
            throw new Report.Error(recExpr.location(), "Record expresion is not of type SemRecType");
        }
        SemRecType record = (SemRecType) varType;

        SymbTable symbTable = symbTables.get(record);
        try {
            AbsCompDecl recordComponentDeclaration = (AbsCompDecl) symbTable.fnd(recExpr.comp.name);
            AbsType compType = recordComponentDeclaration.type;
            SemType semCompType = SemAn.isType.get(compType);
            SemAn.ofType.put(recExpr, semCompType);
            return semCompType;

        } catch (Exception e) {
            throw new Report.Error(recExpr.location(), "Record has now componenet: " + recExpr.comp.name);
        }
    }

}