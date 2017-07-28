package ilusr.core.mvpbase;

@SuppressWarnings("rawtypes")
/**
 * 
 * @author Jeff Riggle
 *
 */
public class MethodSignature {

	private String methodName;
	private Class methodInput;
	
	/**
	 * 
	 * @param name The name of the method.
	 * @param input The data associated with the first parameter.
	 */
	public MethodSignature(String name, Class input) {
		methodName = name;
		methodInput = input;
	}
	
	/**
	 * 
	 * @return The name of the method.
	 */
	public String methodName() {
		return methodName;
	}
	
	/**
	 * 
	 * @param name The new name for the method.
	 */
	public void methodName(String name) {
		methodName = name;
	}
	
	/**
	 * 
	 * @return The type of the data.
	 */
	public Class methodInput() {
		return methodInput;
	}
	
	/**
	 * 
	 * @param input The new type for the data.
	 */
	public void methodInput(Class input) {
		methodInput = input;
	}
}
