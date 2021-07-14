

import java.util.EmptyStackException;

/**
 * A stack implemented using a linked list.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the stack.
 */
public class MULinkedListStack<E> implements MUStack<E> {
	
	// Class Invariants:
	//   All elements of the stack are stored in the list.
	//   The top of the stack is the last element of the list, and all others are
	//     ordered from bottom (front) to top (back).

	/** A linked list that stores the elements. */
	private MULinkedList<E> list;
	
	/**
	 * Initializes a new empty stack.
	 */
	public MULinkedListStack() {
		list = new MULinkedList<E>();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void push(E item) {
		list.insertBack(item);
	}

	@Override
	public E peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		return list.get(list.size() - 1);
	}

	@Override
	public E pop() {
		E result = peek();
		list.removeBack();
		return result;
	}
	
	@Override
	public String toString() {
		return "Stack [bottom ... top]: " + list.toString();
	}

}
