/**
 * This class implements a deque (double-ended queue) using a dynamic circular
 * array. It provides methods to add and remove elements from both ends of the
 * queue, peek at elements, check if the deque is empty, and get the size of the
 * deque. The deque dynamically resizes itself based on the number of elements
 * it contains, ensuring efficient use of memory. However, the main difference
 * is this class resize itself by constant value instead of multiplying.
 *
 * The Deque supports generic types, allowing it to store any type of objects.
 * It offers constant time performance for the add and remove operations at both
 * ends under most circumstances, except when resizing is necessary.
 *
 * @author Doom Al Rajhi
 * @file: Deque1.java
 * @assignment: Programming Assignment #2
 * @course: CSC 345 Spring 2024
 * @date: 02/12/2024
 * @version 1.0
 * @param <T> The type of elements held in this deque
 */
public class Deque1<T> {

	private static final int DEFAULT_SIZE = 16;
	public Array<T> array;
	private int size;
	private int front, rear;
	private static int constantAmount = 5;

	/**
	 * Constructs an empty deque with a default initial capacity.
	 */
	public Deque1() {
		this.array = new Array<>(DEFAULT_SIZE);
		this.size = 0;
		this.front = -1;
		this.rear = -1;
	}

	/**
	 * Constructs an empty deque with the specified initial capacity.
	 * 
	 * @param cap the initial capacity of the deque
	 */
	public Deque1(int cap) {
		this.array = new Array<>(cap);
		this.size = 0;
		this.front = -1;
		this.rear = -1;
	}

	/**
	 * Inserts the specified element at the front of this deque.
	 * 
	 * @param num the element to add
	 */
	public void addFirst(T num) {
		// if Deque is full, resize
		if (isFull()) {
			resize();

		} else if (isEmpty()) {
			rear = 0; // Also update rear if empty
		}
		front = (front - 1 + array.length()) % array.length();
		array.setVal(front, num);
		size++;
	}

	/**
	 * Inserts the specified element at the end of this deque.
	 * 
	 * @param num the element to add
	 */
	public void addLast(T num) {
		// if Deque is full, resize
		if (isFull()) {
			resize();
		} else if (isEmpty()) {
			front = 0;
		}
		rear = (rear + 1) % array.length();
		array.setVal(rear, num);
		size++;
	}

	/**
	 * Removes and returns the first element from this deque.
	 * 
	 * @return the first element from this deque
	 * @throws EmptyDequeException if this deque is empty
	 */
	public T removeFirst() throws EmptyDequeException {
		// if list is empty throw
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		T elementHolder = array.getVal(front); // contains the first index element

		// if we have only 1 element in the deque, we reset the pointers
		if (front == rear) {
			front = -1;
			rear = -1;
		}
		front = (front + 1) % array.length();
		size--; // decreasing the size

		return elementHolder;
	}

	/**
	 * Removes and returns the last element from this deque.
	 * 
	 * @return the last element from this deque
	 * @throws EmptyDequeException if this deque is empty
	 */
	public T removeLast() throws EmptyDequeException {
		// if list is empty throw
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		T elementHolder = array.getVal(rear);

		// if we have only 1 element in the deque, we reset the pointers
		if (front == rear) {
			front = -1;
			rear = -1;
		} else {
			// updating the index to the rear element
			rear = (rear - 1 + array.length()) % array.length();
		}
		size--; // decreasing the size
		return elementHolder;
	}

	/**
	 * Retrieves, but does not remove, the first element of this deque.
	 * 
	 * @return the first element of this deque
	 * @throws EmptyDequeException if this deque is empty
	 */
	public T peekFirst() throws EmptyDequeException {
		// if list is empty throw
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		return array.getVal(front);
	}

	/**
	 * Retrieves, but does not remove, the last element of this deque.
	 * 
	 * @return the last element of this deque
	 * @throws EmptyDequeException if this deque is empty
	 */
	public T peekLast() throws EmptyDequeException {
		// if list is empty throw
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		return array.getVal(rear);
	}

	/**
	 * Returns true if this deque contains no elements.
	 * 
	 * @return true if this deque is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements in this deque.
	 * 
	 * @return the number of elements in this deque
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the deque is full.
	 * 
	 * @return true if this deque is full
	 */
	boolean isFull() {
		return (rear + 1) % array.length() == front;
	}

	/**
	 * Resizes the deque to accommodate more elements or to shrink its size.
	 */
	private void resize() {
		int newSize = -1;
		newSize = newSizeCalculation();

		Array<T> newDequeArray = new Array<>(newSize);

		for (int i = 0; i < size; i++) {
			newDequeArray.setVal(i, array.getVal(front));
			// iterate to next by updating the pointer
			front = (front + 1) % array.length();
		}

		// Update front and rear pointers
		front = 0;
		rear = size - 1;
		array = newDequeArray;
	}

	/**
	 * Calculates the new size for resizing the deque.
	 * 
	 * @return the new calculated size for the deque
	 */
	private int newSizeCalculation() {
		// ensuring to not go lower than the original capacity and
		boolean largerThanOriginalCapacity = size > DEFAULT_SIZE;
		int oneQuarterArraySize = array.length() / 4;

		// Check if the array needs to be doubled
		if (size == array.length()) {
			return array.length() + constantAmount;
			// or shirnk to half
		} else if (largerThanOriginalCapacity && size <= oneQuarterArraySize) {
			int newSize = array.length() - constantAmount;
			return Math.max(DEFAULT_SIZE, newSize);
		}

		return -1; // no resizing needed
	}

	/**
	 * Returns the access count of the underlying array.
	 * 
	 * @return the access count
	 */
	public int getAccessCount() {
		return array.getAccessCount();
	}

	/**
	 * Resets the access count of the underlying array to zero.
	 */
	public void resetAccessCount() {
		array.resetAccessCount();
	}
}
