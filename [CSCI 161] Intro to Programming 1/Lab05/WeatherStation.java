import java.util.Scanner;

/**
 * Calculates and displays weather related information such as wind chill, forecast, and a list of possible activities
 * depending on user input.
 * 
 * @author Nicholas Bellman
 *
 */
public class WeatherStation {

	/**
	 * Asks user to input temperature, humidity, and wind speed, and asks them to choose an option which
	 * displays information depending on the user input.
	 * 
	 * @param args Unknown
	 */
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		
		System.out.print("Temperature: ");
		double tempFahr = input.nextDouble();
		
		System.out.print("Humidity: ");
		double humidity = input.nextDouble();
		
		System.out.print("Wind speed: ");
		double windSpeed = input.nextDouble();
		
		
		System.out.print("\nwindchill\n"
				+ "activities\n"
				+ "forecast\n"
				+ "Choose one: ");
		String menuChoice = input.next();
		
		
		input.close();
		
		if (menuChoice.equals("windchill")) {
			
			printWindChill(tempFahr, windSpeed);
			
		} else if (menuChoice.equals("activities")) {
			
			printPossibleActivities(tempFahr);
			
		} else if (menuChoice.equals("forecast")) {
			
			printForecast(tempFahr, humidity);
			
		} else {
			
			System.out.println("That is not an option.");
			
		}
		
	}

	/**
	 * Prints the wind chill value of a temperature at a certain wind speed.
	 * 
	 * @param tempFahr Current temperature in degrees Fahrenheit.
	 * @param windSpeed Current wind speed in miles per hour.
	 */
	public static void printWindChill(double tempFahr, double windSpeed) {
		
		double windChillTemp = 0;
		
		if (windSpeed < 20) {
			
			windChillTemp = tempFahr;
			
		} else if (windSpeed >= 20 && windSpeed < 40) {
			
			windChillTemp = tempFahr - 10;
			
		} else {
			
			windChillTemp = tempFahr - 25;
			
		}
		
		System.out.println(windChillTemp);
		
	}
	
	/**
	 * Prints a list of possible activities given a certain temperature.
	 * 
	 * @param tempFahr Current temperature in degrees Fahrenheit.
	 */
	public static void printPossibleActivities(double tempFahr) {
		
		if (tempFahr > 40) {
			
			System.out.println("boating");
			
		}
		
		if (tempFahr >= 30 && tempFahr <= 100) {
			
			System.out.println("camping");
			
		}
		
		System.out.println("hiking");
		
		if (tempFahr < 20) {
			
			System.out.println("skating");
			
		}
		
		if (tempFahr > 80) {
			
			System.out.println("swimming");
			
		}
		
	}
	
	/**
	 * Prints a forecast based on the current temperature and humidity.
	 * 
	 * @param tempFahr Current temperature in degrees Fahrenheit.
	 * @param humidity Current relative humidity.
	 */
	public static void printForecast(double tempFahr, double humidity) {
		
		if (humidity < 50) {
			
			System.out.println("dry");
			
		} else if (humidity >= 50 && humidity <= 80) {
			
			if (tempFahr < 32) {
				
				System.out.println("foggy");
				
			} else {
				
				System.out.println("muggy");
				
			}
			
		} else {
			
			if (tempFahr < 32) {
				
				System.out.println("snowing");
				
			} else {
				
				System.out.println("raining");
				
			}
			
		}
		
	}
	
}
