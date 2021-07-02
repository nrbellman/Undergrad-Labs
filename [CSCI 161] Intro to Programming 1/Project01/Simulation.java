import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * A class that remembers what type of particle is at each location in a grid.
 * 
 * @author Chad Hogg, Nicholas Bellman
 */
public class Simulation {

	/** A list of the names of the types of particles. */
	public static final String[] NAMES = {"Empty", "Metal", "Sand", "Water", "Acid", "Sponge", "Cloud", "Ice"};

	// Each of these constants represents a different type of particle that can appear in the simulation.
	/** A space that has no particle in it at all. */
	public static final int EMPTY = 0;

	/** The space is filled with metal. */
	public static final int METAL = 1;

	/** The space is filled with sand. */
	public static final int SAND = 2;

	/** The space is filled with water. */
	public static final int WATER = 3;

	/** The space is filled with acid. */
	public static final int ACID = 4;

	/** The space is filled with sponge.
	 *  
	 *  Sponge, like metal, does not contain an update method. Instead, update methods of other materials take sponge into
	 *  consideration.
	 */
	public static final int SPONGE = 5;

	/** The space is filled with cloud. */
	public static final int CLOUD = 6;

	/** The space is filled with  . */
	public static final int ICE = 7;


	// Each of these constants stores the color of a type of particle.
	/** Empty spaces are black. */
	public static final Color EMPTY_COLOR = new Color(0, 0, 0);

	/** Metal is gray. */
	public static final Color METAL_COLOR = new Color(75, 75, 75);

	/** Sand is yellow-brown. */
	public static final Color SAND_COLOR = new Color(225, 200, 150);

	/** Water is blue. */
	public static final Color WATER_COLOR = new Color(0, 128, 255);

	/** Acid is pink. */
	public static final Color ACID_COLOR = new Color(255, 102, 178);

	/** Sponge is bright yellow. */
	public static final Color SPONGE_COLOR = new Color(253, 254, 3);

	/** Cloud is white. */
	public static final Color CLOUD_COLOR = new Color(245, 245, 245);

	/** Ice is light-blue. */
	public static final Color ICE_COLOR = new Color(175, 200, 250);


	/** Two dimensional array that stores values based on position on a grid. */
	private int[][] grid;

	/** Random object for calculating chance. */
	private Random random;


	/**
	 * Constructs a new Simulation.
	 * 
	 * @param height The number of rows in the simulation.
	 * @param width The number of columns in the simulation.
	 */
	public Simulation(int height, int width) {

		grid = new int[height][width];

		random = new Random();

	}

	/**
	 * Gets the height of the simulation.
	 * 
	 * @return The height of the simulation.
	 */
	public int getHeight() {

		return grid.length;

	}

	/**
	 * Gets the width of the simulation.
	 * 
	 * @return The width of the simulation.
	 */
	public int getWidth() {

		return grid[0].length;

	}

	/**
	 * Fills a location in the grid with a type of material.
	 * 
	 * @param row The row to fill.
	 * @param column The column to fill.
	 * @param type The type of material.
	 */
	public void fillLocation(int row, int column, int type) {

		grid[row][column] = type;

	}

	/**
	 * Gets the material at a location in the grid.
	 * 
	 * @param row The requested row.
	 * @param column The requested column.
	 * @return The value assigned to the type of material at that location.
	 */
	public int getParticleType(int row, int column) {

		return grid[row][column];
	}

	/**
	 * Sets the colors in a display based on what particles are at each location in the grid.
	 * 
	 * @param display A display to update.
	 */
	public void updateDisplay(SandDisplay display) {

		for (int row = 0; row < grid.length; row++) {

			for (int column = 0; column < grid[row].length; column++) {

				if (getParticleType(row, column) == EMPTY) {

					display.setColor(row, column, EMPTY_COLOR);

				}

				if (getParticleType(row, column) == METAL) {

					display.setColor(row, column, METAL_COLOR);

				}

				if (getParticleType(row, column) == SAND) {

					display.setColor(row, column, SAND_COLOR);

				}

				if(getParticleType(row, column) == WATER) {

					display.setColor(row, column, WATER_COLOR);

				}

				if(getParticleType(row, column) == ACID) {

					display.setColor(row, column, ACID_COLOR);

				}

				if(getParticleType(row, column) == SPONGE) {

					display.setColor(row, column, SPONGE_COLOR);

				}

				if(getParticleType(row, column) == CLOUD) {

					display.setColor(row, column, CLOUD_COLOR);

				}

				if(getParticleType(row, column) == ICE) {

					display.setColor(row, column, ICE_COLOR);

				}

			}

		}

	}

	/**
	 * Converts the numerical values stored in the grid into a String.
	 * 
	 * @return The String value of the grid. 
	 */
	public String toString() {

		String firstLine = grid.length + " " + grid[0].length + "\n";
		String otherLines = "";

		for (int row = 0; row < grid.length; row++) {

			for (int column = 0; column < grid[row].length; column++) {

				otherLines += grid[row][column] + " ";

			}

			otherLines += "\n";

		}

		return firstLine + otherLines;

	}

	/**
	 * Swaps the values of two positions on the grid.
	 * 
	 * @param rowOne The row of the first position to be swapped.
	 * @param columnOne The column of the first position to be swapped.
	 * @param rowTwo The row of the second position to be swapped.
	 * @param columnTwo The column of the second position to be swapped.
	 */
	public void swapLocations(int rowOne, int columnOne, int rowTwo, int columnTwo) {

		int locationOne = grid[rowOne][columnOne];
		int locationTwo = grid[rowTwo][columnTwo];

		grid[rowTwo][columnTwo] = locationOne;
		grid[rowOne][columnOne] = locationTwo;

	}

	/**
	 * Updates any position on the grid that contains the value for sand.
	 * 
	 * @param sandRow The row of a point that contains sand.
	 * @param sandColumn The column of a point that contains sand.
	 */
	public void updateSand(int sandRow, int sandColumn) {

		if (sandRow == grid.length - 1) {

			grid[sandRow][sandColumn] = EMPTY;

		}
		else if (grid[sandRow + 1][sandColumn] == EMPTY || grid[sandRow + 1][sandColumn] == WATER || 
				grid[sandRow + 1][sandColumn] == ACID || grid[sandRow + 1][sandColumn] == CLOUD) {

			swapLocations(sandRow, sandColumn, sandRow + 1, sandColumn);

		}

	}

	/**
	 * Updates any position on the grid that contains the value for water.
	 * 
	 * @param waterRow The row of a point that contains water.
	 * @param waterColumn The row of a point that contains water.
	 */
	public void updateWater(int waterRow, int waterColumn) {

		int direction = random.nextInt(2);
		int left = 1;
		int right = 0;

		if (waterRow == grid.length - 1) {

			grid[waterRow][waterColumn] = EMPTY;

		}
		// Passes through any empty positions and any positions that contain cloud.
		else if (grid[waterRow + 1][waterColumn] == EMPTY || grid[waterRow + 1][waterColumn] == CLOUD ) {

			swapLocations(waterRow, waterColumn, waterRow + 1, waterColumn);

		}
		// If it comes in contact with a position on the grid that contains sponge, it "gets absorbed" by the sponge.
		else if (grid[waterRow + 1][waterColumn] == SPONGE) {

			grid[waterRow][waterColumn] = EMPTY;

		}
		else if (grid[waterRow + 1][waterColumn] != EMPTY){

			if (direction == left && waterColumn == 0) {

				grid[waterRow][waterColumn] = EMPTY;

			} 
			else if (waterColumn - 1 >= 0 && direction == left 
					&& (grid[waterRow][waterColumn - 1] == EMPTY)) {

				swapLocations(waterRow, waterColumn, waterRow, waterColumn - 1);

			}

			if (direction == right && waterColumn == grid[0].length - 1) {

				grid[waterRow][waterColumn] = EMPTY;

			}
			else if (waterColumn + 1 < grid[0].length && direction == right 
					&& (grid[waterRow][waterColumn + 1] == EMPTY) ) {

				swapLocations(waterRow, waterColumn, waterRow, waterColumn + 1);

			}

		}

	}

	/**
	 * Updates any position on the grid that contains the value for acid.
	 * 	
	 * @param acidRow The row of a point that contains acid.
	 * @param acidColumn The row of a point that contains acid.
	 */
	public void updateAcid(int acidRow, int acidColumn) {

		int chance = random.nextInt(100) + 1;
		int direction = random.nextInt(4);

		int right = 0;
		int left = 1;
		int up = 2;
		int down = 3;

		if (chance <= 80) {

			// Due to this call, acid will be absorbed by sponge 80% of the time.
			updateWater(acidRow, acidColumn);

		}
		else if (chance <= 95) {

			if (acidColumn + 1 < grid[0].length && direction == right && grid[acidRow][acidColumn + 1] == WATER) {

				swapLocations(acidRow, acidColumn, acidRow, acidColumn + 1);

			}

			if (acidColumn - 1 >= 0 && direction == left && grid[acidRow][acidColumn - 1] == WATER) {

				swapLocations(acidRow, acidColumn, acidRow, acidColumn - 1);

			}

			if (acidRow - 1 >= 0 && direction == up && grid[acidRow - 1][acidColumn] == WATER) {

				swapLocations(acidRow, acidColumn, acidRow - 1, acidColumn);

			}

			if (acidRow + 1 < grid.length && direction == down && grid[acidRow + 1][acidColumn] == WATER) {

				swapLocations(acidRow, acidColumn, acidRow + 1, acidColumn);

			}

		}
		else {

			// 5% of the time, instead of being absorbed by sponge, the acid will disolve the sponge.
			if (acidRow + 1 < grid.length && (grid[acidRow + 1][acidColumn] == SAND || 
					grid[acidRow + 1][acidColumn] == METAL || grid[acidRow + 1][acidColumn] == SPONGE)) {

				grid[acidRow + 1][acidColumn] = EMPTY;

			}

		}

	}

	/**
	 * Updates any position on the grid that contains the value for cloud.
	 * 
	 * Cloud moves upwards towards the top of the grid or any position that contains a stationary material where it
	 * settles. When 4 positions in the same column contain cloud, the cloud starts to "rain" by creating water 
	 * underneath the cloud.
	 * 
	 * @param cloudRow The row of a point that contains cloud.
	 * @param cloudColumn The row of a point that contains cloud.
	 */
	public void updateCloud(int cloudRow, int cloudColumn) {

		int chance = random.nextInt(100) + 1;
		int direction = random.nextInt(2);

		int left = 1;
		int right = 0;

		if ((cloudRow - 1 >= 0 && grid[cloudRow - 1][cloudColumn] == EMPTY) && chance <= 99) {

			swapLocations(cloudRow, cloudColumn, cloudRow - 1, cloudColumn);

		}
		else if (cloudRow - 1 >= 0 && grid[cloudRow - 1][cloudColumn] != EMPTY){

			if (direction == left && cloudColumn == 0) {

				grid[cloudRow][cloudColumn] = EMPTY;

			} 
			else if (cloudColumn - 1 >= 0 && direction == left && (grid[cloudRow][cloudColumn - 1] == EMPTY)) {

				swapLocations(cloudRow, cloudColumn, cloudRow, cloudColumn - 1);

			}

			if (direction == right && cloudColumn == grid[0].length - 1) {

				grid[cloudRow][cloudColumn] = EMPTY;

			}
			else if (cloudColumn - 1 < grid[0].length && direction == right && 
					(grid[cloudRow][cloudColumn + 1] == EMPTY) ) {

				swapLocations(cloudRow, cloudColumn, cloudRow, cloudColumn + 1);

			}

		}

		if (cloudRow + 5 < grid.length) {
			
			if ((grid[cloudRow + 4][cloudColumn] == CLOUD && grid[cloudRow + 3][cloudColumn] == CLOUD && 
					grid[cloudRow + 2][cloudColumn] == CLOUD && grid[cloudRow + 1][cloudColumn] == CLOUD && 
					grid[cloudRow + 5][cloudColumn] == EMPTY) && chance <= 2) {

				grid[cloudRow + 5][cloudColumn] = WATER;

			}

		}

	}

	/**
	 * Updates any position on the grid that contains the value for ice.
	 * 
	 * Ice directly interacts with two materials: Water and Acid.
	 * If there is acid adjacent above or to the sides of ice, acid will "melt" the ice by turning it into water 5% of 
	 * the time.
	 * If there is water adjacent to ice on any side, the ice will "freeze" the water by making into more ice. This
	 * causes a cascading effect when ice is placed in a "pool" of water.
	 * 
	 * @param iceRow The row of a point that contains ice.
	 * @param iceColumn The row of a point that contains ice.
	 */
	public void updateIce(int iceRow, int iceColumn) {

		int chance = random.nextInt(100) + 1;

		if ((iceRow - 1 >= 0 && iceRow + 1 < grid.length) && (iceColumn - 1 >= 0 && iceColumn + 1 < grid[0].length)) {

			if (chance >= 95) {

				if (grid[iceRow - 1][iceColumn] == ACID) {

					grid[iceRow][iceColumn] = WATER;

				}

				if (grid[iceRow][iceColumn + 1] == ACID) {

					grid[iceRow][iceColumn] = WATER;

				}

				if (grid[iceRow][iceColumn - 1] == ACID) {

					grid[iceRow][iceColumn] = WATER;

				}

			}
			else if (chance >= 87) {

				if (grid[iceRow + 1][iceColumn] == WATER) {

					grid[iceRow + 1][iceColumn] = ICE;

				}

				if (grid[iceRow - 1][iceColumn] == WATER) {

					grid[iceRow - 1][iceColumn] = ICE;

				}

				if (grid[iceRow][iceColumn + 1] == WATER) {

					grid[iceRow][iceColumn + 1] = ICE;

				}

				if (grid[iceRow][iceColumn - 1] == WATER) {

					grid[iceRow][iceColumn - 1] = ICE;

				}

			}
		}

	}

	/**
	 * Causes one particle to take an action.
	 */
	public void doOneUpdate() {

		int randomRow = random.nextInt(grid.length);
		int randomColumn = random.nextInt(grid[0].length);

		if (grid[randomRow][randomColumn] == SAND) {

			updateSand(randomRow, randomColumn);

		}

		if (grid[randomRow][randomColumn] == WATER) {

			updateWater(randomRow, randomColumn);

		}

		if (grid[randomRow][randomColumn] == ACID) {

			updateAcid(randomRow, randomColumn);

		}

		if (grid[randomRow][randomColumn] == CLOUD) {

			updateCloud(randomRow, randomColumn);

		}

		if (grid[randomRow][randomColumn] == ICE) {

			updateIce(randomRow, randomColumn);

		}

	}

	/**
	 * Saves the current picture to a file.
	 * 
	 * @param file The file to save to.
	 * @throws FileNotFoundException If there is a problem printing to the file.
	 */
	public void save(File file) throws FileNotFoundException {

		PrintWriter fileWriter = new PrintWriter(file);

		fileWriter.print(this.toString());

		fileWriter.close();

	}

	/**
	 * Loads a picture from a file.
	 * 
	 * @param file The file to load.
	 * @throws FileNotFoundException If there is a problem reading from the file.
	 */
	public void load(File file) throws FileNotFoundException {

		Scanner fileScanner = new Scanner(file);

		int height = fileScanner.nextInt();
		int width = fileScanner.nextInt();

		grid = new int[height][width];

		int row = -1;

		while (fileScanner.hasNextLine() && row < grid.length) {

			int column = 0;

			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);

			while (lineScanner.hasNextInt() && column < grid[0].length) {

				grid[row][column] = lineScanner.nextInt();
				column++;

			}

			row++;

			lineScanner.close();

		}

		fileScanner.close();

	}

}