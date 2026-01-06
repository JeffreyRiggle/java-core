import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ilusr.core.datamanager.xml.XmlGenerator;


public class XmlGeneratorUnitTest {

	private Document _document;
	private final String _rootElementName = "SuperMario";
	private final String _rootElementAttributeName1 = "Version";
	private final String _rootElementAttributeValue1 = "Mario 3";
	private final String _rootElementAttributeName2 = "Year";
	private final String _rootElementAttributeValue2 = "1988";
	
	private final String _characterElementName = "Characters";
	private final String _marioElementName = "Mario";
	private final String _luigiElementName = "Luigi";
	private final String _sizeElementName = "Size";
	private final String _marioSizeElementValue = "150";
	private final String _luigiSizeElementValue = "200";
	private final String _clothingElementName = "Clothing";
	private final String _hatElementName = "Hat";
	private final String _shirtElementName = "Shirt";
	private final String _childElementAttributeName = "value";
	private final String _childElementAttributeValue1 = "red";
	private final String _childElementAttributeValue2 = "green";
	
	private final String _saveLocation = System.getProperty("user.home") + "/nilrem/unittests/UnitTests/writerunittest.xml";
	private final String _saveFullLocation = System.getProperty("user.home") + "/nilrem/unittests/UnitTests/writerunittestfull.xml";
	
	private final String _minimalContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SuperMario/>";
	private final String _fullContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SuperMario Version=\"Mario 3\" Year=\"1988\"><Characters><Mario><Size>150</Size><Clothing><Hat/><Shirt value=\"red\"/></Clothing></Mario><Luigi><Size>200</Size><Clothing><Hat/><Shirt value=\"green\"/></Clothing></Luigi></Characters></SuperMario>";
	
	@Test
	public void testCreateMinimalGenerator() {
		try {
			@SuppressWarnings("unused")
			XmlGenerator gen = new XmlGenerator();
		} catch (TransformerConfigurationException e) {
			Assert.fail(e.toString());
		} catch (ParserConfigurationException e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void testCreateGenerator() {
		try {
			createDocument();
		} catch (ParserConfigurationException e) {
			Assert.fail(e.toString());
		}
		try {
			XmlGenerator gen = new XmlGenerator(_document);
			assertEquals(_document, gen.document());
		} catch (TransformerConfigurationException e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testCreateElement() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element element = gen.createElement(_rootElementName);
			assertEquals(_rootElementName, element.getTagName());
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testAddElementToRoot() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element rootElement = gen.createElement(_rootElementName);
			gen.addElementToRoot(rootElement);
			
			assertEquals(rootElement, _document.getElementsByTagName(_rootElementName).item(0));
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testAddAttributeToElement() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator();
			Element root = gen.createElement(_rootElementName);
			
			gen.addElementToRoot(root);
			gen.addAttributeToElement(root, _rootElementAttributeName1, _rootElementAttributeValue1);
			
			assertEquals(1, gen.document().getElementsByTagName(_rootElementName).item(0).getAttributes().getLength());
			
			gen.addAttributeToElement(root, _rootElementAttributeName2, _rootElementAttributeValue2);
			
			assertEquals(2, gen.document().getElementsByTagName(_rootElementName).item(0).getAttributes().getLength());
		} catch (Exception e){
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testRemoveAttributeFromElement() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator();
			Element root = gen.createElement(_rootElementName);
			
			gen.addElementToRoot(root);
			
			gen.addAttributeToElement(root, _rootElementAttributeName1, _rootElementAttributeValue1);
			gen.addAttributeToElement(root, _rootElementAttributeName2, _rootElementAttributeValue2);
			
			assertEquals(2, gen.document().getElementsByTagName(_rootElementName).item(0).getAttributes().getLength());
			
			gen.removeAttributeFromElement(root, _rootElementAttributeName1);
			assertEquals(1, gen.document().getElementsByTagName(_rootElementName).item(0).getAttributes().getLength());
			
			gen.removeAttributeFromElement(root, _rootElementAttributeName2);
			assertEquals(0, gen.document().getElementsByTagName(_rootElementName).item(0).getAttributes().getLength());
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testAddChildToElement() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element root = gen.createElement(_rootElementName);
			gen.addElementToRoot(root);
			
			Element characters = gen.createElement(_characterElementName);
			gen.addChildToElement(root, characters);
			
			assertEquals(characters, (Element)_document.getElementsByTagName(_characterElementName).item(0));
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testRemoveChildFromElement() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element root = gen.createElement(_rootElementName);
			gen.addElementToRoot(root);
			
			Element characters = gen.createElement(_characterElementName);
			gen.addChildToElement(root, characters);
			
			gen.removeChildFromElement(root, characters);
			
			assertEquals(0, _document.getElementsByTagName(_rootElementName).item(0).getChildNodes().getLength());
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void testAddNodeToChild() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element root = gen.createElement(_rootElementName);
			gen.addElementToRoot(root);
			
			Element characters = gen.createElement(_characterElementName);
			gen.addChildToElement(root, characters);
			
			Element mario = gen.createElement(_marioElementName);
			gen.addChildToElement(characters, mario);
			Element luigi = gen.createElement(_luigiElementName);
			gen.addChildToElement(characters, luigi);
			
			Element marioSize = gen.createElement(_sizeElementName);
			gen.addChildToElement(mario, marioSize);
			Element luigiSize = gen.createElement(_sizeElementName);
			gen.addChildToElement(luigi, luigiSize);
			
			gen.addNodeToElement(marioSize, _marioSizeElementValue);
			
			gen.addNodeToElement(luigiSize, _luigiSizeElementValue);
			
			assertEquals(_marioSizeElementValue, gen.document().getElementsByTagName(_marioElementName).item(0).getFirstChild().getTextContent());
			assertEquals(_luigiSizeElementValue, gen.document().getElementsByTagName(_luigiElementName).item(0).getFirstChild().getTextContent());
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	//TODO: Finish this unit test.
	@Test
	public void testRemoveNodeFromChild() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element root = gen.createElement(_rootElementName);
			gen.addElementToRoot(root);
			
			Element characters = gen.createElement(_characterElementName);
			gen.addChildToElement(root, characters);
			
			Element mario = gen.createElement(_marioElementName);
			gen.addChildToElement(characters, mario);
			Element luigi = gen.createElement(_luigiElementName);
			gen.addChildToElement(characters, luigi);
			
			Element marioSize = gen.createElement(_sizeElementName);
			gen.addChildToElement(mario, marioSize);
			Element luigiSize = gen.createElement(_sizeElementName);
			gen.addChildToElement(luigi, luigiSize);
			
			Node marioNode = gen.addNodeToElement(marioSize, _marioSizeElementValue);
			Node luigiNode = gen.addNodeToElement(luigiSize, _luigiSizeElementValue);
			
			gen.removeNodeFromElement(marioSize, marioNode);
			assertEquals(null, gen.document().getElementsByTagName(_marioElementName).item(0).getNodeValue());
			gen.removeNodeFromElement(luigiSize, luigiNode);
			assertEquals(null, gen.document().getElementsByTagName(_luigiElementName).item(0).getNodeValue());
			
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testSaveXMLToFile() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element root = gen.createElement(_rootElementName);
			gen.addElementToRoot(root);
			
			gen.saveToFile(_saveLocation);
			
			File saveFile = new File(_saveLocation);
			
			if (!saveFile.exists()) {
				Assert.fail("File not found");
			}
			
			String fileContent = getFileContent(saveFile);
			assertEquals(_minimalContent, fileContent);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void testFull() {
		try {
			createDocument();
			XmlGenerator gen = new XmlGenerator(_document);
			Element root = gen.createElement(_rootElementName);
			gen.addElementToRoot(root);
			
			gen.addAttributeToElement(root, _rootElementAttributeName1, _rootElementAttributeValue1);
			gen.addAttributeToElement(root, _rootElementAttributeName2, _rootElementAttributeValue2);
			
			Element characters = gen.createElement(_characterElementName);
			gen.addChildToElement(root, characters);
			
			Element mario = gen.createElement(_marioElementName);
			gen.addChildToElement(characters, mario);
			Element luigi = gen.createElement(_luigiElementName);
			gen.addChildToElement(characters, luigi);
			
			Element marioSize = gen.createElement(_sizeElementName);
			gen.addChildToElement(mario, marioSize);
			Element luigiSize = gen.createElement(_sizeElementName);
			gen.addChildToElement(luigi, luigiSize);
			
			gen.addNodeToElement(marioSize, _marioSizeElementValue);
			gen.addNodeToElement(luigiSize, _luigiSizeElementValue);
			
			Element marioClothing = gen.createElement(_clothingElementName);
			gen.addChildToElement(mario, marioClothing);
			Element marioHat = gen.createElement(_hatElementName);
			gen.addChildToElement(marioClothing, marioHat);
			Element marioShirt = gen.createElement(_shirtElementName);
			gen.addChildToElement(marioClothing, marioShirt);
			gen.addAttributeToElement(marioShirt, _childElementAttributeName, _childElementAttributeValue1);
			
			Element luigiClothing = gen.createElement(_clothingElementName);
			gen.addChildToElement(luigi, luigiClothing);
			Element luigiHat = gen.createElement(_hatElementName);
			gen.addChildToElement(luigiClothing, luigiHat);
			Element luigiShirt = gen.createElement(_shirtElementName);
			gen.addChildToElement(luigiClothing, luigiShirt);
			gen.addAttributeToElement(luigiShirt, _childElementAttributeName, _childElementAttributeValue2);
			
			gen.saveToFile(_saveFullLocation);
			
			File saveFile = new File(_saveFullLocation);
			
			if (!saveFile.exists()) {
				Assert.fail("File not found");
			}
			
			String fileContent = getFileContent(saveFile);
			assertEquals(_fullContent, fileContent);
		} catch (Exception e) {
			Assert.fail(e.toString());
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
	
	private void createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		_document = docBuilder.newDocument();
	}
}