/**
 * This is the MinHeap class that will hold the data structure for a
 * min-priority queue. This method will include all of the related heap
 * functions that mostly consists of the add, remove and reheapify methods.
 * 
 * @author Emily Le
 * 
 */
class MinHeap {

	private int[] element = new int[20];
	private int[] indexes = new int[20];

	int lastIndex = -1;
	int swapCount = 0;
	int heapSize = 0;

	// variable that will hold index of the last removed element
	int lastRemovedIndex;

	/**
	 * This is the heap constructor that will initialize all of the values of
	 * the element[] to -1 values of indexes[] to be -1 to indicate that the
	 * values are empty.
	 */
	MinHeap() {
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = -1;
			element[i] = -1;
		}
	}

	/**
	 * This method returns the index of the last removed element.
	 * 
	 * @return
	 */
	public int getIndex() {
		return lastRemovedIndex;
	}

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
	 * This method returns the element at the top of the min-heap.
	 * 
	 * @return
	 */
	public int peek() {
		return element[0];
	}

	/**
	 * This is the swap method that will swap two specified elements. This
	 * method will also swap the corresponding two indexes of the elements.
	 * 
	 * @param parentIndex
	 * @param childIndex
	 */
	public void swap(int parentIndex, int childIndex) {
		// swap elements
		int temp = element[childIndex];
		element[childIndex] = element[parentIndex];
		element[parentIndex] = temp;

		// swap indexes
		int indexTemp = indexes[childIndex];
		indexes[childIndex] = indexes[parentIndex];
		indexes[parentIndex] = indexTemp;

		swapCount++;
	}

	/**
	 * This add method will add an element into the heap data structure. It will
	 * also store the index of that element. It will call on the
	 * {@link #reheapUp(int)} method to help sort the elements in their
	 * respective positions/indexes.
	 * 
	 * @param newElement
	 * @param index
	 */
	public void addInsertionWay(int newElement, int index) {
		lastIndex++;
		indexes[lastIndex] = index;
		element[lastIndex] = newElement;

		reheapUp(lastIndex);
	}

	/**
	 * This is the remove method that will remove that root of the tree. It will
	 * use the {@link #reheapDown(int)} method to sort the elements in the
	 * respectful places.
	 * 
	 * @return the removed element
	 */
	public int remove() {
		if (isEmpty() == true) {
			return -1;
		} else {
			int tempElement = element[0];
			// set variable of last removedIndex
			lastRemovedIndex = indexes[0];
			indexes[0] = -1;

			swap(0, lastIndex);
			element[lastIndex] = -1;
			lastIndex--;

			reheapDown(0);
			return tempElement;
		}
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
			return; // swap if parent is -1
		} else if (element[PARENT_INDEX] == -1) {
			swap(PARENT_INDEX, elementIndex);
			reheapUp(PARENT_INDEX);
		} else if (element[PARENT_INDEX] > element[elementIndex]) {
			swap(PARENT_INDEX, elementIndex);
			reheapUp(PARENT_INDEX);
		}
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
			int childMin = Math.max(element[RIGHT_CHILD_INDEX],
					element[LEFT_CHILD_INDEX]);
			// if parent = -1
			if (element[PARENT_INDEX] == -1) {
				if (element[LEFT_CHILD_INDEX] < element[RIGHT_CHILD_INDEX]) {
					swap(PARENT_INDEX, LEFT_CHILD_INDEX);
					reheapDown(LEFT_CHILD_INDEX);
				} else {
					swap(PARENT_INDEX, RIGHT_CHILD_INDEX);
					reheapDown(RIGHT_CHILD_INDEX);
				}
			} else if (element[PARENT_INDEX] > childMin) {
				if (element[childMin] == -1) {
					return;
				} else if (element[LEFT_CHILD_INDEX] < element[RIGHT_CHILD_INDEX]) {
					swap(PARENT_INDEX, LEFT_CHILD_INDEX);
					reheapDown(LEFT_CHILD_INDEX);
				} else {
					swap(PARENT_INDEX, RIGHT_CHILD_INDEX);
					reheapDown(RIGHT_CHILD_INDEX);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	/**
	 * This method traverses through and prints out all of the contents of the
	 * heap tree. This method is mainly used for debugging purposes.
	 */
	public void print() {
		for (int i = 0; i < 20; i++) {
			System.out.print(element[i] + " ");
		}
	}
}
