package UnitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ilusr.core.ioc.ServiceManager;

public class ServiceManagerUnitTest {

	@Test
	public void testGetInstance() {
		ServiceManager manager = ServiceManager.getInstance();
		assertNotNull(manager);
	}

	@Test
	public void testRegister() {
		try {
			ServiceManager manager = ServiceManager.getInstance();
			Resource resource = new ClassPathResource("registration1.xml", ServiceManagerUnitTest.class);
			manager.registerServicesFromResource(resource);
			TestClassA testA = manager.<TestClassA>get("TestClassA");
			assertNotNull(testA);
			assertEquals(0, testA.getVal());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testMultiResolveOnSingleton() {
		try {
			ServiceManager manager = ServiceManager.getInstance();
			manager.registerServices(getSpringDef1());
			
			TestClassA testA = manager.<TestClassA>get("TestClassA");
			testA.increment();
			assertEquals(1, testA.getVal());
			
			TestClassA testA2 = manager.<TestClassA>get("TestClassA");
			assertEquals(1, testA2.getVal());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testMultiResolveNonSingleton() {
		try {
			ServiceManager manager = ServiceManager.getInstance();
			manager.registerServices(getSpringDef1());
			
			TestClassB testB = manager.<TestClassB>get("TestClassB");
			testB.append("test");
			assertEquals("TestValuetest", testB.initVal());
			
			TestClassB testB2 = manager.<TestClassB>get("TestClassB");
			assertEquals("TestValuetest", testB2.initVal());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testMultiRegister() {
		try {
			ServiceManager manager = ServiceManager.getInstance();
			manager.registerServices(getSpringDef1());
			manager.registerServices(getSpringDef2());
			
			TestClassA testA = manager.<TestClassA>get("TestClassA");
			assertNotNull(testA);
			
			TestClassB testB = manager.<TestClassB>get("TestClassB");
			assertNotNull(testB);
			
			TestClassC testC = manager.<TestClassC>get("TestClassC");
			assertNotNull(testC);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testMultiSameFileRegister() {
		try {
			ServiceManager manager = ServiceManager.getInstance();
			manager.registerServices(getSpringDef1());
			manager.registerServices(getSpringDef2());
			
			TestClassA testA = manager.<TestClassA>get("TestClassA");
			assertNotNull(testA);
			
			TestClassB testB = manager.<TestClassB>get("TestClassB");
			assertNotNull(testB);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBeanRegistration() {
		try {
			ConstructorArgumentValues cav = new ConstructorArgumentValues();
	        cav.addGenericArgumentValue("TestValue");
	        RootBeanDefinition testClassB = new RootBeanDefinition(TestClassB.class, cav, null);
	        
			ServiceManager manager = ServiceManager.getInstance();
			manager.registerBean("TestClassB", testClassB);
			
			TestClassB testB = manager.<TestClassB>get("TestClassB");
			assertNotNull(testB);
			assertEquals("TestValue", testB.initVal());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	private String getSpringDef1() throws URISyntaxException {
		String p = ServiceManagerUnitTest.class.getResource("registration1.xml").toURI().getSchemeSpecificPart();
		File f = new File(p);
		return f.toPath().toString();
	}
	
	private String getSpringDef2() throws URISyntaxException {
		String p = ServiceManagerUnitTest.class.getResource("registration2.xml").toURI().getSchemeSpecificPart();
		File f = new File(p);
		return f.toPath().toString();
	}
}
