package ilusr.core.environment;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class EnvironmentUtilities {

	private static String OS_NAME = System.getProperty("os.name").toLowerCase();
	
	/**
	 * 
	 * @return A boolean representing if this is a windows environment.
	 */
	public static boolean isWindows() {
		return OS_NAME.contains("win");
	}
	
	/**
	 * 
	 * @return A boolean representing if this is a mac environment.
	 */
	public static boolean isMac() {
		return OS_NAME.contains("mac");
	}
	
	/**
	 * 
	 * @return A boolean representing if this is a unix based environment.
	 */
	public static boolean isUnix() {
		return OS_NAME.contains("nix") || OS_NAME.contains("nux") || OS_NAME.contains("aix");
	}
}
