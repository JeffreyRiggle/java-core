package ilusr.core.io;

import java.nio.file.WatchEvent;

/**
 * 
 * @author Jeff Riggle
 *
 */
public interface IDirectoryListener {
	/**
	 * 
	 * @param event An event that contains information about the directory change.
	 * @param type A @see UpdateType indicating what happened with the directory change.
	 */
	public void directoryUpdated(WatchEvent<?> event, UpdateType type);
}
