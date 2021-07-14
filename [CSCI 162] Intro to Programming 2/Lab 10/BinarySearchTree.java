
/**
 * A collection of elements, stored in a tree in order.
 * 
 * @author Chad Hogg
 * @param <E> The type of element stored in the tree.
 */
public class BinarySearchTree<E extends Comparable<E>> {

	// Class invariants:
	// For every node X, the value stored in all of X's left descendants is <= the value stored in X.
	// For every node X, the value stored in all of X's right descendants is > the value stored in X.

	/** The root of the tree. */
	private BTNode<E> root;

	/**
	 * Initializes a new, empty tree.
	 */
	public BinarySearchTree() {

		root = null;

	}

	/**
	 * Adds a new element to the tree.
	 * 
	 * @param value The element to add.
	 * @postcondition The tree contains one more copy of the element than it used to.
	 */
	public void insert(E value) {

		root = insert(value, root);

	}

	/**
	 * Adds a new element to a subtree, then returns the (possibly new) root of that subtree.
	 * 
	 * @param value The element to add.
	 * @param subtreeRoot The root of a subtree within this tree (could be null).
	 * @return The new root of that subtree (a new node if subtreeRoot was null, otherwise unchanged).
	 * @postcondition The subtree contains one more copy of the element than it used to.
	 */
	private BTNode<E> insert(E value, BTNode<E> subtreeRoot) {

		if (subtreeRoot == null) {

			subtreeRoot = new BTNode<E>(value);

		}
		else {

			if (value.compareTo(subtreeRoot.getData()) <= 0) {

				subtreeRoot.setLeft(insert(value, subtreeRoot.getLeft()));

			}
			else if (value.compareTo(subtreeRoot.getData()) > 0) {

				subtreeRoot.setRight(insert(value, subtreeRoot.getRight()));

			}

		}

		return subtreeRoot;

	}

	/**
	 * Checks whether or not this tree contains an element.
	 * 
	 * @param value The element to look for.
	 * @return True if the tree contains at least one copy of the element, or false.
	 */
	public boolean contains(E value) {

		return contains(value, root);

	}

	/**
	 * Checks whether or not a subtree contains an element.
	 * 
	 * @param value The element to look for.
	 * @param subtreeRoot The root of a subtree within this tree (could be null).
	 * @return True if that subtree contains at least one copy of the element, or false.
	 */
	private boolean contains(E value, BTNode<E> subtreeRoot) {

		boolean exists = false;

		if (subtreeRoot == null) {

			exists = false;

		}
		else if (value.compareTo(subtreeRoot.getData()) == 0) {

			exists = true;

		}
		else {

			if (value.compareTo(subtreeRoot.getData()) < 0) {

				exists = contains(value, subtreeRoot.getLeft());

			}
			else if (value.compareTo(subtreeRoot.getData()) > 0) {

				exists = contains(value, subtreeRoot.getRight());

			}

		}

		return exists;

	}

	/**
	 * Removes one copy of an element from the tree, if at least one exists.
	 * 
	 * @param value The element to remove.
	 * @postcondition If the tree contained at least one copy of the element, it has one fewer.
	 */
	public void remove(E value) {

		root = remove(value, root);

	}

	/**
	 * Removes one copy of an element from a subtree, if at least one exists.
	 * 
	 * @param value The element to remove.
	 * @param node The root of a subtree in which the element should be removed.
	 * @return The new root of that subtree (unchanged, unless node was the one removed).
	 * @postcondition If the subtree contained at least one copy of the element, it has one fewer.
	 */
	private BTNode<E> remove(E value, BTNode<E> node) {

		BTNode<E> result = node;

		if (node == null) {

			result = null;

		}
		else if (node.getData().equals(value)) {

			if (node.getLeft() != null && node.getRight() != null) {

				node.setData(removeLargest(node, node.getLeft()));
				
			}
			else if (node.getLeft() != null && node.getRight() == null) {

				node = node.getLeft();
				result = node;

			}
			else if (node.getLeft() == null && node.getRight() != null) {

				node = node.getRight();
				result = node;

			}
			else if (node.getLeft() == null && node.getRight() == null) {

				node = null;
				result = node;

			}

		}
		else {

			if (value.compareTo(node.getData()) < 0) {

				node.setLeft(remove(value, node.getLeft()));

			}
			else if (value.compareTo(node.getData()) > 0) {

				node.setRight(remove(value, node.getRight()));

			}

		}

		return result;

	}

	/**
	 * Removes the largest value in a subtree.
	 * 
	 * @param parent The parent of the root of the subtree to delete from.
	 * @param node The root of the subtree to delete from.
	 * @return The element that was removed.
	 * @postcondition The largest element in the subtree has been removed.
	 */
	private E removeLargest(BTNode<E> parent, BTNode<E> node) {

		E result = null;

		if (node.getRight() == null) {

			result = node.getData();
			remove(node.getData(), parent);

		}
		else {

			result = removeLargest(node, node.getRight());

		}

		return result;

	}

	/**
	 * Gets a String containing the elements of this tree, in order.
	 * 
	 * @return A String containing the element of this tree, in order.
	 */
	@Override
	public String toString() {
		BSTUtils.StringPrinter<E> printer = new BSTUtils.StringPrinter<E>();
		BSTUtils.inorder(root, printer);
		return printer.toString();
	}

	/**
	 * Gets the number of elements in this tree.
	 * 
	 * @return The number of elements in this tree.
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Gets the number of elements in a subtree.
	 * 
	 * @param node The root of the subtree.
	 * @return The number of elements in that subtree.
	 */
	private int size(BTNode<E> node) {
		int result;
		if(node == null) {
			result = 0;
		}
		else {
			result = 1 + size(node.getLeft()) + size(node.getRight()); 
		}
		return result;
	}

	/**
	 * Gets the depth of the tree.
	 * 
	 * @return The number of links from the root to the deepest node.
	 */
	public int depth() {
		return depth(root);
	}

	/**
	 * Gets the depth of a subtree.
	 * 
	 * @param node The root of the subtree.
	 * @return The number of links from that node to its deepest descendant.
	 */
	private int depth(BTNode<E> node) {
		int result;
		if(node == null) {
			result = -1;
		}
		else {
			result = 1 + Math.max(depth(node.getLeft()), depth(node.getRight()));
		}
		return result;
	}

	/**
	 * Gets a String containing all elements, in order.
	 * 
	 * @return A String containing the elements, with root coming between left and right.
	 */
	public String inOrderString() {
		StringBuilder builder = new StringBuilder();
		inOrderString(root, builder);
		return builder.toString();
	}

	/**
	 * Builds a String containing all elements of a subtree, in order.
	 * 
	 * @param node A String containing the elements, with root coming between left and right.
	 * @param builder A StringBuilder to which the elements should be added.
	 */
	private void inOrderString(BTNode<E> node, StringBuilder builder) {
		if(node != null) {
			inOrderString(node.getLeft(), builder);
			builder.append(node.getData().toString() + " ");
			inOrderString(node.getRight(), builder);
		}
	}

	/**
	 * Gets a String containing all elements, pre order.
	 * 
	 * @return A String containing the elements, with root coming before left and right.
	 */
	public String preOrderString() {
		StringBuilder builder = new StringBuilder();
		preOrderString(root, builder);
		return builder.toString();
	}

	/**
	 * Builds a String containing all elements of a subtree, pre order.
	 * 
	 * @param node A String containing the elements, with root coming before left and right.
	 * @param builder A StringBuilder to which the elements should be added.
	 */
	private void preOrderString(BTNode<E> node, StringBuilder builder) {
		if(node != null) {
			builder.append(node.getData().toString() + " ");
			preOrderString(node.getLeft(), builder);
			preOrderString(node.getRight(), builder);
		}
	}

	/**
	 * Gets a String containing all elements, post order.
	 * 
	 * @return A String containing the elements, with root coming after left and right.
	 */
	public String postOrderString() {
		StringBuilder builder = new StringBuilder();
		postOrderString(root, builder);
		return builder.toString();
	}

	/**
	 * Builds a String containing all elements of a subtree, post order.
	 * 
	 * @param node A String containing the elements, with root coming after left and right.
	 * @param builder A StringBuilder to which the elements should be added.
	 */
	private void postOrderString(BTNode<E> node, StringBuilder builder) {
		if(node != null) {
			postOrderString(node.getLeft(), builder);
			postOrderString(node.getRight(), builder);
			builder.append(node.getData().toString() + " ");
		}
	}

	/**
	 * Prints the tree sideways.
	 */
	public void print() {
		BSTUtils.reverseInorder(root, new BSTUtils.TreePrinter<E>());
	}

}
