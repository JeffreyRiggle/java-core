package ilusr.core.datamanager.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class XmlManager {

	private XmlGenerator generator;
	private IReader reader;
	private String saveLocation;
	
	//TODO: Consider, since we can set and get save location maybe we shouldn't
	//force the user to construct with a file location.
	/**
	 * 
	 * @param saveLocation The location of the xml document.
	 * @throws TransformerConfigurationException When the generated xml cannot be transformed.
	 * @throws ParserConfigurationException When the generated xml cannot be transformed.
	 * @throws SAXException When the xml document cannot be parsed.
	 * @throws IOException When file does not exist or when access is restricted to the file.
	 */
	public XmlManager(String saveLocation) throws TransformerConfigurationException, ParserConfigurationException, SAXException, IOException {
		this(saveLocation, new XmlGenerator(), new XmlReader(saveLocation));
	}
	
	/**
	 * 
	 * @param saveLocation The location of the xml document.
	 * @param generator A xml generator for creating xml files @see XmlGenerator.
	 * @param reader A xml reader for reading in xml files @see XmlReader.
	 */
	public XmlManager(String saveLocation, XmlGenerator generator, IReader reader) {
		this.saveLocation = saveLocation;
		this.generator = generator;
		this.reader = reader;
	}
	
	/**
	 * 
	 * @return The current xml file location.
	 */
	public String saveLocation() {
		return saveLocation;
	}
	
	/**
	 * 
	 * @param value The new location for the xml file location.
	 */
	public void saveLocation(String value) {
		saveLocation = value;
	}
	
	/**
	 * 
	 * @param location The location the file is to be created in.
	 * @throws TransformerConfigurationException When the generated xml cannot be transformed.
	 * @throws ParserConfigurationException When the generated xml cannot be transformed.
	 */
	public void createNewFile(String location) 
		throws TransformerConfigurationException, ParserConfigurationException {
		saveLocation = location;
		generator = new XmlGenerator();
	}
	
	/**
	 * 
	 * @return The current @see XmlGenerator.
	 */
	public XmlGenerator generator() {
		return generator;
	}
	
	/**
	 * 
	 * @return The document associated with this manager.
	 */
	public Document document() {
		//TODO: Consider creating a private variable for this.
		return generator.document();
	}
	
	/**
	 *  Loads the xml file into memory.
	 * @throws SAXException When the xml document cannot be parsed.
	 * @throws IOException When file does not exist or when access is restricted to the file.
	 */
	public void load() throws SAXException, IOException {
		if (!reader.fileLocation().equals(saveLocation)) {
			reader.fileLocation(saveLocation);
		}
		
		reader.read();
		generator.document(reader.document());
	}
	
	/**
	 *  Saves the xml file to disk.
	 * @throws TransformerException When the generated xml cannot be transformed.
	 */
	public void save() throws TransformerException {
		generator.saveToFile(saveLocation);
	}
}
