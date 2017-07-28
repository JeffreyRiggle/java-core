package ilusr.core.datamanager.xml;

import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author Jeff Riggle
 *
 */
public interface IReader {
	/**
	 * 
	 * @return The current xml file location.
	 */
	String fileLocation();
	
	/**
	 * 
	 * @param value The new xml file location.
	 */
	void fileLocation(String value);
	
	/**
	 * Reads the xml file into memory.
	 * @throws SAXException When the xml document cannot be parsed.
	 * @throws IOException When file does not exist or when access is restricted to the file.
	 */
	void read() throws SAXException, IOException;
	
	/**
	 * 
	 * @return The xml file in memory.
	 */
	Document document();
}
