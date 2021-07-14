import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
 * JUnit tests for Statistician.
 *
 * @author Beth Katz, David Hutchens, Chad Hogg
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Q01OutcomeTest {
	
	/** The maximum allowable difference when comparing doubles. */
	private static double EPSILON = 0.00001;
	
	/**
	 * Creates a Statistician and checks whether it was constructed properly.
	 */
	@Test
	public void test01Constructor() {
		Statistician statistician = new Statistician();

		assertEquals("New Statistician had wrong sum.", 0.0, statistician.getSum(), EPSILON);
		assertEquals("New Statistician had wrong length.", 0, statistician.getLength());
		assertTrue("New Statistician's mean should be Double.NaN.", Double.isNaN(statistician.getMean()));
		assertTrue("New Statistician's largest value should be Double.NaN.", Double.isNaN(statistician.getLargest()));
		assertTrue("New Statistician's smallest value should be Double.NaN.", Double.isNaN(statistician.getSmallest()));
	}

	/**
	 * Checks that inserting into a Statistician increments the length.
	 */
	@Test
	public void test02IncrementLength() {
		Statistician statistician = new Statistician();
		statistician.insert(42);
		assertEquals("Statistician's length is wrong after one insertion.", 1, statistician.getLength());
		statistician.insert(12);
		statistician.insert(17);
		assertEquals("Statistician's length is wrong after three inserations.", 3, statistician.getLength());
	}


	/**
	 * Tests that reset returns everything to original values.
	 */
	@Test
	public void test03Reset() {
		Statistician statistician = new Statistician();

		statistician.insert(42);
		statistician.insert(17);
		statistician.insert(-5.5);
		statistician.insert(0);

		assertEquals("Statistician's sum is wrong after inserting 42, 17, -5.5, and 0.", 53.5, statistician.getSum(), EPSILON);
		assertEquals("Statistician's length is wrong after inserting 42, 17, -5.5, and 0.", 4, statistician.getLength());
		assertEquals("Statistician's mean is wrong after inserting 42, 17, -5.5, and 0.", 13.375, statistician.getMean(), EPSILON);
		assertEquals("Statistician's largest is wrong after inserting 42, 17, -5.5, and 0.", 42, statistician.getLargest(), EPSILON);
		assertEquals("Statistician's smallest is wrong after inserting 42, 17, -5.5, and 0.", -5.5, statistician.getSmallest(), EPSILON);

		statistician.reset();	

		assertEquals("Statistician's sum is wrong after resetting.", 0.0, statistician.getSum(), EPSILON);
		assertEquals("Statistician's length is wrong after resetting.", 0, statistician.getLength());
		assertTrue("Statistician's mean should be Double.NaN after resetting.", Double.isNaN(statistician.getMean()));
		assertTrue("Statistician's largest should be Double.NaN after resetting.", Double.isNaN(statistician.getLargest()));
		assertTrue("Statistician's smallest should be Double.NaN after resetting.", Double.isNaN(statistician.getSmallest()));
	}

	/**
	 * Tests behavior of Statistician when all values are negative.
	 */
	@Test
	public void test04AllNegative( ) {
		Statistician statistician = new Statistician();

		statistician.insert(-42);
		statistician.insert(-10);
		statistician.insert(-8.15);

		assertEquals("Statistician's sum is wrong after inserting -42, -10, and -8.15.", -60.15, statistician.getSum(), EPSILON);
		assertEquals("Statistician's length is wrong after inserting -42, -10, and -8.15.", 3, statistician.getLength());
		assertEquals("Statistician's mean is wrong after inserting -42, -10, and -8.15.", -20.05, statistician.getMean(), EPSILON);
		assertEquals("Statistician's largest is wrong after inserting -42, -10, and -8.15.", -8.15, statistician.getLargest(), EPSILON);
		assertEquals("Statistician's smallest is wrong after inserting -42, -10, and -81.5.", -42.0, statistician.getSmallest(), EPSILON);
	}

	/**
	 * Tests behavior of Statistician when smallest value is zero.
	 */
	@Test
	public void test05SmallestZero( ) {
		Statistician statistician = new Statistician();

		statistician.insert(1);
		statistician.insert(0);
		statistician.insert(2);

		assertEquals("Statistician's smallest is wrong after inserting 1, 0, and 2.", 0.0, statistician.getSmallest(), EPSILON);
	}

	/**
	 * Creates Statisticians with single different values
	 *   and compares the statisticians to see if they are different.
	 * It then adds the 'opposing' values to each and compares to
	 *   see that the Statisticians seem to be the same.
	 */
	@Test
	public void test06CompareTwoSmall() {
		Statistician first = new Statistician();
		Statistician second = new Statistician();

		first.insert(42);
		second.insert(-736.3);

		assertFalse("The equals method returned true for two Statisticians with different numbers.", first.equals(second));
		assertFalse("The equals method returned true for two Statisticians with different numbers.", second.equals(first));

		first.insert(-736.3);
		second.insert(42);

		assertTrue("The equals method returned false for two Statisticians with the same numbers entered in a different order.", first.equals(second));
		assertTrue("The equals method returned false for two Statisticians with the same numbers entered in a different order.", second.equals(first));
		assertFalse("The equals method returned true for a Statistician compared to null.", first.equals(null));
	}


	/**
	 * Creates Statisticians with several different values
	 *   and compares the statisticians to see if they are the same.
	 * It then adds an extra value to one and compares to
	 *   see that the Statisticians seem to be different.
	 */
	@Test
	public void test07CompareTwoMedium() {
		Statistician first = new Statistician();
		Statistician second = new Statistician();

		for(int counter = -5; counter <= 10; counter++) {
			first.insert(counter);
		}
		for(int counter = 10; counter >= -5; counter--) {
			second.insert(counter);
		}
		assertTrue("The equals method returned false for two Statisticians with the same numbers entered in a different order.", first.equals(second));
		assertTrue("The equals method returned false for two Statisticians with the same numbers entered in a different order.", second.equals(first));

		first.insert(15);

		assertFalse("The equals method returned true for two Statisticians when one's numbers were a subset of the other's.", first.equals(second));
		assertFalse("The equals method returned true for two Statisticians when one's numbers were a subset of the other's.", second.equals(first));

		second.insert(-20);

		assertFalse("The equals method returned true for two Statisticians with different numbers.", first.equals(second));
		assertFalse("The equals method returned true for two Statisticians with different numbers.", second.equals(first));
	}

	/**
	 * Creates Statisticians with a lot of values and sees
	 *   if that causes problems. But it doesn't try to
	 *   overflow it.
	 */
	@Test
	public void test08CompareALot() {
		Statistician statistician = new Statistician();
		for (int counter = 1; counter <= 10000; counter++) {
			statistician.insert(counter);
		}

		assertEquals("Statistician's mean is wrong after adding the numbers 1 through 10000 (inclusive).", 5000.5, statistician.getMean(), EPSILON);
		assertEquals("Statistician's length is wrong after adding the numbers 1 through 10000 (inclusive).", 10000, statistician.getLength());
	}

	/**
	 * Creates two statisticians, adds them, and checks answers.
	 */
	@Test
	public void test09Union() {
		Statistician first = new Statistician();
		Statistician second = new Statistician();
		Statistician union = first.union(second);

		assertEquals("Statistician's length was wrong after unioning it with another.", 0, first.getLength());
		assertEquals("Statistician's length was wrong after unioning it with another.", 0, second.getLength());
		assertEquals("Union of two empty Statisticians had incorrect length.", 0, union.getLength());
		assertTrue("Union of two empty Statisticians should have had Double.NaN as largest.", Double.isNaN(union.getLargest()));
		assertTrue("Union of two empty Statisticians should have had Double.NaN as smallest.", Double.isNaN(union.getSmallest()));

		for(int counter = 1; counter <= 3; counter++) {
			first.insert(counter);
		}
		for(int counter = 5; counter <= 7; counter++) {
			second.insert(counter);
		}

		union = first.union(second);

		assertEquals("Union of <1, 2, 3> with <5, 6, 7> had wrong length.", 6, union.getLength());
		assertEquals("Union of <1, 2, 3> with <5, 6, 7> had wrong sum.", first.getSum() + second.getSum(), union.getSum(), EPSILON);
		assertEquals("Union of <1, 2, 3> with <5, 6, 7> had wrong largest.", 7.0, union.getLargest(), EPSILON);
		assertEquals("Union of <1, 2, 3> with <5, 6, 7> had wrong smallest.", 1.0, union.getSmallest(), EPSILON);
	}

	/**
	 * Creates two empty statisticians, adds them, and checks answers.
	 */
	@Test
	public void test10AddEmpty() {

		Statistician first = new Statistician();
		Statistician second = new Statistician();
		first.add(second);

		assertEquals("New Statistician that had another new Statistician added had wrong length.", 0, first.getLength());
		assertEquals("New Statistician that was added to another Statistician had wrong length.", 0, second.getLength());
		assertTrue("New Statistician that had another new Statistician added should have had Double.NaN for largest.", Double.isNaN(first.getLargest()));
		assertTrue("New Statistician that had another new Statistician added should have had Double.NaN for smallest.", Double.isNaN(first.getSmallest()));
	}


	/**
	 * Creates two Statisticians, adds them and unions them, and checks largest and smallest.
	 */
	@Test
	public void test11UnionSmall() {

		Statistician first = new Statistician();
		Statistician second = new Statistician();
		Statistician third = new Statistician();
		double value = 42.0;

		first.insert(value);  // first contains one

		first.add(third);   // add empty to first
		assertEquals("Smallest was wrong after adding empty Statistician to <42.0> one.", value, first.getSmallest(), EPSILON);
		assertEquals("Largest was wrong after adding empty Statistician to <42.0> one.", value, first.getLargest(), EPSILON);

		second.add(first);   // add first to empty
		assertEquals("Smallest was wrong after adding <42.0> Statistician to empty one.", value, second.getSmallest(), EPSILON);
		assertEquals("Largest was wrong after adding <42.0> Statistician to empty one.", value, second.getLargest(), EPSILON);

		second.insert(value * 2 + 10);
		third = first.union(second);  // union the two

		assertEquals("Length was wrong in union of <42.0> Statistician with <42.0, 94.0> Statistician.", 3, third.getLength());
		assertEquals("Sum was wrong in union of <42.0> Statistician with <42.0, 94.0> Statistician.", second.getSum() + first.getSum(), third.getSum(), EPSILON);
		assertEquals("Largest was wrong in union of <42.0> Statistician with <42.0, 94.0> Statistician.", 94.0, third.getLargest(), EPSILON);
		assertEquals("Smallest was wrong in union of <42.0> Statistician with <42.0, 94.0> Statistician.", 42.0, third.getSmallest(), EPSILON);
	}

	/**
	 * Creates two statisticians, adds them, and checks largest and smallest.
	 */
	@Test
	public void test12AddExtremes() {

		Statistician first = new Statistician();
		Statistician second = new Statistician();
		first.insert(17.17);
		first.insert(78.0);
		second.insert(4.0);
		second.insert(100.0);

		second.add(first);
		assertEquals("Smallest was wrong after adding <17.17, 78.0> Statistician to <4.0, 100.0> one.", 4.0, second.getSmallest(), EPSILON);
		assertEquals("Largest was wrong after adding <17.17, 78.0> Statistician to <4.0, 100.0> one.", 100.0, second.getLargest(), EPSILON);
	}

	/**
	 * Looks for bugs in the clone and equals methods.
	 */
	@Test
	public void test13CloneEquals() {
		Statistician first = new Statistician();
		Statistician second;

		first.insert(42);
		first.insert(-1.5);
		first.insert(0);
		first.insert(-0.5);
		second = first.clone();
		assertTrue("The clone of a Statistician was not equal to the original.", first.equals(second));
		assertTrue("The clone of a Statistician was not equal to the original.", second.equals(first));
		assertFalse("The clone of a Statistician was not a distinct object.", first == second);
		second.insert(40);
		assertTrue("The clone of a Statistician should no longer be equal to the original after the clone had a number inserted.", !first.equals(second));		
	}

	/**
	 * Looks for bug in passing a null pointer to union.
	 */
	@Test
	public void test14UnionException() {
		try {
			Statistician first = new Statistician();
			Statistician second = null;
			first.union(second);
			fail("Trying to union a Statistician with null should have resulted in a NullPointerException.");
		}
		catch(NullPointerException exception) {
			// expected
		}
	}

	/**
	 * Looks for bug in passing a null pointer to add.
	 */
	@Test
	public void test15AddException() {
		try {
			Statistician first = new Statistician();
			Statistician second = null;
			first.add(second);
			fail("Trying to add null to a Statistician should have resulted in a NullPointerException.");
		}
		catch(NullPointerException exception) {
			// expected
		}
	}

}
