import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

public class TestPrefixEvaluator {

	@Test
	public void simple() {
		System.out.println("==simple==");
		String[][] problem = {
				{ "+ 3 4", "7.0" },
				{ "* 3 4", "12.0" },
				{ "/ 32 8", "4.0" },
				{ "- 17 3", "14.0" },
				{ "- 0 42", "-42.0" },
				{ "/ + - 23 10 2 3", "5.0" },
				{ "+ / * 4 - 0 8 8 8", "4.0" },
				{ "+ 1 * + 3 7 - * 9 5 * 8 5", "51.0" } };

		for (String[] item : problem) {
			assertEquals(item[0], item[1], PrefixEvaluator.evaluate(item[0]));
		}
	}

	@Test
	public void arithmeticExceptions() {
		System.out.println("==arithmeticExceptions==");
		String[] problem = { "/ 5 0", "/ 10 * 0 5" };
		final String infinityMessage = "Infinity";

		try {
			for (String item : problem) {
				assertEquals(item, infinityMessage, PrefixEvaluator.evaluate(item));
			}
		} catch (ArithmeticException e) {
			assertTrue("Should have caught arithmetic exception - divide by zero", false);
		}

	}

	@Test
	public void notEnough() {
		System.out.println("==notEnough==");
		String answer;
		final String underflowMessage = "ot enough";

		try {
			answer = PrefixEvaluator.evaluate("- 4");
			assertTrue(answer.contains(underflowMessage));
		} catch (NoSuchElementException e) {
			assertTrue("#1: Should have detected not enough input", false);
		}

		try {
			answer = PrefixEvaluator.evaluate("+ + + + 3 4 5 6 ");
			assertTrue(answer.contains(underflowMessage));
		} catch (NoSuchElementException e) {
			assertTrue("#2: Should have caught not enough input", false);
		}
	}

	@Test
	public void tooMuchInput() {
		System.out.println("==tooMuchOnStack==");
		String answer;

		answer = PrefixEvaluator.evaluate("* 5 6 7");
		assertFalse("7 remains unevaluated so answer is not valid", answer.equals("42.0"));

		answer = PrefixEvaluator.evaluate("5 6");
		assertFalse("6 remains unevaluated so answer is not valid", answer.equals("6.0"));

		answer = PrefixEvaluator.evaluate("42");
		assertTrue("42 by itself is okay", answer.equals("42.0"));
	}

	@Test
	public void nothing() {
		System.out.println("==nothing==");
		String answer;

		answer = PrefixEvaluator.evaluate("");
		assertTrue("Empty string", answer.contains("No input"));

		answer = PrefixEvaluator.evaluate(" ");
		assertTrue("Only space", answer.contains("No input"));

		answer = PrefixEvaluator.evaluate("            ");
		assertTrue("Only spaces", answer.contains("No input"));

		answer = PrefixEvaluator.evaluate("\t");
		assertTrue("Only tab", answer.contains("No input"));

		answer = PrefixEvaluator.evaluate("#");
		assertTrue("Only junk character(s)", answer.contains("No input"));
	}

	@Test
	public void badNumbers() {
		System.out.println("==badNumbers==");
		String[] problem = { "35.2.3", "16.3x", "4x5" };
		final String notUsedMessage = "not all input used";

		try {
			for (String item : problem) {
				String answer = PrefixEvaluator.evaluate(item);
				assertTrue(answer.contains(notUsedMessage));
			}
		} catch (NoSuchElementException e) {
			assertTrue("Should have caught NoSuchElementException from Token", false);
		}
	}

	@Test
	public void badInput() {
		System.out.println("==badInput==");
		String[] problem = { "3 5 a", "* 4 5 13.3.3   ", "12 % 5" };

		final String extraMessage = "but not all input";

		try {
			for (String item : problem) {
				String answer = PrefixEvaluator.evaluate(item);
				assertTrue(answer.contains(extraMessage));
			}
		} catch (NoSuchElementException e) {
			assertTrue("Should have caught NoSuchElementException from Token", false);
		}
	}

	@Test
	public void leftoverInput() {
		System.out.println("==leftoverInput==");
		String[] problem = { "+ 3 5 a", "5 ? 3", "2 # 2 " };

		final String extraMessage = "not all input used";

		try {
			for (String item : problem) {
				String answer = PrefixEvaluator.evaluate(item);
				assertTrue(answer.contains(extraMessage));
			}
		} catch (NoSuchElementException e) {
			assertTrue("Should have caught NoSuchElementException from Token", false);
		}
	}

	@Test
	public void parentheses() {
		System.out.println("==parentheses==");
		String[] problem = { "(", ")" };

		final String noMeaningMessage = "has no meaning here";

		for (String item : problem) {
			String answer = PrefixEvaluator.evaluate(item);
			assertTrue(answer.contains(noMeaningMessage));
		}
	}

}
