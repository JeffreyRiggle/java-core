package ilusr.core.mvpbase;

/**
 * 
 * @author Jeff Riggle
 *
 */
public interface IView {
	/**
	 * 
	 * @param listener The listener to add.
	 */
	void addListener(IViewListener listener);
	/**
	 * 
	 * @param listener The listener to remove.
	 */
	void removeListener(IViewListener listener);
}
