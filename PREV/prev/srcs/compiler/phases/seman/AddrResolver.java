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
public class AddrResolver extends AbsFullVisitor<Boolean, Object> {

    @Override
    public Boolean visit(AbsVarName varName, Object visArg) {
        SemAn.isAddr.put(varName, true);
        return true;
    }

    @Override
    public Boolean visit(AbsArrExpr arrExpr, Object visArg) {
        arrExpr.array.accept(this, visArg);
        Boolean isArrAddr = SemAn.isAddr.get(arrExpr.array);
        if (isArrAddr) {
            SemAn.isAddr.put(arrExpr, true);
        }

        arrExpr.index.accept(this, visArg);
        return true;
    }

    @Override
    public Boolean visit(AbsRecExpr recExpr, Object visArg) {
        recExpr.record.accept(this, visArg);
        recExpr.comp.accept(this, visArg);

        Boolean isRecAddr = SemAn.isAddr.get(recExpr.record);
        if (isRecAddr) {
            SemAn.isAddr.put(recExpr, true);
        }

        return true;
    }

    @Override
    public Boolean visit(AbsUnExpr unExpr, Object visArg) {
        if (unExpr.oper == Oper.DATA) {
            SemType type = SemAn.ofType.get(unExpr.subExpr);
            if (type instanceof SemPtrType) {
                SemAn.isAddr.put(unExpr, true);
            }

        }

        return true;
    }

}
