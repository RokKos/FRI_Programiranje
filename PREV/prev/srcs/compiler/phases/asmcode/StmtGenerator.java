/**
 * @author sliva
 */
package compiler.phases.asmcode;

import java.util.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;
import compiler.data.layout.*;
import compiler.data.asmcode.*;
import compiler.common.report.*;

/**
 * @author sliva
 */
public class StmtGenerator implements ImcVisitor<Vector<AsmInstr>, Object> {

    private final String kSetNormal = "SET `d0 `s0";

    // TODO

    // IZ STMT GENERATORJA CALLI V EXPR GENERATOR K TI DA NAZAJ TEMP STVARI

    public Vector<AsmInstr> visit(ImcBINOP binOp, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcCALL call, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcCJUMP cjump, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcCONST constant, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcESTMT eStmt, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcJUMP jump, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcLABEL label, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcMEM mem, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcMOVE move, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        if (move.dst instanceof ImcTEMP && move.src instanceof ImcTEMP) {
            Temp destTemp = move.dst.accept(new ExprGenerator(), instructions);
            Vector<Temp> defs = new Vector<>();
            defs.add(destTemp);

            Temp srcTemp = move.src.accept(new ExprGenerator(), instructions);
            Vector<Temp> uses = new Vector<>();
            uses.add(srcTemp);

            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        } else if (move.dst instanceof ImcTEMP && move.src instanceof ImcBINOP) {
            Temp destTemp = move.dst.accept(new ExprGenerator(), instructions);
            Vector<Temp> defs = new Vector<>();
            defs.add(destTemp);

            Temp srcTemp = move.src.accept(new ExprGenerator(), instructions);
            Vector<Temp> uses = new Vector<>();
            uses.add(srcTemp);

            instructions.add(new AsmMOVE(kSetNormal, uses, defs));
        }

        return instructions;
    }

    public Vector<AsmInstr> visit(ImcNAME name, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        Vector<Temp> defs = new Vector<>();
        Temp temp = new Temp();
        defs.add(temp);

        String set = "SET `d0 " + name.label;
        instructions.add(new AsmOPER(set, null, defs, null));

        return instructions;
    }

    public Vector<AsmInstr> visit(ImcSEXPR sExpr, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcSTMTS stmts, Object visArg) {
        Vector<AsmInstr> instructions = new Vector<AsmInstr>();
        for (ImcStmt stmt : stmts.stmts()) {
            instructions.addAll(stmt.accept(this, visArg));
        }
        return instructions;
    }

    public Vector<AsmInstr> visit(ImcTEMP temp, Object visArg) {
        return new Vector<AsmInstr>();
    }

    public Vector<AsmInstr> visit(ImcUNOP unOp, Object visArg) {
        return new Vector<AsmInstr>();
    }
}
