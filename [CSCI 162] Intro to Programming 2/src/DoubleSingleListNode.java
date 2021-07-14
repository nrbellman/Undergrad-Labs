

/**
 * A node in a singly-linked list of doubles.
 * 
 * @author Chad Hogg
 */
public class DoubleSingleListNode {

	/** The number stored in this node. */
	private double value;
	/** A reference to the node that comes after this one (null if none). */
	private DoubleSingleListNode next;
	
	/**
	 * Constructs a new IntSingleNode.
	 * 
	 * @param value The number to store in the new node.
	 * @param next The node that should come after the new one.
	 */
	public DoubleSingleListNode(double value, DoubleSingleListNode next) {
		this.value = value;
		this.next = next;
	}
	
	/**
	 * Gets the value stored at this node.
	 * 
	 * @return The value stored at this node.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Gets the next node after this (null if none).
	 * 
	 * @return The next node after this (null if none).
	 */
	public DoubleSingleListNode getNext() {
		return next;
	}
	
	/**
	 * Sets the value stored in this node.
	 * 
	 * @param value The new value to be stored in this node.
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	/**
	 * Sets the next node after this one.
	 * 
	 * @param next The node that should come after this.
	 */
	public void setNext(DoubleSingleListNode next) {
		this.next = next;
	}
	
}
