import java.util.Scanner;

/**
 * Generates a report for a painting service.
 * 
 * @author nrbellma
 *
 */
public class ReportGenerator {
	
	/**
	 * The area that can be painted from one can of paint, which is 400 square feet.
	 */
	public static final int AREA_PER_CAN = 400;
	
	/**
	 * The cost of a can of paint, which is $43.50.
	 */
	public static final double COST_PER_CAN = 43.50;
	
	/**
	 * The area that an experienced person can paint in an hour, which is 200 square feet.
	 */
	public static final int AREA_PER_HOUR = 200;
	
	/**
	 * What we charge for each hour of labor, which is $50.00.
	 */
	public static final double COST_PER_HOUR = 50.00;
	
	/**
	 * Prompts user for information used to calculate the cost of a painting job.
	 * 
	 * @param args Unknown.
	 */
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Address to paint: ");
		String address = input.nextLine();
		
		String standardAddress = standardizeAddress(address);
		
		System.out.print("Length of building: ");
		double buildingLength = input.nextDouble();
		
		System.out.print("Width of building: ");
		double buildingWidth = input.nextDouble();
		
		System.out.print("Height of building: ");
		double buildingHeight = input.nextDouble();
		
		
		double longArea = 2 * (buildingLength * buildingHeight);
		double shortArea = 2 * (buildingWidth * buildingHeight);
		double totalArea = longArea + shortArea;
		
		double nonPaintableArea = getNonPaintableArea(input);
		
		double paintableArea = totalArea - nonPaintableArea;
		
		
		System.out.println();
		
		printEstimate(standardAddress, paintableArea);
		
		input.close();
		
	}

	/**
	 * Formats an address to be used by the report generator.
	 * 
	 * @param address The unformatted address.
	 * @return The formatted address.
	 */
	public static String standardizeAddress(String address) {
		
		String standardAddress = address.toUpperCase();
		
		standardAddress = standardAddress.replace(" ROAD", " RD");
		standardAddress = standardAddress.replace(" STREET", " ST");
		standardAddress = standardAddress.replace(" LANE", " LN");
		standardAddress = standardAddress.replace(" AVENUE", " AVE");
		standardAddress = standardAddress.replace(" COURT", " CT");
		
		return standardAddress;
		
	}
	
	/**
	 * Calculates the area not to be painted during the paint job.
	 * 
	 * @param input Scanner object used to get information from user.
	 * @return The total area not to be painted.
	 */
	public static double getNonPaintableArea(Scanner input) {
		
		double nonPaintableArea = 0;
		int totalDoorsAndWindows = 0;
		
		System.out.print("Number of doors/windows: ");
		totalDoorsAndWindows = input.nextInt();
		
		for (int counter = 1; counter <= totalDoorsAndWindows; counter++) {
			
			double height = 0;
			double width = 0;
			double tempArea = 0;
			
			System.out.print("Height of door/window " + counter + ": ");
			height = input.nextDouble();
			
			System.out.print("Width of door/window " + counter + ": ");
			width = input.nextDouble();
			
			tempArea = height * width;
			
			nonPaintableArea += tempArea;
			
		}
		
		return nonPaintableArea;
		
	}
	
	/**
	 * Prints an estimate of the costs and time required for the paint job.
	 * 
	 * @param standardAddress The formatted address gotten from the method standardizeAddress.
	 * @param paintableArea The total area to be painted.
	 */
	public static void printEstimate(String standardAddress, double paintableArea) {
		
		double totalCans = paintableArea / AREA_PER_CAN;
		double totalHours = paintableArea / AREA_PER_HOUR;
		double totalCost = (totalCans * COST_PER_CAN) + (totalHours * COST_PER_HOUR);
		
		System.out.println("Painting Estimate for " + standardAddress);
		System.out.println("Total area is " + paintableArea + " square feet.");
		System.out.println("We will need " + totalCans + " cans of paint.");
		System.out.println("It will take us " + totalHours + " hours.");
		System.out.println("Your total cost will be $" + totalCost + ".");
		
	}
	
}
