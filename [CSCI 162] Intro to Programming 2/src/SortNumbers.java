import java.util.Scanner;

/**
 * A program for experimenting with SortedList.  It allows the user to type
 *   in numbers, adds them to a sorted list, and then prints that sorted list.
 * 
 * @author Beth Katz, Chad Hogg
 */
public class SortNumbers {
	
	/**
	 * Runs the program.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		SortedList myList = new SortedList();
		double num = 0;

		System.out.println("Enter numeric values." 
				+ " Finish with return and control-D.");
		System.out.print("Next? ");
		while(console.hasNextDouble()) {
			num = console.nextDouble();
			myList.insert(num);
			System.out.print("Next? ");
		}
		System.out.println();
		System.out.println(myList.toString());
		console.close();
	}
}
