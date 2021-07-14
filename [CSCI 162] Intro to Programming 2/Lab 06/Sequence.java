

/**
 * A collection of elements, with possible duplicates, in a specific order, with 
 *   the ability to read them in order.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the sequence.
 */
public interface Sequence<E> {

	/**
	 * Set the current element at the beginning of this sequence.
	 * 
	 * @postcondition The first element of this sequence is now the current element (but 
	 *   if this sequence has no elements at all, then there is no current element).
	 */
	public void start();
	
	/**
	 * Gets the current element, if one exists.
	 * 
	 * @precondition This has a current element.
	 * @return The current element.
	 * @throws IllegalStateException If there is no current element.
	 */
	public E getCurrent();

	/**
	 * Changes the current element to the next one in the sequence.
	 * 
	 * @precondition This has a current element.
	 * @postcondition If the current element was already the end element of this sequence 
	 *   (with nothing after it), then there is no longer any current element.  Otherwise, 
	 *   the new current element is the element immediately after the original current element.
	 * @throws IllegalStateException If there is no current element.
	 */
	public void advance();
	
	/**
	 * Checks whether or not this has a current element.
	 * 
	 * @return True if this has a current element, or false otherwise.
	 */
	public boolean isCurrent();
	
	/**
	 * Gets the number of elements in this sequence.
	 * 
	 * @return The number of elements in this sequence.
	 */ 
	public int size();
	
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
	public void addBefore(E element);
	
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
	public void addAfter(E element);
		
	/**
	 * Remove the current element from this sequence.
	 * 
	 * @precondition This has a current element.
	 * @postcondition The current element has been removed from this sequence, and
	 *   the following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current element.
	 * @throws IllegalStateException If there was no current element.
	 */
	public void removeCurrent();
	
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
	public void addAll(Sequence<E> addend);
	
	/**
	 * Generates a copy of this sequence.
	 * 
	 * @return A copy of this sequence, with the same elements and the same
	 *   current element.
	 */ 
	public Sequence<E> clone();

}
