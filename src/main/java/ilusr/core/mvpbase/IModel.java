package ilusr.core.mvpbase;

/**
 * 
 * @author Jeff Riggle
 *
 */
public interface IModel {
	/**
	 * 
	 * @param listener The listener to add.
	 */
	void addListener(IModelListener listener);
	/**
	 * 
	 * @param listener The listener to remove.
	 */
	void removeListener(IModelListener listener);
}
