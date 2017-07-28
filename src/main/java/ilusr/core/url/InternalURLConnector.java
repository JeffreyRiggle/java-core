package ilusr.core.url;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class InternalURLConnector extends URLConnection {

	private String data;
	
	/**
	 * 
	 * @param url The @see URL to use.
	 * @param data The data that should be associated with this URL.
	 */
	public InternalURLConnector(URL url, String data) {
		super(url);
		this.data = data;
	}
	
	/**
	 * Updates the data associated with the internal URL.
	 * 
	 * @param data The new Data for this URL.
	 */
	public void updateData(String data) {
		this.data = data;
	}
	
	@Override
	public void connect() throws IOException {}

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data.getBytes());
    }
}