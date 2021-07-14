import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.FixMethodOrder;


/*
 * You must include the Junit4 library to use this test unit.
 *
 * To add it, select the project and then choose Properties... from the File menu.
 * Click on "Java Build Path" in the panel at the left.
 * Select Libraries from the list at the top.
 * Select Add Library... from the right side of the panel.
 * Select JUnit and click on Next.
 * Change the version to JUnit 4 and click on Finish.
 * Click on OK in the properties panel.
 * 
 * You can then run these tests using "Run As JUnit Test" instead of "Runs As Java Application".
 */


/**
 * JUnit tests for SortedList.
 *
 * @author Chad Hogg
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortedListTests {

	/**
	 * Creates a default SortedList and confirms that it was initialized correctly.
	 */
	@Test
	public void test01Constructor() {
		SortedList list = new SortedList();
		assertEquals("New list had wrong size.", 0, list.size());
		assertEquals("New list toString was wrong.", "( )", list.toString());
	}

	/**
	 * Inserts one element into a SortedList.
	 */
	@Test
	public void test02InsertOne() {
		SortedList list = new SortedList();
		list.insert(4.0);
		assertEquals("Inserting 4.0 to empty list yielded wrong size.", 1, list.size());
		assertEquals("Inserting 4.0 to empty list yielded wrong string.", "( 4.0 )", list.toString());
	}

	/**
	 * Tests that numbers sort on input to the list.
	 */
	@Test
	public void test03InsertSort() {
		
		SortedList list = new SortedList();
		list.insert(4.0);
		list.insert(5.0);
		list.insert(2.0);
		list.insert(3.0);
		
		assertEquals("Inserting 4.0, 5.0, 2.0, and 3.0 to an empty list yeilded wrong result.", 
				"( 2.0 3.0 4.0 5.0 )", list.toString());
		
	}
	
	/**
	 * Finds an element in a non-empty SortedList.
	 */
	@Test
	public void test04FindNonEmpty() {
		
		SortedList list = new SortedList();
		list.insert(2.0);
		list.insert(3.0);
		list.insert(4.0);
		
		list.find(4.0);
		assertEquals("Finding 3.0 in ( 2.0 3.0 4.0 ) yielded wrong index.", 1, list.find(3.0));
		
		list.find(5.0);
		assertEquals("Finding 5.0 in ( 2.0 3.0 4.0 ) yielded wrong index.", -1, list.find(5.0));
		
	}
	
	/**
	 * Tries to find an element in an empty SortedList
	 */
	@Test
	public void test05FindEmpty() {
		
		SortedList list = new SortedList();
		
		list.find(2.0);
		assertEquals("Finding 2.0 in empty list yielded wrong index.", -1, list.find(2.0));
		
	}
	
	/**
	 * Tries to remove an element from the beginning of the list.
	 */
	@Test
	public void test06RemoveFront() {
		
		SortedList list = new SortedList();
		list.insert(2.0);
		list.insert(1.0);
		
		list.removeAt(0);
		assertEquals("Removing the first node from list ( 1.0 2.0 ) yielded wrong result.", 
				"( 2.0 )", list.toString());
		
	}
	
	/**
	 * Tries to remove an element from the end of the list.
	 */
	@Test
	public void test07RemoveBack() {
		
		SortedList list = new SortedList();
		list.insert(1.0);
		list.insert(2.0);
		list.insert(3.0);
		list.insert(4.0);
		list.insert(5.0);
		
		list.removeAt(4);
		assertEquals("Removing the last node from list ( 1.0 2.0 3.0 4.0 5.0 ) yielded wrong result.", 
				"( 1.0 2.0 3.0 4.0 )", list.toString());
		
	}
	
	/**
	 * Tries to remove an element from the middle of the list.
	 */
	@Test
	public void test08RemoveMiddle() {
		
		SortedList list = new SortedList();
		list.insert(0.0);
		list.insert(1.0);
		list.insert(2.0);
		
		list.removeAt(1);
		assertEquals("Removing the middle node from list ( 0.0 1.0 2.0 ) yielded wrong result.", 
				"( 0.0 2.0 )", list.toString());
		
	}
	
}
