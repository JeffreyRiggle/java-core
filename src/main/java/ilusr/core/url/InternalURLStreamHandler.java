package ilusr.core.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

import ilusr.core.data.Tuple;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class InternalURLStreamHandler extends URLStreamHandler{

	private Map<String, Tuple<String, InternalURLConnector>> connectorMap;
	private final Object connectorLock = new Object();
	
	/**
	 * Ctor..
	 */
	public InternalURLStreamHandler() {
		connectorMap = new HashMap<String, Tuple<String, InternalURLConnector>>();
	}
	
	/**
	 * 
	 * @param path The location of the stream.
	 * @param content The content from the stream.
	 */
	public void addStream(String path, String content) {
		synchronized(connectorLock) {
			connectorMap.put(path, new Tuple<String, InternalURLConnector>(content, null));
		}
	}
	
	/**
	 * 
	 * @param path The location of the stream.
	 */
	public void removeStream(String path) {
		synchronized(connectorLock) {
			connectorMap.remove(path);
		}
	}
	
	@Override 
    public URLConnection openConnection(URL url) throws IOException {
		
		String path = url.toString().replaceAll(".*:", "");
		
		if (!connectorMap.containsKey(path)) {
			throw new IOException();
		}
		
        InternalURLConnector connector = connectorMap.get(path).value();
        if (connector == null) {
        	connector = new InternalURLConnector(url, connectorMap.get(path).key());
        	connectorMap.get(path).value(connector);
        }
        
        return connector;
    }
}
