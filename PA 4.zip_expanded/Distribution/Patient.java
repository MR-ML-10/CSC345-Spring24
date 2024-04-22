/**
 * 
 * The Patient class defines the properties and behaviors of patients managed by
 * the PatientQueue. Patients are prioritized based on their urgency level, with
 * tie-breaking by arrival time. This class implements the Comparable interface
 * to allow for sorting within the priority queue.
 *
 * @author Doom Al Rajhi
 * @file: Patient.java
 * @assignment: Programming Assignment #4
 * @course: CSC 345 Spring 2024
 * @date: 04/11/2024
 * @version: 1.0
 */
public class Patient implements Comparable<Patient> {
	private String name;
	private int urgencyLevel;
	private long time; // less time = patient came in earlier
	private int positionInQueue; // position of each patient in the queue
	private boolean handled;

	/**
	 * constructor: creates a Patient with the given name, urgency level, and
	 * time_in
	 * 
	 * @param name
	 * @param urgency
	 * @param time_in
	 */
	public Patient(String name, int urgency, long time_in) {
		this.name = name;
		this.urgencyLevel = urgency;
		this.time = time_in;
		this.positionInQueue = -1; // null
		this.handled = false;
	}

	public String name() {
		return this.name;
	}

	public int urgency() {
		return this.urgencyLevel;
	}

	public void setUrgency(int urgency) {
		this.urgencyLevel = urgency;
	}

	/**
	 * returns a String containing Patient’s name, urgency level, time in, and
	 * position in the queue. These values should be separated by commas. For
	 * example: "Alice Jones, 10, 1000, 3"
	 */
	@Override
	public String toString() {
		return this.name + ", " + this.urgencyLevel + ", " + this.time;
	}

	/**
	 * compares two Patients according to prioritization (positive result means
	 * higher priority relative to other); priority is based on (1) urgency level
	 * and (2) time in, meaning that a higher urgency level automatically gets
	 * priority; if the urgency levels are equal, an earlier time in gets priority
	 */
	@Override
	public int compareTo(Patient other) {
		// First compare by urgency level
		if (this.urgencyLevel > other.urgencyLevel) {
			return 1;
		} else if (this.urgencyLevel < other.urgencyLevel) {
			return -1;
		} else {
			// Urgency levels are equal, compare by time in (earlier time has higher
			// priority)
			if (this.time < other.time) {
				return 1;
			} else if (this.time > other.time) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public boolean isHandled() {
		return handled;
	}

	public void setHandled(boolean handled) {
		this.handled = handled;
	}

	public int posInQueue() {
		return positionInQueue;
	}

	public void setPositionInQueue(int position) {
		this.positionInQueue = position;
	}
}