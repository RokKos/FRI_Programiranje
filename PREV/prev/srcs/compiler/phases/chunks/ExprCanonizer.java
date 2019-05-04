/**
 * @author sliva
 */
package compiler.phases.chunks;

import java.util.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;

/**
 * @author sliva
 */
public class ExprCanonizer implements ImcVisitor<ImcExpr, Vector<ImcStmt>> {

    public ImcExpr visit(ImcBINOP binOp, Vector<ImcStmt> visArg) {
        Vector<ImcStmt> stmts1 = binOp.fstExpr.accept(new StmtCanonizer(), null);
        visArg.addAll(stmts1);

        ImcExpr expr1 = binOp.fstExpr.accept(this, visArg);
        ImcTEMP temp1 = new ImcTEMP(new Temp());
        ImcStmt move1 = new ImcMOVE(temp1, expr1);
        visArg.add(move1);

        Vector<ImcStmt> stmts2 = binOp.sndExpr.accept(new StmtCanonizer(), null);
        visArg.addAll(stmts2);

        ImcExpr expr2 = binOp.sndExpr.accept(this, visArg);
        ImcTEMP temp2 = new ImcTEMP(new Temp());
        ImcStmt move2 = new ImcMOVE(temp2, expr2);
        visArg.add(move2);

        return new ImcBINOP(binOp.oper, temp1, temp2);

    }

    public ImcExpr visit(ImcCALL call, Vector<ImcStmt> visArg) {
        Vector<ImcExpr> args = new Vector<ImcExpr>();
        for (ImcExpr expr : call.args()) {
            visArg.addAll(expr.accept(new StmtCanonizer(), null));

            ImcExpr argExpr = expr.accept(this, visArg);
            ImcTEMP temp = new ImcTEMP(new Temp());
            args.add(temp);
            ImcStmt move = new ImcMOVE(temp, argExpr);
            visArg.add(move);
        }

        return new ImcCALL(call.label, args);
    }

    public ImcExpr visit(ImcCJUMP cjump, Vector<ImcStmt> visArg) {
        Vector<ImcStmt> stmts = cjump.cond.accept(new StmtCanonizer(), null);
        visArg.addAll(stmts);

        ImcExpr expr = cjump.cond.accept(this, visArg);
        ImcTEMP temp = new ImcTEMP(new Temp());
        ImcStmt move = new ImcMOVE(temp, expr);
        visArg.add(move);

        return temp;// new ImcCJUMP(temp, cjump.posLabel, cjump.negLabel);
    }

    public ImcExpr visit(ImcCONST constant, Vector<ImcStmt> visArg) {
        return constant;
    }

    public ImcExpr visit(ImcESTMT eStmt, Vector<ImcStmt> visArg) {
        // Vector<ImcStmt> stmts = eStmt.accept(new StmtCanonizer(), null);
        // visArg.addAll(stmts);
        ImcExpr expr = eStmt.expr.accept(this, visArg);
        visArg.add(new ImcESTMT(expr));
        return null;
    }

    public ImcExpr visit(ImcJUMP jump, Vector<ImcStmt> visArg) {
        visArg.add(jump);
        return null;
    }

    public ImcExpr visit(ImcLABEL label, Vector<ImcStmt> visArg) {
        visArg.add(label);
        return null;
    }

    public ImcExpr visit(ImcMEM mem, Vector<ImcStmt> visArg) {
        ImcExpr expr = mem.addr.accept(this, visArg);
        Vector<ImcStmt> stmts = mem.addr.accept(new StmtCanonizer(), null);
        visArg.addAll(stmts);

        ImcTEMP temp = new ImcTEMP(new Temp());
        ImcStmt move = new ImcMOVE(temp, expr);
        visArg.add(move);

        return new ImcMEM(temp);

    }

    public ImcExpr visit(ImcMOVE move, Vector<ImcStmt> visArg) {
        Vector<ImcStmt> srcStmts = move.src.accept(new StmtCanonizer(), null);
        visArg.addAll(srcStmts);
        ImcExpr srcExpr = move.src.accept(this, visArg);
        ImcTEMP temp = new ImcTEMP(new Temp());
        ImcStmt moveS = new ImcMOVE(temp, srcExpr);
        visArg.add(moveS);

        Vector<ImcStmt> desStmts = move.dst.accept(new StmtCanonizer(), null);
        visArg.addAll(desStmts);

        ImcExpr desExpr = move.dst.accept(this, visArg);
        ImcMOVE finalMove = new ImcMOVE(desExpr, temp);
        visArg.add(finalMove);

        return desExpr;
    }

    public ImcExpr visit(ImcNAME name, Vector<ImcStmt> visArg) {
        return name;
    }

    public ImcExpr visit(ImcSEXPR sExpr, Vector<ImcStmt> visArg) {
        ImcExpr expr = sExpr.stmt.accept(this, visArg);
        Vector<ImcStmt> stmts = sExpr.stmt.accept(new StmtCanonizer(), null);
        visArg.addAll(stmts);
        // visArg.addAll(sExpr.expr.accept(new StmtCanonizer(), null));
        return sExpr.expr.accept(this, visArg);
    }

    public ImcExpr visit(ImcSTMTS stmts, Vector<ImcStmt> visArg) {
        for (ImcStmt stmt : stmts.stmts()) {
            stmt.accept(this, visArg);
        }

        return null;
    }

    public ImcExpr visit(ImcTEMP temp, Vector<ImcStmt> visArg) {
        return temp;
    }

    public ImcExpr visit(ImcUNOP unOp, Vector<ImcStmt> visArg) {
        ImcExpr expr = unOp.subExpr.accept(this, visArg);
        Vector<ImcStmt> stmts = unOp.subExpr.accept(new StmtCanonizer(), null);
        visArg.addAll(stmts);

        ImcTEMP temp = new ImcTEMP(new Temp());
        ImcStmt move = new ImcMOVE(temp, expr);
        visArg.add(move);

        return new ImcUNOP(unOp.oper, temp);
    }

}
