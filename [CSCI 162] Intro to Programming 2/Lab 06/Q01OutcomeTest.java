import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
 * JUnit tests for LinkedSequence.
 *
 * @author Chad Hogg
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Q01OutcomeTest {
	
	/** The maximum allowable difference when comparing doubles. */
	private static final double EPSILON = 0.00001;
	/** The start of the message when size is wrong. */
	private static final String WRONG_SIZE = "Wrong size";
	/** The start of the message when current element is wrong. */
	private static final String WRONG_CURRENT = "Wrong current element";
	/** The start of the message when string is wrong. */
	private static final String WRONG_STRING = "Wrong toString value";
	/** The start of the message when should have had a current element. */
	private static final String NEED_CURRENT = "Should have had current element";
	/** The start of the message when should not have had a current element. */
	private static final String UNEXPECTED_CURRENT = "Should not have had current element";
	
	/**
	 * Creates a message string for an assertion.
	 * 
	 * @param type The type of error.
	 * @param action The action that was taken.
	 * @param description The object the action was applied to.
	 * @return The concatenation of this information into a sentence.
	 */
	private String message(String type, String action, String description) {
		return type + " after " + action + " " + description + ".";
	}
	
	/**
	 * Creates a default LinkedSequence<Double> and confirms that it was initialized correctly.
	 */
	@Test
	public void test01ConstructorNoParameters() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		assertEquals("New default sequence had wrong size.", 0, sequence.size());
		assertFalse("New default sequence should not have had current element.", sequence.isCurrent());
		assertEquals("New default sequence toString was wrong.", "empty sequence", sequence.toString());
	}

	/**
	 * Calls addBefore on an empty sequence.
	 */
	@Test
	public void test05AddBeforeEmpty() {
		final String ACTION  = "adding (before) 5.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		final String DESCRIPTION = sequence.toString();
		sequence.addBefore(5.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 1, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 5.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(5.0) ", sequence.toString());
	}
	
	/**
	 * Calls addBefore on a sequence with 1 element that is the current.
	 */
	@Test
	public void test06AddBeforeHasOneCurrent() {
		final String ACTION = "adding (before) 3.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(5.0);
		final String DESCRIPTION = sequence.toString();
		sequence.addBefore(3.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 3.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(3.0) 5.0 ", sequence.toString());
	}
	
	/**
	 * Calls advance on a sequence with 1 element that is the current.
	 */
	@Test
	public void test07AdanceHasOneCurrent() {
		final String ACTION = "advancing";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		final String DESCRIPTION = sequence.toString();
		sequence.advance();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 1, sequence.size());
		assertFalse(message(UNEXPECTED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "8.0 ", sequence.toString());
	}
	
	/**
	 * Calls addBefore on a sequence with 1 element and no current.
	 */
	@Test
	public void test08AddBeforeOneNoCurrent() {
		final String ACTION = "adding (before) 9.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(6.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.addBefore(9.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 9.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(9.0) 6.0 ", sequence.toString());
	}
	
	/**
	 * Calls addBefore on a sequence with 2 elements, first is current.
	 */
	@Test
	public void test09AddBeforeTwoFirstCurrent() {
		final String ACTION = "adding (before) 5.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		final String DESCRIPTION = sequence.toString();
		sequence.addBefore(5.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 5.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(5.0) 2.0 8.0 ", sequence.toString());
	}

	/**
	 * Calls addBefore on a sequence with 2 elements, second is current.
	 */
	@Test
	public void test10AdvanceHasTwoFirstCurrent() {
		final String ACTION = "advancing";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		final String DESCRIPTION = sequence.toString();
		sequence.advance();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 8.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "2.0 (8.0) ", sequence.toString());
	}

	/**
	 * Calls addBefore on a sequence with 2 elements, first is current.
	 */
	@Test
	public void test11AdvanceHasTwoSecondCurrent() {
		final String ACTION = "advancing";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.advance();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertFalse(message(UNEXPECTED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "2.0 8.0 ", sequence.toString());
	}
	
	/**
	 * Calls addBefore on a sequence with 2 elements, second is current.
	 */
	@Test
	public void test12AddBeforeTwoFirstCurrent() {
		final String ACTION = "adding (before) 5.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.addBefore(5.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 5.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "2.0 (5.0) 8.0 ", sequence.toString());
	}

	/**
	 * Calls addBefore on a sequence with 2 elements, neither is current.
	 */
	@Test
	public void test13AddBeforeTwoFirstCurrent() {
		final String ACTION = "adding (before) 5.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.advance();
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.addBefore(5.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 5.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(5.0) 2.0 8.0 ", sequence.toString());
	}
	
	/**
	 * Calls advance on a sequence with 2 elements, none is current.
	 */
	@Test
	public void test14AdvanceHasTwoNoCurrent() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.advance();
		sequence.advance();
		try {
			sequence.advance();
			fail("Advancing a sequence without a current element should have thrown an exception.");
		}
		catch(IllegalStateException exception) {
			// expected
		}
		catch(Exception exception) {
			fail("Advancing a sequence without a current element threw the wrong kind of exception.");
		}
	}
	
	/**
	 * Calls advance on an empty sequence.
	 */
	@Test
	public void test15AdvanceEmpty() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		try {
			sequence.advance();
			fail("Advancing an empty sequence should have thrown an exception.");
		}
		catch(IllegalStateException exception) {
			// expected
		}
		catch(Exception exception) {
			fail("Advancing an empty sequence threw the wrong kind of exception.");
		}
	}
	
	/**
	 * Calls addAfter on an empty sequence.
	 */
	@Test
	public void test16AddAfterEmpty() {
		final String ACTION = "adding (after) 6.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		final String DESCRIPTION = sequence.toString();
		sequence.addAfter(6.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 1, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 6.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(6.0) ", sequence.toString());
	}
	
	/**
	 * Calls addAfter on a sequence with one element, that is current.
	 */
	@Test
	public void test17AddAfterOneIsCurrent() {
		final String ACTION = "adding (after) 3.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(6.0);
		final String DESCRIPTION = sequence.toString();
		sequence.addAfter(3.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 3.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "6.0 (3.0) ", sequence.toString());
	}
	
	/**
	 * Calls addAfter on a sequence with one element, but no current.
	 */
	@Test
	public void test18AddAfterOneNoCurrent() {
		final String ACTION = "adding (after) 3.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(6.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.addAfter(3.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 3.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "6.0 (3.0) ", sequence.toString());
	}
	
	/**
	 * Calls start on a sequence with no elements.
	 */
	@Test
	public void test19StartEmpty() {
		final String ACTION = "starting";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		final String DESCRIPTION = sequence.toString();
		sequence.start();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 0, sequence.size());
		assertFalse(message(UNEXPECTED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "empty sequence", sequence.toString());
	}
	
	/**
	 * Calls start on a sequence with one element that is current.
	 */
	@Test
	public void test20StartHasCurrent() {
		final String ACTION = "starting";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(1.0);
		final String DESCRIPTION = sequence.toString();
		sequence.start();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 1, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 1.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(1.0) ", sequence.toString());
	}

	/**
	 * Calls start on a sequence with one element but no current.
	 */
	@Test
	public void test21StartNoCurrent() {
		final String ACTION = "starting";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(1.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.start();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 1, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 1.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(1.0) ", sequence.toString());
	}
	
	/**
	 * Calls addAfter on a sequence with two elements, first current.
	 */
	@Test
	public void test22AddAfterTwoFirstCurrent() {
		final String ACTION = "adding (after) 4.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(6.0);
		sequence.addAfter(3.0);
		sequence.start();
		final String DESCRIPTION = sequence.toString();
		sequence.addAfter(4.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 4.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "6.0 (4.0) 3.0 ", sequence.toString());
	}

	/**
	 * Calls addAfter on a sequence with two elements, second current.
	 */
	@Test
	public void test23AddAfterTwoSecondCurrent() {
		final String ACTION = "adding (after) 4.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(6.0);
		sequence.addAfter(3.0);
		final String DESCRIPTION = sequence.toString();
		sequence.addAfter(4.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 4.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "6.0 3.0 (4.0) ", sequence.toString());
	}

	/**
	 * Calls addAfter on a sequence with two elements, none current.
	 */
	@Test
	public void test24AddAfterTwoNoCurrent() {
		final String ACTION = "adding (after) 4.0 to";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(6.0);
		sequence.addAfter(3.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.addAfter(4.0);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 4.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "6.0 3.0 (4.0) ", sequence.toString());
	}

	
	/**
	 * Calls getCurrent when there is no current element.
	 */
	@Test
	public void test30GetCurrentNoCurrent() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(1.0);
		sequence.addBefore(3.0);
		sequence.advance();
		sequence.advance();
		try {
			sequence.getCurrent();
			fail("Getting current should have thrown an exception when there was no current element.");
		}
		catch(IllegalStateException exception) {
			// expected
		}
		catch(Exception exception) {
			fail("Getting current threw the wrong kind of exception when there was no current elemnt.");
		}
	}
	
	/**
	 * Calls removeCurrent when there is no current element.
	 */
	@Test
	public void test31RemoveCurrentNoCurrent() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(5.0);
		sequence.addAfter(2.0);
		sequence.advance();
		try {
			sequence.removeCurrent();
			fail("Removing current should have thrown an exception when there was no current element.");
		}
		catch(IllegalStateException exception) {
			// expected
		}
		catch(Exception exception) {
			fail("Removing current threw the wrong kind of exception when there was no current element.");
		}
	}

	/**
	 * Calls removeCurrent when there is only one element.
	 */
	@Test
	public void test32RemoveCurrentJustOne() {
		final String ACTION = "removing current from";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addAfter(7.0);
		final String DESCRIPTION = sequence.toString();
		sequence.removeCurrent();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 0, sequence.size());
		assertFalse(message(UNEXPECTED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "empty sequence", sequence.toString());				
	}
	
	/**
	 * Calls removeCurrent when there are three elements, first is current.
	 */
	@Test
	public void test33RemoveCurrentFirstOfThree() {
		final String ACTION = "removing current from";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.addBefore(5.0);
		final String DESCRIPTION = sequence.toString();
		sequence.removeCurrent();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 2.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "(2.0) 8.0 ", sequence.toString());		
	}
	
	/**
	 * Calls removeCurrent when there are three elements, second is current.
	 */
	@Test
	public void test34RemoveCurrentSecondOfThree() {
		final String ACTION = "removing current from";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.addBefore(5.0);
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.removeCurrent();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 8.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "5.0 (8.0) ", sequence.toString());		
	}

	/**
	 * Calls removeCurrent when there are three elements, third is current.
	 */
	@Test
	public void test35RemoveCurrentThirdOfThree() {
		final String ACTION = "removing current from";
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(8.0);
		sequence.addBefore(2.0);
		sequence.addBefore(5.0);
		sequence.advance();
		sequence.advance();
		final String DESCRIPTION = sequence.toString();
		sequence.removeCurrent();
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertFalse(message(UNEXPECTED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "5.0 2.0 ", sequence.toString());		
	}

	/**
	 * Calls addAll with a null parameter.
	 */
	@Test
	public void test36AddAllNull() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(5.0);
		try {
			sequence.addAll(null);
			fail("Adding all with a null parameter should have thrown an exception.");
		}
		catch(NullPointerException exception) {
			// expected
		}
		catch(Exception exception) {
			fail("Adding all with a null pointer threw the wrong type of exception.");
		}
	}
	
	/**
	 * Calls addAll with empty parameter.
	 */
	@Test
	public void test37AddAllParameterEmpty() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(5.0);
		sequence.addAfter(3.0);
		sequence.addBefore(7.0);
		LinkedSequence<Double> addend = new LinkedSequence<Double>();
		final String ACTION = "adding all of " + addend.toString() + " to";
		final String DESCRIPTION = sequence.toString();
		sequence.addAll(addend);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 3, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 7.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "5.0 (7.0) 3.0 ", sequence.toString());
		assertEquals("Calling addAll should not have changed addend.", "empty sequence", addend.toString());
	}
	
	/**
	 * Calls addAll with empty this.
	 */
	@Test
	public void test38AddAllThisEmpty() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		LinkedSequence<Double> addend = new LinkedSequence<Double>();
		addend.addAfter(6.0);
		addend.addAfter(3.0);
		final String ACTION = "adding all of " + addend.toString() + " to";
		final String DESCRIPTION = sequence.toString();
		sequence.addAll(addend);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 2, sequence.size());
		assertFalse(message(UNEXPECTED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "6.0 3.0 ", sequence.toString());
		assertEquals("Calling addAll should not have changed addend.", "6.0 (3.0) ", addend.toString());
	}
	
	/**
	 * Calls addAll with both having numbers.
	 */
	@Test
	public void test39AddAllThisBothFull() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(5.0);
		sequence.addAfter(3.0);
		sequence.addBefore(7.0);
		LinkedSequence<Double> addend = new LinkedSequence<Double>();
		addend.addAfter(6.0);
		addend.addAfter(3.0);
		final String ACTION = "adding all of " + addend.toString() + " to";
		final String DESCRIPTION = sequence.toString();
		sequence.addAll(addend);
		assertEquals(message(WRONG_SIZE, ACTION, DESCRIPTION), 5, sequence.size());
		assertTrue(message(NEED_CURRENT, ACTION, DESCRIPTION), sequence.isCurrent());
		assertEquals(message(WRONG_CURRENT, ACTION, DESCRIPTION), 7.0, sequence.getCurrent(), EPSILON);
		assertEquals(message(WRONG_STRING, ACTION, DESCRIPTION), "5.0 (7.0) 3.0 6.0 3.0 ", sequence.toString());
		assertEquals("Calling addAll should not have changed addend.", "6.0 (3.0) ", addend.toString());
	}
	
	/**
	 * Calls clone.
	 */
	@Test
	public void test40Clone() {
		LinkedSequence<Double> sequence = new LinkedSequence<Double>();
		sequence.addBefore(2.0);
		sequence.addBefore(4.0);
		sequence.addAfter(5.0);
		Sequence<Double> copy = sequence.clone();
		assertTrue("Clone of an LinkedSequence<Double> should be an LinkedSequence<Double>.", copy instanceof LinkedSequence<?>);
		LinkedSequence<Double> clone = (LinkedSequence<Double>)copy;
		assertEquals("Cloning should not have changed the original's size.", 3, sequence.size());
		assertTrue("Cloning should not have erased original's current element.", sequence.isCurrent());
		assertEquals("Cloning should not have changed original's current element.", 5.0, sequence.getCurrent(), EPSILON);
		assertEquals("Cloning should not have changed original's string.", "4.0 (5.0) 2.0 ", sequence.toString());
		assertEquals("Size of clone should be the same as original.", sequence.size(), clone.size());
		assertTrue("Clone should have current since original did.", sequence.isCurrent());
		assertEquals("Current of clone should be the same as original.", sequence.getCurrent(), clone.getCurrent(), EPSILON);
		assertEquals("String of clone should be the same as original.", sequence.toString(), clone.toString());
		assertFalse("Clone should be a different object than original.", sequence == clone);
		sequence.addBefore(6.0);
		assertEquals("Changing original should not have changed clone's size.", 3, clone.size());
		assertEquals("Changing original should not have changed clone's string.", "4.0 (5.0) 2.0 ", clone.toString());
	}
}
