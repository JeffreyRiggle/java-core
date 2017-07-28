package ilusr.core.i18n;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ilusr.core.io.FileUtilities;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class LanguageManager {

	private static LanguageManager instance;
	private static Object instanceLock = new Object();
	
	private final String DEFUALT_LANGUAGE = "en-US";
	private final String CODE = "LanguageCode";
	private final String DELIM = ";=;";
	
	private Map<String, Map<String, String>> languagePacks;
	private List<Runnable> listeners;
	private String currentLanguage;
	
	public LanguageManager() {
		languagePacks = new HashMap<String, Map<String, String>>();
		listeners = new ArrayList<Runnable>();
		currentLanguage = DEFUALT_LANGUAGE;
	}
	
	/**
	 * 
	 * @return A @see LanguageManager instance
	 */
	public static LanguageManager getInstance() {
		synchronized (instanceLock) {
			if (instance == null) {
				instance = new LanguageManager();
			}
			
			return instance;
		}
	}
	
	/**
	 * Adds a language file to the current language manager.
	 * 
	 * Language files must have a language code.
	 * <code>LanguageCode;=;en-US</code>
	 * 
	 * Language files must also have values delimited by ;=;
	 * 
	 * @param languageFile The file to load and parse.
	 * @throws IOException
	 */
	public void addLanguagePack(File languageFile) throws IOException {
		parseFile(languageFile);
	}
	
	/**
	 * Adds a language file to the current language manager.
	 * 
	 * Language files must have a language code.
	 * <code>LanguageCode;=;en-US</code>
	 * 
	 * Language files must also have values delimited by ;=;
	 * 
	 * @param content The file content to load and parse.
	 */
	public void addLanguagePack(String content) {
		parseFile(content);
	}
	
	private void parseFile(File file) throws IOException {
		parseFile(FileUtilities.getFileContentWithReturns(file));
	}
	
	private void parseFile(String content) {
		String[] parts = content.split("\r\n");
		
		String languageCode = null;
		Map<String, String> values = new HashMap<String, String>();
		
		for (String part : parts) {
			String[] kvp = part.split(DELIM);
			
			if (kvp.length != 2) {
				continue;
			}
			
			if (kvp[0].equals(CODE)) {
				languageCode = kvp[1];
				continue;
			}
			
			values.put(kvp[0], kvp[1]);
		}
		
		if (languageCode == null) {
			return;
		}
		
		languagePacks.put(languageCode, values);
	}
	
	/**
	 * 
	 * @param languageCode The language to remove.
	 */
	public void removeLanguagePack(String languageCode) {
		languagePacks.remove(languageCode);
		
		if (currentLanguage.equals(languageCode)) {
			setLanguage(DEFUALT_LANGUAGE);
		}
	}
	
	/**
	 * 
	 * @param language Sets the current language code.
	 */
	public void setLanguage(String language) {
		currentLanguage = language;
		notifyListeners();
	}
	
	/**
	 * 
	 * @return Gets the current language code.
	 */
	public String currentLanguage() {
		return currentLanguage;
	}
	
	/**
	 * 
	 * @param listener A @see Runnable to call when the language code is changed.
	 */
	public void addLanguageChangeListener(Runnable listener) {
		listeners.add(listener);
	}
	
	/**
	 * 
	 * @param listener A @see Runnable to call when the language code is changed.
	 */
	public void removeLanguageChangeListener(Runnable listener) {
		listeners.remove(listener);
	}
	
	/**
	 * 
	 * @param key The key to look for.
	 * @return The language specific value.
	 */
	public String getValue(String key) {
		if (!languagePacks.containsKey(currentLanguage)) {
			return null;
		}
		
		Map<String, String> language = languagePacks.get(currentLanguage);
		if (!language.containsKey(key)) {
			return null;
		}
		
		return language.get(key);
	}
	
	private void notifyListeners() {
		for (Runnable listener : listeners) {
			listener.run();
		}
	}
}
