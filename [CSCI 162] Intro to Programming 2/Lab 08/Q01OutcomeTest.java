import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
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
 * JUnit tests for PrefixEvaluator.
 *
 * @author Chad Hogg
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Q01OutcomeTest {
	
	/** The maximum allowable difference when comparing doubles. */
	private static final double EPSILON = 0.00001;
	/** The return value when the expression was empty. */
	private static final String NO_INPUT = "No input.";
	/** The return value when there were not enough operands on the stack. */
	private static final String INSUFFICIENT_OPERANDS = "Not enough operands.";
	/** The return value when the answer is infinity. */
	private static final String INFINITY = "Infinity";
	/** The return value when values are left on the stack. */
	private static final String OVERFULL_STACK = "Computed answer, but not all input used.";
	/** The return value when not all input is used. */
	private static final String MORE_INPUT = "Computed answer, but not all input used.";
	/** The return value when a left parenthesis is found. */
	private static final String LEFT_PAREN = "( has no meaning here.";
	/** The return value when a right parenthesis is found. */
	private static final String RIGHT_PAREN = ") has no meaning here.";

	/**
	 * Attempts to evaluate a well-formed expression.
	 * 
	 * @param input The postfix expression to evaluate.
	 * @param expected The correct answer.
	 */
	private void runTest(String input, Double expected) {
		try {
			String result = PrefixEvaluator.evaluate(input);
			try {
				Double dResult = Double.parseDouble(result);
				assertEquals("\"" + input + "\" produced wrong answer.", expected, dResult, EPSILON);				
			}
			catch(NumberFormatException exception) {
				fail("\"" + input + "\" should have produced a number, not \"" + result + "\"");
			}
		}
		catch(Exception exception) {
			fail("\"" + input + "\" should not have caused a " + exception.getClass() + " to be thrown.");
		}
	}

	/**
	 * Attempts to evaluate a mal-formed expression.
	 * 
	 * @param input The bad postfix expression to evaluate.
	 * @param expected The expected error message.
	 */
	private void runBadTest(String input, String expected) {
		try {
			String result = PrefixEvaluator.evaluate(input);
			assertEquals("\"" + input + "\" should have produced \"" + expected + "\", not \"" + result + "\"", expected, result);
		}
		catch(Exception exception) {
			fail("\"" + input + "\" should not have caused a " + exception.getClass() + " to be thrown.");
		}
	}
	
	/**
	 * Tests that an addition of two doubles works.
	 */
	@Test
	public void test01SimpleAddition() {
		runTest("+ 5.0 7.0", 12.0);
	}

	/**
	 * Tests that a subtraction of two doubles works.
	 */
	@Test
	public void test02SimpleSubtraction() {
		runTest("- 7.0 5.0", 2.0);
	}
	
	/**
	 * Tests that a multiplication of two doubles works.
	 */
	@Test
	public void test03SimpleMultiplication() {
		runTest("* 3.0 2.0", 6.0);
	}
	
	/**
	 * Tests that a division of two doubles works.
	 */
	@Test
	public void test04SimpleDivision() {
		runTest("/ 3.0 2.0", 1.5);
	}
	
	/**
	 * Tests that combining two subexpressions works.
	 */
	@Test
	public void test05CombiningSubexpressions() {
		runTest("- - 8.0 2.0 + 1.0 3.0", 2.0);
	}
	
	/**
	 * Tests that multiple consecutive operations works.
	 */
	@Test
	public void test06ConsecutiveOperations() {
		runTest("/ + - + 5.0 3.0 2.0 6.0 2.0", 6.0);
	}

	/**
	 * Tests that more than two initial numbers works.
	 */
	@Test
	public void test07ManyNumbersToStart() {
		runTest("+ 3 * 2 + 5 4", 21.0);
	}

	/**
	 * Tests evaluating a long expression.
	 */
	@Test
	public void test08LongExpression() {
		runTest("+ - + - + 5 * 7 4 * * 3 2 4 5 1 * 6 5", 43.0);
	}
	
	/**
	 * Tests an expression whose value is negative.
	 */
	@Test
	public void test09NegativeAnswer() {
		runTest("* - 5 7 3", -6.0);
	}
	
	/**
	 * Tests an empty expression.
	 */
	@Test
	public void test10EmptyInput() {
		runBadTest("", NO_INPUT);
	}
	
	/**
	 * Tests an expression without enough numbers.
	 */
	@Test
	public void test11Underflow() {
		runBadTest("- + 5 7", INSUFFICIENT_OPERANDS);
	}
	
	/**
	 * Tests an expression involving division by 0.
	 */
	@Test
	public void test12Infinity() {
		runBadTest("/ 7 0", INFINITY);
	}

	/**
	 * Tests an expression with too few operands.
	 */
	@Test
	public void test13NotEnoughOperands() {
		runBadTest("+ 5 3 8", OVERFULL_STACK);
	}
	
	/**
	 * Tests an expression that will not be completely read.
	 */
	@Test
	public void test14TooMuchInput() {
		runBadTest("+ 5 3 a", MORE_INPUT);
	}
	
	/**
	 * Tests input with just a number.
	 */
	@Test
	public void test15JustANumber() {
		runTest("7.5", 7.5);
	}
	
	/**
	 * Tests input with a left parenthesis.
	 */
	@Test
	public void test16LeftParenthesis() {
		runBadTest("- (+ 5 3) 4", LEFT_PAREN);
	}
	
	/**
	 * Tests input with a right parenthesis.
	 */
	@Test
	public void test17RightParenthesis() {
		runBadTest(")))4(((", RIGHT_PAREN);
	}
	
}
