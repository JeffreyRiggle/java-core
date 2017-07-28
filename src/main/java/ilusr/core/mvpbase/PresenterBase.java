package ilusr.core.mvpbase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class PresenterBase implements IViewListener, IModelListener {

	private IView view;
	private IModel model;
	
	/**
	 * 
	 * @param view The view (UI component ex. JPanel).
	 * @param model The model (The backend logic and data for the view).
	 */
	public PresenterBase(IView view, IModel model) {
		this.view = view;
		this.model = model;
		
		this.view.addListener(this);
		this.model.addListener(this);
	}
	
	// Workaround for methods with primitive signatures
	@SuppressWarnings("rawtypes")
	public <D> void notifyView(String method, D data, Class type) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method func = view.getClass().getMethod(method, type);
		func.setAccessible(true);
		func.invoke(view, data);
	}
	
	public <D> void notifyView(String method, D data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method func = view.getClass().getMethod(method, data.getClass());
		func.setAccessible(true);
		func.invoke(view, data);
	}
	
	// Workaround for methods with primitive signatures
	@SuppressWarnings("rawtypes")
	public <D> void notifyModel(String method, D data, Class type) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method func = model.getClass().getMethod(method, type);
		func.setAccessible(true);
		func.invoke(model, data);
	}
	
	public <D> void notifyModel(String method, D data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//TODO: Add better support for 0 param methods
		if (data == null) {
			Method func = model.getClass().getMethod(method);
			func.setAccessible(true);
			func.invoke(model);
			return;
		}
		
		Method func = model.getClass().getMethod(method, data.getClass());
		func.setAccessible(true);
		func.invoke(model, data);
	}
}
