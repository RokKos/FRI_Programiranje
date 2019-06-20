/**
 * @author sliva
 */
package compiler.common.logger;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import compiler.common.report.*;

/**
 * A logger used for producing XML/XSL logs of compiler internal data
 * structures.
 * 
 * @author sliva
 */
public class Logger implements AutoCloseable {

	/** The name of the XML file to be produced. */
	private final String xmlFileName;

	/** The name of the relating XSL file (to be included in the XML header). */
	private final String xslFileName;

	/** The entire XML document being constructed. */
	private final Document doc;

	/** The stack of the XML documents (used during construction). */
	private final Stack<Element> elements = new Stack<Element>();

	/**
	 * Constructs a new logger.
	 * 
	 * @param phaseName   The name of the phase being logged.
	 * @param xmlFileName The name of the XML file to be produced.
	 * @param xslFileName The name of the relating XSL file.
	 */
	public Logger(String phaseName, String xmlFileName, String xslFileName) {
		this.xmlFileName = xmlFileName;
		this.xslFileName = xslFileName;

		// Prepare a new log document.
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException ex) {
			throw new Report.InternalError();
		}

		// Create the root element representing the entire phase.
		Element phase = doc.createElement(phaseName);
		doc.appendChild(phase);
		elements.push(phase);

		// Add XSL declaration.
		ProcessingInstruction xsl = doc.createProcessingInstruction("xml-stylesheet",
				"type=\"text/xsl\" href=\"" + this.xslFileName + "\"");
		doc.insertBefore(xsl, phase);
	}

	@Override
	public void close() {
		try {
			elements.pop();
		} catch (EmptyStackException ex) {
			throw new InternalError();
		}
		if (!elements.empty())
			throw new InternalError();

		// Dump the log document out.
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFileName));
			transformer.transform(source, result);
		} catch (TransformerException ex) {
			Report.warning("Cannot open log file '" + xmlFileName + "'.");
		}
	}

	/**
	 * Starts a new XML element (within the active XML element) and makes it active.
	 * Only one XML element can be active at each moment.
	 * 
	 * @param tagName The tag name of a new XML element.
	 */
	public void begElement(String tagName) {
		try {
			Element element = doc.createElement(tagName);
			elements.peek().appendChild(element);
			elements.push(element);
		} catch (EmptyStackException ex) {
			throw new InternalError();
		}
	}

	/**
	 * Ends the current XML element and makes its parent element active. Only one
	 * XML element can be active at each moment.
	 */
	public void endElement() {
		try {
			elements.pop();
		} catch (EmptyStackException ex) {
			throw new InternalError();
		}
	}

	/**
	 * Adds an attribute to the active XML element.
	 * 
	 * @param attrName  The name of an attribute.
	 * @param attrValue The value of an attribute.
	 */
	public void addAttribute(String attrName, String attrValue) {
		try {
			elements.peek().setAttribute(attrName, attrValue);
		} catch (EmptyStackException ex) {
			throw new InternalError();
		}
	}

}
