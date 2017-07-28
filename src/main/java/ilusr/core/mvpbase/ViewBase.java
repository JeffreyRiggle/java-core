package ilusr.core.mvpbase;

import java.util.ArrayList;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class ViewBase extends ArrayList<IViewListener> {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param method The @see MethodSignature to update the model with.
	 * @param data The data to send to the model.
	 */
	public <D> void raiseViewChanged(MethodSignature method, D data) {
		for (IViewListener listener : this) {
			try {
				listener.notifyModel(method.methodName(), data, method.methodInput());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param member The name of the method to update on the model.
	 * @param data The data to send to the model.
	 */
	public <D> void raiseViewChanged(String member, D data) {
		for (IViewListener listener : this) {
			try {
				listener.notifyModel(member, data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
