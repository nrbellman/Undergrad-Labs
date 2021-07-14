

/**
 * A generic linked list, implemented with doubly-linked nodes and dummy head/tail nodes.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the list.
 */
public class MULinkedList<E> implements MUList<E>, Cloneable {
	
	// Class invariants:
	// Head is never null, and always holds a dummy node.
	// Tail is never null, and always holds a dummy node.
	// If there are no real nodes, head.next is tail and tail.prev is head.
	// If there are some real nodes, head.next is the first and tail.prev is the last.
	// Size is the number of non-dummy nodes.
	// For any real node x, x.prev is the real node before it, or head if it is the first.
	// For any real node x, x.next is the real node after it, or tail if it is the last.

	/** The dummy head node, which precedes the first real node (or tail if no real nodes). */
	private ListNode<E> head;
	/** The dummy tail node, which succeeds the last real node (or head if no real nodes). */ 
	private ListNode<E> tail;
	/** The number of real nodes (not including head or tail). */
	private int size;
	
	/**
	 * Initializes a new, empty LinkedList.
	 */
	public MULinkedList() {
		head = new ListNode<E>(null, null, null);
		tail = new ListNode<E>(null, null, null);
		head.setNext(tail);
		tail.setPrev(head);
		size = 0;
	}
	
	/**
	 * Gets the number of elements in this list.
	 * 
	 * @return The number of elements.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Gets whether or not this list contains no elements.
	 * 
	 * @return True if this list contains no elements, or false.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Gets a String representation of this list.
	 * 
	 * @return A string like "( 1 4 8 )".
	 */
	public String toString() {
		String result = "( ";
		for(ListNode<E> current = head.getNext(); current.getNext() != null; current = current.getNext()) {
			result += current.getValue() + " ";
		}
		result += ")";
		return result;
	}
	
	/**
	 * Inserts a new value directly before a node.
	 * 
	 * @param value The value to add.
	 * @param after The node to insert before, which must not be null and must be
	 *   part of this list. (Unchecked precondition.)
	 * @throws NullPointerException If node is null.
	 * @postcondition A new node has been created that contains value and appears
	 *   in the list before the specified node.
	 */
	private void insertBefore(E value, ListNode<E> after) {
		ListNode<E> before = after.getPrev();
		ListNode<E> newNode = new ListNode<E>(value, before, after);
		before.setNext(newNode);
		after.setPrev(newNode);
		size++;
	}

	/**
	 * Inserts a new value at the beginning of this list.
	 * 
	 * @param value The value to add.
	 * @postcondition The value is the first element in the list, preceding all
	 *   other elements.
	 */
	public void insertFront(E value) {
		insertBefore(value, head.getNext());
	}
	
	/**
	 * Inserts a new value at the end of this list.
	 * 
	 * @param value The value to add.
	 * @postcondition The value is the last element in the list, following all
	 *   other elements.
	 */
	public void insertBack(E value) {
		insertBefore(value, tail);
	}
	
	/**
	 * Gets the node at a specific index in the list.
	 * 
	 * @param index A 0-based index (0 is first real node, 1 is second, etc.),
	 *   which must be at least 0 and less than size.
	 * @return The node at that index.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	private ListNode<E> getNode(int index) {
		if(index < 0 || index >= size) {
			throw new IllegalArgumentException("Index out of bounds.");
		}
		ListNode<E> current = head.getNext();
		for(int counter = 0; counter < index; counter++) {
			current = current.getNext();
		}
		return current;
	}
	
	/**
	 * Inserts a new value before an existing element.
	 * 
	 * @param index The 0-based index of that element, which must be at least
	 *   0 and less than size.
	 * @param value The value to insert.
	 * @postcondition The value is in the list, between index-1 and index.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public void insertBefore(int index, E value) {
		insertBefore(value, getNode(index));
	}
	
	/**
	 * Inserts a new value after an existing element.
	 * 
	 * @param index The 0-based index of that element, which must be at least
	 *   0 and less than size.
	 * @param value The value to insert.
	 * @postcondition The value is in the list, between index and index+1.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public void insertAfter(int index, E value) {
		insertBefore(value, getNode(index).getNext());
	}
	
	/**
	 * Removes a node from this list.
	 * 
	 * @param node A node to remove, which must be part of this list and not
	 *   the head or tail dummy node. (Unchecked precondition.)
	 * @return The value stored in the removed node.
	 * @postcondition The node has been removed.
	 */
	private E remove(ListNode<E> node) {
		ListNode<E> before = node.getPrev();
		ListNode<E> after = node.getNext();
		before.setNext(after);
		after.setPrev(before);
		size--;
		return node.getValue();
	}
	
	/**
	 * Removes the first element of this list.
	 * 
	 * @precondition The list may not be empty.
	 * @return The element that was removed.
	 * @postcondition The first element of the list was removed.
	 * @throws IllegalStateException If this list is empty.
	 */
	public E removeFront() {
		if(size() == 0) {
			throw new IllegalStateException("Cannot remove from empty list.");
		}
		return remove(head.getNext());
	}
	
	/**
	 * Removes the last element of this list.
	 * 
	 * @precondition The list may not be empty.
	 * @return The element that was removed.
	 * @postcondition The last element of the list was removed.
	 * @throws IllegalStateException If this list is empty.
	 */
	public E removeBack() {
		if(size() == 0) {
			throw new IllegalStateException("Cannot remove from empty list.");
		}
		return remove(tail.getPrev());
	}
	
	/**
	 * Removes an element of this list.
	 * 
	 * @param index The 0-based index of the element to remove, which must
	 *   be at least 0 and less than size.
	 * @return The element that was removed.
	 * @postcondition The element has been removed.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public E removeAt(int index) {
		ListNode<E> node = getNode(index);
		return remove(node);
	}
	
	/**
	 * Adds all elements of another list to this one.
	 * 
	 * @param addend The list whose elements should be added, which may not be null.
	 * @postcondition All elements of addend have been added at the end of this.
	 * @throws NullPointerException If addend is null.
	 */
	public void addAll(MUList<E> addend) {
		for(int index = 0; index < addend.size(); index++) {
			insertBack(addend.get(index));
		}
	}
	
	/**
	 * Removes all elements from this list.
	 */
	public void clear() {
		head.setNext(tail);
		tail.setPrev(head);
		size = 0;
	}
	
	/**
	 * Gets the element at a specified index.
	 * 
	 * @param index The 0-based index of the element to get, which must be at
	 *   least 0 and less than size.
	 * @return The element at that index.
	 * @throws IllegalArgumentException If the index is out of range.
	 */
	public E get(int index) {
		ListNode<E> node = getNode(index);
		return node.getValue();
	}
	
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
	public E set(int index, E value) {
		ListNode<E> node = getNode(index);
		E result = node.getValue();
		node.setValue(value);
		return result;
	}
	
	/**
	 * Creates a copy of this list.
	 * 
	 * @return A distinct object with the same elements in the same order.
	 */
	public MULinkedList<E> clone() {
		MULinkedList<E> copy = new MULinkedList<E>();
		copy.addAll(this);
		return copy;
	}
}
