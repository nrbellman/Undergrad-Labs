/**
 * 
 * Draws a number of different rectangles and triangles.
 * 
 * @author nrbellma
 *
 */
public class ShapeDrawer {

	/**
	 * Draws specified shapes using specified parameters.
	 * 
	 * @param args I don't know what this does.
	 */
	public static void main(String[] args) {

		drawRectangle(4, 10, '1');
		System.out.println();
		drawRectangle(7, 5, '2');
		System.out.println();
		drawLowerLeftTriangle(3, '3');
		System.out.println();
		drawUpperLeftTriangle(4, '4');
		System.out.println();
		drawLowerRightTriangle(5, '5');
		System.out.println();
		drawUpperRightTriangle(6, '6');

	}

	/**
	 * Draws a rectangle.
	 * 
	 * @param height Height of the rectangle.
	 * @param width Width of the rectangle.
	 * @param symbol Character the rectangle is made up of.
	 */
	public static void drawRectangle(int height, int width, char symbol) {

		for (int row = 1; row <= height; row++) {

			for (int column = 1; column <= width; column++) {

				System.out.print(symbol);

			}

			System.out.println();

		}

	}

	/**
	 * Draws a right isosceles triangle with the right angle in the lower left.
	 * 
	 * @param length Length of the equivalent sides.
	 * @param symbol Character the triangle is made up of.
	 */
	public static void drawLowerLeftTriangle(int length, char symbol) {

		for (int row = 1; row <= length; row++) {

			for (int column = 1; column <= row; column++) {

				System.out.print(symbol);

			}

			System.out.println();

		}

	}

	/**
	 * Draws a right isosceles triangle with the right angle in the upper left.
	 * 
	 * @param length Length of the equivalent sides.
	 * @param symbol Character the triangle is made up of.
	 */
	public static void drawUpperLeftTriangle(int length, char symbol) {

		for (int row = 1; row <= length; row++) {

			for (int column = length; column >= row; column--) {

				System.out.print(symbol);

			}

			System.out.println();

		}

	}

	/**
	 * Draws a right isosceles triangle with the right angle in the lower right.
	 * 
	 * @param length Length of the equivalent sides.
	 * @param symbol Character the triangle is made up of.
	 */
	public static void drawLowerRightTriangle(int length, char symbol) {

		for (int row = 1; row <= length; row++) {

			for (int spaces = 1; spaces <= length - row; spaces++) {

				System.out.print(" ");

			}

			for (int characters = 1; characters <= row; characters++) {

				System.out.print(symbol);

			}

			System.out.println();

		}

	}

	/**
	 * Draws a right isosceles triangle with the right angle in the upper right.
	 * 
	 * @param length Length of the equivalent sides.
	 * @param symbol Character the triangle is made up of.
	 */
	public static void drawUpperRightTriangle(int length, char symbol) {

		for (int row = 0; row < length; row++) {

			for (int spaces = 1; spaces <= row; spaces++) {

				System.out.print(" ");

			}

			for (int characters = 1; characters <= length - row; characters++) {

				System.out.print(symbol);

			}

			System.out.println();

		}

	}

}
