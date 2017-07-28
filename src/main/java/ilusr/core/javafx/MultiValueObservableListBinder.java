package ilusr.core.javafx;

import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author Jeff Riggle
 *
 * @param <TSource> The original type.
 * @param <TTarget> The converted type.
 */
public class MultiValueObservableListBinder<TSource, TTarget> implements ListChangeListener<TSource> {
	private final Object listLock;
	private final ListItemConverter<TSource, TTarget> converter;
	private ObservableList<TSource> source;
	private ObservableList<TTarget> target;
	
	/**
	 * 
	 * @param source The source @see ObservableList
	 * @param target The target @see ObservableList
	 */
	public MultiValueObservableListBinder(ObservableList<TSource> source, ObservableList<TTarget> target, ListItemConverter<TSource, TTarget> converter) {
		listLock = new Object();
		this.converter = converter;
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
	
	private void bind() {
		for (TSource item : source) {
			TTarget tItem = converter.convert(item);
			target.add(0, tItem);
		}
		
		source.addListener(this);
	}

	@Override
	public void onChanged(ListChangeListener.Change<? extends TSource> c) {
		if (!c.next()) return;
		
		final List<? extends TSource> removedItems = c.getRemoved();
		final List<? extends TSource> addedItems = c.getList();
		
		synchronized(listLock) {
			if (removedItems.size() > 0) {
				target.removeAll(removedItems);
			}
			if (addedItems.size() > 0) {
				for (int i = 0; i < addedItems.size(); i++) {
					TTarget item = converter.convert(addedItems.get(i));
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
