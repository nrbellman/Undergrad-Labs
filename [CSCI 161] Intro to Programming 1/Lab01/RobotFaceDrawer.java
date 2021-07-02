/**
 * Draws a sideways robot face.
 * 
 * @author Nicholas Bellman
 *
 */
public class RobotFaceDrawer 
{

	/**
	 * Assembles the separate parts of the robot's face.
	 */
	public static void main(String[] args) 
	{
		
		drawTriangleUp();
		drawDiamond();
		
		System.out.print("\n");
		drawCheckedPattern();
		System.out.print("\n");
		
		drawDiamond();
		drawTriangleDown();
		
	}
	
	/**
	 * Draws a upward pointing triangle.
	 */
	public static void drawTriangleUp() 
	{
		
		System.out.println("     *");
		System.out.println("    / \\");
		System.out.println("   /   \\");
		System.out.println("  /     \\");
		System.out.println(" /       \\");
		System.out.println("/         \\");
		
	}
	
	/**
	 * Draws a downward pointing triangle.
	 */
	public static void drawTriangleDown() 
	{
		
		System.out.println("\\         /");
		System.out.println(" \\       /");
		System.out.println("  \\     /");
		System.out.println("   \\   /");
		System.out.println("    \\ /");
		System.out.println("     *");
		
	}
	
	/**
	 * Draws a diamond using an upward triangle and a downwards triangle.
	 */
	public static void drawDiamond() 
	{
		
		drawTriangleUp();
		drawTriangleDown();
		
	}
	
	/**
	 * Draws a checked line of hashes with the first character being a hash.
	 */
	public static void drawCheckedLineStartsFilled() 
	{
		
		System.out.println("# # # # # # # ");
		
		// By writing this method, it allows us to make any changes to drawCheckedLineStartsEmpty, 
		// and by extension drawCheckedPattern in only one place.
		
	}
	
	/**
	 * Draws a checked line of hashes with the first character being a space.
	 */
	public static void drawCheckedLineStartsEmpty() 
	{
		
		System.out.print(" ");
		drawCheckedLineStartsFilled();
		
	}
	
	/**
	 * Draws a checked pattern using alternating checked lines.
	 */
	public static void drawCheckedPattern() 
	{
		
		drawCheckedLineStartsFilled();
		drawCheckedLineStartsEmpty();
		drawCheckedLineStartsFilled();
		drawCheckedLineStartsEmpty();
		drawCheckedLineStartsFilled();
		drawCheckedLineStartsEmpty();
		drawCheckedLineStartsFilled();
		drawCheckedLineStartsEmpty();
		drawCheckedLineStartsFilled();
		
	}
	
}

