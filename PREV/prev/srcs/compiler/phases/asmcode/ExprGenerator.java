/**
 * @author sliva
 */
package compiler.phases.asmcode;

import java.util.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;
import compiler.common.report.Report;
import compiler.data.asmcode.*;

/**
 * @author sliva
 */
public class ExprGenerator implements ImcVisitor<Temp, Vector<AsmInstr>> {

    private final String kSetHigh = "SET `d0 ";
    private final String kBinopAppend = "`d0 `s0 `s1";
    private final String kCompareParam = "`d0 `s0 0";
    private final String kHighBit = "32768"; // 2^15
    // Binary operator op names
    private final String kAdd = "ADD ";
    private final String kAnd = "AND ";
    private final String kCompare = "CMP ";
    private final String kDiv = "DIV ";
    private final String kMul = "MUL ";
    private final String kNxor = "NXOR ";
    private final String kOr = "OR ";
    private final String kSub = "SUB ";
    private final String kXor = "XOR ";

    //
    private final String kLeftShiftUnsigned = "SLU ";

    public Temp visit(ImcBINOP binOp, Vector<AsmInstr> instructions) {
        Vector<Temp> defs = new Vector<>();
        Vector<Temp> uses = new Vector<>();

        Temp resTemp = new Temp();
        defs.add(resTemp);

        Temp fstTemp = binOp.fstExpr.accept(this, instructions);
        Temp sndTemp = binOp.sndExpr.accept(this, instructions);
        uses.add(fstTemp);
        uses.add(sndTemp);

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
            Vector<Temp> regRR = new Vector<>();
            // Hacky
            regRR.add(new Temp());
            instructions.add(new AsmMOVE("SET `d0 $rR", regRR, defs)); // Remainder is in rR register
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
            GreaterLowerComparison(kAnd, kXor, instructions, uses, defs);
            break;

        case GEQ:
            GreaterLowerComparison(kOr, kXor, instructions, uses, defs);
            break;

        case LTH:
            GreaterLowerComparison(kAnd, kNxor, instructions, uses, defs);
            break;

        case LEQ:
            GreaterLowerComparison(kOr, kNxor, instructions, uses, defs);
            break;

        default:
            throw new Report.Error("EXPR GENERATOR: Unsuported binary operator");
        }

        return resTemp;
    }

    private void GreaterLowerComparison(String finalComparison, String largerGreaterChecking,
            Vector<AsmInstr> instructions, Vector<Temp> uses, Vector<Temp> defs) {
        instructions.add(new AsmOPER(kCompare + kBinopAppend, uses, defs, null));

        // Comparing last bit to see if differend than zero, that means that is bigger
        // or smaller
        Vector<Temp> defs_A_reg = new Vector<>();
        defs_A_reg.add(new Temp());
        instructions.add(new AsmOPER(kXor + kCompareParam, defs, defs_A_reg, null));

        // Seting high of one register to 1000000000000000
        Vector<Temp> defs_C_reg = new Vector<>();
        defs_C_reg.add(new Temp());
        instructions.add(new AsmOPER(kSetHigh + kHighBit, null, defs_C_reg, null));

        // Comparing first bit to see if differend than zero, that means that is smaller
        Vector<Temp> defs_B_reg = new Vector<>();
        Temp regB = new Temp();
        defs_B_reg.add(regB);
        defs_C_reg.add(regB);
        instructions.add(new AsmOPER(largerGreaterChecking + kBinopAppend, defs_C_reg, defs_B_reg, null));

        // Shift top bit to the begining for later and
        instructions.add(new AsmOPER(kLeftShiftUnsigned + "`d0 `s0 63", defs_B_reg, defs_B_reg, null));

        Vector<Temp> A_and_B = new Vector<>();
        A_and_B.addAll(defs_A_reg);
        A_and_B.addAll(defs_B_reg);
        // Final end to check all out
        instructions.add(new AsmOPER(finalComparison + kBinopAppend, A_and_B, defs, null));
    }

    public Temp visit(ImcCALL call, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcCJUMP cjump, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcCONST constant, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcESTMT eStmt, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcJUMP jump, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcLABEL label, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcMEM mem, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcMOVE move, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcNAME name, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcSEXPR sExpr, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcSTMTS stmts, Vector<AsmInstr> visArg) {
        return new Temp();
    }

    public Temp visit(ImcTEMP temp, Vector<AsmInstr> visArg) {
        return temp.temp;
    }

    public Temp visit(ImcUNOP unOp, Vector<AsmInstr> visArg) {
        return new Temp();
    }

}
