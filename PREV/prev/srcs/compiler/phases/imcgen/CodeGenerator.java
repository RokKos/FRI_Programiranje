/**
 * @author sliva
 */
package compiler.phases.imcgen;

import java.util.*;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsAtomExpr.Type;
import compiler.data.abstree.visitor.*;
import compiler.data.imcode.ImcBINOP;
import compiler.data.imcode.ImcCONST;
import compiler.data.imcode.ImcESTMT;
import compiler.data.imcode.ImcLABEL;
import compiler.data.imcode.ImcMEM;
import compiler.data.imcode.ImcNAME;
import compiler.data.imcode.ImcTEMP;
import compiler.data.imcode.ImcBINOP.Oper;
import compiler.data.layout.*;
import compiler.data.type.SemArrType;
import compiler.data.type.SemType;
import compiler.phases.frames.*;
import compiler.phases.seman.SemAn;

/**
 * Intermediate code generator.
 * 
 * This is a plain full visitor
 * 
 * @author sliva
 */
public class CodeGenerator extends AbsFullVisitor<Object, Stack<Frame>> {

    private ImcMEM AccesToMemory(Access acc) {
        if (acc instanceof RelAccess) {
            RelAccess rAcc = (RelAccess) acc;
            ImcCONST offset = new ImcCONST(rAcc.offset);
            ImcTEMP fpTemp = new ImcTEMP(new Temp());
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
            ImcMEM memAcces = AccesToMemory(Frames.strings.get(atomExpr));
            ImcGen.exprImCode.put(atomExpr, memAcces);
            return memAcces;

        case INT:
            ImcCONST intConst = new ImcCONST(Long.parseLong(atomExpr.expr));
            ImcGen.exprImCode.put(atomExpr, intConst);
            return intConst;

        default:
            break;
        }
        return null;
    }

    @Override
    public Object visit(AbsVarName varName, Stack<Frame> visArg) {
        AbsVarDecl varDecl = (AbsVarDecl) SemAn.declaredAt.get(varName);
        ImcMEM memAcces = AccesToMemory(Frames.accesses.get(varDecl));
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

}
