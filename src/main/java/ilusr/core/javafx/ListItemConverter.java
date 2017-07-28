package ilusr.core.javafx;

/**
 * 
 * @author Jeff Riggle
 *
 * @param <TSource> The original object type.
 * @param <TTarget> The converted object type.
 */
public interface ListItemConverter<TSource, TTarget> {
	/**
	 * 
	 * @param item The item to convert.
	 * @return The converted item.
	 */
	TTarget convert(TSource item);
}
