import java.util.Scanner;

public class TestBSTStudent {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		for(int i = 0; i < 2; i++) {
			BinarySearchTree<Character> tree = new BinarySearchTree<Character>();
			String data;

			System.out.print("Line of characters to insert: ");
			data = stdin.next();
			for (char c : data.toCharArray()) {
				tree.insert(c);
			}
			System.out.println("After inserting: " + tree.toString());
			System.out.println();
			tree.print();

			System.out.print("Line of characters to remove: ");
			data = stdin.next();
			for (char c : data.toCharArray()) {
				tree.remove(c);
			}
			System.out.println("After removing: " + tree.toString());
			System.out.println();
			tree.print();


		}
		stdin.close();
	}
}