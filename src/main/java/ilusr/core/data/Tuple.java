package ilusr.core.data;

//TODO: Make this more like tuple.
/**
 * 
 * @author Jeff Riggle
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public class Tuple<K, V> {

	private K key;
	private V value;
	
	/**
	 * 
	 * @param key The key.
	 * @param value The value.
	 */
	public Tuple(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * 
	 * @return The key.
	 */
	public K key() {
		return key;
	}
	
	/**
	 * 
	 * @param key The new key.
	 */
	public void key(K key) {
		this.key = key;
	}
	
	/**
	 * 
	 * @return The value.
	 */
	public V value() {
		return value;
	}
	
	/**
	 * 
	 * @param value The new value.
	 */
	public void value(V value) {
		this.value = value;
	}
}
