
import java.util.Random;
import java.util.Scanner;

/**
 * Simulates living creatures in an area. The area is displayed as a 2D array, with each entry representing a creature.
 * 
 * @author Nicholas Bellman
 */
public class Life {

	/** The minimum neighborhood size to support birth. */
	private int birthLow;

	/** The maximum neighborhood size to support birth. */
	private int birthHigh;

	/** The minimum neighborhood size to support existing life. */
	private int liveLow;

	/** The maximum neighborhood size to support existing life. */
	private int liveHigh;

	/** 2D array representing an area with life in it. */
	private boolean[][] matrix;

	/**
	 * Constructs a new Life game with a random initial state determined by the seed.
	 * 
	 * @param seed The random seed to be used.
	 * @param rows The height of the game. Must be positive.
	 * @param columns The width of the game. Must be positive.
	 * @param birthLow The minimum neighborhood size for a new birth. Must be 1-9 inclusive.
	 * @param birthHigh The maximum neighborhood size for a new birth. Must be 1-9 inclusive.
	 * @param liveLow The minimum neighborhood size to continue living. Must be 1-9 inclusive.
	 * @param liveHigh The maximum neighborhood size to continue living. Must be 1-9 inclusive.
	 */
	public Life(long seed, int rows, int columns, int birthLow, int birthHigh, int liveLow, int liveHigh) {

		Random random = new Random(seed);

		if (rows < 1 || columns < 1) {

			throw new IllegalArgumentException("The number of rows and/or columns must be positive.");

		}
		else {

			matrix = new boolean[rows][columns]; 
			matrix = createMatrix(random);

		}



		if (birthLow < 1 || birthLow > 9) {

			throw new IllegalArgumentException("The minimum neighborhood size for birth must be between 1 and 9 "
					+ "(inclusive).");

		}
		else {

			this.birthLow = birthLow;

		}

		if (birthHigh < 1 || birthHigh > 9) {

			throw new IllegalArgumentException("The maximum neighborhood size for birth must be between 1 and 9 "
					+ "(inclusive).");

		}
		else {

			this.birthHigh = birthHigh;

		}

		if (liveLow < 1 || liveLow > 9) {

			throw new IllegalArgumentException("The minimum neighborhood size to continue living must be between 1 and"
					+ " 9 (inclusive).");

		}
		else {

			this.liveLow = liveLow;

		}	

		if(liveHigh < 1 || liveHigh > 9) {

			throw new IllegalArgumentException("The maximum neighborhood size to continue living must be between 1 and"
					+ " 9 (inclusive).");
		}
		else {

			this.liveHigh = liveHigh;

		}

	}

	/**
	 * Updates the matrix, birthing and killing creatures according to the rules given to the constructor.
	 */
	public void update() {

		boolean[][] newMatrix = world();

		for (int rowCounter = 1; rowCounter < matrix.length - 1; rowCounter++) {

			for (int columnCounter = 1; columnCounter < matrix[0].length - 1; columnCounter++) {

				int density = densityCheck(rowCounter, columnCounter);

				if (matrix[rowCounter][columnCounter] == false) {

					if (density >= birthLow && density <= birthHigh) {

						newMatrix[rowCounter][columnCounter] = true;

					}
					else {

						newMatrix[rowCounter][columnCounter] = false;

					}

				}
				else if (matrix[rowCounter][columnCounter] == true) {

					if (density < liveLow || density > liveHigh) {

						newMatrix[rowCounter][columnCounter] = false;

					}
					else {

						newMatrix[rowCounter][columnCounter] = true;

					}

				}

			}

		}

		matrix = newMatrix;

	}

	/**
	 * Gets a copy of the matrix, made so that changes to it will not affect the original.
	 * @return A copy of the matrix.
	 */
	public boolean[][] world(){

		boolean[][] newMatrix = new boolean[matrix.length][matrix[0].length];

		for (int rIndex = 0; rIndex < matrix.length; rIndex++) {

			for (int cIndex = 0; cIndex < matrix[0].length; cIndex++) {

				newMatrix[rIndex][cIndex] = matrix[rIndex][cIndex];

			}

		}

		return newMatrix;

	}

	/**
	 * Prints the matrix, using '#' for life and '-' for non-life, with a space after each cell.
	 */
	public void print() {

		for (int rowCounter = 0; rowCounter < matrix.length ; rowCounter++) {

			for (int columnCounter = 0; columnCounter < matrix[0].length; columnCounter++) {

				if (matrix[rowCounter][columnCounter] == false) {

					System.out.print("- ");

				}
				else {

					System.out.print("# ");

				}

			}

			System.out.println();

		}

		System.out.println();

	}

	/**
	 * Reads input from the user and runs the program.
	 * 
	 * @param args Command-line arguments. Not used.
	 */
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int rows = input.nextInt();
		int columns = input.nextInt();

		long seed = input.nextLong();

		int birthLow = input.nextInt();
		int birthHigh = input.nextInt();

		int liveLow = input.nextInt();
		int liveHigh = input.nextInt();

		Life life = new Life(seed, rows, columns, birthLow, birthHigh, liveLow, liveHigh);

		life.print();

		for (int count = 0; count < 5; count++) {

			life.update();

			life.print();

		}

		input.close();

	}

	/**
	 * Creates a 2D array of booleans of a "height" and "width" given by the user.
	 * 
	 * @param random A Random object used to determine the values, true or false, of the positions of the array.
	 * @return A 2D array of booleans filled with random true or false values.
	 */
	public boolean[][] createMatrix(Random random) {

		for (int rowCounter = 0; rowCounter < matrix.length; rowCounter++) {

			if (rowCounter == 0 || rowCounter == matrix.length - 1) {

				matrix[rowCounter][0] = false;
				matrix[rowCounter][matrix[0].length - 1] = false;

			}
			else {

				for (int columnCounter = 0; columnCounter < matrix[0].length; columnCounter++) {

					if (columnCounter == 0 || columnCounter == matrix[0].length - 1) {

						matrix[0][columnCounter] = false;
						matrix[matrix.length - 1][columnCounter] = false;

					}
					else {

						matrix[rowCounter][columnCounter] = random.nextBoolean();

					}

				}

			}

		}

		return matrix;

	}

	/**
	 * Checks the area density of a point in a 2D array.
	 * 
	 * @param row The row of the point in question.
	 * @param column The column of the point in question.
	 * @return The total number of neighbors the point has.
	 */
	public int densityCheck(int row, int column) {

		int density = 0;

		for (int checkRow = row - 1; checkRow <= row + 1; checkRow++) {

			for (int checkColumn = column - 1; checkColumn <= column + 1; checkColumn++) {

				if (matrix[checkRow][checkColumn] == true) {

					density++;

				}

			}

		}

		return density;

	}

	/**
	 * Creates a copy of a given 2D array, then fills in the copy with updated information based on given parameters.
	 * @param matrix The original 2D array used to make a copy.
	 * @param birthLow The lowest area density of a point that will generate a new "organism".
	 * @param birthHigh The highest area density of a point that will generate a new "organism".
	 * @param liveLow The lowest area density that will allow an "organism" to live.
	 * @param liveHigh The highest area density that will allow an "organism" to live.
	 * @param random Random object used to generate values for the 2D array copy.
	 * @return The copy filled in with new values.
	 */
	/* public boolean[][] updateMatrix(boolean[][] matrix, int birthLow, int birthHigh, int liveLow, int liveHigh, 
			Random random) {

		boolean[][] newMatrix = createMatrix(random);

		for (int rowCounter = 1; rowCounter < matrix.length - 1; rowCounter++) {

			for (int columnCounter = 1; columnCounter < matrix[0].length - 1; columnCounter++) {

				int density = densityCheck(rowCounter, columnCounter);

				if (matrix[rowCounter][columnCounter] == false) {

					if (density >= birthLow && density <= birthHigh) {

						newMatrix[rowCounter][columnCounter] = true;

					}
					else {

						newMatrix[rowCounter][columnCounter] = false;

					}

				}
				else if (matrix[rowCounter][columnCounter] == true) {

					if (density < liveLow || density > liveHigh) {

						newMatrix[rowCounter][columnCounter] = false;

					}
					else {

						newMatrix[rowCounter][columnCounter] = true;

					}

				}

			}

		}

		return newMatrix;

	} */



	/**
	 * Prints the values of the coordinates of a 2D array.
	 * 
	 * @param matrix A 2D array of booleans.
	 */
	/*public static void printMatrix(boolean[][] matrix) {

		for (int rowCounter = 0; rowCounter < matrix.length ; rowCounter++) {

			for (int columnCounter = 0; columnCounter < matrix[0].length; columnCounter++) {

				if (matrix[rowCounter][columnCounter] == false) {

					System.out.print("- ");

				}
				else {

					System.out.print("# ");

				}

			}

			System.out.println();

		}

		System.out.println();

	}*/

}
