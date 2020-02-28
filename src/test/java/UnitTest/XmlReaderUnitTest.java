package UnitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ilusr.core.datamanager.xml.XmlReader;

public class XmlReaderUnitTest {

	private final String _fileLocation1 = System.getProperty("user.dir") + "/unittests/UnitTests/readerTest1.xml";
	private final String _fileLocation2 = System.getProperty("user.dir") + "/unittests/UnitTests/readerTest2.xml";
	
	private final String _file1Contents = "ReaderTest";
	private final String _file2Contents = "ReaderTest23";
	
	private final String _file1FullContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ReaderTest/>";
	private final String _file2FullContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ReaderTest23/>";
	
	public void createTestFiles(String seed) {
		try {
			File file1 = new File(_fileLocation1 + seed);
			if (!file1.exists())
			{
				file1.createNewFile();
				Files.write(file1.toPath(), Arrays.asList(_file1FullContents), Charset.forName("UTF-8"));
			}
			
			File file2 = new File(_fileLocation2 + seed);
			if (!file2.exists())
			{
				file2.createNewFile();
				Files.write(file2.toPath(), Arrays.asList(_file2FullContents), Charset.forName("UTF-8"));
			}
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testCreateXmlReader() {
		try {
			createTestFiles("createReader");
			XmlReader reader = new XmlReader(_fileLocation1 + "createReader");

			assertEquals(_fileLocation1 + "createReader", reader.fileLocation());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testFileLocation() {
		try {
			createTestFiles("fileLocation");
			XmlReader reader = new XmlReader(_fileLocation1 + "fileLocation");

			assertEquals(_fileLocation1 + "fileLocation", reader.fileLocation());
			
			reader.fileLocation(_fileLocation2 + "fileLocation");
			assertEquals(_fileLocation2 + "fileLocation", reader.fileLocation());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testReadFile() {
		try {
			createTestFiles("readFile");
			XmlReader reader = new XmlReader(_fileLocation1 + "readFile");
			reader.read();
			assertEquals(_file1Contents, reader.document().getFirstChild().getNodeName());
			
			reader.fileLocation(_fileLocation2 + "readFile");
			reader.read();
			assertEquals(_file2Contents, reader.document().getFirstChild().getNodeName());
			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
