package ilusr.core.datamanager.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class XmlReader implements IReader{

	private Document document;
	private String file;
	private DocumentBuilder builder;
	
	/**
	 * 
	 * @param xmlFile The location of the xml file to read.
	 * @throws SAXException When the generated xml cannot be transformed.
	 * @throws IOException When file does not exist or when access is restricted to the file.
	 * @throws ParserConfigurationException When the xml cannot be parsed.
	 */
	public XmlReader (String xmlFile) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		file = xmlFile;
		builder = dbFactory.newDocumentBuilder();
	}
	
	@Override
	public String fileLocation() {
		return file;
	}
	
	@Override
	public void fileLocation(String value) {
		file = value;
	}
	
	@Override
	public void read() throws SAXException, IOException {
		document = builder.parse(file);
		document.getDocumentElement().normalize();
	}
	
	@Override
	public Document document() {
		return document;
	}
}
