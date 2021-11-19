import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays; 

/**
 * The Class Algorithm, containing methods to run either the RGS algorithm
 * for HR or Kiraly's algorithm for HRT, to print the matching and to check
 * the matching for stability
 */
public class Algorithm {	

	/** The HR/HRT instance */
	private Instance instance;

	/**
	 * Instantiates a new Algorithm object
	 * @param instance the HR/HRT instance
	 */
	public Algorithm (Instance instance) {
		this.instance = instance;
	}

	/**
	 *  Kiraly's algorithm for HRT
	 */
	public void run() 
	{
		ArrayList<Doctor> unmatched = new ArrayList<>(Arrays.asList(this.instance.getAllDoctors()));
		Doctor doctor = unmatched.remove(0);

		while (doctor != null && !doctor.isPromoted()) 
		{
			Hospital hospital = doctor.popPref();

			if (hospital == null) 
			{
				if (doctor.isPromoted())
				{
					continue;
				}
				else
				{
					hospital = doctor.promote();
				}
			} 

			if (hospital.isUnderSubscribed())  
			{
				doctor.assignTo(hospital);
				hospital.incrementNumAssignees();
				hospital.addAssignee(doctor);
			} 
			else 
			{
				Doctor worst = hospital.getWorstAssignee();
				if (hospital.prefers(doctor))
				{
					doctor.assignTo(hospital);
					hospital.addAssignee(doctor);
					hospital.removeAssignee(worst);
					worst.assignTo(null);
					unmatched.add(worst);
				} 
				else 
				{
					unmatched.add(doctor);
				}
			}
		

			if (unmatched.isEmpty()) 
			{
				doctor = null;
			} else 
			{
				doctor = unmatched.remove(0);
			}
		}

		for (Doctor doc:instance.getAllDoctors()) {
			doc.resetPreferences();
		}
	}		

	/**
	 * Prints the matching to the console
	 */
	public void printMatching() {
		Doctor [] doctors = instance.getAllDoctors();
		int matched = 0;

		System.out.println("Matching:");
		for (Doctor d : doctors) {
			if (d.getAssignment() == null) {
				System.out.println("Doctor " + d.getId() + " is unmatched");
			} else {
				System.out.println("Doctor " + d.getId() + " is assigned to hospital " + d.getAssignment().getId());
				matched++;
			}
		}
		System.out.println("Matching size: " + matched);
	}

	/**
	 * Checks the matching for stability
	 */
	public void checkStability() {
		int[] worstRanked = new int[instance.getAllHospitals().length];
		Arrays.fill(worstRanked, -1);

		for (Doctor doctor:instance.getAllDoctors()) {
			Hospital hospital = doctor.getAssignment();
			if (hospital != null) {
				int rank = hospital.getRank(doctor);
				if (rank > worstRanked[hospital.getId()-1]) {
					worstRanked[hospital.getId()-1] = rank;
				}
			}
		}

		boolean stable = true;

		for (Doctor doctor:instance.getAllDoctors()) {
			ArrayList<Hospital> preferenceList = doctor.getPreferenceList();
			Hospital hospital = preferenceList.remove(0);
			while (doctor.getAssignment() != hospital) {
				int worstRank = worstRanked[hospital.getId()-1];
				int doctorRank = hospital.getRank(doctor);
				if (doctorRank<worstRank) {
					System.out.println("Blocking pair between doctor " + doctor.getId() + " and hospital " + hospital.getId());
					stable = false;
				}
				if (!preferenceList.isEmpty()) {
					hospital = preferenceList.remove(0);
				} else {
					break;
				}
			}
		}

		if (stable) {
			System.out.println("Matching is stable");
		} else {
			System.out.println("Matching is not stable");
		}
	}

	/**
	 * Determines whether we have a valid matching
	 * @return true if we have a valid matching, false otherwise
	 */
	public boolean checkMatching() {
	        // get all doctors and hospitals
	        Doctor [] doctors = instance.getAllDoctors();
		Hospital [] hospitals = instance.getAllHospitals();
		// reset number of assignees of each hospital to 0
		for (Hospital h : hospitals)
		        h.resetNumAssignees();
		// iterate over each doctor in turn
		for (Doctor d : doctors) {
		        // check if d is assigned
		        if (d.getAssignment() != null) {
			        // get hospital h that d is assigned to
			        Hospital h = d.getAssignment();
			        // determine whether h finds d acceptable
		                if (h.getRank(d) < 0) {
				        // h finds d unacceptable, matching invalid
				        System.out.println("Hospital "+h.getId()+" does not find doctor "+d.getId()+" acceptable!");
					return false;
				}
				// d is a legal assignee of h
				h.incrementNumAssignees();
		        }
	        }
                // check whether a hospital is oversubscribed
		for (Hospital h : hospitals)
		        if (h.isOverSubscribed()) {
			        System.out.println("Hospital "+h.getId()+" is oversubscribed!");
				return false;
			}
		// we have a valid matching
		return true;
	}
}
