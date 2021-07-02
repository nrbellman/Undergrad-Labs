import java.util.Scanner;

/**
 * A program that simulates two vehicles.
 * Part of Lab 10 for CSCI161.
 * 
 * Note that this program does NO input validation, so that you can test all features of the Vehicle class.
 * A better designed program would do its own input validation, so that no exceptions could possibly be thrown.
 * 
 * @author Chad Hogg
 */
public class CarProgram {

	/**
	 * Runs the program.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {
	
		Scanner console = new Scanner(System.in);
		
		Vehicle firstVehicle = readVehicle(console, "first");
		Vehicle secondVehicle = readVehicle(console, "second");
		
		String choice = getMenuChoice(console);
		while(!choice.equals("9")) {
			
			if(choice.equals("1")) {
			
				System.out.println(firstVehicle.toString());
			
			}
			else if(choice.equals("2")) {
			
				System.out.println(secondVehicle.toString());
			
			}
			else if(choice.equals("3")) {
				
				driveVehicle(console, firstVehicle);
				
			}
			else if(choice.equals("4")) {
				
				driveVehicle(console, secondVehicle);
				
			}
			else if(choice.equals("5")) {
				
				refuelVehicle(console, firstVehicle);
				
			}
			else if(choice.equals("6")) {
				
				refuelVehicle(console, secondVehicle);
				
			}
			else if(choice.equals("7")) {
				
				firstVehicle.resetTripOdometer();
				
			}
			else if(choice.equals("8")) {
				
				secondVehicle.resetTripOdometer();
				
			}
			else {
				
				System.out.println("Sorry, that doesn't seem to be a menu option.");
				
			}
		
			choice = getMenuChoice(console);
		
		}
	
	}
	
	/**
	 * Reads information about a vehicle and creates it.
	 * 
	 * @param console A Scanner connected to the keyboard.
	 * @param description A description of the vehicle.
	 * @return A Vehicle object.
	 */
	public static Vehicle readVehicle(Scanner console, String description) {
		
		System.out.print("VIN for " + description + " vehicle: ");
		String vin = console.next();
		
		System.out.print("Fuel capacity for " + description + " vehicle: ");
		double capacity = console.nextDouble();
		
		System.out.print("Maximum fuel efficiency for " + description + " vehicle: ");
		double efficiency = console.nextDouble();
		
		return new Vehicle(vin, capacity, efficiency);
	
	}
	
	/**
	 * Prints a menu of options and asks the user to choose one.
	 * 
	 * @param console A Scanner connected to the keyboard.
	 * @return The user's choice, which will hopefully be a digit 1-9.
	 */
	public static String getMenuChoice(Scanner console) {
		
		System.out.println();
		System.out.println("1: Print information about first vehicle");
		System.out.println("2: Print information about second vehicle");
		System.out.println("3: Drive first vehicle");
		System.out.println("4: Drive second vehicle");
		System.out.println("5: Refuel first vehicle");
		System.out.println("6: Refuel second vehicle");
		System.out.println("7: Reset first vehicle's trip odometer");
		System.out.println("8: Reset second vehicle's trip odometer");
		System.out.println("9: Quit");
		System.out.print("Your choice: ");
		
		return console.next();
	
	}
	
	/**
	 * Drives a Vehicle.
	 * 
	 * @param console A Scanner connected to the keyboard.
	 * @param car The Vehicle to drive.
	 */
	public static void driveVehicle(Scanner console, Vehicle car) {
		
		System.out.print("How many minutes are you driving for: ");
		double minutes = console.nextDouble();
		
		System.out.print("How fast are you driving: ");
		double speed = console.nextDouble();
		
		car.drive(minutes, speed);
		
	}
	
	/**
	 * Refuels a Vehicle.
	 * 
	 * @param console A Scanner connected to the keyboard.
	 * @param car The Vehicle to refuel.
	 */
	public static void refuelVehicle(Scanner console, Vehicle car) {
		
		System.out.print("How much fuel to add: ");
		double amount = console.nextDouble();
		
		car.refuel(amount);
	
	}

}
