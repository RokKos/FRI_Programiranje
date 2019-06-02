/**
 * @author sliva
 */
package compiler.phases.asmcode;

import java.util.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;
import compiler.data.layout.*;
import compiler.data.asmcode.*;
import compiler.data.chunk.CodeChunk;
import compiler.common.report.*;

/**
 * @author sliva
 */
public class StmtGenerator implements ImcVisitor<Vector<AsmInstr>, Object> {

    private final String kSetNormal = "SET `d0,`s0";
    private final String kJump = "JMP ";
    private final String kCjump = "BZ `s0,";
    private final String kStore = "STO `s0,`s1,0";
    private final String kLoad = "LDO `d0,`s0,0";

    public Vector<AsmInstr> visit(ImcCJUMP cjump, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        Vector<Label> jumps = new Vector<>();
        Temp condTemp = cjump.cond.accept(new ExprGenerator(), instructions);
        Vector<Temp> uses = new Vector<>();
        uses.add(condTemp);

        jumps.add(cjump.posLabel);
        jumps.add(cjump.negLabel);
        instructions.add(new AsmOPER(kCjump + cjump.negLabel.name, uses, null, jumps));
        return instructions;

    }

    public Vector<AsmInstr> visit(ImcJUMP jump, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        Vector<Label> jumps = new Vector<>();
        jumps.add(jump.label);

        Vector<Temp> uses = new Vector<>();

        CodeChunk codeChunk = (CodeChunk) visArg;
        if (jump.label == codeChunk.exitLabel) {
            uses.add(codeChunk.frame.RV);
        }

        instructions.add(new AsmOPER(kJump + jump.label.name, uses, null, jumps));
        return instructions;
    }

    public Vector<AsmInstr> visit(ImcLABEL label, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        instructions.add(new AsmLABEL(label.label));
        return instructions;
    }

    public Vector<AsmInstr> visit(ImcMEM mem, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcMOVE move, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();

        Temp destTemp = move.dst.accept(new ExprGenerator(), instructions);
        Vector<Temp> defs = new Vector<>();
        defs.add(destTemp);

        Temp srcTemp = move.src.accept(new ExprGenerator(), instructions);
        Vector<Temp> uses = new Vector<>();
        uses.add(srcTemp);

        if (move.dst instanceof ImcTEMP && move.src instanceof ImcTEMP) {
            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        } else if (move.dst instanceof ImcTEMP && move.src instanceof ImcBINOP) {
            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        } else if (move.dst instanceof ImcTEMP && move.src instanceof ImcCONST) {
            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        } else if (move.dst instanceof ImcMEM) {
            uses.add(destTemp);
            instructions.add(new AsmOPER(kStore, uses, null, null));
        } else if (move.src instanceof ImcMEM) {
            Temp loadReg = new Temp();
            Vector<Temp> defs_load = new Vector<>();
            defs_load.add(loadReg);
            instructions.add(new AsmOPER(kLoad, uses, defs_load, null));

            instructions.add(new AsmMOVE(kSetNormal, defs_load, defs));
        } else if (move.dst instanceof ImcTEMP && move.src instanceof ImcNAME) {
            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        } else if (move.dst instanceof ImcTEMP && move.src instanceof ImcCALL) {
            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        }

        return instructions;
    }

    public Vector<AsmInstr> visit(ImcESTMT eStmt, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        eStmt.expr.accept(new ExprGenerator(), instructions);
        return instructions;
    }

    public Vector<AsmInstr> visit(ImcCONST constant, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcSEXPR sExpr, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        instructions.addAll(sExpr.stmt.accept(this, null));
        sExpr.accept(new ExprGenerator(), instructions);
        return instructions;
    }

    public Vector<AsmInstr> visit(ImcSTMTS stmts, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        for (ImcStmt stmt : stmts.stmts()) {
            instructions.addAll(stmt.accept(this, visArg));
        }
        return instructions;
    }
}
