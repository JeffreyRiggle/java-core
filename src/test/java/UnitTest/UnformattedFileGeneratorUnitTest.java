package UnitTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import ilusr.core.datamanager.unformatted.UnformattedFileGenerator;

public class UnformattedFileGeneratorUnitTest {

	private final String _emptyString = new String();
	private final String _initialContent = "This is the initial file content";
	private final String _finalContent = "This is the final file content.";
	
	private final String _fileLocation = System.getProperty("user.home") + "/nilrem/unittests/dml/unformattedunittest.txt";
	
	@Test
	public void testCreateMinialUnformattedFileGenerator() {
		UnformattedFileGenerator gen = new UnformattedFileGenerator();
		
		assertEquals(_emptyString, gen.content());
	}
	
	@Test
	public void testCreateFullUnformattedFileGenerator() {
		UnformattedFileGenerator gen = new UnformattedFileGenerator(_initialContent);
		
		assertEquals(_initialContent, gen.content());
	}
	
	@Test
	public void testSetContent() {
		UnformattedFileGenerator gen = new UnformattedFileGenerator(_initialContent);
		
		gen.content(_finalContent);
		assertEquals(_finalContent, gen.content());
	}

	@Test
	public void testCreateFile() {
		try {
			UnformattedFileGenerator gen = new UnformattedFileGenerator(_finalContent);
			
			gen.saveToFile(_fileLocation);
			
			File saveFile = new File(_fileLocation);
			
			if (!saveFile.exists()) {
				fail("File not found");
			}
			
			String fileContent = getFileContent(saveFile);
			assertEquals(_finalContent, fileContent);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	private String getFileContent(File file) throws IOException {
		String output = new String();
		
		FileInputStream inputStream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String line = new String();
		
		while((line = reader.readLine()) != null) {
			output += line;
		}
		
		reader.close();
		return output;
	}
}