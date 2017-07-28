package ilusr.core.url;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class InternalURLProvider {
	
	private static final Object instanceLock = new Object();
	private static InternalURLProvider instance;
	
	private final String INTERNAL_URL = "internalurl";
	private final InternalURLStreamHandlerFactory factory;
	private final Object factoryLock;
	
	/**
	 * 
	 * @param factory A @see InternalURLStreaHandlerFactory to use.
	 */
	private InternalURLProvider(InternalURLStreamHandlerFactory factory) {
		this.factory = factory;
		factoryLock = new Object();
	}
	
	public static InternalURLProvider getInstance() {
		synchronized(instanceLock) {
			if (instance == null) {
				instance = new InternalURLProvider(new InternalURLStreamHandlerFactory());
			}
			
			return instance;
		}
	}
	
	/**
	 * Prepares a URL to be used internally.
	 * 
	 * @param content The data associated with the URL.
	 * @param resource The location of the data (URL).
	 * @return The formatted URL including protocol.
	 */
	public String prepareURL(String content, String resource) {
		synchronized(factoryLock) {
			String res = String.format("%s:%s", INTERNAL_URL, resource);
			factory.addResouce(INTERNAL_URL, resource, content);
			
			return res;
		}
	}
	
	/**
	 * Removes an internal URL.
	 * @param resource The URL to remove.
	 */
	public void removeURL(String resource) {
		synchronized(factoryLock) {
			String[] id = resource.split(":");
			
			if (id.length <= 0) {
				return;
			}
			
			factory.removeResource(id[0], id[1]);
		}
	}
}
