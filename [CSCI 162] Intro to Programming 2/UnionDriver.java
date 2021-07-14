import java.util.Scanner;


/**
 * A class that tries out the Statistician class's union operator.
 * 
 * @author Beth Katz, Chad Hogg
 */
public class UnionDriver {
	
	/**
	 * Prints a labeled summary of a Statistician.
	 * 
	 * @param statistician A Statistician to be printed.
	 * @param label The label for that Statistician.
	 */
	public static void print(Statistician statistician, String label) {
		System.out.println();
		System.out.print("Stat <<" + label + ">> has ");
		System.out.printf("%d entries totalling %5.3f with mean of %5.3f\n",
				statistician.getLength(), statistician.getSum(), statistician.getMean());
		System.out.printf("Largest value is %5.3f and smallest is %5.3f\n", 
				statistician.getLargest(), statistician.getSmallest());
	}

	/**
	 * Obtains several values from Scanner and adds them to a Statistician.
	 * 
	 * @param scan A connection to the keyboard.
	 * @param statistician A Statistician to be filled.
	 * @param count The number of values to obtain from user.
	 */
	public static void fillStat(Scanner scan, Statistician statistician, int count) {
		System.out.print("Enter " + count + " values to add: ");
		for(int counter = 1; counter <= count; counter++) {
			double value = scan.nextDouble();
			statistician.insert(value);
		}
	}

	/**
	 * Builds two Statisticians, fills them with data from user, and 
	 *   unions them to create a new Statistician.
	 * Also tests clone method.
	 *
	 * @param scan A connection to the keyboard.
	 */
	public static void run (Scanner scan) {
		Statistician first = new Statistician();
		Statistician second = new Statistician();
		Statistician union, clone;

		fillStat(scan, first, 5);
		fillStat(scan, second, 4);

		print(first, "a");
		print(second, "b");

		union = first.union(second);
		print(union, "c is union(a,b)");

		clone = first.clone();
		print(clone, "d is a clone of a");

		if (clone.equals(first)) {
			System.out.println("a and d should be equivalent");
		} else {
			System.out.println("a and d are not equivalent. Why not?");
		}
		clone.insert(42);
		print(clone, "d after inserting 42");
		print(first, "a after d was changed");

		if (clone.equals(first)) {
			System.out.println("d and a are still equivalent. Why?");
		} else {
			System.out.println("d and a should no longer be equivalent");
		}
	}
}
