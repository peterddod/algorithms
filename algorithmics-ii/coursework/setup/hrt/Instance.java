/**
 * The Class Instance, to represent an HR / HRT problem instance
 */
public class Instance {
	
	/** The array of Doctor objects */
	private Doctor [] doctors;
	
	/** The array of Hospitals objects */
	private Hospital [] hospitals;
	
	/**
	 * Instantiates a new instance
	 * @param numDoctors the number of doctors
	 * @param numHospitals the number of hospitals
	 */
	public Instance(int numDoctors, int numHospitals) {
		// record the number of doctors in a static variable
		// of class Hospital
		Hospital.numDoctors = numDoctors;
		
		// instantiate Doctor and Hospital arrays
		doctors = new Doctor[numDoctors];
		hospitals = new Hospital[numHospitals];
		
		// instantiate Doctor and Hospital objects within arrays
		for (int index = 1; index <= numDoctors; index++)
			doctors[index - 1] = new Doctor(index);
		for (int index = 1; index <= numHospitals; index++)
			hospitals[index - 1] = new Hospital(index);
	}

	/**
	 * Gets the array of Doctor objects
	 * @return the array of Doctor objects
	 */
	public Doctor [] getAllDoctors() {
		return doctors;
	}

	/**
	 * Gets the array of Hospital objects
	 * @return the array of Hospital objects
	 */
	public Hospital [] getAllHospitals() {
		return hospitals;
	}

	/**
	 * Gets the Doctor object with a given id, assumes id counts from 1
	 * @param id the Doctor's id
	 * @return the Doctor object with the given id
	 */
	public Doctor getDoctorById(int id) {
		return doctors[id - 1];
	} 

	/**
	 * Gets the Hospital object with a given id, assumes id counts from 1
	 * @param id the Hospital's id
	 * @return the Hospital object with the given id
	 */
	public Hospital getHospitalById(int id) {
		return hospitals[id - 1];
	}
}
