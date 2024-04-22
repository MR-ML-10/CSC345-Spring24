/**
 * This class implements a hashtable specifically designed to store Patient
 * objects. The hashtable uses open addressing with quadratic probing to resolve
 * collisions, optimizing for scenarios where the load factor approaches 0.5.
 * Each slot in the hashtable array can be marked with a tombstone to indicate
 * previously occupied slots that are now available due to the removal of an
 * item. This allows the hashtable to handle deletions effectively without
 * interrupting the probe sequence. The class provides basic operations such as
 * insertions (put), deletions (remove), and lookups (get) by patient name. The
 * capacity of the hashtable is dynamically increased as necessary by resizing
 * to the next prime number greater than double the current size, which helps
 * maintain the efficiency of probing by reducing the likelihood of clustering.
 * This class is ideal for use in applications where rapid access to
 *
 * 
 * @author Doom Al Rajhi
 * @file: PHashtable.java
 * @assignment: Programming Assignment #4
 * @course: CSC 345 Spring 2024
 * @date: 04/11/2024
 * @version 1.0
 */
public class PHashtable {
	private Patient[] table;
	private static final int DEFAULT_TABLE_SIZE = 11;
	private int size;
	private boolean[] tombstone;

	/**
	 * Constructor–creates an empty hashtable with a starting capacity of 11
	 */
	public PHashtable() {
		this.table = new Patient[DEFAULT_TABLE_SIZE];
		tombstone = new boolean[DEFAULT_TABLE_SIZE];
		setTombstone();
	}

	/**
	 * Constructor–creates an empty hashtable with a starting capacity of cap
	 * 
	 * @param cap the starting capacity of the hashtable
	 */
	public PHashtable(int cap) {
		this.table = new Patient[cap];
		tombstone = new boolean[cap];
		setTombstone();
	}

	/**
	 * Put Patient p into the table.
	 * 
	 * @param p the Patient to add
	 */
	public void put(Patient p) {
		if (isFull()) {
			resize();
		}
		// get index
		int index = hash(p.name());

		// if the spot is available or marked by a tombstone,
		if (!slotIsOccupied(index) || tombstone[index] || table[index].name().equals(p.name())) {
			// or it's an update for the same patient
			if (!slotIsOccupied(index) || tombstone[index]) {
				// adding a new patient
				size++;
			} // If not, updating an existing patient.
			table[index] = p;
			updateTombstone(index, false, false);
		} else {
			// using quadratic probing to find the new spot
			int newIndex = quadraticProbing(index);
			// the spot is valid
			if (newIndex != -1) {
				table[newIndex] = p;
				updateTombstone(newIndex, false, false);
				size++;
			}
		}
	}

	/**
	 * Return the Patient with the given name.
	 * 
	 * @param patientName the patient name
	 * @return the Patient with the given name, or null if the Patient is not
	 */
	public Patient get(String patientName) {
		Patient retPatient = null;
		int index = hash(patientName);

		// checking if current slot is not null, and not deleted and matches the given
		if (slotIsOccupied(index) && !tombstone[index] && table[index].name().equals(patientName)) {
			retPatient = table[index];
		} else {
			// using quadratic probing to find the new index
			int newSpot = quadraticProbing(index, patientName);
			// if newSpot is valid
			if (newSpot != -1) {
				retPatient = table[newSpot];
			}
		}
		// Patient not found
		return retPatient;
	}

	/**
	 * Remove and return the Patient with the given name.
	 * 
	 * @param name the name of the patient to be removed
	 * @return the Patient with the given name, or null if the Patient is not
	 */
	public Patient remove(String name) {
		Patient removedPatient = null;
		int index = hash(name);

		// if patient is found
		if (slotIsOccupied(index) && !tombstone[index] && table[index].name().equals(name)) {
			removedPatient = table[index];
			updateTombstone(index, true, true);
		} else {
			// using quadratic probing we get the new spot
			int newSpot = quadraticProbing(index, name);

			// the new spot is valid
			if (newSpot != -1) {
				removedPatient = table[newSpot];
				updateTombstone(newSpot, true, true);
			}
		}
		if (removedPatient != null) {
			size--;
		}
		return removedPatient;
	}

	/**
	 * Return the number of patients in the table.
	 * 
	 * @return the number of patients in the table
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Performs quadratic probing to find the next available slot in the hashtable
	 * that matches the given name. This method is used primarily when handling
	 * collision scenarios where the initial hash index is already occupied. The
	 * probing sequence increases quadratically, which helps in evenly distributing
	 * key indices in the hash table and reduces clustering.
	 *
	 * @param index the initial hash index calculated for the name
	 * @param name  the name of the patient to be placed in the hashtable
	 * @return the index of either a matching existing entry or -1 if no suitable
	 *         slot is found
	 */
	private int quadraticProbing(int index, String name) {
		int j = 1;
		while (j < table.length) {
			// trying to find an empty slot
			int newSpot = (index + (j * j)) % table.length;

			// checking if current slot is not null, and not deleted and matches the given
			// name
			if (slotIsOccupied(newSpot) && !tombstone[newSpot] && table[newSpot].name().equals(name)) {
				return newSpot;
				// checking if current slot is null, and not deleted
			} else if (!slotIsOccupied(newSpot) && !tombstone[newSpot]) {
				return -1;
			}
			j++;
		}
		return -1;
	}

	/**
	 * Performs quadratic probing to find the next available or reusable slot in the
	 * hashtable. This method is used to resolve collisions and to find empty slots
	 * for new entries after the initial position calculated by the hash function is
	 * found occupied or marked by a tombstone. The quadratic nature of the probing
	 * sequence helps reduce clustering and secondary collision issues.
	 *
	 * @param index the initial hash index
	 * @return the index of an available slot or -1 if no suitable slot is found
	 *         after full probing
	 */
	private int quadraticProbing(int index) {
		int j = 1;
		while (j < table.length) {
			// by quadratic probing, we are trying to find an empty slot
			int newSpot = (index + (j * j)) % table.length;
			if (!slotIsOccupied(newSpot) || tombstone[newSpot]) {
				updateTombstone(newSpot, false, false); // Clear the tombstone
				return newSpot;
			}
			j++;
		}
		return -1;
	}

	/**
	 * resize the table to the next prime number greater than the current size.
	 */
	private void resize() {
		int newSize = findTheNextPrimeNumber(table.length * 2);

		Patient[] oldTable = table;
		table = new Patient[newSize];
		// create a new tombstone with the new size
		tombstone = new boolean[newSize];
		setTombstone();
		// resetting the size since put will increase iit
		size = 0;

		// re-inserting the old patients into the new table
		for (Patient p : oldTable) {
			if (p != null) {
				put(p);
			}
		}
	}

	/**
	 * find the next prime number greater than the current size.
	 * 
	 * @param oldSize given size of the table
	 * @return the new size of the table
	 */
	private int findTheNextPrimeNumber(int oldSize) {
		int nextSize = oldSize + 1;
		while (!isPrime(nextSize)) {
			nextSize++;
		}
		return nextSize;
	}

	/**
	 * checks if the given number is a prime number.
	 * 
	 * @param oldSize given number (size)
	 * @return A true if the given number is a prime number, false otherwise
	 */
	private boolean isPrime(int oldSize) {
		if (oldSize <= 1) {
			return false;
		}
		for (int i = 2; i * i <= oldSize; i++) {
			if (oldSize % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * hashing given string into a number between 0 and size-1
	 * 
	 * @param patientName Patient name
	 * @return an index between 0 and size-1
	 */
	private int hash(String patientName) {
		int hash = 17;
		hash = 31 * hash + patientName.hashCode();
		hash = hash % table.length;
		// if the hash is negative, add the length of the table
		if (hash < 0) {
			hash += table.length;
		}
		return hash;
	}

	/**
	 * initialize the tombstone array with false.
	 */
	private void setTombstone() {
		for (int i = 0; i < tombstone.length; i++) {
			updateTombstone(i, false, true);
		}
	}

	/**
	 * update the tombstone array with true.
	 * 
	 * @param index
	 * @param markNull an indicator to mark the slot as null after removing it
	 */
	private void updateTombstone(int index, boolean value, boolean markNull) {
		table[index] = markNull ? null : table[index];
		tombstone[index] = value;
	}

	/**
	 * checks if the given slot is occupied.
	 * 
	 * @param index current slot
	 * @return a true if the given slot is occupied, false otherwise
	 */
	private boolean slotIsOccupied(int index) {
		return table[index] != null;
	}

	/**
	 * checks if the table is full.
	 * 
	 * @return true if the table is full, false otherwise
	 */
	private boolean isFull() {
		return loadFactor() >= 1.0 / 2;
	}

	/**
	 * checks the average load factor of the table.
	 * 
	 * @return
	 */
	private double loadFactor() {
		return (double) size / table.length;
	}
}