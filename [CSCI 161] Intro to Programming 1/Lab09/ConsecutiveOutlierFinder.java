import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Checks for outliers in data from a file.
 * 
 * @author Nicholas Bellman
 */
public class ConsecutiveOutlierFinder {

	/**
	 * Runs the program.
	 * 
	 * @param args Command line arguments.
	 * @throws FileNotFoundException The contents of args do not contain a valid file name.
	 */
	public static void main(String[] args) throws FileNotFoundException {

		if (args.length == 0) {

			System.out.println("Error: you must supply at least one file name.");

		}
		else { 
			
			for (String element : args) {

				findOutliersInFile(element);

			}
			
		}

	}

	/**
	 * Calculates the mean average of an array of doubles.
	 * 
	 * @param dataPoints The array of doubles to be averaged.
	 * @return The calculated mean of the array.
	 */
	public static double mean(double[] dataPoints) {

		double mean = 0;
		double total = 0;

		for (double element : dataPoints) {

			total += element;

		}

		mean = total / dataPoints.length;

		return mean;

	}

	/**
	 * Calculates the standard deviation of an array of doubles.
	 * 
	 * @param dataPoints The array of doubles to be calculated.
	 * @param mean The mean of the array of doubles.
	 * @return The standard deviation of the array of doubles.
	 */
	public static double stdDev(double[] dataPoints, double mean) {


		double total = 0;
		for (int index = 0; index < dataPoints.length; index++) {

			total += Math.pow((dataPoints[index] - mean), 2);

		}

		double deviation = Math.sqrt(total / (dataPoints.length - 1));

		return deviation;

	}

	/**
	 * Checks whether two consecutive data points are both outliers.
	 * 
	 * @param times A string array of times.
	 * @param dataPoints An array of doubles.
	 */
	public static void printConsecutiveOutliers(String[] times, double[] dataPoints) {

		double mean = mean(dataPoints);
		double deviation = stdDev(dataPoints, mean);

		boolean isOutlier = false;

		for (int index = 0; index < dataPoints.length; index++) {

			if ( (dataPoints[index] > mean + (2 * deviation) || dataPoints[index] < mean - (2 * deviation) ) && 
					isOutlier == true ) {

				System.out.printf("%s and %s were both outliers.\n", times[index - 1], times[index]);
				isOutlier = true;

			} 
			else if (dataPoints[index] > mean + (2 * deviation) || dataPoints[index] < mean - (2 * deviation)) {

				isOutlier = true;

			} 
			else {

				isOutlier = false;

			}

		}

	}

	/**
	 * Checks whether or not files contain any outlier data.
	 * 
	 * @param fileName The name of the file to be checked.
	 * @throws FileNotFoundException The file with the name of the parameter 'fileName' does not exist in the working directory.
	 */
	public static void findOutliersInFile(String fileName) throws FileNotFoundException {

		File file = new File(fileName);
		Scanner fileScanner = new Scanner(file);

		System.out.println("Starting to process " + fileName);

		int numReadings = fileScanner.nextInt();

		String[] times = new String[numReadings];
		double[] dataPoints = new double[numReadings];

		int index = 0;
		while (fileScanner.hasNext()) {

			times[index] = fileScanner.next();
			dataPoints[index] = fileScanner.nextDouble();

			index++;

		}

		printConsecutiveOutliers(times, dataPoints);

		System.out.println("Finished processing " + fileName);

		fileScanner.close();

	}

}
