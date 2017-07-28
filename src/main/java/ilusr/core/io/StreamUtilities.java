package ilusr.core.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class StreamUtilities {

	/**
	 * 
	 * @param stream A @see InputStream to read.
	 * @return The contents including new lines as a string.
	 * @throws IOException
	 */
	public static String getStreamContents(InputStream stream) throws IOException {
		String output = new String();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line = new String();
		
		while((line = reader.readLine()) != null) {
			output += line;
			
			output += "\r\n";
		}
		
		reader.close();
		return output;
	}
}
