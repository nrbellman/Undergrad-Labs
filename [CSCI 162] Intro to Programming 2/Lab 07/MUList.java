

/**
 * A collection of elements, all of the same type, in a user-defined order.
 * It is possible to edit the list at any index.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the list.
 */
public interface MUList<E> {

	/**
	 * Gets the number of elements in this list.
	 * 
	 * @return The number of elements.
	 */
	public int size();
	
	/**
	 * Gets whether or not this list contains no elements.
	 * 
	 * @return True if this list contains no elements, or false.
	 */
	public boolean isEmpty();

	/**
	 * Inserts a new value at the beginning of this list.
	 * 
	 * @param value The value to add.
	 * @postcondition The value is the first element in the list, preceding all
	 *   other elements.
	 */
	public void insertFront(E value);
	
	/**
	 * Inserts a new value at the end of this list.
	 * 
	 * @param value The value to add.
	 * @postcondition The value is the last element in the list, following all
	 *   other elements.
	 */
	public void insertBack(E value);
	
	/**
	 * Inserts a new value before an existing element.
	 * 
	 * @param index The 0-based index of that element, which must be at least
	 *   0 and less than size.
	 * @param value The value to insert.
	 * @postcondition The value is in the list, between index-1 and index.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public void insertBefore(int index, E value);
	
	/**
	 * Inserts a new value after an existing element.
	 * 
	 * @param index The 0-based index of that element, which must be at least
	 *   0 and less than size.
	 * @param value The value to insert.
	 * @postcondition The value is in the list, between index and index+1.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public void insertAfter(int index, E value);
	
	/**
	 * Removes the first element of this list.
	 * 
	 * @precondition The list may not be empty.
	 * @return The element that was removed.
	 * @postcondition The first element of the list was removed.
	 * @throws IllegalStateException If this list is empty.
	 */
	public E removeFront();
	
	/**
	 * Removes the last element of this list.
	 * 
	 * @precondition The list may not be empty.
	 * @return The element that was removed.
	 * @postcondition The last element of the list was removed.
	 * @throws IllegalStateException If this list is empty.
	 */
	public E removeBack();
	
	/**
	 * Removes an element of this list.
	 * 
	 * @param index The 0-based index of the element to remove, which must
	 *   be at least 0 and less than size.
	 * @return The element that was removed.
	 * @postcondition The element has been removed.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public E removeAt(int index);
	
	/**
	 * Adds all elements of another list to this one.
	 * 
	 * @param addend The list whose elements should be added, which may not be null.
	 * @postcondition All elements of addend have been added at the end of this.
	 * @throws NullPointerException If addend is null.
	 */
	public void addAll(MUList<E> addend);
	
	/**
	 * Removes all elements from this list.
	 */
	public void clear();
	
	/**
	 * Gets the element at a specified index.
	 * 
	 * @param index The 0-based index of the element to get, which must be at
	 *   least 0 and less than size.
	 * @return The element at that index.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public E get(int index);
	
	/**
	 * Sets the element at a specified index.
	 * 
	 * @param index The 0 based-index of the element to set, which must be at
	 *   least 0 and less than size.
	 * @param value The value to replace.
	 * @return The element previously at that index.
	 * @postcondition The value is in the list, replacing the element that used
	 *   to be at that index.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public E set(int index, E value);

}
