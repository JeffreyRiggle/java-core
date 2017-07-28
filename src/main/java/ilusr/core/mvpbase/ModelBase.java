package ilusr.core.mvpbase;

import java.util.ArrayList;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class ModelBase extends ArrayList<IModelListener> implements IModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param method A @see MethodSignature object used to update the view.
	 * @param data The data to send to the view with this method signature.
	 */
	public <D> void raiseModelChanged(MethodSignature method, D data) {
		for (IModelListener listener : this) {
			try {
				listener.notifyView(method.methodName(), data, method.methodInput());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param member The name of the method to call on the view.
	 * @param data The data to send to the view.
	 */
	public <D> void raiseModelChanged(String member, D data) {
		for (IModelListener listener : this) {
			try {
				listener.notifyView(member, data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addListener(IModelListener listener) {
		super.add(listener);
	}

	@Override
	public void removeListener(IModelListener listener) {
		super.remove(listener);
	}
}
