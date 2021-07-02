
/**
 * Class containing information pertaining to Vehicle objects.
 * 
 * @author Nicholas Bellman
 */
public class Vehicle {

	/**The unique identification number for the Vehicle.*/
	private String vehicleIdentificationNumber;
	
	/**The maximum capacity of the fuel tank of the vehicle in gallons.*/
	private double maximumFuelLevel;

	/**The current amount of fuel in the fuel tank of the vehicle in gallons.*/
	private double currentFuelLevel;

	/**The reading on the trip odometer of the vehicle in miles.*/
	private double tripOdometerReading;

	/**The maximum fuel efficiency of the vehicle in miles per gallons.*/
	private double maximumFuelEfficiency;

	/**
	 * Constructs a Vehicle object.
	 * 
	 * @param theVehicleIdentificationNumber The vehicle identification number read in when the object is constructed.
	 * @param theMaximumFuelLevel The maximum fuel level in gallons read in when the object is constructed.
	 * @param theMaximumFuelEfficiency The maximum fuel efficiency read in when the object is constructed.
	 */
	public Vehicle(String theVehicleIdentificationNumber, double theMaximumFuelLevel, 
			double theMaximumFuelEfficiency) {

		if(theMaximumFuelLevel <= 0) {

			throw new IllegalArgumentException("The maximum fuel level must be greater than 0.");

		}

		if(theMaximumFuelEfficiency <= 0) {

			throw new IllegalArgumentException("The maximum fuel efficiency must be greater than 0.");

		}

		vehicleIdentificationNumber = theVehicleIdentificationNumber;

		maximumFuelLevel = theMaximumFuelLevel;

		currentFuelLevel = theMaximumFuelLevel;

		tripOdometerReading = 0.0;

		maximumFuelEfficiency = theMaximumFuelEfficiency;

	}
	
	/**
	 * Accessor for the vehicleIdentificationNumber field.
	 * 
	 * @return The vehicle identification number of this vehicle object.
	 */
	public String getVehicleIdentificationNumber() {
		
		return vehicleIdentificationNumber;
		
	}
	
	/**
	 * Accessor for the currentFuelLevel field.
	 * 
	 * @return The current fuel level for this object.
	 */
	public double getCurrentFuelLevel() {
		
		return currentFuelLevel;
		
	}
	
	/**
	 * Accessor for the tripOdometerReading field.
	 * 
	 * @return The trip odometer reading for this object.
	 */
	public double getTripOdometerReading() {
		
		return tripOdometerReading;
		
	}
	
	/**
	 * Formats information about this object as a String.
	 * 
	 * @return The value of the formatted String.
	 */
	public String toString() {
		
		return String.format("Vehicle %s\nFuel level is %.1f / %.1f gallons.\nThe odometer says %.1f miles.\n" 
		+ "Maximum fuel efficiency is %.1f miles per gallon.\n", vehicleIdentificationNumber, currentFuelLevel,
		maximumFuelLevel, tripOdometerReading, maximumFuelEfficiency);
		
	}
	
	/**
	 * Calculates the effective fuel efficiency of this Vehicle object.
	 * 
	 * @param speed The speed of the vehicle in miles per hour.
	 * @return The effective fuel efficiency given a certain speed.
	 */
	public double getEffectiveFuelEfficiency(double speed) {
		
		double effectiveFuelEfficiency = 0;
		
		if (speed < 25 || speed >= 85) {
			
			effectiveFuelEfficiency = maximumFuelEfficiency * 0.50;
		
		}
		else if (speed < 45 || speed >= 65) {
		
			effectiveFuelEfficiency = maximumFuelEfficiency * 0.80;
		
		}
		else {
		
			effectiveFuelEfficiency = maximumFuelEfficiency;
		
		}
		
		return effectiveFuelEfficiency;
		
	}
	
	/**
	 * Calculates the distance driven and fuel consumed for a journey.
	 * 
	 * @param timeDriven The amount of time driven in minutes.
	 * @param speed The speed of the vehicle in miles per hour.
	 */
	public void drive(double timeDriven, double speed) {
		
		if (timeDriven <= 0) {
			
			throw new IllegalArgumentException("The time driven must be greater than 0.");
		
		}
		
		if (speed <= 0) {
		
			throw new IllegalArgumentException("The speed must be greater than 0.");
			
		}
		
		double distanceDriven = (timeDriven / 60) * speed;
		
		double effectiveFuelEfficiency = getEffectiveFuelEfficiency(speed);
		
		double fuelUsed = distanceDriven / effectiveFuelEfficiency;
		
		if (fuelUsed > currentFuelLevel) {
			
			throw new IllegalArgumentException("Not enough fuel to drive that distance.");
		
		}
		else {
			
			currentFuelLevel -= fuelUsed;
			
			tripOdometerReading += distanceDriven;
			
		}
		
	}
	
	/**
	 * Adds fuel to the fuel tank of this object.
	 * 
	 * @param fuelAdded the amount of fuel to be added in gallons.
	 */
	public void refuel(double fuelAdded) {
		
		if (fuelAdded < 0) {
			
			throw new IllegalArgumentException("Fuel to be added must be a positive number.");
			
		}
		
		if (fuelAdded + currentFuelLevel > maximumFuelLevel) {
			
			throw new IllegalArgumentException("Fuel to be added cannot cause the current fuel level to exceed "
					+ "the maximum.");
			
		}
		
		currentFuelLevel += fuelAdded;
		
	}
	
	/**
	 * Resets the trip odometer of this object.
	 */
	public void resetTripOdometer() {
		
		tripOdometerReading = 0.0;
		
	}
	
}