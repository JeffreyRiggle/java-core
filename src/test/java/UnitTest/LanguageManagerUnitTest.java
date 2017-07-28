package UnitTest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import ilusr.core.i18n.LanguageManager;

public class LanguageManagerUnitTest {

	@Test
	public void testInstance() {
		LanguageManager manager = LanguageManager.getInstance();
		
		assertEquals("en-US", manager.currentLanguage());
	}
	
	@Test
	public void testAddLanguage() {
		LanguageManager manager = new LanguageManager();
		
		try {
			manager.addLanguagePack(new File(getClass().getResource("enLanguageFile.txt").toURI().getSchemeSpecificPart()));
			assertEquals("Hello", manager.getValue("greeting"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testChangeLanguage() {
		LanguageManager manager = new LanguageManager();
		
		try {
			manager.addLanguagePack(new File(getClass().getResource("enLanguageFile.txt").toURI().getSchemeSpecificPart()));
			manager.addLanguagePack(new File(getClass().getResource("jaLanguageFile.txt").toURI().getSchemeSpecificPart()));
			manager.setLanguage("ja-JP");
			assertEquals("konichiwa", manager.getValue("greeting"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testChangeLanguageWithListener() {
		final Watcher w = new Watcher();
		LanguageManager manager = new LanguageManager();
		manager.addLanguageChangeListener(() -> {
			w.called(true);
		});
		
		manager.setLanguage("ja-JP");
		assertTrue(w.called());
	}
	
	@Test
	public void testChangeLanguageWithoutListener() {
		final Watcher w = new Watcher();
		Runnable run = () -> { w.called(true); };
		
		LanguageManager manager = new LanguageManager();
		manager.addLanguageChangeListener(run);
		manager.removeLanguageChangeListener(run);
		
		manager.setLanguage("ja-JP");
		assertFalse(w.called());
	}
	
	private class Watcher {
		private boolean called;
		
		public boolean called() {
			return called;
		}
		
		public void called(boolean call) {
			called = true;
		}
	}
}
