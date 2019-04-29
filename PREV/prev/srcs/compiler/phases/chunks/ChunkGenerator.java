/**
 * @author sliva
 */
package compiler.phases.chunks;

import java.util.*;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsAtomExpr.Type;
import compiler.data.abstree.visitor.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;
import compiler.data.chunk.*;
import compiler.phases.frames.*;
import compiler.phases.imcgen.*;

/**
 * @author sliva
 *
 */
public class ChunkGenerator extends AbsFullVisitor<Object, Object> {

    @Override
    public Object visit(AbsFunDef funDef, Object visArg) {
        Frame frameFun = Frames.frames.get(funDef);
        Vector<ImcStmt> stmts = new Vector<ImcStmt>();
        Label entryLabel = new Label();
        Label exitLabel = new Label();
        stmts.add(new ImcLABEL(entryLabel));

        ImcExpr funBody = ImcGen.exprImCode.get(funDef.value);
        funBody = funBody.accept(new ExprCanonizer(), stmts);

        ImcStmt funStmt = new ImcMOVE(new ImcTEMP(frameFun.RV), funBody);

        // Vector<ImcStmt> canonizeStmts = funStmt.accept(new StmtCanonizer(), null);
        // stmts.addAll(canonizeStmts);
        stmts.add(funStmt);
        stmts.add(new ImcLABEL(exitLabel));

        Chunks.codeChunks.add(new CodeChunk(frameFun, stmts, entryLabel, exitLabel));

        super.visit(funDef, visArg);
        return null;
    }

    @Override
    public Object visit(AbsAtomExpr atomExpr, Object visArg) {
        if (atomExpr.type == Type.STR) {
            Chunks.dataChunks.add(new DataChunk(Frames.strings.get(atomExpr)));
        }
        super.visit(atomExpr, visArg);
        return null;
    }

    @Override
    public Object visit(AbsVarDecl varDecl, Object visArg) {
        if (Frames.accesses.get(varDecl) instanceof AbsAccess) {
            Chunks.dataChunks.add(new DataChunk((AbsAccess) Frames.accesses.get(varDecl)));
        }
        super.visit(varDecl, visArg);
        return null;

    }

}
