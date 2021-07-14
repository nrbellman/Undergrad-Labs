import java.util.Scanner;


/**
 * A program for using the Statistician class.
 * 
 * @author Beth Katz, Chad Hogg
 */
public class ConsoleMenuUI {

	/**
	 * Prints a summary of a Statistician's info.
	 * 
	 * @param statistician A Statistician to be printed.
	 */
	public static void print(Statistician statistician) {
		System.out.printf("%d entries total %5.3f with mean of %5.3f\n",
				statistician.getLength(), statistician.getSum(), statistician.getMean());
		System.out.printf("Largest value is %5.3f and smallest is %5.3f\n",
				statistician.getLargest(), statistician.getSmallest());
	}

	/**
	 * Obtains a value from the user and adds it to a Statistician.
	 * 
	 * @param statistician The Statistician where the value will be inserted.
	 * @param console A connection to the keyboard.
	 */
	public static void addValue(Statistician statistician, Scanner console) {
		System.out.print("Value to add: ");
		double value = console.nextDouble();
		System.out.printf("%5.3f was inserted.", value);
		statistician.insert(value);
	}

	/**
	 * Prints the menu choices.
	 */
	public static void printMenu() {
		System.out.println("Menu choices are:");
		System.out.println("q: quit the testing program");
		System.out.println("?: print this menu");
		System.out.println("+: add a value to the statistician");
		System.out.println("p: print a summary of the statistician values");
		System.out.println("r: reset the statistician");
		System.out.println("u: exercise the union operator");
	}

	/**
	 * Reads commands from the user and handles them until quitting.
	 * 
	 * @param statistician A Statistician to manipulate.
	 * @param console A connection to the keyboard.
	 */
	public static void doMenu(Statistician statistician, Scanner console) {
		System.out.println("Enter command:");
		String theCommand = console.next();
		while(!(theCommand.equals("q"))) {
			if(theCommand.length() > 0) {
				if(theCommand.length() == 1) {
					char command = theCommand.charAt(0);
					if(command == '?') {
						printMenu();
					}
					else if(command == '+') {
						addValue(statistician, console);
					}
					else if(command == 'p') {
						print(statistician);
					}
					else if (command == 'r') {
						statistician.reset();
					}
					else if (command == 'u') {
						UnionDriver.run(console);
					}
					else {
						System.out.println("Unknown command ignored.");
					}
				}
				else {
					System.out.println("Only one letter commands allowed. "
							+ "Your longer one was ignored.");
				}
				System.out.println("Enter command:");
				theCommand = console.next();
			}
		}
		System.out.println("Thank you for using the Statistician.");
	}


	/**
	 * Runs the program.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		Statistician statistician = new Statistician();
		printMenu();
		doMenu(statistician, console);
		console.close();
	}

}
