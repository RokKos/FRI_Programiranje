/**
 * @author sliva
 */
package compiler.data.abstree;

import compiler.common.report.*;
import compiler.data.abstree.visitor.*;

/**
 * @author sliva
 */
public interface AbsTree extends Locatable {

	public abstract <Result, Arg> Result accept(AbsVisitor<Result, Arg> visitor, Arg accArg);

}
