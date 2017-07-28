package ilusr.core.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class DirectoryWatcher {

	private WatchService watchService;
	private Path path;
	private WatchKey key;
	private boolean pulling;
	private Thread watcherThread;
	private List<IDirectoryListener> listeners;
	private String directory;
	
	/**
	 * 
	 * @param dir The directory to watch.
	 * @throws IOException
	 */
	public DirectoryWatcher(String dir) throws IOException {
		watchService = FileSystems.getDefault().newWatchService();
		directory = dir;
		path = Paths.get(dir);
		listeners = new ArrayList<IDirectoryListener>();
	}
	
	/**
	 * Starts watching for directory updates an notifies any registered @see IDirectoryListeners when changes occur.
	 * 
	 * @param listener The @see core.io.IDirectoryListener to be updated when directory modification occur.
	 * @throws IOException
	 */
	public void startWatching(IDirectoryListener listener) throws IOException {
		startWatchingImpl(listener);
	}
	
	/**
	 * 
	 * @return The directory this watcher is watching.
	 */
	public String watchedDirectory() {
		return directory;
	}
	
	private void startWatchingImpl(IDirectoryListener listener) throws IOException {
		
		if (listeners.size() != 0) {
			listeners.add(listener);
			return;
		}
		
		listeners.add(listener);
		key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		pulling = true;
		watcherThread = new Thread(() -> {
			startPulling();
		});
		
		watcherThread.start();
	}
	
	private void startPulling() {
		while (pulling) {
			WatchKey key;
			try {
				key = watchService.take();
			} catch (InterruptedException e) {
				return;
			}
			
			for (WatchEvent<?> event: key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				
				if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
					raiseCreated(event);
				}
				else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
					raiseRemoved(event);
				}
				else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
					raiseModified(event);
				}
				//TODO: what about overflow.
			}
			
			if (!key.reset()) {
				pulling = false;
				break;
			}
		}
	}
	
	private void raiseCreated(WatchEvent<?> event) {
		for(IDirectoryListener listener : listeners) {
			listener.directoryUpdated(event, UpdateType.Added);
		}
	}
	
	private void raiseRemoved(WatchEvent<?> event) {
		for(IDirectoryListener listener : listeners) {
			listener.directoryUpdated(event, UpdateType.Removed);
		}
	}
	
	private void raiseModified(WatchEvent<?> event) {
		for(IDirectoryListener listener : listeners) {
			listener.directoryUpdated(event, UpdateType.Modified);
		}
	}
	
	/**
	 * Stops a @see core.io.IDirectoryListener from geting updates about this directory.
	 * <p>
	 * Note: Once all @see core.io.IDirectoryListeners are not watching this the directory watch is torn down.
	 * </p>
	 * 
	 * @param listener A @see core.io.IDirectoryListener to no longer get updates.
	 */
	public void stopWatching(IDirectoryListener listener) {
		listeners.remove(listener);
		
		if (listeners.size() != 0) return;
		
		pulling = false;
		key.cancel();
		try {
			watcherThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
