/**
 * @author sliva
 */
package compiler.phases.seman;

import compiler.common.report.*;
import compiler.data.abstree.*;
import compiler.data.abstree.AbsUnExpr.Oper;
import compiler.data.abstree.visitor.*;
import compiler.data.type.SemPtrType;
import compiler.data.type.SemType;

/**
 * Determines which value expression can denote an address.
 * 
 * @author sliva
 */
public class LValueChecker extends AbsFullVisitor<Boolean, Object> {
    @Override
    public Boolean visit(AbsAssignStmt assignStmt, Object visArg) {
        Boolean isLValue = SemAn.isAddr.get(assignStmt.dst);
        if (!(isLValue != null && isLValue)) {
            throw new Report.Error(assignStmt.location(), "Left side of assigment is not an address");
        }
        
        return null;
    }
}