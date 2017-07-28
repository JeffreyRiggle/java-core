package UnitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ilusr.core.javafx.ObservableListBinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListBinderUnitTest {

	private ObservableList<String> source;
	private ObservableList<String> target;
	
	private final String value1 = "String1";
	private final String value2 = "String2";
	private final String value3 = "String3";
	
	@Before
	public void setupLists() {
		source = FXCollections.observableArrayList();
		target = FXCollections.observableArrayList();
	}
	
	@Test
	public void testCreate() {
		ObservableListBinder<String> binder = new ObservableListBinder<String>(source, target);
		assertNotNull(binder);
	}
	
	@Test
	public void testAddToSource() {
		ObservableListBinder<String> binder = new ObservableListBinder<String>(source, target);
		source.add(value1);
		assertTrue(target.contains(value1));
		
		source.add(value2);
		assertTrue(target.contains(value2));
		
		source.add(value3);
		assertTrue(target.contains(value3));
	}

	@Test
	public void testRemoveFromSource() {
		ObservableListBinder<String> binder = new ObservableListBinder<String>(source, target);
		source.add(value1);
		source.add(value2);
		source.add(value3);
		
		source.remove(value1);
		assertFalse(target.contains(value1));
		
		source.remove(value2);
		assertFalse(target.contains(value2));
		
		source.remove(value3);
		assertFalse(target.contains(value3));
	}
	
	@Test
	public void testMoveInSource() {
		ObservableListBinder<String> binder = new ObservableListBinder<String>(source, target);
		source.add(value1);
		source.add(value2);
		source.add(1, value3);
		
		assertEquals(target.get(0), value1);
		assertEquals(target.get(1), value3);
		assertEquals(target.get(2), value2);
	}
	
	@Test
	public void testUnbind() {
		ObservableListBinder<String> binder = new ObservableListBinder<String>(source, target);
		source.add(value1);
		binder.unbind();
		
		source.add(value2);
		source.add(value3);
		
		assertTrue(target.contains(value1));
		assertFalse(target.contains(value2));
		assertFalse(target.contains(value3));
	}
}
