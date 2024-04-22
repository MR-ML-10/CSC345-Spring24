/**
 * This Clinic class manages patient treatment based on urgency using a priority
 * queue system. Patients with conditions exceeding a defined urgency threshold
 * (er_threshold) are redirected to the ER, while others are queued for
 * treatment based on their urgency levels. The class utilizes a PatientQueue
 * for prioritizing patients and a PHashtable for quick patient lookup.
 *
 *
 * @author Doom Al Rajhi
 * @file: Clinic.java
 * @assignment: Programming Assignment #4
 * @course: CSC 345 Spring 2024
 * @date: 04/11/2024
 * @version: 1.0
 */

public class Clinic {
	private int er_threshold;
	private int sentToEr;
	private PatientQueue patientQueue;
	private PHashtable patientHashtable;
	private int processed;
	private int seenByDoc;
	private int walkedOut;
	private long lastTime = 0;

	/**
	 * Constructor: Create a new Clinic instance. The er_threshold indicates the
	 * highest level of urgency the clinic can handle. If a patient’s urgency level
	 * exceeds that, they are sent to the ER instead.
	 * 
	 * @param er_threshold
	 */
	public Clinic(int er_threshold) {
		this.er_threshold = er_threshold;
		sentToEr = 0;
		processed = 0;
		seenByDoc = 0;
		walkedOut = 0;
		patientQueue = new PatientQueue();
		patientHashtable = new PHashtable();
	}

	/**
	 * Return the ER threshold.
	 * 
	 * @return
	 */
	public int er_threshold() {
		return er_threshold;
	}

	/**
	 * Process the new patient with the given name and urgency. If their urgency
	 * level exceeds er_threshold, send them to the ER. If not, add them to the
	 * patient queue. Return the name of the patient.
	 * 
	 * The expected runtime should be no worse than O(logN) where N is the number of
	 * patients currently in the queue.
	 * 
	 * @param name    the name of the patient
	 * @param urgency the urgency level of the patient
	 * @return the name of the patient
	 */

	public String process(String name, int urgency) {
		Patient patient = patientHashtable.get(name);

		if (patient != null && patient.isHandled()) {
			// patient has been already processed
			return null;
		}

		// create new patient record if patient doesn't exist
		if (patient == null) {
			patient = new Patient(name, urgency, lastTime++);
			patientHashtable.put(patient);
		}

		// if urgency is above the threshold, send them to the ER
		if (urgency > er_threshold) {
			patient.setHandled(true);
			handle_emergency(name, urgency);
			return name;
		}

		// send patient to the queue for doc
		if (!patient.isHandled()) {
			patientQueue.insert(patient);
			processed++;
		}
		return patient.name();
	}

	/**
	 * Send the next patient in the queue to be seen by a doctor.
	 * 
	 * The expected runtime should be no worse than O(logN) where N is the number of
	 * patients currently in the queue.
	 * 
	 * @return the name of the patient
	 * @throws EmptyQueueException if the queue is empty
	 */
	public String seeNext() {
		try {
			Patient nextPatient = patientQueue.removeNext();
			patientHashtable.remove(nextPatient.name());
			nextPatient.setHandled(true);
			seenByDoc++;
			return nextPatient.name();
		} catch (Exception EmptyQueueException) {
			return null;
		}
	}

	/**
	 * A patient with the given name experiences an emergency while waiting. Update
	 * their urgency level. If it exceeds the er_threshold, send them directly to
	 * the ER (and remove them from the queue). Otherwise, update their urgency
	 * level. Return true if the Patient was sent to the ER and false otherwise.
	 * 
	 * The expected runtime should be no worse than O(logN) where N is the number of
	 * patients currently in the queue.
	 * 
	 * @param name the name of the patient
	 * @param urg  the urgency level of the patient
	 * @return true if the Patient was sent to the ER and false otherwise
	 */
	public boolean handle_emergency(String name, int urgency) {
		// getting the patient
		Patient patient = patientHashtable.get(name);

		// patient doesn't exist or has already been processed
		if (patient == null || patient.isHandled()) {
			return false;
		}

		// update urgency and check if it exceeds the threshold
		patient.setUrgency(urgency);
		if (urgency > er_threshold) {
			patientQueue.remove(patient.name());
			patientHashtable.remove(name);
			patient.setHandled(true);
			sentToEr++;
			return true;
		}
		return false;
	}

	/**
	 * A patient with the given name walks out before being seen by a doctor. Remove
	 * them from the queue.
	 * 
	 * The expected runtime should be no worse than O(logN) where N is the number of
	 * patients currently in the queue.
	 * 
	 * @param name the name of the patient
	 */
	public void walk_out(String name) {
		Patient patient = patientHashtable.get(name);
		// patient doesn't exist or has already been processed
		if (patient == null || patient.isHandled()) {
			return;
		}
		// remove the patient from the records
		patientQueue.remove(name);
		patientHashtable.remove(name);
		patient.setHandled(true);
		walkedOut++;
	}

	/**
	 * Return the number of patients that have been processed.
	 * 
	 * @return
	 */
	public int processed() {
		return processed;
	}

	/**
	 * Return the number of patients that have been sent to the ER.
	 * 
	 * @return
	 */
	public int sentToER() {
		return sentToEr;
	}

	/**
	 * Return the number of patients that have been seen by a doctor at the clinic.
	 * 
	 * @return
	 */
	public int seenByDoctor() {
		return seenByDoc;
	}

	/**
	 * Return the number of patients that walked out before being seen by a doctor.
	 * 
	 * @return
	 */
	public int walkedOut() {
		return walkedOut;
	}
}