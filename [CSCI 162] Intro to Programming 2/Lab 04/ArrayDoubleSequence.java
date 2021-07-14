import java.util.Arrays;

/**
 * A sequence of doubles stored in a partially-filled array.
 * 
 * @author Michael Main, Chad Hogg
 */
public class ArrayDoubleSequence implements DoubleSequence, Cloneable {
	// Invariant of the ArrayDoubleSequence class:
	//   1. The number of elements in the sequence is in the instance variable 
	//      itemCount.
	//   2. For an empty sequence (with no elements), we do not care what is 
	//      stored in any of data; for a non-empty sequence, the elements of the
	//      sequence are stored in data[0] through data[itemCount - 1], and we
	//      don't care what's in the rest of data.
	//   3. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals itemCount.

	/** A partially-filled array containing the elements of this sequence. */
	private double[] data;
	/** The number of elements in this sequence. */
	private int itemCount;
	/** The index of the current element, or a copy of itemCount if there is none. */
	private int currentIndex; 

	/**
	 * Initializes an empty sequence with an initial capacity of 10.
	 * 
	 * @postcondition This sequence is empty and has a capacity of 10.
	 */   
	public ArrayDoubleSequence() {
		// TODO: Fill in this body.
		data = new double[10];
		itemCount = 0;
		currentIndex = itemCount;

	}


	/**
	 * Initialize an empty sequence with a specified initial capacity.
	 * 
	 * @param initialCapacity The initial capacity of the new sequence, which must be
	 *   non-negative.
	 * @postcondition The new sequence is empty and has the given initial capacity.
	 * @throws IllegalArgumentException If initialCapacity is negative.
	 */   
	public ArrayDoubleSequence(int initialCapacity) {
		// TODO: Fill in this body.
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity must be non-negative.");
		}

		data = new double[initialCapacity];
		itemCount = 0;
		currentIndex = itemCount;

	}


	/**
	 * Increases the capacity of this sequence, if necessary.
	 * 
	 * @param minimumCapacity The minimum capacity for this sequence.
	 * @postcondition This sequence's capacity has been changed to at least
	 *   minimumCapacity. If the capacity was already at or greater than
	 *   minimumCapacity, then the capacity is left unchanged.
	 */
	public void ensureCapacity(int minimumCapacity) {
		// TODO: Fill in this body.
		if (data.length < minimumCapacity) {

			data = Arrays.copyOf(data, minimumCapacity);

		}

	}


	/**
	 * Gets the current capacity of this sequence.
	 * 
	 * @return The current capacity of this sequence.
	 */
	public int getCapacity() {
		// TODO: Fill in this body.
		return data.length;

	}


	/**
	 * Reduces the current capacity of this sequence to its actual size.
	 * 
	 * @postcondition This sequence's capacity has been changed to its current size.
	 */
	public void trimToSize() {
		// TODO: Fill in this body.
		if (data.length > itemCount) {

			data = Arrays.copyOf(data, itemCount);

		}

	}

	/**
	 * Set the current element at the beginning of this sequence.
	 * 
	 * @postcondition The first element of this sequence is now the current element (but 
	 *   if this sequence has no elements at all, then there is no current element).
	 */
	@Override
	public void start() {
		// TODO: Fill in this body.
		if(itemCount > 0) {

			currentIndex = 0;

		}
		else {

			currentIndex = itemCount;

		}

	}


	/**
	 * Gets the current element, if one exists.
	 * 
	 * @precondition This has a current element.
	 * @return The current element.
	 * @throws IllegalStateException If there is no current element.
	 */
	@Override
	public double getCurrent() {
		// TODO: Fill in this body.
		if (currentIndex == itemCount) {
			throw new IllegalStateException("There is no current element.");
		}

		return data[currentIndex];

	}


	/**
	 * Changes the current element to the next one in the sequence.
	 * 
	 * @precondition This has a current element.
	 * @postcondition If the current element was already the end element of this sequence 
	 *   (with nothing after it), then there is no longer any current element.  Otherwise, 
	 *   the new current element is the element immediately after the original current element.
	 * @throws IllegalStateException If there is no current element.
	 */
	@Override
	public void advance() {
		// TODO: Fill in this body.
		if (currentIndex == itemCount) {

			throw new IllegalStateException("There is no current element.");

		}

		currentIndex++;

	}


	/**
	 * Checks whether or not this has a current element.
	 * 
	 * @return True if this has a current element, or false otherwise.
	 */
	@Override
	public boolean isCurrent() {
		// TODO: Fill in this body.
		boolean result = false;

		if (currentIndex >= 0 && currentIndex != itemCount) {

			result = true;

		}

		return result;

	}


	/**
	 * Gets the number of elements in this sequence.
	 * 
	 * @return The number of elements in this sequence.
	 */ 
	@Override
	public int size() {
		// TODO: Fill in this body.

		return itemCount;

	}


	/**
	 * Adds a new element to this sequence, before the current element.
	 * 
	 * @param element The new element that should be added.
	 * @postcondition A new copy of the element has been added to this sequence.
	 *   If there was a current element, then the new element is placed before the
	 *   current element. If there was no current element, then the new element is
	 *   placed at the start of the sequence. In all cases, the new element becomes
	 *   the new current element of this sequence. 
	 */
	@Override
	public void addBefore(double element) {
		// TODO: Fill in this body.
		if(data.length == itemCount) {

			ensureCapacity(itemCount * 2 + 1);

		}

		if(isCurrent() == false) {

			currentIndex = 0;

		}

		for(int index = itemCount - 1; index >= currentIndex; index--) {

			data[index + 1] = data[index];

		}

		data[currentIndex] = element;

		itemCount++;

	}


	/**
	 * Adds a new element to this sequence, after the current element.
	 * 
	 * @param element The new element that should be added.
	 * @postcondition A new copy of the element has been added to this sequence.
	 *   If there was a current element, then the new element is placed after the
	 *   current element. If there was no current element, then the new element is
	 *   placed at the end of the sequence. In all cases, the new element becomes
	 *   the new current element of this sequence. 
	 */
	@Override
	public void addAfter(double element) {
		// TODO: Fill in this body.
		if(data.length == itemCount) {

			ensureCapacity(itemCount * 2 + 1);

		}

		if (isCurrent() == true) {

			for (int index = itemCount - 1; index > currentIndex; index--) {

				data[index + 1] = data[index];

			}

			data[currentIndex + 1] = element;
			currentIndex++;

		}
		else {

			data[currentIndex] = element;

		}

		itemCount++;


	}


	/**
	 * Remove the current element from this sequence.
	 * 
	 * @precondition This has a current element.
	 * @postcondition The current element has been removed from this sequence, and
	 *   the following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current element.
	 * @throws IllegalStateException If there was no current element.
	 */
	@Override
	public void removeCurrent() {
		// TODO: Fill in this body.
		if (isCurrent() == false) {

			throw new IllegalStateException("There is no current element to remove.");

		}

		for (int index = currentIndex; index < itemCount; index++) {

			data[index] = data[index + 1];

		}

		itemCount--;

	}


	/**
	 * Places the contents of another sequence at the end of this sequence.
	 * 
	 * @param addend A sequence whose contents will be placed at the end of this.
	 *   It may not be null.
	 * @postcondition The elements from addend have been placed at the end of 
	 *   this sequence. The current element of this sequence remains where it 
	 *   was, and the addend is also unchanged.
	 * @throws NullPointerException If the addend is null.
	 */
	@Override
	public void addAll(DoubleSequence addend) {
		// TODO: Fill in this body.
		if (addend == null) {

			throw new NullPointerException("The addend must not be empty.");

		}

		if (this.itemCount + addend.size() >= data.length) {

			ensureCapacity((this.itemCount + addend.size()) * 2);

		}

		if (addend.size() > 0) {
			
			DoubleSequence copy = addend.clone();

			int temp = this.currentIndex;
			this.currentIndex = this.itemCount;
			
			for (copy.start(); copy.isCurrent(); copy.advance()) {

				this.addAfter(copy.getCurrent());

			}
			
			if (this.size() - copy.size() == 0) {
				
				this.currentIndex = this.itemCount;
				
			}
			else {
				
				this.currentIndex = temp;
				
			}
			
		}

	}


	/**
	 * Generates a copy of this sequence.
	 * 
	 * @return A copy of this sequence, with the same elements and the same
	 *   current element.
	 */ 
	@Override
	public DoubleSequence clone() {
		// TODO: Fill in this body.
		ArrayDoubleSequence copy = new ArrayDoubleSequence(this.itemCount);

		copy.data = Arrays.copyOf(this.data, this.itemCount);
		copy.itemCount = this.itemCount;
		copy.currentIndex = this.currentIndex;

		return copy;
		
	}


	/**
	 * Gets a string representation of the sequence with current item 
	 *   in parentheses.
	 * 
	 * @return A String displaying this sequence.
	 */
	public String toString() {

		String answer = "";

		for (int index = 0; index < itemCount; index++) {

			if(index == currentIndex) {

				answer += "(" + data[index] + ") ";

			}
			else {

				answer += data[index] + " ";  

			}
		}

		if (answer.length() == 0) {

			answer = "empty sequence";

		}

		return answer;

	}

}
