/**
 * @author sliva
 */
package compiler.phases.chunks;

import java.util.*;
import compiler.common.report.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;
import compiler.data.imcode.visitor.*;

/**
 * @author sliva
 */
public class StmtCanonizer implements ImcVisitor<Vector<ImcStmt>, Object> {

    public Vector<ImcStmt> visit(ImcBINOP binOp, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcCALL call, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcCJUMP cjump, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcCONST constant, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcESTMT eStmt, Object visArg) {
        return eStmt.expr.accept(this, null);
    }

    public Vector<ImcStmt> visit(ImcJUMP jump, Object visArg) {
        Vector<ImcStmt> stmts = new Vector<ImcStmt>();
        stmts.add(jump);
        return stmts;
    }

    public Vector<ImcStmt> visit(ImcLABEL label, Object visArg) {
        Vector<ImcStmt> stmts = new Vector<ImcStmt>();
        stmts.add(label);
        return stmts;
    }

    public Vector<ImcStmt> visit(ImcMEM mem, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcMOVE move, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcNAME name, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcSEXPR sExpr, Object visArg) {
        return new Vector<ImcStmt>();
        // Vector<ImcStmt> stmts = new Vector<ImcStmt>();
        // stmts.add(sExpr.stmt);
        // return stmts;
    }

    public Vector<ImcStmt> visit(ImcSTMTS stmts, Object visArg) {
        Vector<ImcStmt> vStmts = new Vector<ImcStmt>();
        for (ImcStmt stmt : stmts.stmts()) {
            vStmts.addAll(stmt.accept(this, null));
        }
        return vStmts;
    }

    public Vector<ImcStmt> visit(ImcTEMP temp, Object visArg) {
        return new Vector<ImcStmt>();
    }

    public Vector<ImcStmt> visit(ImcUNOP unOp, Object visArg) {
        return new Vector<ImcStmt>();
    }

}
