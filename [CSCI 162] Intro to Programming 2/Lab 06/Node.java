/**
 * A generic node in a single-linked list.
 * 
 * @author Michael Main, Beth Katz, Chad Hogg
 * @param <E> The type of element stored in the node.
 */
public class Node<E> {
	// Invariant of the Node class:
	//   1. The node's data is in the instance variable data.
	//   2. For the final node of a list, the link is null.
	//      Otherwise, link is a reference to the next node of the list.
	
	/** The element stored in this node. */
	private E value;
	/** A reference to the next node, or null if this is the last one. */
	private Node<E> next;
   
	/**
	 * Initialize a node with given initial data and link to the next node.
	 * 
	 * The initialNext may be the null reference, which indicates that the
	 *   new node has nothing after it.
	 *   
	 * @param initialValue The initial data of this new node.
	 * @param initialNext A reference to the node after this new node.
	 */
	public Node(E initialValue, Node<E> initialNext) {
		value = initialValue;
		next = initialNext;
	}

	/**
	 * Gets the value from this node.
	 * 
	 * @return The value from this node.
	 */
	public E getValue() {
		return value;
	}

	/**
	 * Gets a reference to the next node after this node.
	 * 
	 * @return A reference to the node after this node (or null).
	 */
	public Node<E> getNext() {
		return next;
	}

	/**
	 * Sets the value in this node.   
	 * 
	 * @param newValue The new value to place in this node.
	 * @postcondition The value of this node has been set to newData.
	 */
	public void setValue(E newValue) {
		value = newValue;
	}

	/**
	 * Sets the link to the next node after this node.
	 * 
	 * @param newNext A reference to the node that is after this (or null).
	 */
	public void setNext(Node<E> newNext) {
		next = newNext;
	}
}
