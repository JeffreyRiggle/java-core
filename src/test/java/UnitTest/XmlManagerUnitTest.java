package UnitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import ilusr.core.datamanager.xml.IReader;
import ilusr.core.datamanager.xml.XmlGenerator;
import ilusr.core.datamanager.xml.XmlManager;
import ilusr.core.datamanager.xml.XmlReader;

public class XmlManagerUnitTest {

	private final String _file1 = System.getProperty("user.home") + "/nilrem/unittests/UnitTests/managerunittest1.xml";
	private final String _file2 = System.getProperty("user.home") + "/nilrem/unittests/UnitTests/managerunittest2.xml";
	private final String _file3 = System.getProperty("user.home") + "/nilrem/unittests/UnitTests/managerunittest3.xml";
	
	private final String _element1Name = "TestingElement11";
	private final String _element2Name = "CoolElement3";
	
	private XmlGenerator _generator;
	private IReader _reader;
	
	@Test
	public void testCreateXmlManagerMinimal() {
		try {
			XmlManager manager = new XmlManager(_file1);
			
			assertEquals(_file1, manager.saveLocation());
			assertNotNull(manager.generator());
			assertNotNull(manager.document());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testCreateXmlManagerFull() {
		try {
			instantiate();
			XmlManager manager = new XmlManager(_file1, _generator, _reader);
			
			assertEquals(_file1, manager.saveLocation());
			assertEquals(_generator, manager.generator());
			assertNotNull(manager.document());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testSaveLocation() {
		try {
			instantiate();
			XmlManager manager = new XmlManager(_file1, _generator, _reader);
			
			assertEquals(_file1, manager.saveLocation());
			
			manager.saveLocation(_file2);
			assertEquals(_file2, manager.saveLocation());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testSaveXml() {
		try {
			instantiate();
			XmlManager manager = new XmlManager(_file1, _generator, _reader);
			
			Element child = manager.generator().createElement(_element1Name);
			manager.generator().addElementToRoot(child);
			manager.save();
			
			File saveFile1 = new File(_file1);
			
			if (!saveFile1.exists()) {
				Assert.fail("File not found");
			}
			
			//TODO: make sure its contents are right.
			manager.createNewFile(_file2);
			Element child2 = manager.generator().createElement(_element2Name);
			manager.generator().addElementToRoot(child2);
			manager.save();
			
			File saveFile2 = new File(_file2);
			
			if (!saveFile2.exists()) {
				Assert.fail("File not found");
			}
			//TODO: make sure its contents are right.
			
			manager.saveLocation(_file3);
			manager.save();
			
			File saveFile3 = new File(_file3);
			
			if (!saveFile3.exists()) {
				Assert.fail("File not found");
			}
			//TODO: make sure its contents are right.
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testLoadXml() {
		try {
			instantiate();
			XmlManager manager = new XmlManager(_file1, _generator, _reader);
			
			manager.load();
			assertEquals(_element1Name, manager.document().getFirstChild().getNodeName());
			
			manager.saveLocation(_file2);
			manager.load();
			assertEquals(_element2Name, manager.document().getFirstChild().getNodeName());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	private void instantiate() throws TransformerConfigurationException, 
		ParserConfigurationException, SAXException, IOException {
		_generator = new XmlGenerator();
		_reader = new XmlReader(_file1);
	}
}
