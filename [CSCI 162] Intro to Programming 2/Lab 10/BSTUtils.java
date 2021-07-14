/**
 * Some static methods for working with binary trees.
 * 
 * @author William Killian, Chad Hogg
 */
public class BSTUtils {

	/**
	 * Visits all nodes of a [sub]tree, in order.
	 * 
	 * @param root The root of a [sub]tree.
	 * @param visitor The visitor that should visit each node.
	 * @param <E> The type of element in the tree.
	 */
	public static <E> void inorder(BTNode<E> root, BTNode.Visitor<E> visitor) {
		visitor.beforeVisit();
		if (root != null) {
			inorder(root.getLeft(), visitor);
		}
		visitor.visit(root);
		if (root != null) {
			inorder(root.getRight(), visitor);
		}
		visitor.afterVisit();
	}

	/**
	 * Visits all nodes of a [sub]tree, in reverse order.
	 * 
	 * @param root The root of a [sub]tree.
	 * @param visitor The visitor that should visit each node.
	 * @param <E> The type of element in the tree.
	 */
	public static <E> void reverseInorder(BTNode<E> root, BTNode.Visitor<E> visitor) {
		visitor.beforeVisit();
		if (root != null) {
			reverseInorder(root.getRight(), visitor);
		}
		visitor.visit(root);
		if (root != null) {
			reverseInorder(root.getLeft(), visitor);
		}
		visitor.afterVisit();
	}

	/**
	 * Visits all nodes of a [sub]tree, pre order.
	 * 
	 * @param root The root of a [sub]tree.
	 * @param visitor The visitor that should visit each node.
	 * @param <E> The type of element in the tree.
	 */
	public static <E> void preorder(BTNode<E> root, BTNode.Visitor<E> visitor) {
		visitor.beforeVisit();
		visitor.visit(root);
		if (root != null) {
			preorder(root.getLeft(), visitor);
			preorder(root.getRight(), visitor);
		}
		visitor.afterVisit();
	}

	/**
	 * Visits all nodes of a [sub]tree, post order.
	 * 
	 * @param root The root of a [sub]tree.
	 * @param visitor The visitor that should visit each node.
	 * @param <E> The type of element in the tree.
	 */
	public static <E> void postorder(BTNode<E> root, BTNode.Visitor<E> visitor) {
		visitor.beforeVisit();
		if (root != null) {
			postorder(root.getLeft(), visitor);
			postorder(root.getRight(), visitor);
		}
		visitor.visit(root);
		visitor.afterVisit();
	}

	/**
	 * Makes a copy of a [sub]tree.
	 * 
	 * @param root The root of a [sub]tree.
	 * @return A deep copy of that [sub]tree.
	 * @param <E> The type of element in the tree.
	 */
	public static <E> BTNode<E> copy(BTNode<E> root) {
		BTNode<E> result;
		if (root == null) {
			result = null;
		}
		else {
			result = new BTNode<E>(root.getData(), copy(root.getLeft()), copy(root.getRight()));
		}
		return result;
	}

	/**
	 * Gets the size of a [sub]tree.
	 * 
	 * @param root The root of the [sub]tree.
	 * @return The number of nodes in the [sub]tree.
	 * @param <E> The type of elements in the tree.
	 */
	public static <E> long size(BTNode<E> root) {
		long result;
		if (root == null) {
			result = 0;
		}
		else {
			result = 1 + size(root.getLeft()) + size(root.getRight());
		}
		return result;
	}

	/**
	 * Checks whether or not a node is a leaf.
	 * 
	 * @param node A node.
	 * @return True if that node is a leaf, or false.
	 * @param <E> The type of elements in the tree.
	 */
	public static <E> boolean isLeaf(BTNode<E> node) {
		return node.getLeft() == null && node.getRight() == null;
	}

	/**
	 * Gets the number of children a node has.
	 * 
	 * @param node A node.
	 * @return The number of children it has.
	 * @param <E> The type of elements in the tree.
	 */
	public static <E> int children(BTNode<E> node) {
		int children = 0;
		if(node.getLeft() != null) {
			children++;
		}
		if(node.getRight() != null) {
			children++;
		}
		return children;
	}

	/**
	 * A visitor that prints the elements in all the nodes it visits.
	 * 
	 * @author William Killian, Chad Hogg
	 * @param <E> The type of elements in the nodes being visited.
	 */
	public static class SimplePrinter<E> extends BTNode.Visitor<E> {
		@Override
		public void visit(BTNode<E> node) {
			if (node != null) {
				System.out.print(node.getData());
			}
		}
	}

	/**
	 * A visitor that produces a string containing the elements of the nodes it visits.
	 * 
	 * @author William Killian, Chad Hogg
	 * @param <E> The type of elements in the nodes being visited.
	 */
	public static class StringPrinter<E> extends BTNode.Visitor<E> {

		/** A StringBuilder into which data will be added. */
		private StringBuilder buffer;

		/**
		 * Initializes a new StringPrinter.
		 */
		public StringPrinter() {
			buffer = new StringBuilder();
		}
		
		/**
		 * Appends a character to the string.
		 * 
		 * @param a The character to append.
		 */
		public void append(char a) {
			buffer.append(a);
		}

		@Override
		public void visit(BTNode<E> node) {
			if (node != null) {
				buffer.append(node.getData().toString());
			}
		}

		@Override
		public String toString() {
			return buffer.toString();
		}
	}

	/**
	 * A visitor that draws a tree sideways.
	 * 
	 * @author William Killian, Chad Hogg
	 * @param <E> The type of element in the tree.
	 */
	public static class TreePrinter<E> extends BTNode.Visitor<E> {

		/** The indentation for each depth level. */
		private static final int DIFF = 4;
		/** The current depth. */
		private int depth;
		
		/**
		 * Initializes a new TreePrinter.
		 */
		public TreePrinter() {
			depth = 0;
		}

		/**
		 * Prints many copies of a character, one for each depth.
		 * @param c The character to print.
		 */
		private void printMany(char c) {
			for (int counter = 0; counter < depth; ++counter) {
				System.out.print(c);
			}
		}

		@Override
		public void visit(BTNode<E> node) {
			printMany(' ');
			if (node == null) {
				System.out.println('-');
			} else {
				System.out.println(node.getData());
			}
		}

		@Override
		public void beforeVisit() {
			depth += DIFF;
		}

		@Override
		public void afterVisit() {
			depth -= DIFF;
		}

	}
}
