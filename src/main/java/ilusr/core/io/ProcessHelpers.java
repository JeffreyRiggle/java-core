package ilusr.core.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class ProcessHelpers {

	/**
	 * 
	 * @param proc The @see Process to get an input stream from.
	 * @return The input stream associated with the process.
	 */
	public static String getProcessInputStream(Process proc) {
		String retVal = new String();
		try {
			retVal = "stdout:" + buildLines(proc.getInputStream());
		} catch (Exception e) {
			retVal = e.getMessage();
		}
		
		return retVal;
	}
	
	/**
	 * 
	 * @param proc The @see Process to get an error stream from.
	 * @return The error stream associated with the process.
	 */
	public static String getProcessErrorStream(Process proc) {
		String retVal = new String();
		try {
			retVal = "stderr:" + buildLines(proc.getErrorStream());
		} catch (Exception e) {
			retVal = e.getMessage();
		}
		return retVal;
	}
	
	private static String buildLines(InputStream stream) {
		StringBuilder builder = new StringBuilder();
		
		String curr = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			while((curr = reader.readLine()) != null) {
				builder.append(curr);
			}
			
			reader.close();
		} catch (Exception e) {
			builder.append(e.getMessage());
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @param proc The @see Process to handle streams for.
	 */
	public static void handleProcessStreams(Process proc) {
		new Thread(() -> {
			System.out.println(getProcessInputStream(proc));
		}).start();
		new Thread(() -> {
			System.out.println(getProcessErrorStream(proc));
		}).start();
	}
}
