import java.util.ArrayList;

/**
 * The Class Hospital, to represent a single hospital
 */
public class Hospital {

	/** The hospital's id, counting from 1 */
	private int id;
	
	/** The hospital's preference list, in preference order */
	private ArrayList<Doctor> preferenceList;
	
	/** The hospital's ranking list
	 *  Given a doctor with id i, rankList[i-1] gives the hospital's
	 *  ranking for that doctor 
	 */
	private int [] rankList;
	
	/** The hospital's capacity */
	private int capacity;
	
	/** The number of doctors assigned to the hospital */
	private int numAssignees;

	/** The rank of the hospital's worst assignee
	 */
	private Doctor worstAssignee;
	
	/** The number of doctors in the instance */
	public static int numDoctors;

	/**
	 * Instantiates a new Hospital
	 * @param i the Hospital's id
	 */
	public Hospital(int i) {
		id = i;
		// create empty preference list initially
		preferenceList = new ArrayList<Doctor>();
		// capacity and number of assignees are 0 initially
		capacity = 0;
		resetNumAssignees();
		// instantiate ranking list
		rankList = new int[numDoctors];
		// each doctor initially is given a rank of -1 which means
		// that the hospital finds that doctor unacceptable
		for (int index = 0; index < numDoctors; index++)
			rankList[index] = -1;
	}

	/**
	 * Gets the hospital's id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the hospital's capacity
	 * @param capacity the new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Gets the hospital's preference list
	 * @return the preference list
	 */
	public ArrayList<Doctor> getPreferenceList() {
		return preferenceList;
	}

	/**
	 * Adds a doctor with a given rank to the end of the hospital's 
	 * preference list
	 * @param doctor the doctor to be added
	 * @param rank the rank of the doctor
	 */
	public void addPref(Doctor doctor, int rank) {
		preferenceList.add(doctor);
		rankList[doctor.getId()-1] = rank;
	}

	/**
	 * Finds the rank of the provided doctor in this hospital's
	 * preference list. Returns -1 if the doctor does not appear in the list
	 * @param doctor the doctor
	 * @return the rank of the doctor in this hospital's list
	 */
	public int getRank(Doctor doctor) {
		return rankList[doctor.getId()-1];
	}
	
	/**
	 * Sets the hospital's number of assignees to 0
	 */
	public void resetNumAssignees() {
		numAssignees = 0;
	}

	/**
	 * Increments the hospital's number of assignees
	 */
	public void incrementNumAssignees() {
		numAssignees++;
	}

	/**
	 * Determine whether hospital is oversubscribed
	 * @return true if hospital is oversubscribed, false otherwise
	 */
	public boolean isOverSubscribed() {
	    return (numAssignees > capacity);
	}

	/**
	 * Determine whether hospital is undersubscribed
	 * @return true if hospital is undersubscribed, false otherwise
	 */
	public boolean isUnderSubscribed() {
		return (numAssignees < capacity);
	}

	/**
	 * return hospital's id as String representation
	 */
	public String toString() {
		return Integer.toString(id);
	}

	public Doctor getWorstAssignee() {
		return worstAssignee;
	}

	public void updateWorstAssignee(Doctor doctor) {
		if (worstAssignee == null || rankList[doctor.getId()-1] > rankList[worstAssignee.getId()-1]) {
			worstAssignee = doctor;
		}
	}

	public void setWorstAssignee(Doctor doctor) {
			worstAssignee = doctor;
	}

	public int[] getRanks() { return rankList; }
}
