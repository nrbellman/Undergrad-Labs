
/**
 * Practice for counting using loops and variables.
 * 
 * @author Nicholas Bellman
 *
 */
public class CountingPractice {

	/**
	 * Displays the outputs of the makeChange method, the printMultiplesOf3UpTo30 method, and the printFirst10MultiplesOf3 method.
	 */
	public static void main(String[] args) {

		makeChange();

		printMultiplesOf3UpTo30();

		printFirst10MultiplesOf3();

	}

	/**
	 * Calculates and displays the amount of change needed to be distributed from a certain given value.
	 */
	public static void makeChange() {

		int totalChangeNeeded = 273;

		// Value of each coin.
		int dollarValue = 100;
		int quarterValue = 25;
		int dimeValue = 10;
		int nickelValue = 5;
		int pennyValue = 1;	

		// Amount of each coin.
		int dollarCount;
		int quarterCount;
		int dimeCount;
		int nickelCount;
		int pennyCount;

		// Remaining change after calculation.
		int changeStillNeeded;

		System.out.println("Change for " + totalChangeNeeded + " cents:");

		// Dollar calculation.
		dollarCount = totalChangeNeeded / dollarValue;
		System.out.println("\t" + dollarCount + " dollars =\t" + (dollarCount * dollarValue));
		changeStillNeeded = totalChangeNeeded % dollarValue;

		// Quarter calculation.
		quarterCount = changeStillNeeded / quarterValue;
		System.out.println("\t" + quarterCount + " quarters =\t" + (quarterCount * quarterValue));
		changeStillNeeded %= quarterValue;

		// Dime calculation.
		dimeCount = changeStillNeeded / dimeValue;
		System.out.println("\t" + dimeCount + " dimes =\t" + (dimeCount * dimeValue));
		changeStillNeeded %= dimeValue;

		// Nickel calculation.
		nickelCount = changeStillNeeded / nickelValue;
		System.out.println("\t" + nickelCount + " nickels =\t" + (nickelCount * nickelValue));
		changeStillNeeded %= nickelValue;

		// Penny calculation.
		pennyCount = changeStillNeeded / pennyValue;
		System.out.println("\t" + pennyCount + " pennies =\t" + (pennyCount * pennyValue));
		changeStillNeeded %= pennyValue;

	}

	/**
	 * Uses a loop to display the multiples of 3 up to 30.
	 */
	public static void printMultiplesOf3UpTo30() {

		for (int counter = 3; counter <= 30; counter += 3) {

			System.out.print(counter + " ");

		}

		System.out.println("");

	}

	/**
	 * Uses a loop and multiplication to display the first 10 multiples of 3.
	 */
	public static void printFirst10MultiplesOf3() {

		for (int counter = 1; counter <= 10; counter++) {

			System.out.print((counter * 3) + " ");

		}

		System.out.println("");

	}

}
