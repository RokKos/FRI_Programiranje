/**
 * @author sliva
 */
package compiler.phases.asmcode;

import java.util.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;
import compiler.Main;
import compiler.common.report.Report;
import compiler.data.asmcode.*;

/**
 * @author sliva
 */
public class ExprGenerator implements ImcVisitor<Temp, Vector<AsmInstr>> {

    private final String kSetConstPrePendix = "SET `d0,";
    private final String kLoadNormal = "LDO `d0,`s0,0";
    private final String kINCLow = "INCML `d0,";
    private final String kINCMidHigh = "INCMH `d0,";
    private final String kINCHigh = "INCH `d0,";
    private final String kBinopAppend = "`d0,`s0,`s1";
    private final String kCompareParam = "`d0,`s0,0";
    private final String kNeg = "NEG `d0,0,`s0";
    private final String kHighBit = "32768"; // 2^15
    private final String kPush = "PUSHJ $" + Main.numOfRegs + ","; // Temporary
    private final String kStore = "STO `s0,$254,";
    private final String kLoad = "LDO `d0,$254,0";
    private final short kOctaSize = 8;

    // Binary operator op names
    private final String kAdd = "ADD ";
    private final String kAnd = "AND ";
    private final String kCompare = "CMP ";
    private final String kDiv = "DIV ";
    private final String kMul = "MUL ";
    private final String kNxor = "NXOR ";
    private final String kNor = "NOR ";
    private final String kOr = "OR ";
    private final String kSub = "SUB ";
    private final String kXor = "XOR ";

    // Comparing
    private final String kSettingCompare = "`d0,`s0,1";
    private final String kPositive = "ZSP ";
    private final String kNonNegative = "ZSNN ";
    private final String kNonPositive = "ZSNP ";
    private final String kNegative = "ZSN ";

    private final String kLeftShiftUnsigned = "SLU ";

    public Temp visit(ImcBINOP binOp, Vector<AsmInstr> instructions) {
        Vector<Temp> defs = new Vector<>();
        Vector<Temp> uses = new Vector<>();

        Temp resTemp = new Temp();
        defs.add(resTemp);

        Temp fstTemp = binOp.fstExpr.accept(this, instructions);
        Temp sndTemp = binOp.sndExpr.accept(this, instructions);

        uses.add(fstTemp);

        // checking for mem
        if (binOp.fstExpr instanceof ImcMEM) {
            Temp loadReg = new Temp();
            Vector<Temp> defs_load = new Vector<>();
            defs_load.add(loadReg);
            instructions.add(new AsmOPER(kLoadNormal, uses, defs_load, null));

            // we used that temp
            uses.remove(fstTemp);
            uses.addAll(defs_load);
        }

        uses.add(sndTemp);

        if (binOp.sndExpr instanceof ImcMEM) {
            Temp loadReg = new Temp();
            Vector<Temp> defs_load = new Vector<>();
            defs_load.add(loadReg);
            instructions.add(new AsmOPER(kLoadNormal, uses, defs_load, null));

            // we used that temp
            uses.remove(sndTemp);
            uses.addAll(defs_load);
        }

        switch (binOp.oper) {
        case ADD:
            instructions.add(new AsmOPER(kAdd + kBinopAppend, uses, defs, null));
            break;

        case SUB:
            instructions.add(new AsmOPER(kSub + kBinopAppend, uses, defs, null));
            break;

        case MUL:
            instructions.add(new AsmOPER(kMul + kBinopAppend, uses, defs, null));
            break;

        case DIV:
            instructions.add(new AsmOPER(kDiv + kBinopAppend, uses, defs, null));
            break;

        case MOD:
            instructions.add(new AsmOPER(kDiv + kBinopAppend, uses, defs, null));
            instructions.add(new AsmOPER("GET `d0,rR", null, defs, null)); // Remainder is in rR register
            break;

        case AND:
            instructions.add(new AsmOPER(kAnd + kBinopAppend, uses, defs, null));
            break;

        case IOR:
            instructions.add(new AsmOPER(kOr + kBinopAppend, uses, defs, null));
            break;

        case XOR:
            instructions.add(new AsmOPER(kXor + kBinopAppend, uses, defs, null));
            break;

        case EQU:
            instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));
            // Bitwise negation because compare returns 0 if they are equal
            instructions.add(new AsmOPER(kNxor + kCompareParam, defs, defs, null));
            break;

        case NEQ:
            instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));
            // Bitwise negation because compare returns 0 if they are equal
            instructions.add(new AsmOPER(kXor + kCompareParam, defs, defs, null));
            break;

        case GTH:
            instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));
            instructions.add(new AsmOPER(kPositive + kSettingCompare, defs, defs, null));
            break;

        case GEQ:
            instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));
            instructions.add(new AsmOPER(kNonNegative + kSettingCompare, defs, defs, null));
            break;

        case LTH:
            instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));
            instructions.add(new AsmOPER(kNegative + kSettingCompare, defs, defs, null));
            break;

        case LEQ:
            instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));
            instructions.add(new AsmOPER(kNonPositive + kSettingCompare, defs, defs, null));
            break;

        default:
            throw new Report.Error("EXPR GENERATOR: Unsuported binary operator");
        }

        return resTemp;
    }

    public Temp visit(ImcCALL call, Vector<AsmInstr> instructions) {

        // Vector<Temp> defs = new Vector<>();
        // Temp registerCountReg = new Temp();
        // defs.add(registerCountReg);

        // int registerCound
        // instructions.add(new AsmOPER(kSetConstPrePendix + bits, null, defs, null));

        int offset = 0;
        for (ImcExpr parameter : call.args()) {
            Vector<Temp> uses = new Vector<>();
            Temp tempParam = parameter.accept(this, instructions);
            uses.add(tempParam);
            instructions.add(new AsmOPER(kStore + offset, uses, null, null));
            offset += kOctaSize;
        }

        Vector<Label> jumps = new Vector<>();
        jumps.add(call.label);

        instructions.add(new AsmOPER(kPush + call.label.name, null, null, jumps));

        Temp retVal = new Temp();
        Vector<Temp> defs = new Vector<>();
        defs.add(retVal);
        instructions.add(new AsmOPER(kLoad, null, defs, null));

        return retVal;
    }

    public Temp visit(ImcCONST constant, Vector<AsmInstr> instructions) {
        long value = Math.abs(constant.value);

        Vector<Temp> defs = new Vector<>();
        Temp resTemp = new Temp();
        defs.add(resTemp);

        int bits = ((short) value & 0xffff);
        instructions.add(new AsmOPER(kSetConstPrePendix + bits, null, defs, null));
        value >>= 16;

        if (value > 0) {
            bits = ((short) value & 0xffff);
            instructions.add(new AsmOPER(kINCLow + bits, null, defs, null));
            value >>= 16;
        }

        if (value > 0) {
            bits = ((short) value & 0xffff);
            instructions.add(new AsmOPER(kINCMidHigh + bits, null, defs, null));
            value >>= 16;
        }

        if (value > 0) {
            bits = ((short) value & 0xffff);
            instructions.add(new AsmOPER(kINCHigh + bits, null, defs, null));
            value >>= 16;
        }

        if (constant.value < 0) {
            instructions.add(new AsmOPER(kNeg, defs, defs, null));
        }

        return resTemp;
    }

    public Temp visit(ImcESTMT eStmt, Vector<AsmInstr> visArg) {
        return eStmt.expr.accept(this, visArg);
    }

    public Temp visit(ImcMEM mem, Vector<AsmInstr> visArg) {
        return mem.addr.accept(this, visArg);
    }

    public Temp visit(ImcNAME name, Vector<AsmInstr> instructions) {
        Vector<Temp> defs = new Vector<>();
        Temp temp = new Temp();
        defs.add(temp);

        String set = "LDA `d0," + name.label.name;

        instructions.add(new AsmOPER(set, null, defs, null));

        Vector<Temp> defsLoad = new Vector<>();
        Temp tempLoad = new Temp();
        defsLoad.add(tempLoad);
        String load = "LDO `d0,`s0,0";
        instructions.add(new AsmOPER(load, defs, defsLoad, null));

        return temp;
    }

    public Temp visit(ImcTEMP temp, Vector<AsmInstr> visArg) {
        return temp.temp;
    }

    public Temp visit(ImcUNOP unOp, Vector<AsmInstr> instructions) {
        Vector<Temp> uses = new Vector<>();

        Temp exprTemp = unOp.subExpr.accept(this, instructions);
        uses.add(exprTemp);

        switch (unOp.oper) {
        case NEG:
            instructions.add(new AsmOPER(kNeg, uses, uses, null));
            break;

        case NOT:
            instructions.add(new AsmOPER(kNor + kCompareParam, uses, uses, null));
            break;

        default:
            throw new Report.Error("EXPR GENERATOR: Unsuported unary operator");
        }

        return exprTemp;
    }

}
