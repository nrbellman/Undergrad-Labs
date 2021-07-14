/**
 * A class that keeps track of statistics about a sequence of real numbers.
 * It has the following class invariants:
 * - sumOfValues contains the sum of all values entered, or
 *        Double.POSITIVE_INFINITY or Double.NEGATIVE_INFINITY
 *        if that sum ever becomes too high or low to represent.
 * - count contains the number of values, or
 *        Integer.MAX_VALUE if too many values are entered.
 * - smallestValue contains smallest value entered, or
 *        0.0 if no values were entered.
 * - largestValue contains largest value entered, or
 *        0.0 if no values were entered.
 * 
 * @author Michael Main, Beth Katz, Chad Hogg
 */
public class Statistician implements Cloneable {

	/** The sum of all the numbers inserted since the last reset. */
	private double sumOfValues;
	/** The amount of numbers inserted since the last reset. */
	private int count;
	/** The smallest value entered, or 0.0 if there were no numbers yet. */
	private double smallestValue;
	/** The largest value entered, or 0.0 if there were no numbers yet. */
	private double largestValue;

	/**
	 * Initializes a new Statistician.
	 * 
	 * @postcondition The new Statistician contains no numbers.
	 **/   
	public Statistician() {
		smallestValue = 0.0;
		largestValue = 0.0;
		count = 0;
		sumOfValues = 0.0;
	}        

	/**
	 * Determines how many numbers have been given to this Statistician.
	 * Note:
	 *   Giving a Statistician more than Integer.MAX_VALUE numbers, 
	 *   will cause failure with an arithmetic overflow.
	 * 
	 * @return The count of how many numbers have been given to this Statistician
	 *   since it was initialized or reinitialized.
	 **/ 
	public int getLength() {
		// TODO: Fill in the body of this method, replacing the line below.
		return count;
	}

	/**
	 * Determines the sum of all the numbers that have been given to this Statistician.
	 * Note:
	 *   If the sum exceeds the bounds of double numbers, then the answer
	 *   from this method may be Double.POSITIVE_INFINITY or
	 *   Double.NEGATIVE_INFINITY.
	 *   
	 * @return The sum of all the numbers that have been given to this 
	 *   Statistician since it was created or reset.
	 **/ 
	public double getSum() {
		// TODO: Fill in the body of this method, replacing the line below.
		return sumOfValues;
	}

	/**
	 * Determines the arithmetic average of all the numbers that have been 
	 *   given to this Statistician.
	 * Note:
	 *   If this Statistician has been given more than Integer.MAX_VALUE 
	 *   numbers, then this method fails because of arithmetic overflow.
	 *   If length() is zero, then the answer is Double.NaN.
	 *   If sum() exceeds the bounds of double numbers, then the answer 
	 *   may be Double.POSITIVE_INFINITY or Double.NEGATIVE_INFINITY.
	 *   
	 * @return The arithmetic mean of all the numbers that have been given to this 
	 *   Statistician since it was created or reset.
	 **/
	public double getMean() {
		// TODO: Fill in the body of this method, replacing the line below.
		return sumOfValues / count;
	}

	/**
	 * Determines the smallest number that has been given to this Statistician.
	 *   
	 * @return The smallest number that has been given to this Statistician since
	 *   it was created or reset, or Double.NAN if there have been no numbers yet.
	 **/ 
	public double getSmallest() {
		// TODO: Fill in the body of this method, replacing the line below.
		double smallest = smallestValue;
		if (count == 0) {

			smallest = Double.NaN;

		}
		return smallest;
	}

	/**
	 * Determines the largest number that has been given to this Statistician.
	 * 
	 * @return The largest number that has been given to this Statistician since
	 *   it was created or reset, or Double.NAN if there have been no numbers yet.
	 **/ 
	public double getLargest() {
		// TODO: Fill in the body of this method, replacing the line below.
		double largest = largestValue;
		if (count == 0) {

			largest = Double.NaN;

		}
		return largest;
	}

	/**
	 * Gives a new number to this Statistician. 
	 * 
	 * @param number The new number that is being given.
	 * @postcondition This Statistician has updated its statistics to include the new number.
	 **/
	public void insert(double number) {
		// TODO: Fill in the body of this method.

		sumOfValues += number;

		if (number > largestValue || count == 0) {

			largestValue = number;

		}

		if (number < smallestValue || count == 0) {

			smallestValue = number;

		}

		count++;

	}

	/**
	 * Resets this Statistician.
	 * 
	 * @postcondition This Statistician is reinitialized as if it has never been 
	 *   given any numbers.
	 **/
	public void reset() {
		// TODO: Fill in the body of this method.
		sumOfValues = 0.0;
		count = 0;
		largestValue = 0.0;
		smallestValue = 0.0;
	}

	/**
	 * Compares this Statistician to another object for equality.
	 * 
	 * @param obj The object with which this Statistician will be compared.
	 * @return True if and only if the object is a Statistician with the same field values.
	 **/   
	public boolean equals(Object obj) {
		// TODO: Fill in the body of this method, replacing the line below.
		boolean result;

		if (this == obj) {
			
			result = true;
			
		}
		else if(obj == null) {

			result = false;

		}
		else if (!(obj instanceof Statistician)) {

			result = false;

		}
		else {

			Statistician other = (Statistician) obj;

			if (this.count == other.count && this.smallestValue == other.smallestValue && 
					this.largestValue == other.largestValue && this.sumOfValues == other.sumOfValues) {

				result = true;

			}
			else {

				result = false;

			}

		}

		return result;

	} 

	/**
	 * Makes a copy of this Statistician with exactly the same field values
	 *   but stored in a different object.
	 *   
	 * @return An exact copy of this Statistician.
	 */
	public Statistician clone() {
		// TODO: Fill in the body of this method, replacing the line below.
		Statistician newStatistician = new Statistician();

		newStatistician.smallestValue = this.smallestValue;
		newStatistician.largestValue = this.largestValue;
		newStatistician.count = this.count;
		newStatistician.sumOfValues = this.sumOfValues;

		return newStatistician;

	}

	/**
	 * Add all the numbers of another Statistician to this Statistician.
	 * 
	 * @param addend A Statistician whose numbers will be added to this Statistician,
	 *   which must not be null.
	 * @postcondition The numbers from addend have been added to this Statistician. 
	 *   After the operation, this Statistician acts as if it were given 
	 *   all of its original numbers and also given all of the numbers from the addend.
	 * @throws NullPointerException If addend is null.
	 **/
	public void add(Statistician addend) {
		// TODO: Fill in the body of this method.
		if (this.count == 0 && addend.count == 0) {
			
			this.smallestValue = 0.0;
			this.largestValue = 0.0;
			
		}
		else if (this.count == 0) {
			
			this.smallestValue = addend.smallestValue;
			this.largestValue = addend.largestValue;
		
		}
		else if (addend.count != 0){
			
			if (this.smallestValue > addend.smallestValue) {
				
				this.smallestValue = addend.smallestValue;
				
			}
			
			if(this.largestValue < addend.largestValue) {
				
				this.largestValue = addend.largestValue;
				
			}
			
		}
		
		this.count += addend.count;
		this.sumOfValues += addend.sumOfValues;
		
	}   

	/**
	 * Create a new Statistician that behaves as if it was given all 
	 *   the numbers from this and another Statistician.
	 *   
	 * @param other An existing Statistician, which may not be null.
	 * @return A new Statistician that acts as if it was given all the 
	 *   numbers from this Statistician and the other Statistician.
	 * @postcondition Nothing about this Statistician has changed.
	 * @throws NullPointerException If other is null.
	 **/   
	public Statistician union(Statistician other) {
		// TODO: Fill in the body of this method, replacing the line below.
		Statistician union = this.clone();

		union.add(other);

		return union;

	}

}
