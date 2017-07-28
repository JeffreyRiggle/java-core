package ilusr.core.datamanager.unformatted;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

//TODO: Consider thread safety.
//TODO: Consider adding async save.
/**
* 
* @author Jeff Riggle
*
*/
public class UnformattedFileGenerator {

	private String content;
	
	/**
	 * Creates a new file generator class with no content.
	 */
	public UnformattedFileGenerator() {
		this(new String());
	}
	
	/**
	 * 
	 * @param content The content to be saved.
	 */
	public UnformattedFileGenerator(String content) {
		this.content = content;
	}
	
	/**
	 * 
	 * @param value The new value for the content.
	 */
	public void content(String value) {
		content = value;
	}
	
	/**
	 * 
	 * @return The content associated with this class.
	 */
	public String content() {
		return content;
	}
	
	/**
	 * 
	 * @param fileLocation The file path that this file should be saved at, must contain file name.
	 * @throws IOException
	 */
	public void saveToFile(String fileLocation) throws IOException {
		File saveFile = new File(fileLocation);
		
		File p = new File(saveFile.getParent());
		
		if (!Files.exists(p.toPath()))
		{
			p.mkdirs();
		}
		
		FileOutputStream outputStream = new FileOutputStream(saveFile);
		byte[] data = content.getBytes();
		outputStream.write(data);
		outputStream.close();
	}
}
