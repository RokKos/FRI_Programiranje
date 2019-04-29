/**
 * @author sliva
 */
package compiler.data.chunk;

import java.util.*;
import compiler.data.layout.*;
import compiler.data.imcode.*;

/**
 * A code chunk.
 * 
 * @author sliva
 */
public class CodeChunk extends Chunk {

	/** A frame of a function. */
	public final Frame frame;

	/** The statements of a function body. */
	private final Vector<ImcStmt> stmts;

	/**
	 * The function's body entry label, i.e., the label the prologue jumps to.
	 */
	public final Label entryLabel;

	/**
	 * The function's body exit label, i.e., the label at which the epilogue starts.
	 */
	public final Label exitLabel;

	/**
	 * Constructs a new code chunk.
	 * 
	 * @param frame A frame of a function.
	 * @param stmts The statements of a function body.
	 */
	public CodeChunk(Frame frame, Vector<ImcStmt> stmts, Label entryLabel, Label exitLabel) {
		this.frame = frame;
		this.stmts = new Vector<ImcStmt>(stmts);
		this.entryLabel = entryLabel;
		this.exitLabel = exitLabel;
	}

	/**
	 * Returns the statements of a function body.
	 * 
	 * @return The statements of a function body.
	 */
	public Vector<ImcStmt> stmts() {
		return new Vector<ImcStmt>(stmts);
	}

}
