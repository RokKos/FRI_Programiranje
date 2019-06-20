/**
 * @author sliva
 */
package compiler.phases.chunks;

import java.util.*;
import compiler.data.chunk.*;
import compiler.phases.*;

/**
 * @author sliva
 */
public class Chunks extends Phase {

	public static Vector<DataChunk> dataChunks = new Vector<DataChunk>();

	public static Vector<CodeChunk> codeChunks = new Vector<CodeChunk>();

	public Chunks() {
		super("chunks");
	}

	public void log() {
		ChunkLogger chunkLogger = new ChunkLogger(logger);
		for (DataChunk dataChunk : dataChunks)
			chunkLogger.log(dataChunk);
		for (CodeChunk codeChunk : codeChunks)
			chunkLogger.log(codeChunk);
	}

}
