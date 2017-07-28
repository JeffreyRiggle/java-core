package ilusr.core.javafx;

import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author Jeff Riggle
 *
 * @param <T> The type of the bound @see ObservableList
 * 
 * Provides a one way binding from a source @see ObservableList to a 
 * target @see ObservableList of the same type.
 */
public class ObservableListBinder<T> implements ListChangeListener<T> {

	private final Object listLock;
	private ObservableList<T> source;
	private ObservableList<T> target;
	
	/**
	 * 
	 * @param source The source @see ObservableList
	 * @param target The target @see ObservableList
	 */
	public ObservableListBinder(ObservableList<T> source, ObservableList<T> target) {
		listLock = new Object();
		this.source = source;
		this.target = target;
		
		bind();
	}
	
	/**
	 * Unbinds the lists so that the target list will no longer receive
	 * updates from the source list.
	 */
	public void unbind() {
		source.removeListener(this);
	}
	
	public void bindSourceToTarget() {
		for (T item : source) {
			target.add(0, item);
		}
	}
	
	private void bind() {
		source.addListener(this);
	}

	@Override
	public void onChanged(ListChangeListener.Change<? extends T> c) {
		if (!c.next()) return;
		
		final List<? extends T> removedItems = c.getRemoved();
		final List<? extends T> addedItems = c.getList();
		
		synchronized(listLock) {
			if (removedItems.size() > 0) {
				target.removeAll(removedItems);
			}
			if (addedItems.size() > 0) {
				for (int i = 0; i < addedItems.size(); i++) {
					T item = addedItems.get(i);
					if (target.size() - 1 < i) {
						if (target.contains(item)) {
							target.remove(item);
						}
						target.add(item);
						continue;
					}
					
					if (target.get(i) != item) {
						if (target.contains(item)) {
							target.remove(item);
						}
						
						target.add(i, item);
					}
				}
			}
		}
	}
}
