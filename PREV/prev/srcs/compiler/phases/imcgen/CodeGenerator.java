/**
 * @author sliva
 */
package compiler.phases.imcgen;

import java.util.*;

import compiler.common.report.Report;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsAtomExpr.Type;
import compiler.data.abstree.visitor.*;
import compiler.data.imcode.ImcBINOP;
import compiler.data.imcode.ImcCALL;
import compiler.data.imcode.ImcCJUMP;
import compiler.data.imcode.ImcCONST;
import compiler.data.imcode.ImcESTMT;
import compiler.data.imcode.ImcExpr;
import compiler.data.imcode.ImcLABEL;
import compiler.data.imcode.ImcMEM;
import compiler.data.imcode.ImcMOVE;
import compiler.data.imcode.ImcNAME;
import compiler.data.imcode.ImcSEXPR;
import compiler.data.imcode.ImcSTMTS;
import compiler.data.imcode.ImcStmt;
import compiler.data.imcode.ImcTEMP;
import compiler.data.imcode.ImcUNOP;
import compiler.data.imcode.ImcBINOP.Oper;
import compiler.data.layout.*;
import compiler.data.type.SemArrType;
import compiler.data.type.SemCharType;
import compiler.data.type.SemRecType;
import compiler.data.type.SemType;
import compiler.phases.frames.*;
import compiler.phases.seman.SemAn;
import compiler.phases.seman.SymbTable;
import compiler.phases.seman.TypeResolver;

/**
 * Intermediate code generator.
 * 
 * This is a plain full visitor
 * 
 * @author sliva
 */
public class CodeGenerator extends AbsFullVisitor<Object, Stack<Frame>> {

    private ImcMEM AccesToMemory(Access acc, Temp FP) {
        if (acc instanceof RelAccess) {
            RelAccess rAcc = (RelAccess) acc;
            ImcCONST offset = new ImcCONST(rAcc.offset);
            ImcTEMP fpTemp = new ImcTEMP(FP);
            ImcBINOP binop = new ImcBINOP(Oper.ADD, fpTemp, offset);
            return new ImcMEM(binop);
        } else {
            AbsAccess aAcc = (AbsAccess) acc;
            ImcNAME globalName = new ImcNAME(aAcc.label);
            return new ImcMEM(globalName);
        }
    }

    @Override
    public Object visit(AbsAtomExpr atomExpr, Stack<Frame> visArg) {

        switch (atomExpr.type) {

        case STR:
            ImcMEM memAcces = AccesToMemory(Frames.strings.get(atomExpr), visArg.peek().FP);
            ImcGen.exprImCode.put(atomExpr, memAcces);
            return memAcces;

        case INT:
            ImcCONST intConst = new ImcCONST(Long.parseLong(atomExpr.expr));
            ImcGen.exprImCode.put(atomExpr, intConst);
            return intConst;

        case BOOL:
            ImcCONST boolConst = new ImcCONST(Boolean.parseBoolean(atomExpr.expr) ? 1 : 0);
            ImcGen.exprImCode.put(atomExpr, boolConst);
            return boolConst;

        case CHAR:
            ImcCONST charConst = new ImcCONST((long) atomExpr.expr.charAt(1));
            ImcGen.exprImCode.put(atomExpr, charConst);
            return charConst;

        case VOID:
            ImcCONST voidConst = new ImcCONST(0);
            ImcGen.exprImCode.put(atomExpr, voidConst);
            return voidConst;

        case PTR:
            ImcCONST ptrConst = new ImcCONST(0);
            ImcGen.exprImCode.put(atomExpr, ptrConst);
            return ptrConst;

        default:
            break;
        }
        return null;
    }

    @Override
    public Object visit(AbsVarName varName, Stack<Frame> visArg) {
        AbsVarDecl varDecl = (AbsVarDecl) SemAn.declaredAt.get(varName);
        ImcMEM memAcces = AccesToMemory(Frames.accesses.get(varDecl), visArg.peek().FP);
        ImcGen.exprImCode.put(varName, memAcces);
        return memAcces;
    }

    @Override
    public Object visit(AbsArrExpr arrExpr, Stack<Frame> visArg) {
        ImcMEM arrayStart = (ImcMEM) arrExpr.array.accept(this, visArg);
        ImcCONST arrayIndex = (ImcCONST) arrExpr.index.accept(this, visArg);

        AbsVarName arrName = (AbsVarName) arrExpr.array;
        AbsVarDecl arrDecl = (AbsVarDecl) SemAn.declaredAt.get(arrName);
        SemArrType arrType = (SemArrType) SemAn.isType.get(arrDecl.type);
        SemType arrElemType = arrType.elemType;

        ImcCONST lenghtConst = new ImcCONST(arrElemType.size());
        ImcBINOP arrayIndexOffset = new ImcBINOP(Oper.MUL, arrayIndex, lenghtConst);

        ImcBINOP binop = new ImcBINOP(Oper.ADD, arrayStart, arrayIndexOffset);
        ImcMEM memAcces = new ImcMEM(binop);
        ImcGen.exprImCode.put(arrExpr, memAcces);

        return memAcces;
    }

    @Override
    public Object visit(AbsSource source, Stack<Frame> visArg) {
        Stack<Frame> frames = new Stack<Frame>();
        source.decls.accept(this, frames);
        return null;
    }

    @Override
    public Object visit(AbsFunDef funDef, Stack<Frame> visArg) {
        visArg.add(Frames.frames.get(funDef));

        funDef.parDecls.accept(this, visArg);
        funDef.type.accept(this, visArg);
        funDef.value.accept(this, visArg);

        visArg.pop();
        return null;
    }

    @Override
    public Object visit(AbsRecExpr recExpr, Stack<Frame> visArg) {
        AbsVarName recordName = (AbsVarName) recExpr.record;
        AbsVarName recordComponent = (AbsVarName) recExpr.comp;

        AbsVarDecl recordDeclaration = (AbsVarDecl) SemAn.declaredAt.get(recordName);
        SemRecType recordType = (SemRecType) SemAn.isType.get(recordDeclaration.type).actualType();
        SymbTable recTable = TypeResolver.symbTables.get(recordType);

        AbsCompDecl recComponentDeclaration;
        try {
            recComponentDeclaration = (AbsCompDecl) recTable.fnd(recordComponent.name);
        } catch (Exception e) {
            throw new Report.Error(recExpr.location(), "Record name: " + recordName.name + " was not found.");
        }

        RelAccess recordAcces = (RelAccess) Frames.accesses.get(recComponentDeclaration);

        ImcMEM memAcces = AccesToMemory(recordAcces, visArg.peek().FP);
        ImcGen.exprImCode.put(recExpr, memAcces);
        return memAcces;
    }

    @Override
    public Object visit(AbsUnExpr unExpr, Stack<Frame> visArg) {
        ImcExpr expr = (ImcExpr) unExpr.subExpr.accept(this, visArg);
        switch (unExpr.oper) {
        case DATA: {
            ImcMEM memAcces = new ImcMEM(expr);
            ImcGen.exprImCode.put(unExpr, memAcces);
            return memAcces;
        }
        case ADDR: {
            ImcMEM memAcces = (ImcMEM) expr;
            ImcGen.exprImCode.put(unExpr, memAcces.addr);
            return memAcces.addr;
        }
        case ADD:
            ImcGen.exprImCode.put(unExpr, expr);
            return expr;
        case SUB: {
            if (expr instanceof ImcCONST) {
                ImcCONST constExpr = (ImcCONST) expr;
                ImcCONST negativeConstant = new ImcCONST(-constExpr.value);
                ImcGen.exprImCode.put(unExpr, negativeConstant);
                return negativeConstant;
            }

            ImcUNOP sub = new ImcUNOP(ImcUNOP.Oper.NEG, expr);
            ImcGen.exprImCode.put(unExpr, sub);
            return sub;
        }
        case NOT: {
            if (expr instanceof ImcCONST) {
                ImcCONST constExpr = (ImcCONST) expr;
                ImcCONST negatingConstant = new ImcCONST(constExpr.value == 1 ? 0 : 1);
                ImcGen.exprImCode.put(unExpr, negatingConstant);
                return negatingConstant;
            }

            ImcUNOP not = new ImcUNOP(ImcUNOP.Oper.NOT, expr);
            ImcGen.exprImCode.put(unExpr, not);
            return not;
        }

        default:
            throw new Report.Error(unExpr.location(), "Not supported unary operator");

        }
    }

    private Map<AbsBinExpr.Oper, ImcBINOP.Oper> kAbsOperToImcOper = new HashMap<AbsBinExpr.Oper, ImcBINOP.Oper>() {
        {
            put(AbsBinExpr.Oper.IOR, ImcBINOP.Oper.IOR);
            put(AbsBinExpr.Oper.XOR, ImcBINOP.Oper.XOR);
            put(AbsBinExpr.Oper.AND, ImcBINOP.Oper.AND);
            put(AbsBinExpr.Oper.EQU, ImcBINOP.Oper.EQU);
            put(AbsBinExpr.Oper.NEQ, ImcBINOP.Oper.NEQ);
            put(AbsBinExpr.Oper.LTH, ImcBINOP.Oper.LTH);
            put(AbsBinExpr.Oper.GTH, ImcBINOP.Oper.GTH);
            put(AbsBinExpr.Oper.LEQ, ImcBINOP.Oper.LEQ);
            put(AbsBinExpr.Oper.GEQ, ImcBINOP.Oper.GEQ);
            put(AbsBinExpr.Oper.ADD, ImcBINOP.Oper.ADD);
            put(AbsBinExpr.Oper.SUB, ImcBINOP.Oper.SUB);
            put(AbsBinExpr.Oper.MUL, ImcBINOP.Oper.MUL);
            put(AbsBinExpr.Oper.DIV, ImcBINOP.Oper.DIV);
            put(AbsBinExpr.Oper.MOD, ImcBINOP.Oper.MOD);
        }
    };

    @Override
    public Object visit(AbsBinExpr binExpr, Stack<Frame> visArg) {
        ImcExpr leftExpr = (ImcExpr) binExpr.fstExpr.accept(this, visArg);
        ImcExpr rightExpr = (ImcExpr) binExpr.sndExpr.accept(this, visArg);

        ImcBINOP binopExpr = new ImcBINOP(kAbsOperToImcOper.get(binExpr.oper), leftExpr, rightExpr);
        ImcGen.exprImCode.put(binExpr, binopExpr);
        return binopExpr;
    }

    @Override
    public Object visit(AbsNewExpr newExpr, Stack<Frame> visArg) {
        Vector<ImcExpr> args = new Vector<ImcExpr>();
        SemType newType = SemAn.isType.get(newExpr.type);
        args.add(new ImcCONST(newType.size()));
        ImcCALL stdNewCall = new ImcCALL(new Label("new"), args);
        ImcGen.exprImCode.put(newExpr, stdNewCall);
        return stdNewCall;
    }

    @Override
    public Object visit(AbsDelExpr delExpr, Stack<Frame> visArg) {
        Vector<ImcExpr> args = new Vector<ImcExpr>();
        ImcExpr expr = (ImcExpr) delExpr.expr.accept(this, visArg);
        args.add(expr);

        ImcCALL stdDelCall = new ImcCALL(new Label("del"), args);
        ImcGen.exprImCode.put(delExpr, stdDelCall);
        return stdDelCall;
    }

    @Override
    public Object visit(AbsFunName funName, Stack<Frame> visArg) {
        Vector<ImcExpr> args = new Vector<ImcExpr>();
        for (AbsExpr arg : funName.args.args()) {
            ImcExpr imcArg = (ImcExpr) arg.accept(this, visArg);
            args.add(imcArg);
        }

        AbsFunDecl funDecl = (AbsFunDecl) SemAn.declaredAt.get(funName);
        Label funLabel = Frames.frames.get(funDecl).label;

        ImcCALL funCall = new ImcCALL(funLabel, args);
        ImcGen.exprImCode.put(funName, funCall);
        return funCall;
    }

    @Override
    public Object visit(AbsCastExpr castExpr, Stack<Frame> visArg) {
        ImcExpr expr = (ImcExpr) castExpr.expr.accept(this, visArg);

        SemType castType = SemAn.isType.get(castExpr.type);

        if (castType instanceof SemCharType) {
            ImcBINOP mod256 = new ImcBINOP(Oper.MOD, expr, new ImcCONST(256));
            ImcGen.exprImCode.put(castExpr, mod256);
            return mod256;
        }

        ImcGen.exprImCode.put(castExpr, expr);
        return expr;
    }

    @Override
    public Object visit(AbsBlockExpr blockExpr, Stack<Frame> visArg) {
        blockExpr.decls.accept(this, visArg);
        ImcSTMTS stmts = (ImcSTMTS) blockExpr.stmts.accept(this, visArg);

        ImcExpr expr = (ImcExpr) blockExpr.expr.accept(this, visArg);
        ImcSEXPR sExpr = new ImcSEXPR(stmts, expr);
        ImcGen.exprImCode.put(blockExpr, sExpr);
        return expr;
    }

    @Override
    public Object visit(AbsAssignStmt assignStmt, Stack<Frame> visArg) {
        ImcExpr destination = (ImcExpr) assignStmt.dst.accept(this, visArg);
        ImcExpr source = (ImcExpr) assignStmt.src.accept(this, visArg);

        ImcMOVE move = new ImcMOVE(destination, source);
        ImcGen.stmtImCode.put(assignStmt, move);
        return move;
    }

    @Override
    public Object visit(AbsExprStmt exprStmt, Stack<Frame> visArg) {
        ImcExpr expr = (ImcExpr) exprStmt.expr.accept(this, visArg);
        ImcESTMT estmt = new ImcESTMT(expr);
        ImcGen.stmtImCode.put(exprStmt, estmt);
        return estmt;
    }

    @Override
    public Object visit(AbsStmts stmts, Stack<Frame> visArg) {
        Vector<ImcStmt> vStmts = new Vector<ImcStmt>();
        for (AbsStmt stmt : stmts.stmts()) {
            vStmts.add((ImcStmt) stmt.accept(this, visArg));
        }

        ImcSTMTS imcStmts = new ImcSTMTS(vStmts);
        return imcStmts;
    }

    @Override
    public Object visit(AbsWhileStmt whileStmt, Stack<Frame> visArg) {
        Vector<ImcStmt> vStmts = new Vector<ImcStmt>();
        ImcExpr cond = (ImcExpr) whileStmt.cond.accept(this, visArg);

        Label trueLabel = new Label();
        ImcLABEL imcTrueLabel = new ImcLABEL(trueLabel);
        vStmts.add(imcTrueLabel);

        ImcStmt whileStmts = (ImcStmt) whileStmt.stmts.accept(this, visArg);
        vStmts.add(whileStmts);

        Label falseLabel = new Label();
        ImcLABEL imcFalseLabel = new ImcLABEL(falseLabel);
        vStmts.add(imcFalseLabel);

        ImcCJUMP cJump = new ImcCJUMP(cond, trueLabel, falseLabel);
        vStmts.add(0, cJump);

        ImcSTMTS stmts = new ImcSTMTS(vStmts);
        ImcGen.stmtImCode.put(whileStmt, stmts);
        return stmts;
    }

    @Override
    public Object visit(AbsIfStmt ifStmt, Stack<Frame> visArg) {
        Vector<ImcStmt> vStmts = new Vector<ImcStmt>();
        ImcExpr cond = (ImcExpr) ifStmt.cond.accept(this, visArg);

        Label trueLabel = new Label();
        ImcLABEL imcTrueLabel = new ImcLABEL(trueLabel);
        vStmts.add(imcTrueLabel);

        ImcStmt thenStmts = (ImcStmt) ifStmt.thenStmts.accept(this, visArg);
        vStmts.add(thenStmts);

        Label falseLabel = new Label();
        ImcSTMTS elseStmts = (ImcSTMTS) ifStmt.elseStmts.accept(this, visArg);
        if (elseStmts.stmts().size() > 0) {
            ImcLABEL imcFalseLabel = new ImcLABEL(falseLabel);
            vStmts.add(imcFalseLabel);

            vStmts.add(elseStmts);
        }

        ImcCJUMP cJump = new ImcCJUMP(cond, trueLabel, falseLabel);
        vStmts.add(0, cJump);

        ImcSTMTS stmts = new ImcSTMTS(vStmts);
        ImcGen.stmtImCode.put(ifStmt, stmts);
        return stmts;
    }
}
