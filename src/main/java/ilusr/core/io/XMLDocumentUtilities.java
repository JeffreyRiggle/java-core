package ilusr.core.io;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class XMLDocumentUtilities {

	/**
	 * 
	 * @param doc The @see Document to parse.
	 * @return The string representation of the document.
	 */
	public static String convertDocumentToString(Document doc) {
		String retVal = new String();
		
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			retVal = writer.getBuffer().toString();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal;
	}
}
