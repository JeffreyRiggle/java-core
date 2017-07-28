package ilusr.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class FileUtilities {

	/**
	 * 
	 * @param clz A @see Class that is in the same package as the resource file.
	 * @param fileName The name of the resource file to create.
	 * @return A @see java.io.File
	 * @throws URISyntaxException
	 */
	public static <T> File getResourceFile(Class<T> clz, String fileName) throws URISyntaxException {
		String res = clz.getResource(fileName).toURI().getSchemeSpecificPart();
		return new File(res);
	}
	
	public static String getFileContentWithReturns(File file) throws IOException {
		return getFileContentImpl(file, true);
	}
	
	/**
	 * 
	 * @param file The file to read from.
	 * @return The content of the file in a String format.
	 * @throws IOException
	 */
	public static String getFileContent(File file) throws IOException {
		return getFileContentImpl(file, false);
	}
	
	private static String getFileContentImpl(File file, boolean returns) throws IOException {
		String output = new String();
		FileInputStream inputStream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
		String line = new String();
		
		while((line = reader.readLine()) != null) {
			output += line;
			
			if (returns) {
				output += "\r\n";
			}
		}
		
		reader.close();
		return output;
	}
	
	/**
	 * 
	 * @param dir The directory to delete.
	 */
	public static void deleteDir(File dir) {
		deleteImpl(dir);
	}
	
	/**
	 * 
	 * @param fileLocation The location to save the file to.
	 * @param content The content to save int the file.
	 * @throws IOException
	 */
	public static void saveToFile(String fileLocation, String content) throws IOException {
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
	
	private static void deleteImpl(File file) {
		if (!file.isDirectory()) {
			file.delete();
			return;
		}
		
		for (File f : file.listFiles()) {
			deleteImpl(f);
		}
		
		file.delete();
	}
}
