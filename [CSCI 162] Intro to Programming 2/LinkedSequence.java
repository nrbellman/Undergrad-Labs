/**
 * A sequence implemented using a (singly-) linked list (without dummy nodes).
 * 
 * @author Michael Main, Beth Katz, Chad Hogg, Nicholas Bellman
 * @param <E> The type of element stored in this sequence.
 */
public class LinkedSequence<E> implements Sequence<E>, Cloneable {
	// Invariants of the LinkedSequence class:
	// 1. the number of items in the sequence is maintained in size.  
	// 2. head points to the first node, if any, or it is null.
	// 3. tail points to the last node, if any, or it is null.
	// 4. if there is a current item, cursor points to it and
	//    precursor points to the node before it, if any.
	// 5. if there is no current item, cursor and precursor are both null.

	/** A reference to the first node, or null if there are none. */
	private Node<E> head;
	/** A reference to the last node, or null if there are none. */
	private Node<E> tail;
	/** A reference to the current node, or null if there is none. */
	private Node<E> cursor;
	/** A reference to the node before the current node, or null if there is none. */
	private Node<E> precursor;
	/** The number of elements in the sequence. */
	private int size;

	/**
	 * Initializes an empty sequence.
	 * 
	 * @postcondition The new sequence is empty.
	 */
	public LinkedSequence() {

		tail = null;
		head = null;

		cursor = null;
		precursor = null;

		size = 0;

	}


	/**
	 * Gets the number of elements in this sequence.
	 * 
	 * @return The number of elements in this sequence.
	 */ 
	@Override
	public int size() {

		return size;

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
	public void addAfter(E element) {

		Node<E> addend = new Node(element, null);

		if (size == 0) {

			head = addend;
			tail = addend;

		}
		else if (!isCurrent() || cursor == tail) {

			precursor = tail;
			tail = addend;
			precursor.setNext(tail);

		}
		else if (cursor == head) {

			precursor = head;
			addend.setNext(head.getNext());
			precursor.setNext(addend);

		}
		else {

			precursor = cursor;
			precursor.setNext(addend);
			addend.setNext(cursor.getNext());

		}

		cursor = addend;
		size++;

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
	public void addBefore(E element) {

		Node<E> addend = new Node(element, null);

		if (size == 0) {

			head = addend;
			tail = addend;

		}
		else if (!isCurrent() || cursor == head) {

			addend.setNext(head);
			head = addend;

		}
		else if (cursor == tail) {

			precursor.setNext(addend);
			addend.setNext(tail);

		}
		else {

			precursor.setNext(addend);
			addend.setNext(cursor);

		}

		cursor = addend;
		size++;

	}


	/**
	 * Set the current element at the beginning of this sequence.
	 * 
	 * @postcondition The first element of this sequence is now the current element (but 
	 *   if this sequence has no elements at all, then there is no current element).
	 */
	@Override
	public void start() {

		if (size > 0) {

			cursor = head;

		}
		else {

			cursor = null;

		}

		precursor = null;

	}


	/**
	 * Checks whether or not this has a current element.
	 * 
	 * @return True if this has a current element, or false otherwise.
	 */
	@Override
	public boolean isCurrent() {

		boolean current = true;

		if (cursor == null) {

			current = false;

		}

		return current;
	}


	/**
	 * Gets the current element, if one exists.
	 * 
	 * @precondition This has a current element.
	 * @return The current element.
	 * @throws IllegalStateException If there is no current element.
	 */
	@Override
	public E getCurrent() {

		if (!isCurrent()) {

			throw new IllegalStateException("There is no current element to get.");

		}

		E current;
		current = cursor.getValue();

		return current;

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

		if (!isCurrent()) {

			throw new IllegalStateException("There is no current element to advance.");

		}
		else if (cursor == tail) {

			cursor = null;

		}
		else {

			precursor = cursor;
			cursor = cursor.getNext();

		}

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

		if (!isCurrent()) {

			throw new IllegalStateException("There is no current element to remove");

		}

		if (cursor == tail) {

			cursor = null;

			if (precursor != null) {

				precursor.setNext(null);
				precursor = null;

			}

		}
		else if (cursor == head) {

			head = cursor.getNext();
			cursor = head;

		}
		else {

			precursor.setNext(cursor.getNext());
			cursor = cursor.getNext();

		}

		size--;

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
	public void addAll(Sequence<E> addend) {

		if (addend == null) {

			throw new NullPointerException();

		}

		Sequence<E> clone = addend.clone();
		Sequence<E> copy = this.clone();
		
		Node<E> tempCursor = cursor;
		
		if (clone.size() > 0) {
			
			clone.start();
			cursor = null;

			while(clone.isCurrent()) {

				this.addAfter(clone.getCurrent());
				clone.advance();

			}
			
			if (copy.size() == 0) {
				
				cursor = null;
				
			}
			
			cursor = tempCursor;
			
		}

	}   


	/**
	 * Makes a copy of this sequence.
	 * 
	 * @return A copy of this sequence.
	 */
	@Override
	public LinkedSequence<E> clone() {
		LinkedSequence<E> copy = new LinkedSequence<E>();
		for(Node<E> current = head; current != null; current = current.getNext()) {
			Node<E> nodeCopy = new Node<E>(current.getValue(), null);
			if(copy.head == null) {
				copy.head = nodeCopy;
				copy.tail = nodeCopy;
			}
			else {
				copy.tail.setNext(nodeCopy);
				copy.tail = nodeCopy;
			}
			if(current == precursor) {
				copy.precursor = nodeCopy;
			}
			else if(current == cursor) {
				copy.cursor = nodeCopy;
			}
			copy.size++;
		}
		return copy;
	}


	/**
	 * Gets a string picture of the sequence, with the current element in parenthesis.
	 * 
	 * @return A string like "3 6 (9) 12".
	 */
	public String toString() {
		// DO NOT CHANGE THIS METHOD
		String answer = "";

		for( Node<E> current = head; current != null; current = current.getNext()) {
			if(current == cursor) {
				answer += "(" + current.getValue() + ") ";
			}
			else {
				answer += current.getValue() + " ";                   
			}
		}
		if(size == 0) {
			answer = "empty sequence";
		}
		return answer;
	}


}

