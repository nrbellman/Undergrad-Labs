

/**
 * A LIFO collection of elements in which items can only be added or removed from the top.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the stack.
 */
public interface MUStack<E> {

	/**
	 * Gets the number of elements in the stack.
	 * 
	 * @return The number of elements in the stack.
	 */
	public int size();
	
	/**
	 * Gets whether or not the stack is empty.
	 * 
	 * @return True if the stack contains no elements, or false otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Adds an element to the stack.
	 * 
	 * @param item The element to add.
	 * @postcondition The item is now the top, and any existing items have been pushed down.
	 */
	public void push(E item);
	
	/**
	 * Gets a reference to the top element on the stack.
	 * 
	 * @return The top element of the stack.
	 * @precondition The stack may not be empty.
	 * @throws EmptyStackException If the stack is empty.
	 */
	public E peek();
	
	/**
	 * Removes the top element of the stack.
	 * 
	 * @return The element that had been the top.
	 * @postcondition The top element has been removed, and all other elements pushed up.
	 * @precondition The stack may not be empty.
	 * @throws EmptyStackException If the stack is empty.
	 */
	public E pop();
}
