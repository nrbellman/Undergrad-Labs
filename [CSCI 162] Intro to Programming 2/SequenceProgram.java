import java.util.Scanner;

/**
 * A program that demonstrates basic use of a sequence.
 * 
 * @author Chad Hogg
 */
public class SequenceProgram {

	/**
	 * Runs the program.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		Sequence<Double> seq = new LinkedSequence<Double>();
		Scanner console = new Scanner(System.in);
		printMenu();
		String choice = "x";
		while(!choice.equals("q")) {
			System.out.println();
			System.out.println(seq.toString());
			System.out.print("Command > ");
			choice = console.next();
			if(choice.equals("s")) {
				seq.start();
			}
			else if(choice.equals("a")) {
				if(seq.isCurrent()) {
					seq.advance();
				}
				else {
					System.out.println("Nice try! You know I'm not supposed to advance a sequence without a current element.");
				}
			}
			else if(choice.equals("+")) {
				seq.addAfter(safeReadNumber(console));
			}
			else if(choice.equals("-")) {
				seq.addBefore(safeReadNumber(console));
			}
			else if(choice.equals("r")) {
				if(seq.isCurrent()) {
					seq.removeCurrent();
				}
				else {
					System.out.println("Nice try! You know I'm not supposed to remove without a current element.");
				}
			}
			else if(choice.equals("t")) {
				System.out.println(total(seq));
			}
			else if(choice.equals("?")) {
				printMenu();
			}
			else if(choice.equals("q")) {
				System.out.println("Goodbye!");
			}
			else {
				System.out.println("Unknown command.");
			}
		}
	}
	
	/**
	 * Gets a number from the user, clearing any non-number items they might enter.
	 * 
	 * @param console A connection to the keyboard.
	 * @return A number typed by the user.
	 */
	public static double safeReadNumber(Scanner console) {
		System.out.print("Number: ");
		while(!console.hasNextDouble()) {
			console.next();
		}
		return console.nextDouble();
	}
	
	/**
	 * Prints a menu of options.
	 */
	public static void printMenu() {
		System.out.println("s: start current from beginning of sequence");
		System.out.println("a: advance current");
		System.out.println("+: add after current");
		System.out.println("-: add before current");
		System.out.println("r: remove current");
		System.out.println("t: print total");
		System.out.println("?: print these instructions");
		System.out.println("q: quit");
	}
	
	/**
	 * Calculates the total of all numbers in a sequence.
	 * 
	 * @param seq The sequence.
	 * @return The total of the numbers in it.
	 */
	public static double total(Sequence<Double> seq) {
		// We need to make a copy because we need to move the current element, and shouldn't change the original.
		Sequence<Double> copy = seq.clone();
		double sum = 0.0;
		// Isn't this a cool for loop?
		for(copy.start(); copy.isCurrent(); copy.advance()) {
			sum += copy.getCurrent();
		}
		return sum;
	}
}
