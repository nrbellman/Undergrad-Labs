
/**
 * A class that stores a list of doubles, keeping them in numerical order at all times.
 * 
 * @author Beth Katz, Chad Hogg, Nicholas Bellman
 *
 */
public class SortedList {

	// Class Invariants:
	//	
	//	1. 	The number of nodes in the list is stored in the field listLength.
	//	
	//	2.	If the size of the list is greater than zero, the first node in
	//		the list is stored in the field head which stores a reference to
	//		further nodes (if they exist).
	//


	/** The first node of this list, or null if there is none. */
	private DoubleSingleListNode head;
	/** The number of nodes in this list. */
	private int listLength;

	/**
	 * Constructs a new, empty list.
	 * 
	 * @postcondition This list has no nodes.
	 */
	public SortedList() {
		// TODO: Fill in body.
		head = null;
		listLength = 0;

	}

	/**
	 * Inserts a new value into this list, maintaining the sorted order.
	 * 
	 * @param value The value to add.
	 * @postcondition The new value has been added to the list, after all
	 *   elements that are smaller than it and before all elements that are
	 *   greater than it.
	 */
	public void insert(double value) {

		DoubleSingleListNode node = new DoubleSingleListNode(value, null);

		if (listLength == 0) {

			head = node;

		}
		else if (node.getValue() <= head.getValue()) {

			node.setNext(head);
			head = node;

		}
		else {

			DoubleSingleListNode current = head;
			DoubleSingleListNode previous = getPrecedingNode(value);

			boolean inserted = false;
			while (current != null && inserted == false) {

				if (current.getValue() > node.getValue()) {

					node.setNext(current);
					previous.setNext(node);

					inserted = true;

				}
				else if(current.getNext() == null) {

					current.setNext(node);

					inserted = true;

				}

				current = current.getNext();

			}

		}

		listLength++;

	}

	/**
	 * Gets the node that would come before the new one created to hold a
	 *   value (also known as the last node that contains a number less than the
	 *   value).
	 * 
	 * @param value A value that is going to be inserted.
	 * @return The last node that contains a number less than the value, or null
	 *   if no nodes contain numbers less than the value.
	 */
	private DoubleSingleListNode getPrecedingNode(double value) {

		DoubleSingleListNode previous = null;
		DoubleSingleListNode current = head;

		while (current != null && current.getValue() <= value) {

			previous = current;
			current = current.getNext();

		}

		return previous;

	}

	/**
	 * Searches this list for a value.
	 * 
	 * @param value A number to search for.
	 * @return The first position at which that value appears (0 = first node,
	 *   1 = second node, ...) or -1 if it not found anywhere.
	 */
	public int find(double value) {

		DoubleSingleListNode current = head;

		boolean found = false;
		int index = -1;

		if (current == null) {

			index = -1;

		}
		else {
			
			while(current != null && found == false) {

				if (current.getValue() == value) {

					found = true;

				}

				current = current.getNext();
				index++;

			}
			
			if (found == false) {
				
				index = -1;
				
			}

		}

		return index;

	}

	/**
	 * Removes a node from this list.
	 * 
	 * @param index The position of the node to remove (0 = first node, 1 = second
	 *   node, ...).
	 * @return Whether or not such a node exists.  (True if 0 >= index < size().)
	 * @postcondition If there was a node at that position, it has been removed.
	 */
	public boolean removeAt(int index) {

		boolean result = true;
		if (index < 0 || index >= listLength) {

			result = false;

		}
		else {

			DoubleSingleListNode current = head;
			DoubleSingleListNode previous = null;

			if (index == 0) {

				head = head.getNext();

			}
			else if (index == listLength - 1) {

				while(current.getNext() != null) {

					previous = current;
					current = current.getNext();

				}

				previous.setNext(null);

			}
			else {

				int counter = 0;

				while (counter != index) {

					previous = current;
					current = current.getNext();

					counter++;

				}

				previous.setNext(current.getNext());


			}

			listLength--;

		}

		return result;

	}

	/**
	 * Gets the number of elements in this list.
	 * 
	 * @return The number of elements in this list.
	 */
	public int size() {
		return listLength; // TODO: Fill in body.
	}

	/**
	 * Gets a String representing this list.
	 * 
	 * @return A String like "( 2.0 4.0 5.0 )".
	 */
	public String toString() {
		String answer = "( ";

		for(DoubleSingleListNode current = head; current != null; current = current.getNext()) {
			answer += current.getValue() + " ";
		}
		answer += ")";
		return answer;
	}
}
