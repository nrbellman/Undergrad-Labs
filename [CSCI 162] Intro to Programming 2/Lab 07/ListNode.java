

/**
 * A generic node in a doubly-linked list.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the list.
 */
public class ListNode<E> {

	/** The value stored in this node. */
	private E value;
	/** A reference to the previous node, or null if first. */
	private ListNode<E> prev;
	/** A reference to the next node, or null if last. */
	private ListNode<E> next;
	
	/**
	 * Initializes a new node.
	 * 
	 * @param value The value to be stored in the new node.
	 * @param prev The node that comes before the new node.
	 * @param next The node that comes after the new node.
	 */
	public ListNode(E value, ListNode<E> prev, ListNode<E> next) {
		this.value = value;
		this.prev = prev;
		this.next = next;
	}
	
	/**
	 * Gets the value stored in this node.
	 * 
	 * @return The value stored in this node.
	 */
	public E getValue() {
		return value;
	}
	
	/**
	 * Gets the node before this node.
	 * 
	 * @return The node before this node.
	 */
	public ListNode<E> getPrev() {
		return prev;
	}
	
	/**
	 * Gets the node after this node.
	 * 
	 * @return The node after this node.
	 */
	public ListNode<E> getNext() {
		return next;
	}
	
	/**
	 * Stores a different value in this node.
	 * 
	 * @param value The value to be stored in this node.
	 */
	public void setValue(E value) {
		this.value = value;
	}
	
	/**
	 * Changes which node comes before this one.
	 * 
	 * @param prev The new previous node.
	 */
	public void setPrev(ListNode<E> prev) {
		this.prev = prev;
	}
	
	/**
	 * Changes which node comes after this one.
	 * 
	 * @param next The new next node.
	 */
	public void setNext(ListNode<E> next) {
		this.next = next;
	}
}
