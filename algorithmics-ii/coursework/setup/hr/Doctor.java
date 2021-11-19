import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Class Doctor, to represent a single doctor
 */
public class Doctor {

	/** The doctor's id, counting from 1 */
	private int id;
	
	/** The doctor's preference list, in preference order */
	private ArrayList<Hospital> preferenceList;
	private ArrayList<Hospital> rejectList;
	
	/** A list iterator over the doctor's preference list */
	private Iterator<Hospital> iterator;
	
	/** The doctor's assigned hospital, or null if unassigned */
	private Hospital assignment;
	
	/**
	 * Instantiates a new Doctor
	 * @param i the Doctor's id
	 */
	public Doctor(int i) {
		id = i;
		preferenceList = new ArrayList<Hospital>();
		rejectList = new ArrayList<Hospital>();
		assignment = null;
	}

	/**
	 * Gets the doctor's id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the doctor's preference list
	 * @return the preference list
	 */
	public ArrayList<Hospital> getPreferenceList() {
		return preferenceList;
	}

	/**
	 * Pops the first hospital in the preference list
	 * @return the first hospital in preference list
	 */
	public Hospital popPref() {
		if (preferenceList.isEmpty()) {
			return null;
		}
		Hospital hospital = preferenceList.remove(0);
		rejectList.add(hospital);
		return hospital;
	}

	/**
	 * Adds a hospital to the end of the doctor's preference list
	 * @param hospital the hospital to be added
	 */
	public void addPref(Hospital hospital) {
		preferenceList.add(hospital);
	}

	/**
	 * Sets the iterator to the start of the doctor's preference list
	 */
	public void setIterator() {
		iterator = preferenceList.iterator();
	}
	
	/**
	 * Gets the doctor's assigned hospital, or null if unassigned
	 * @return the assignment
	 */
	public Hospital getAssignment() {
		return assignment;
	}

	/**
	 * Assigns the doctor to the given hospital
	 * @param hospital the hospital
	 */
	public void assignTo(Hospital hospital) {
		if (assignment != null) {
			
		}

		this.assignment = hospital;
	}

	/**
	 * return doctor's id as String representation
	 */
	public String toString(){
		return Integer.toString(id);
	}

	public ArrayList<Hospital> getRejects() { return rejectList; }
	
	public void resetPreferences() {
		rejectList.addAll(preferenceList);
		preferenceList = rejectList;
	}
}