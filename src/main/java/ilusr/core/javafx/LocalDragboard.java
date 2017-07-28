package ilusr.core.javafx;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class LocalDragboard {

	private final Map<Class<?>, Object> contents;
	
	private static LocalDragboard instance;
	private static final Object instanceLock = new Object();
	
	private LocalDragboard() {
		this.contents = new HashMap<Class<?>, Object>();
	}
	
	/**
	 * 
	 * @return A @see LocalDragboard.
	 */
	public static LocalDragboard getInstance() {
		if (instance != null) return instance;
		
		synchronized (instanceLock) {
			instance = new LocalDragboard();
		}
		
		return instance;
	}
	
	/**
	 * 
	 * @param type The class to put on the drag board.
	 * @param value The value to associate with that class on the drag board.
	 */
	public <T> void putValue(Class<T> type, T value) {
		contents.put(type, type.cast(value));
	}
	
	/**
	 * 
	 * @param type The class to pull from the drag board.
	 * @return The value for the class on the drag board.
	 */
	public <T> T getValue(Class<T> type) {
		return type.cast(contents.get(type));
	}
	
	/**
	 * 
	 * @param type The class to evaluate on the drag board.
	 * @return If the class exists on the drag board.
	 */
	public boolean hasType(Class<?> type) {
		return contents.keySet().contains(type);
	}
	
	/**
	 * 
	 * @param type The class to remove from the drag board.
	 */
	public void clear(Class<?> type) {
		contents.remove(type);
	}
	
	/**
	 * Clears all objects from the drag board.
	 */
	public void clearAll() {
		contents.clear();
	}
}
