/**
 * This is the Binary Search Tree class that extends the Binary Tree class. This
 * class will hold methods specific to the Binary Search Tree. These methods
 * will include an add and subtract method.
 * 
 * @author Emily Le
 * 
 * @param <E>
 */
class BSTNode<E> extends BTNode<E> {

	/**
	 * This is the constructor of the {@link #BSTNode(Object, BTNode, BTNode)}
	 * class. This constructor will initialize the 'data', 'left' and 'right'
	 * variables.
	 * 
	 * @param initialData
	 * @param initialLeft
	 * @param initialRight
	 */
	BSTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight) {
		super(initialData, initialLeft, initialRight);
	}

	/**
	 * This method will add an element to the Binary Search Tree. By doing a
	 * series of checks, the element will be added to its respective location
	 * with the help of recursion.
	 * 
	 * @param element
	 */
	public boolean add(BTNode<E> root, E element) {
		boolean done = true;
		BSTNode<E> newElement = new BSTNode<E>(element, null, null);

		if (root.getData() == null) { // if root is empty, then create and add
										// new element
			root.setData(element);
		} else {
			done = addHelper(root, newElement);
		}

		return done;
	}

	/**
	 * This is a add helper method that will be called by the
	 * {@link #add(BinarySearchTree, Object)} method.
	 */
	public boolean addHelper(BTNode<E> root, BTNode<E> newElement) {
		boolean done = true;
		if (((Integer) newElement.getData()) <= ((Integer) root.getData())) {
			// if element is equal to the root, set boolean value to false
			if (((Integer) newElement.getData()) == ((Integer) root.getData())) {
				done = false;
			} else if (root.getLeft() == null) {
				root.setLeft(newElement);
			} else {
				// add helper method
				done = addHelper(root.getLeft(), newElement);
			}
		} else { // go to right branch
			if (root.getRight() == null) {
				root.setRight(newElement);
			} else {
				// add helper method
				done = addHelper(root.getRight(), newElement);
			}
		}
		return done;

	}

	/**
	 * This is the remove method. This method will remove the user-specified
	 * element by using a system of checks and recursion.
	 * 
	 * @param root
	 * @param element
	 * @return
	 */
	public boolean remove(BTNode<E> root, int element) {
		BTNode<E> parentNode = root;

		if (root == null) {
			return false;
		} else if (root.isLeaf() == true) {
			root.setData(null);
			return root.isLeaf();
		} else {
			return removeHelper(element, root, parentNode);
		}
	}

	/**
	 * This is the remove helper method that will be called by the
	 * {@link #remove(BTNode, int)} method. This method accomadate 4 different
	 * removal cases. This includes the base case.
	 * 
	 * @param element
	 * @param root
	 * @param parentNode
	 * @return
	 */
	public boolean removeHelper(int element, BTNode<E> root,
			BTNode<E> parentNode) {
		// case #1 (base case)
		if (root == null) {
			return false;
		}

		if (element == (Integer) root.getData()) {
			// case #2
			if (root.getLeft() == null) {
				if (root == parentNode) {
					parentNode.setData(parentNode.getRight().getData());
					parentNode.setLeft(parentNode.getRight().getLeft());
					parentNode.setRight(parentNode.getRight().getRight());
					return true;
				}
				// case #3: target found with no left child
				if (root == parentNode.getLeft()) {
					parentNode.setLeft(root.getRight());
					return true;
				} else {
					parentNode.setRight(root.getRight());
					return true;
				}
			} else {

				// case #4: there is a left subtree
				if (root.getLeft().isLeaf() == true) {
					root.setData(root.getLeft().getData());
					root.setLeft(null);
				} else {
					root.setData(root.getLeft().getRightmost());
					root.getLeft().removeRightmost();
				}
				return true;
			}
		} else if (element <= (Integer) root.getData()) {
			return removeHelper(element, root.getLeft(), root);
		} else {
			return removeHelper(element, root.getRight(), root);
		}
	}

}
