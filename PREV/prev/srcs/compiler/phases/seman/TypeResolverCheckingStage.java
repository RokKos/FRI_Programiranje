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
        if (unExpr.oper == Oper.ADD || unExpr.oper == Oper.SUB) {
            SemType unaryType = (SemType) unExpr.subExpr.accept(this, visArg);
            if (unaryType instanceof SemIntType) {
                return unaryType;
            }

            throw new Report.Error(unExpr.location(), "Unary + or - are infront of expresion that is not of type INT");
        } else if (unExpr.oper == Oper.NOT) {
            SemType unaryType = (SemType) unExpr.subExpr.accept(this, visArg);
            if (unaryType instanceof SemBoolType) {
                return unaryType;
            }

            throw new Report.Error(unExpr.location(), "NOT is  infront of expresion that is not of type BOOL");
        }

        return visit(unExpr, visArg);
    }

    @Override
    public SemType visit(AbsAtomExpr atomExpr, Object visArg) {
        return SemAn.ofType.get(atomExpr);
    }

    @Override
    public SemType visit(AbsVarName varName, Object visArg) {
        // TODO: Put in paramter declarations
        return SemAn.ofType.get(varName);
    }

    // Binary

    @Override
    public SemType visit(AbsBinExpr binExpr, Object visArg) {
        SemType firstType = (SemType) binExpr.fstExpr.accept(this, visArg);
        SemType secondType = (SemType) binExpr.sndExpr.accept(this, visArg);

        switch (binExpr.oper) {
        case AND:
        case IOR:
        case XOR:
            if (firstType instanceof SemBoolType && secondType instanceof SemBoolType) {
                return firstType;
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
                return firstType;
            } else {
                System.out.println(firstType.toString() + " s: " + secondType.toString());
                throw new Report.Error(binExpr.location(),
                        "Binary operator +, -, *, /, %  is inbetween two expresions that are not of type INT or CHAR");
            }

        case EQU:
        case NEQ:
            if (firstType.getClass().equals(secondType.getClass()) && (firstType instanceof SemIntType
                    || firstType instanceof SemBoolType || firstType instanceof SemCharType)) {
                return new SemBoolType();
            } else if (firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemPtrType) {
                SemPtrType firstPtrType = (SemPtrType) firstType;
                SemPtrType secondPtrType = (SemPtrType) secondType;
                if (!firstPtrType.ptdType.getClass().equals(secondPtrType.ptdType.getClass())) {
                    throw new Report.Error(binExpr.location(),
                            "Binary operator ==, !=  is inbetween two expresions that don't have same PTD TYPE");
                }
                return new SemBoolType();
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
                return new SemBoolType();
            } else if (firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemPtrType) {
                SemPtrType firstPtrType = (SemPtrType) firstType;
                SemPtrType secondPtrType = (SemPtrType) secondType;
                if (!firstPtrType.ptdType.getClass().equals(secondPtrType.ptdType.getClass())) {
                    throw new Report.Error(binExpr.location(),
                            "Binary operator <=, >=, <, >  is inbetween two expresions that don't have same PTD TYPE");
                }
                return new SemBoolType();
            } else {
                throw new Report.Error(binExpr.location(),
                        "Binary operator <=, >=, <, >  is inbetween two expresions that are not of type INT, CHAR, BOOL or PTR");
            }

        default:

            break;
        }

        return null;
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
}