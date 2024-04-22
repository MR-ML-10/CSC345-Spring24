/**
 * This class implements a priority queue using a max-heap data structure to
 * manage Patient objects based on their urgency level. It supports operations
 * such as inserting a new patient, removing a specific patient by name, and
 * updating the urgency level of a patient. The class handles dynamic resizing
 * of the heap to accommodate more patients as needed. The primary operations
 * provided include insert, which adds a patient to the queue; remove, which
 * removes and returns a patient by name; and getNext, which retrieves the
 * patient with the highest urgency without removing them from the queue. This
 * implementation ensures that patients with the highest urgency are given
 * priority in treatment, making it suitable for use in the clinic class.
 *
 * @author Doom Al Rajhi
 * @file: PatientQueue.java
 * @assignment: Programming Assignment #4
 * @course: CSC 345 Spring 2024
 * @date: 04/11/2024
 * @version: 1.0
 */
public class PatientQueue {
	private Patient[] heap; // max heap;
	private PHashtable patientTable;
	private static final int DEFAULT_HEAP_SIZE = 11;
	private int size;

	/**
	 * Constructor: creates a new PatientQueue with a starting capacity of 11.
	 */
	public PatientQueue() {
		this.heap = new Patient[DEFAULT_HEAP_SIZE];
		this.patientTable = new PHashtable(DEFAULT_HEAP_SIZE);
		this.size = 0;
	}

	/**
	 * Constructor: creates a new PatientQueue with a starting capacity of cap.
	 * 
	 * @param cap the starting capacity of the queue
	 */
	public PatientQueue(int cap) {
		this.heap = new Patient[cap];
		this.patientTable = new PHashtable(cap);
		this.size = 0;
	}

	/**
	 * Insert patient p into the queue.
	 * 
	 * @param patient to be inserted
	 */
	public void insert(Patient patient) {
		if (isFull()) {
			resize();
		}
		heap[size + 1] = patient;
		size++;
		swim(size);
		patientTable.put(patient);
	}

	/**
	 * Remove and return the next patient in the queue, which should be the person
	 * with the highest priority according to the compareTo method of Patient. Throw
	 * the exception if the queue is empty.
	 * 
	 * @return patient in the queue
	 * @throws EmptyQueueException if the queue is empty
	 */
	public Patient removeNext() throws EmptyQueueException {
		// check if the queue is empty before trying to remove an element
		if (size == 0) {
			throw new EmptyQueueException();
		}
		// patient exists
		Patient removedPatient = heap[1];
		// moving the last element to the root
		heap[1] = heap[size];
		heap[size] = null;
		size--;
		removedPatient.setPositionInQueue(-1);
		if (size > 0) {
			// reheapify
			sink(1);
		}
		return removedPatient;
	}

	/**
	 * Return but do not remove the next patient in the queue, which should be the
	 * person with the highest priority according to the compareTo method of
	 * Patient. Throw the exception if the queue is empty.
	 * 
	 * @return the next patient in the queue
	 * @throws EmptyQueueException if the queue is empty
	 */
	public Patient getNext() throws EmptyQueueException {
		// Check if the queue is empty before trying to remove an element
		if (size == 0) {
			throw new EmptyQueueException();
		}
		return heap[1];
	}

	/**
	 * Remove and return the patient with the given name. Return null if the patient
	 * is not in the queue.
	 * 
	 * @param patientName the name of the patient
	 * @return returns the patient with the given name or null if the patient is not
	 */
	public Patient remove(String patientName) {
		Patient patient = patientTable.get(patientName);

		// patient not found
		if (patient == null) {
			return null;
		}

		// patient found, get his index
		int patientIndex = findPatientIndex(patient);
		if (patientIndex == -1) {
			// patient not found in heap
			return null;
		}

		// swapping the patient with the last element
		swap(patientIndex, size);
		Patient removedPatient = heap[size];
		heap[size] = null; // remove the patient
		size--;
		removedPatient.setPositionInQueue(-1);

		if (patientIndex <= size) { // ensuring we're not out of bounds after decrementing size
			// checking the current patient with its parent and decide whether to swim or
			// sink
			if (patientIndex > 1 && heap[size].compareTo(heap[patientIndex / 2]) > 0) {
				swim(patientIndex);
			} else {
				sink(patientIndex);
			}
		}
		patientTable.remove(patientName);
		return removedPatient;
	}

	/**
	 * Update the urgency level of the patient with the given name. The new urgency
	 * value is passed as parameter urgency. Return the updated patient or null if
	 * the patient does not exist in the table.
	 * 
	 * @param name    the name of the patient
	 * @param urgency the new urgency level
	 * @return returns the updated patient or null if the patient does not exist in
	 */
	public Patient update(String name, int urgency) {
		Patient updatedPatient = patientTable.get(name);

		// patient not found
		if (updatedPatient == null) {
			return null;
		}

		// patient found, get his index
		int patientIndex = findPatientIndex(updatedPatient);
		if (patientIndex == -1) {
			// patient not found in heap
			return null;
		}

		// if we found the patient
		if (updatedPatient.urgency() != urgency) {
			int oldUrgency = updatedPatient.urgency();
			updatedPatient.setUrgency(urgency);

			// reheapify
			if (oldUrgency > urgency) {
				sink(patientIndex);
			} else {
				swim(patientIndex);
			}
		}
		return updatedPatient;
	}

	/**
	 * getting the size
	 * 
	 * @return number of patients in the queue
	 */
	public int size() {
		return this.size;
	}

	/**
	 * check if the queue is empty
	 * 
	 * @return true if the queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * swim up the patient at the given index
	 * 
	 * @param index current patient index
	 */
	private void swim(int index) {
		// as long as it didnt reach the root
		while (index > 1) { // arr starts from 1
			int parent = index / 2;
			if (heap[index].compareTo(heap[parent]) > 0) {
				swap(index, parent);
				index = parent;
			} else {
				break;
			}
		}
	}

	/**
	 * sink down the patient at the given index
	 * 
	 * @param patient the patient to sink down
	 */
	private void sink(int index) {
		while (2 * index <= size) { // ensure the left child exists
			int leftChild = 2 * index;
			int rightChild = 2 * index + 1;
			int largerChild = leftChild; // assume left child is larger to start

			// determine if the right child exists and is larger than the left child
			if (rightChild <= size && heap[rightChild].compareTo(heap[leftChild]) > 0) {
				largerChild = rightChild;
			}

			// if the current node is larger than the larger child, stop sinking
			if (heap[index].compareTo(heap[largerChild]) >= 0) {
				break;
			}

			// swap with the larger child
			swap(index, largerChild);
			// continue sinking from the larger child's position
			index = largerChild;
		}
	}

	/**
	 * Swap two indexes in the array
	 * 
	 * @param index  the index to swap
	 * @param parent the index to swap with
	 */
	private void swap(int index, int parent) {
		Patient temp = heap[index];
		heap[index] = heap[parent];
		heap[parent] = temp;
		patientTable.put(heap[index]);
		patientTable.put(heap[parent]);
	}

	/**
	 * resizing the heap by x2
	 */
	private void resize() {
		int newSize = heap.length * 2;
		Patient[] newHeap = new Patient[newSize];
		for (int i = 1; i <= size; i++) {
			newHeap[i] = heap[i];
		}
		heap = newHeap;
	}

	/**
	 * The heap is full if the size is equal to the length of the array minus 1.
	 * since the heap[0] is unused, the actual capacity is heap.length - 1.
	 * 
	 * @return true if the queue is full, false otherwise
	 */
	private boolean isFull() {
		return this.size == this.heap.length - 1;
	}

	/**
	 * Find the index of the given patient in the queue.
	 *
	 * @param patient the patient to find
	 * @return index of the given patient in the queue or -1 if the patient is not
	 */
	private int findPatientIndex(Patient patient) {
		for (int i = 1; i <= size; i++) {
			if (heap[i].equals(patient)) {
				return i;
			}
		}
		return -1;
	}
}