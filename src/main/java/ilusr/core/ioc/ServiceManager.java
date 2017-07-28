package ilusr.core.ioc;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

/**
 * Simple class that uses spring to do Inversion of Control.
 * 
 * @author Jeff Riggle
 */
public class ServiceManager {

	private GenericApplicationContext context;
	private XmlBeanDefinitionReader xmlReader;
	private boolean hasRefreshed;
	
	private static ServiceManager instance;
	private static final Object _instanceLock = new Object();
	
	private ServiceManager() {
		context = new GenericApplicationContext();
		xmlReader = new XmlBeanDefinitionReader(context);
		xmlReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_AUTO);
	}
	
	/**
	 * Gets or creates a ServiceManager instance.
	 * 
	 * @return A Shared @see ServiceManager instance.
	 */
	public static ServiceManager getInstance() {
		synchronized(_instanceLock) {
			if (instance == null) {
				instance = new ServiceManager();
			}
			return instance;
		}
	}
	
	/**
	 * Registers a spring based xml file.
	 * @param xmlfile The path where the spring based xml exists.
	 */
	public void registerServices(String xmlfile) {
		xmlReader.loadBeanDefinitions(new PathResource(xmlfile));
		tryRefresh();
	}
	
	public void registerServicesFromResource(Resource resource) {
		xmlReader.loadBeanDefinitions(resource);
		tryRefresh();
	}
	
	/**
	 * Registers a @see BeanDefinition with a key of name.
	 * 
	 * @param name The name to register the bean as.
	 * @param bean The @see BeanDefinition to register.
	 */
	public void registerBean(String name, BeanDefinition bean) {
		context.registerBeanDefinition(name, bean);
		tryRefresh();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param name The name of the object to resolve.
	 * @return The requested object cast as type T.
	 */
	public <T> T get(String name) {
		return (T)context.getBean(name);
	}
	
	private void tryRefresh() {
		if (!hasRefreshed) {
			context.refresh();
			hasRefreshed = true;
		}
	}
}
