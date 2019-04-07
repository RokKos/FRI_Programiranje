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

    private SemType ResolveName(AbsName name) {
        AbsDecl varDeclaration = SemAn.declaredAt.get(name);
        if (varDeclaration.type instanceof AbsTypName) {
            SemNamedType namedType = (SemNamedType) SemAn.isType.get(varDeclaration.type);
            return namedType.actualType();

        }
        SemType varType = SemAn.isType.get(varDeclaration.type);
        return varType;
    }

    @Override
    public SemType visit(AbsVarName varName, Object visArg) {
        SemType varType = ResolveName(varName);
        SemAn.ofType.put(varName, varType);
        return varType;
    }

    @Override
    public SemType visit(AbsBlockExpr blockExpr, Object visArg) {
        super.visit(blockExpr, visArg);

        SemType blockExprType = (SemType) blockExpr.expr.accept(this, visArg);
        SemAn.ofType.put(blockExpr, blockExprType);
        return blockExprType;
    }

    private boolean AreMatchingBoolTypes(SemType firstType, SemType secondType) {
        return firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemBoolType;
    }

    private boolean AreMatchingCharTypes(SemType firstType, SemType secondType) {
        return firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemBoolType;
    }

    private boolean AreMatchingIntTypes(SemType firstType, SemType secondType) {
        return firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemIntType;
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
            if (AreMatchingBoolTypes(firstType, secondType)) {
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
            if (AreMatchingCharTypes(firstType, secondType) || AreMatchingIntTypes(firstType, secondType)) {
                binType = firstType;
                break;
            } else {
                throw new Report.Error(binExpr.location(),
                        "Binary operator +, -, *, /, %  is inbetween two expresions that are not of type INT or CHAR");
            }

        case EQU:
        case NEQ:
            if (AreMatchingCharTypes(firstType, secondType) || AreMatchingIntTypes(firstType, secondType)
                    || AreMatchingBoolTypes(firstType, secondType)) {
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
            if (AreMatchingCharTypes(firstType, secondType) || AreMatchingIntTypes(firstType, secondType)) {
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

    @Override
    public SemType visit(AbsFunName funName, Object visArg) {
        AbsFunDecl funDeclaration = (AbsFunDecl) SemAn.declaredAt.get(funName);
        Vector<SemType> expectedParametersType = new Vector<SemType>();
        for (AbsParDecl parameterDecl : funDeclaration.parDecls.parDecls()) {
            expectedParametersType.add(SemAn.isType.get(parameterDecl.type).actualType());
        }

        visit(funName.args, expectedParametersType);
        SemType funType = ResolveName(funName);
        SemAn.ofType.put(funName, funType);
        return funType;
    }

    @Override
    public SemType visit(AbsArgs args, Object visArg) {
        Vector<SemType> expectedParametersType = (Vector<SemType>) visArg;

        int ind = 0;
        for (AbsExpr arg : args.args()) {
            SemType argType = (SemType) arg.accept(this, visArg);
            SemType actType = argType.actualType();
            if (!(expectedParametersType.get(ind).getClass().equals(actType.getClass()))) {
                throw new Report.Error(arg.location(),
                        "Arguments of function call doesn't match declared function arguments.");
            }
            ind++;
        }

        return null;
    }

    private boolean IsChrIntPtr(SemType type) {
        return type instanceof SemCharType || type instanceof SemIntType || type instanceof SemPtrType;
    }

    @Override
    public SemType visit(AbsCastExpr castExpr, Object visArg) {
        SemType originalType = (SemType) castExpr.expr.accept(this, visArg);
        SemType castType = SemAn.isType.get(castExpr.type);

        if (!IsChrIntPtr(originalType.actualType())) {
            throw new Report.Error(castExpr.location(),
                    "Original type is of type Char, Int or pointer so it cannot be casted");
        }

        SemType actType = castType.actualType();

        if (!IsChrIntPtr(actType)) {
            throw new Report.Error(castExpr.location(),
                    "Casted type is of type Char, Int or pointer so it cannot be casted");
        }

        SemAn.ofType.put(castExpr, castType);
        return castType;
    }

    @Override
    public SemType visit(AbsAssignStmt assignStmt, Object visArg) {
        SemType firstType = (SemType) assignStmt.dst.accept(this, visArg);
        SemType secondType = (SemType) assignStmt.src.accept(this, visArg);

        SemType assigmentType;

        if (AreMatchingCharTypes(firstType, secondType) || AreMatchingIntTypes(firstType, secondType)
                || AreMatchingBoolTypes(firstType, secondType)) {
            assigmentType = new SemVoidType();
        } else if (firstType.getClass().equals(secondType.getClass()) && firstType instanceof SemPtrType) {
            SemPtrType firstPtrType = (SemPtrType) firstType;
            SemPtrType secondPtrType = (SemPtrType) secondType;
            if (!firstPtrType.ptdType.getClass().equals(secondPtrType.ptdType.getClass())) {
                throw new Report.Error(assignStmt.location(),
                        "Assigment between two expresions that don't have same PTD TYPE");
            }
            assigmentType = new SemVoidType();

        } else {
            throw new Report.Error(assignStmt.location(),
                    "Assigment between two expresions that don't have same TYPE (possible same types are INT, CHAR, BOOL)");
        }

        return assigmentType;
    }

    private boolean IsBool(SemType type) {
        return type instanceof SemBoolType;
    }

    private boolean IsVoid(SemType type) {
        return type instanceof SemVoidType;
    }

    @Override
    public SemType visit(AbsIfStmt ifStmt, Object visArg) {
        SemType condType = (SemType) ifStmt.cond.accept(this, visArg);
        if (!IsBool(condType)) {
            throw new Report.Error(ifStmt.cond.location(), "Condition of IF statement is not of type BOOL");
        }

        SemType thenType = (SemType) ifStmt.thenStmts.accept(this, visArg);
        if (!IsVoid(thenType)) {
            throw new Report.Error(ifStmt.thenStmts.location(), "Then statement of IF statement is not of type VOID");
        }

        SemType elseType = (SemType) ifStmt.elseStmts.accept(this, visArg);
        if (elseType != null && IsVoid(elseType)) {
            throw new Report.Error(ifStmt.elseStmts.location(), "Else statement of IF statement is not of type VOID");
        }

        return new SemVoidType();
    }

    @Override
    public SemType visit(AbsWhileStmt whileStmt, Object visArg) {
        SemType condType = (SemType) whileStmt.cond.accept(this, visArg);
        if (!IsBool(condType)) {
            throw new Report.Error(whileStmt.cond.location(), "Condition of IF statement is not of type BOOL");
        }

        SemType stmtsType = (SemType) whileStmt.stmts.accept(this, visArg);
        if (stmtsType != null && IsVoid(stmtsType)) {
            throw new Report.Error(whileStmt.stmts.location(), "Else statement of IF statement is not of type VOID");
        }

        return null;
    }

    @Override
    public SemType visit(AbsStmts stmts, Object visArg) {
        // Not completly OK but everything is checked before so it's cool
        super.visit(stmts, visArg);
        if (stmts.stmts().size() > 0) {
            return new SemVoidType();
        }
        return null;
    }

    public SemType visit(AbsStmt stmt, Object visArg) {
        return new SemVoidType();
    }

    @Override
    public SemType visit(AbsVarDecl varDecl, Object visArg) {
        SemType varType = (SemType) varDecl.type.accept(this, visArg);
        if (varType instanceof SemVoidType) {
            throw new Report.Error(varDecl.location(), "Variable type cannot be of type VOID");
        } else if (varType instanceof SemArrType) {
            SemArrType arrType = (SemArrType) varType;
            if (arrType.elemType.actualType() instanceof SemVoidType) {
                throw new Report.Error(varDecl.location(), "This array element type is void");
            }
        } else if (varType instanceof SemRecType) {
            SemRecType recType = (SemRecType) varType;
            for (SemType compType : recType.compTypes()) {
                if (compType.actualType() instanceof SemVoidType) {
                    throw new Report.Error(varDecl.location(), "This component type of record is void");
                }
            }
        }

        super.visit(varDecl, visArg);
        return varType;
    }

    @Override
    public SemType visit(AbsFunDef funDef, Object visArg) {
        funDef.parDecls.accept(this, visArg);
        SemType funType = (SemType) SemAn.isType.get(funDef.type).actualType();
        SemType valType = (SemType) funDef.value.accept(this, visArg);
        valType = valType.actualType();

        if (!(funType.getClass().equals(valType.getClass()))) {
            throw new Report.Error(funDef.location(), "Function type and return value types don't match");
        }

        return funType;
    }

    @Override
    public SemType visit(AbsArrType arrType, Object visArg) {
        arrType.len.accept(this, visArg);
        arrType.elemType.accept(this, visArg);
        return SemAn.isType.get(arrType);
    }

    @Override
    public SemType visit(AbsPtrType ptrType, Object visArg) {
        ptrType.ptdType.accept(this, visArg);
        return SemAn.isType.get(ptrType);
    }

    @Override
    public SemType visit(AbsRecType recType, Object visArg) {
        recType.compDecls.accept(this, visArg);
        return SemAn.isType.get(recType);
    }

    @Override
    public SemType visit(AbsAtomType atomType, Object visArg) {
        return SemAn.isType.get(atomType);
    }
}