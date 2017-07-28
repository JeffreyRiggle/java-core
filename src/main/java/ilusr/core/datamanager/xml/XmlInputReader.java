package ilusr.core.datamanager.xml;
import java.io.IOException;
import java.io.InputStream;

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
public class XmlInputReader implements IReader{

	private Document document;
	private InputStream stream;
	private DocumentBuilder builder;
	
	/**
	 * 
	 * @param stream The location of the xml file to read.
	 * @throws SAXException When the xml document cannot be parsed.
	 * @throws IOException When file does not exist or when access is restricted to the file.
	 * @throws ParserConfigurationException When the generated xml cannot be transformed.
	 */
	public XmlInputReader (InputStream stream) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		this.stream = stream;
		this.builder = dbFactory.newDocumentBuilder();
	}
	
	@Override
	public String fileLocation() {
		return new String();
	}

	@Override
	public void fileLocation(String value) {
		// No op
	}

	@Override
	public void read() throws SAXException, IOException {
		document = builder.parse(stream);
		document.getDocumentElement().normalize();
	}

	@Override
	public Document document() {
		return document;
	}

}
