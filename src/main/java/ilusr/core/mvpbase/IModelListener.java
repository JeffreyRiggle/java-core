package ilusr.core.mvpbase;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author Jeff Riggle
 *
 */
public interface IModelListener {
	/**
	 * 
	 * @param method The name of the method to notify on the view.
	 * @param data The data to send to the view's method.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	<D> void notifyView(String method, D data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	@SuppressWarnings("rawtypes")
	/**
	 * 
	 * @param method The name of the method to notify on the view.
	 * @param data The data to send to the view's method.
	 * @param type The type of the data (in the case of primitives we must use this) <int> would end up getting
	 * 			cast to Integer instead of remaining int.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	<D> void notifyView(String method, D data, Class type) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
