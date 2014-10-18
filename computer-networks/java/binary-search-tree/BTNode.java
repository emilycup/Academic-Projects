
/**
 * This class contains all the implementations that are related to a Binary
 * Tree.
 * 
 * @author Emily Le
 * 
 * @param <E>
 */
class BTNode<E> {

	private E data;
	private BTNode<E> left;
	private BTNode<E> right;

	/**
	 * 
	 * This is the constructor of the {@link #BTNode(Object, BTNode, BTNode)}
	 * class. The constructor will initialize the 'data', 'left', and 'right'
	 * variables.
	 * 
	 * @param initialData
	 * @param initialLeft
	 * @param initialRight
	 */
	BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight) {
		data = initialData;
		left = initialLeft;
		right = initialRight;
	}

	/**
	 * This is a getter method that returns the data variable.
	 * 
	 * @return
	 */
	public E getData() {
		return this.data;
	}

	/**
	 * This is a getter method that returns the left node.
	 * 
	 * @return
	 */
	public BTNode<E> getLeft() {
		return this.left;
	}

	/**
	 * Getter method that returns the right node.
	 * 
	 * @return
	 */
	public BTNode<E> getRight() {
		return this.right;
	}

	/**
	 * Setter method that sets the value of the data variable.
	 * 
	 * @param newData
	 */
	public void setData(E newData) {
		data = newData;
	}

	/**
	 * Setter method that sets the value of the left node.
	 * 
	 * @param newLeft
	 */
	public void setLeft(BTNode<E> newLeft) {
		left = newLeft;
	}

	/**
	 * Setter method that sets the value of the right node.
	 * 
	 * @param newRight
	 */
	public void setRight(BTNode<E> newRight) {
		right = newRight;
	}

	/**
	 * Testing whether a node is a leaf or not.
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		if (right == null && left == null)
			return true;
		else
			return false;
	}

	/**
	 * This method returns the leftmost data.
	 * 
	 * @return
	 */
	public E getLeftmost() {

		if (left == null)
			return data;
		else
			return left.getLeftmost();
	}

	/**
	 * THis method returns the rightmost data.
	 * 
	 * @return
	 */
	public E getRightmost() {

		if (right == null)
			return data;
		else
			return right.getLeftmost();
	}

	/**
	 * This method removes and returns the leftmost node.
	 * 
	 * @return
	 */
	public BTNode<E> removeLeftmost() {
		if (left == null)
			return right;
		else {
			left = left.removeLeftmost();
			return this;
		}
	}

	/**
	 * This method removes and returns the leftmost node.
	 * 
	 * @return
	 */
	public BTNode<E> removeRightmost() {
		if (right == null) {
			return left;
		} else {
			right = right.removeRightmost();
			return this;
		}
	}

	/**
	 * This method will copy the entire tree from a specified source/(new)root.
	 * 
	 * @param root
	 * @return
	 */
	public static <E> BTNode<E> treeCopy(BTNode<E> root) {

		BTNode<E> leftCopy;
		BTNode<E> rightCopy;

		if (root == null)
			return null;
		else {
			leftCopy = treeCopy(root.left);
			rightCopy = treeCopy(root.right);
			return new BTNode<E>(root.data, leftCopy, rightCopy);
		}
	}

	/**
	 * This method returns the height at any specified source/(new)root.
	 * 
	 * @param root
	 * @return
	 */
	public static <E> int height(BTNode<E> root) {
		if (root == null)
			return -1;
		else {
			return 1 + Math.max(height(root.getLeft()), height(root.getLeft()));
		}
	}

	/**
	 * This method returns the complete size of a tree given a specified
	 * source/(new)root.
	 * 
	 * @param root
	 * @return
	 */
	public static <E> int treeSize(BTNode<E> root) {
		if (root == null)
			return 0;
		else {
			return 1 + treeSize(root.left) + treeSize(root.right);
		}
	}

	/**
	 * This method will do a Pre-Order Traversal through the tree.
	 * 
	 * @param root
	 */
	public void preOrderTraversal(BTNode<E> root) {
		if (root.data == null) {
			System.out.println("There is nothing in the tree");
		} else
			System.out.print(root.getData() + " ");
		if (root.left != null)
			preOrderTraversal(root.left);
		if (root.right != null)
			preOrderTraversal(root.right);

	}

	/**
	 * This method will do a In-Order Traversal through the Binary Tree.
	 * 
	 * @param root
	 */
	public void inOrderTraversal(BTNode<E> root) {
		if (root.left != null)
			inOrderTraversal(root.left);
		if (root.data == null) {
			System.out.println("There is nothing in the tree");
		} else
			System.out.print(root.getData() + " ");
		if (root.right != null)
			inOrderTraversal(root.right);

	}

	/**
	 * This method will do a Post-Order Traversal through the Binary Tree.
	 * 
	 * @param root
	 */
	public void postOrderTraversal(BTNode<E> root) {
		if (root.left != null)
			postOrderTraversal(root.left);
		if (root.right != null)
			postOrderTraversal(root.right);
		if (root.data == null) {
			System.out.println("There is nothing in the tree");
		} else
			System.out.print(root.getData() + " ");
	}

}
