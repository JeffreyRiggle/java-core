package ilusr.core.interfaces;

/**
 * 
 * @author Jeff Riggle
 *
 * @param <T> The type of object to pass in the callback function.
 */
public interface Callback<T> {
	/**
	 * 
	 * @param value The value to callback with.
	 */
	void execute(T value);
}
