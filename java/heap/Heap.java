/**
 * This is the Heap class that will include all the functions related to Heap
 * Tree data structures. These functions will mainly consist of the
 * add(insertion way), add(optimal way), and the remove methods.
 * 
 * @author Emily Le
 * 
 */
class Heap {

	private int[] element = new int[100];
	int lastIndex = -1;
	int swapCount = 0;
	int heapSize = 0;

	/**
	 * This is the getter method that will return the element array.
	 * 
	 * @return
	 */
	public int[] getElement() {
		return element;
	}

	/**
	 * This is the getter method that will return the number of swaps it takes
	 * during the add and remove methods.
	 * 
	 * @return
	 */
	public int getSwapCount() {
		int tempSwapCount = swapCount;
		swapCount = 0;
		return tempSwapCount;
	}

	/**
	 * This method will check if the tree is empty. If the tree is indeed empty,
	 * the method will return a true value. If the tree is not empty, the method
	 * will return a false value.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (lastIndex == -1)
			return true;
		else
			return false;
	}

	/**
	 * This is the swap method that will swap the the two specified elements
	 * around. This method will be called by the {@link #add()} method and the
	 * {@link #remove()} method.
	 * 
	 * @param parentIndex
	 * @param childIndex
	 */
	public void swap(int parentIndex, int childIndex) {
		int temp = element[childIndex];
		element[childIndex] = element[parentIndex];
		element[parentIndex] = temp;

		// swap count is increased each time a swap is made
		swapCount++;
	}

	/**
	 * This is an add insertion method that will add an element to the heap data
	 * structure. In this particular add method, the tree will sort all of its
	 * elements each time and element is added to the end of the tree. It will
	 * call on the {@link #reheapUp(int)} method to help sort the elements in
	 * their respective positions/indexes.
	 * 
	 * @param newElement
	 * @return
	 */
	public void addInsertionWay(int newElement) {
		lastIndex++;
		element[lastIndex] = newElement;

		reheapUp(lastIndex);
	}

	/**
	 * This is the add method that will strictly add an element to the end of
	 * the heap. This method will be used in conjunction with the
	 * {@link #optimalReheapUp(int)} method.
	 */
	public void add(int newElement) {
		lastIndex++;
		element[lastIndex] = newElement;
	}

	/**
	 * This is the remove method that will remove that root of the tree. The
	 * root will be replaced with the last element in the tree and will call the
	 * {@link #reheapDown(int)} method to sort the elements in the respectful
	 * places.
	 * 
	 * @return
	 */
	public boolean remove() {
		// base case: check if heap is empty
		if (isEmpty() == true) {
			return false;
		} else {
			// swap with first element and the last index
			swap(0, lastIndex);
			element[lastIndex] = 0;
			// last index will decrease after an element is removed
			lastIndex--;

			reheapDown(0);
		}
		return true;

	}

	/**
	 * This method will reheapify the tree in a upward fashion. This method will
	 * be called by the {@link #add(int)} method. After this methods is called,
	 * all of the elements should be sorted in their respective positions.
	 * 
	 * @param elementIndex
	 */
	public void reheapUp(int elementIndex) {
		final int PARENT_INDEX = (elementIndex - 1) / 2;

		// base case: only one element in heap
		if (lastIndex == 0) {
			return;
		} else if (element[PARENT_INDEX] < element[elementIndex]) {
			swap(PARENT_INDEX, elementIndex);
			reheapUp(PARENT_INDEX);
		}
	}

	/**
	 * This is the optimal reheap-up method that will reheapify the tree in an
	 * upwards fashion. This optimal method will sort an already built tree and
	 * will move all the elements to their respective positions after a series
	 * of comparisons.
	 */
	public void optimalReheapUp(int elementIndex) {
		// base case: only one element in heap
		if (elementIndex == 0) {
			return;
		}
		// if the element is the last element in the tree
		else if (elementIndex == lastIndex) {
			// if last element is in right subtree (even)
			if (elementIndex % 2 == 0) {
				optimalReheapRightTreeHelper(elementIndex);
			} else {
				// if last element is in left subtree
				optimalReheapLeftTreeHelper(elementIndex);
			}
		} else {
			optimalReheapRightTreeHelper(elementIndex);
		}
	}

	/**
	 * This is the optimal reheap helper method that will be called each time
	 * the elementIndex is in the right subtree. This method will be called by
	 * the {@link #optimalReheapUp(int)} method.
	 * 
	 * @param elementIndex
	 */
	public void optimalReheapRightTreeHelper(int elementIndex) {
		final int PARENT_INDEX = (elementIndex - 1) / 2;
		final int LEFT_CHILD_INDEX = (2 * PARENT_INDEX) + 1;
		final int RIGHT_CHILD_INDEX = (2 * PARENT_INDEX) + 2;

		// System.out.println("RECURSION THROUGH TREE - debug");
		int childMax = Math.max(element[LEFT_CHILD_INDEX],
				element[RIGHT_CHILD_INDEX]);
		// parent less than a child
		if (element[PARENT_INDEX] < childMax) {
			if (element[LEFT_CHILD_INDEX] > element[RIGHT_CHILD_INDEX]) {
				// left child is bigger
				swap(PARENT_INDEX, LEFT_CHILD_INDEX);
				reheapDown(LEFT_CHILD_INDEX);
				optimalReheapUp(elementIndex - 1);
			} else {
				// right child is bigger
				swap(PARENT_INDEX, RIGHT_CHILD_INDEX);
				reheapDown(RIGHT_CHILD_INDEX);
				optimalReheapUp(elementIndex - 2);
			}
		} else
			optimalReheapUp(elementIndex - 1);
	}

	/**
	 * This is the optimal reheap helper that will be called each time the
	 * elementIndex is in the left subtree. This method will be called by the
	 * {@link #optimalReheapUp(int)} method.
	 * 
	 * @param elementIndex
	 */
	public void optimalReheapLeftTreeHelper(int elementIndex) {
		final int PARENT_INDEX = (elementIndex - 1) / 2;
		// System.out.println("LEFT SUBTREE - debug");
		if (element[PARENT_INDEX] < element[elementIndex]) {
			swap(PARENT_INDEX, elementIndex);
			reheapDown(elementIndex);
			optimalReheapUp(elementIndex - 1);
		}else
			optimalReheapUp(elementIndex -1);
	}

	/**
	 * This method will reheapify the tree in a downwards fashion. This method
	 * will be called by the {@link #remove()} method. After this methods is
	 * called, all of the elements should be sorted in their respective
	 * positions.
	 * 
	 * @param elementIndex
	 */
	public void reheapDown(int elementIndex) {
		final int PARENT_INDEX = elementIndex;
		final int LEFT_CHILD_INDEX = 2 * PARENT_INDEX + 1;
		final int RIGHT_CHILD_INDEX = 2 * PARENT_INDEX + 2;

		try {
			int childMax = Math.max(element[RIGHT_CHILD_INDEX],
					element[LEFT_CHILD_INDEX]);

			// parent is less than a child
			if (element[PARENT_INDEX] < childMax) {
				// left child greater than right child
				if (element[LEFT_CHILD_INDEX] > element[RIGHT_CHILD_INDEX]) {
					swap(PARENT_INDEX, LEFT_CHILD_INDEX);
					reheapDown(LEFT_CHILD_INDEX);
				} else {
					// right child greater than left child
					swap(PARENT_INDEX, RIGHT_CHILD_INDEX);
					reheapDown(RIGHT_CHILD_INDEX);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	/**
	 * This method traverses through and prints out all of the elements in the
	 * heap tree.
	 */
	public void print() {
		for (int i : element) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	/**
	 * This method traverses through and prints out the first 10 elements in the
	 * tree
	 */
	public void printFirstTen() {
		for (int i = 0; i < 10; i++) {
			System.out.print(element[i] + " ");
		}
	}

	/**
	 * This method removes only the first 10 values from the heap tree.
	 */
	public void removeFirstTen() {
		for (int i = 0; i < 10; i++) {
			remove();
		}
	}
}
