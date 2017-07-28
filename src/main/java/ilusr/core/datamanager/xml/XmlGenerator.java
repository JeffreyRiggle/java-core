package ilusr.core.datamanager.xml;
import java.io.File;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Jeff Riggle
 *
 * This class provides a simple wrapper around the xml document class.
 * This allows the user to write data to a xml document and save it later.
 */
public class XmlGenerator {

	private Document document;
	private Transformer transformer;
	
	//TODO: Add more functionality to this class to make it more useful.
	//TODO: Should this be thread safe?
	//TODO: Should this have a async save function?
	/**
	 * Minimal constructor, using this constructor will cause the xml document to be created for you.
	 * 
	 * @throws ParserConfigurationException When the generated xml cannot be parsed.
	 * @throws TransformerConfigurationException When the generated xml cannot be transformed.
	 */
	public XmlGenerator() throws ParserConfigurationException, TransformerConfigurationException {
		this(null);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		document(docBuilder.newDocument());
	}
	
	/**
	 * 
	 * @param document The document to modify and save.
	 * @throws TransformerConfigurationException When the generated xml cannot be transformed.
	 */
	public XmlGenerator(Document document) throws TransformerConfigurationException {
		this.document = document;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformer = transformerFactory.newTransformer();
	}
	
	/**
	 * 
	 * @param document The new document to generate on.
	 */
	public void document(Document document) {
		this.document = document;
	}
	
	/**
	 * 
	 * @return The document associated with this generator.
	 */
	public Document document() {
		return document;
	}
	
	/**
	 * 
	 * @param value The element to add to the root of this document.
	 */
	public void addElementToRoot(Element value) {
		document.appendChild(value);
	}
	
	/**
	 * 
	 * @param value The element to remove from the root of this document.
	 */
	public void removeElementFromRoot(Element value) {
		document.removeChild(value);
	}
	
	/**
	 * 
	 * @param parent The parent element.
	 * @param child The element you would like to add to this parent.
	 */
	public void addChildToElement(Element parent, Element child) {
		parent.appendChild(child);
	}
	
	/**
	 * 
	 * @param parent The parent element.
	 * @param child The elment you would like to remove from this parent.
	 */
	public void removeChildFromElement(Element parent, Element child) {
		parent.removeChild(child);
	}
	
	/**
	 * 
	 * @param parent The element you would like to add an attribute to.
	 * @param name The name of the attribute
	 * @param value The value of the attribute.
	 */
	public void addAttributeToElement(Element parent, String name, String value) {
		parent.setAttribute(name, value);
	}
	
	/**
	 * 
	 * @param parent The element you would like to remove an attribute from.
	 * @param name The name of the attribute you would like to remove.
	 */
	public void removeAttributeFromElement(Element parent, String name) {
		parent.removeAttribute(name);
	}
	
	/**
	 * 
	 * @param parent The element you would like to add this node data to.
	 * @param data The data to insert into the node.
	 * @return The node that has been created.
	 */
	public Node addNodeToElement(Element parent, String data) {
		Node retVal = document.createTextNode(data);
		parent.appendChild(retVal);
		return retVal;
	}
	
	/**
	 * 
	 * @param parent The element you would like to remove a node from.
	 * @param value The node to remove.
	 */
	public void removeNodeFromElement(Element parent, Node value) {
		parent.removeChild(value);
	}
	
	/**
	 * 
	 * @param id The String identifier for this element.
	 * @return The element that has been created.
	 */
	public Element createElement(String id) {
		return document.createElement(id);
	}
	
	/**
	 * 
	 * @param fileLocation The file location to save this xml, should include file name.
	 * @throws TransformerException When the generated xml cannot be transformed.
	 */
	public void saveToFile(String fileLocation) throws TransformerException {
		makeDirectoryIfNeeded(fileLocation);
		
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(fileLocation));

		transformer.transform(source, result);
	}
	
	private void makeDirectoryIfNeeded(String location) {
		File saveFile = new File(location);
		
		File p = new File(saveFile.getParent());
		
		if (!Files.exists(p.toPath()))
		{
			p.mkdirs();
		}
	}
}
