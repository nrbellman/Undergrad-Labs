/**
 * A number of exercises to help learn recursion.
 * @author Nicholas
 *
 */
public class Recursion {

	/**
	 * Generates a pattern of numbers.
	 * @param number The number to base the pattern after.
	 * @return The complete pattern.
	 */
	public static String pattern(int number) {

		String result = "";

		if (number <= 0) {

			result = "Argument must be >= 1.";

		}
		else {

			result = rPattern(number, result);

		}

		return result;

	}

	/**
	 * Helper method for the public pattern method.
	 * @param number The number to base a sub-pattern after.
	 * @param result A String to store the pattern.
	 * @return A sub-pattern of numbers.
	 */
	private static String rPattern(int number, String result) {

		if(number == 0) {

			result += "";

		}
		else {

			if (number < 6) {
				result = rPattern(number - 1, result) + " " + number + rPattern(number - 1, result);
			}
			else {
				result = rPattern(number - 1, result) + " " + number + "\n" + rPattern(number - 1, result);
			}

		}

		return result;

	}

	/**
	 * Draws a symmetrical hourglass pattern of a given size.
	 * @param size The size of the top and bottom of the hourglass.
	 * @return The hourglass pattern.
	 */
	public static String hourglass(int size) {

		String result = "";

		if (size <= 0) {

			result = "Argument must be >= 1.";

		}
		else {

			result = rHourglass(size, 0);

		}

		return result;

	}

	/**
	 * A recursive helper method that combines the lines of the hourglass pattern.
	 * @param size The size of the top and bottom of the hourglass.
	 * @param spaces The number of spaces of each subsequent line.
	 * @return A partial hourglass pattern.
	 */
	private static String rHourglass(int size, int spaces) {

		String result;

		if (size == 0) {

			result = "";

		}
		else {

			result = hgHelper(" ", spaces) + hgHelper("* ", size) + "\n" + rHourglass(size - 1, spaces + 1) + 
					hgHelper(" ", spaces) + hgHelper("* ", size) + "\n";

		}

		return result;

	}

	/**
	 * Creates a series of identical symbols of a given size.
	 * @param symbol The symbol to serialize.
	 * @param copies The number of copies of the given symbol.
	 * @return A line of symbols.
	 */
	private static String hgHelper(String symbol, int copies) {

		String result;

		if (copies == 0) {

			result = "";

		}
		else if (copies == 1) {

			result = symbol;

		}
		else {

			result = symbol + hgHelper(symbol, copies - 1);

		}

		return result;

	}

	/**
	 * Inserts commas into large numbers.
	 * @param number The number to divide.
	 * @return A String containing the comma-ized number.
	 */
	public static String commas(long number) {

		String result = "";
		result = rCommas(number, number / 1000);
		return result;

	}

	/**
	 * Separates large numbers by 3 places, and inserts a comma between them.
	 * @param number The number to split.
	 * @param places The place of the number.
	 * @return A partial separation of places.
	 */
	private static String rCommas(long number, long places) {

		String result = "";

		if (number < 1000 && number > -1000) {

			result += number;

		}
		else{

			result += rCommas(number / 1000, places / 1000) + "," + zerosFix(number % 1000);

		}

		return result;

	}

	/**
	 * Helper method to insert missing leading zeros of large numbers.
	 * @param number The number to fix.
	 * @return The fixed number.
	 */
	private static String zerosFix (long number) {
		String result = "";
		if(number < 10 && number > -10) {
			result += "00" + Math.abs(number);
		}
		else if (number < 100 && number > -100) {
			result += "0" + Math.abs(number);
		}
		else {
			result += Math.abs(number);
		}
		return result;
	}
}
