package ilusr.core.url;

import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class InternalURLStreamHandlerFactory implements URLStreamHandlerFactory {

	private Map<String, InternalURLStreamHandler> factoryMap;
	private final Object factoryLock;
	
	/**
	 * Base Ctor..
	 */
	public InternalURLStreamHandlerFactory() {
		factoryLock = new Object();
		factoryMap = new HashMap<String, InternalURLStreamHandler>();
		URL.setURLStreamHandlerFactory(this);
	}
    
	/**
	 * 
	 * @param protocol The name of the protocol
	 * @param path The location of the resource.
	 * @param content The content to associate with the resource.
	 */
	public void addResouce(String protocol, String path, String content) {
		synchronized(factoryLock) {
			if (!factoryMap.containsKey(protocol)) {
				factoryMap.put(protocol, createStreamHandler());
			}
			
			factoryMap.get(protocol).addStream(path, content);
		}
	}
	
	/**
	 * 
	 * @param protocol The name of the protocol
	 * @param path The location of the resource.
	 */
	public void removeResource(String protocol, String path) {
		synchronized(factoryLock) {
			if (!factoryMap.containsKey(protocol)) {
				return;
			}
			
			factoryMap.get(protocol).removeStream(path);
		}
	}
	
    @Override 
    public URLStreamHandler createURLStreamHandler(String protocol) {
        synchronized(factoryLock) {
        	if (factoryMap.containsKey(protocol)) {
        		return factoryMap.get(protocol);
        	}
        	
        	return null;
        }
    }
    
    private InternalURLStreamHandler createStreamHandler() {
    	return new InternalURLStreamHandler();
    }
}
