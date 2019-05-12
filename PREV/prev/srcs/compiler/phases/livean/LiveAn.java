/**
 * @author sliva
 */
package compiler.phases.livean;

import java.util.*;
import compiler.data.asmcode.*;
import compiler.data.layout.*;
import compiler.phases.*;
import compiler.phases.asmcode.*;

/**
 * @author sliva
 */
public class LiveAn extends Phase {

	public LiveAn() {
		super("livean");
	}

	public void chunkLiveness(Code code) {
		// TODO
	}

	public void chunksLiveness() {
		for (Code code : AsmGen.codes) {
			chunkLiveness(code);
		}
	}

	public void log() {
		if (logger == null)
			return;
		for (Code code : AsmGen.codes) {
			{
				logger.begElement("code");
				logger.addAttribute("entrylabel", code.entryLabel.name);
				logger.addAttribute("exitlabel", code.exitLabel.name);
				logger.addAttribute("tempsize", Long.toString(code.tempSize));
				code.frame.log(logger);
				logger.begElement("instructions");
				for (AsmInstr instr : code.instrs) {
					logger.begElement("instruction");
					logger.addAttribute("code", instr.toString());
					logger.begElement("temps");
					logger.addAttribute("name", "use");
					for (Temp temp : instr.uses()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.begElement("temps");
					logger.addAttribute("name", "def");
					for (Temp temp : instr.defs()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.begElement("temps");
					logger.addAttribute("name", "in");
					for (Temp temp : instr.in()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.begElement("temps");
					logger.addAttribute("name", "out");
					for (Temp temp : instr.out()) {
						logger.begElement("temp");
						logger.addAttribute("name", temp.toString());
						logger.endElement();
					}
					logger.endElement();
					logger.endElement();
				}
				logger.endElement();
				logger.endElement();
			}
		}
	}

}
