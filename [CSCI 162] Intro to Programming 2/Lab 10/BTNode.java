
/**
 * A node in a binary tree.
 * 
 * @author Chad Hogg
 * @param <E> The type of data stored in the tree.
 */
public class BTNode<E> {

	/** The data stored in this node. */
	private E data;
	/** The left child of this node. */
	private BTNode<E> left;
	/** The right child of this node. */
	private BTNode<E> right;
	
	/**
	 * Initializes a node with no children.
	 * 
	 * @param data The data for the new node.
	 */
	public BTNode(E data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * Initializes a node.
	 * 
	 * @param data The data for the new node.
	 * @param left The left child of the new node.
	 * @param right The right child of the new node.
	 */
	public BTNode(E data, BTNode<E> left, BTNode<E> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Gets the data at this node.
	 * 
	 * @return The data.
	 */
	public E getData() {
		return data;
	}

	/**
	 * Gets the left child of this node.
	 * 
	 * @return The left child.
	 */
	public BTNode<E> getLeft() {
		return left;
	}

	/**
	 * Gets the right child of this node.
	 * 
	 * @return The right child.
	 */
	public BTNode<E> getRight() {
		return right;
	}

	/**
	 * Sets the data in this node.
	 * 
	 * @param data The data to store here.
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * Sets the left child of this node.
	 * 
	 * @param left The new left child.
	 */
	public void setLeft(BTNode<E> left) {
		this.left = left;
	}

	/**
	 * Sets the right child of this node.
	 * 
	 * @param right The new right child.
	 */
	public void setRight(BTNode<E> right) {
		this.right = right;
	}

	/**
	 * Any class that visits the nodes of a tree.
	 * 
	 * @author Will Killian, Chad Hogg
	 * @param <E> The type of elements in the tree nodes.
	 */
	public static abstract class Visitor<E> {

		/**
		 * Visits a node and do something there.
		 * 
		 * @param node The node to visit.
		 */
		public abstract void visit (BTNode<E> node);

		/**
		 * Does any setup before visiting a node.
		 */
		public void beforeVisit() {			
		}
		
		/**
		 * Does any teardown after visiting a node.
		 */
		public void afterVisit() {
		}
	}
}