import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Class Parser, to handle reading in an instance and a matching
 */
public class Parser {

	/** The HR/HRT instance */
	private Instance instance;

	/**
	 * Parses the instance file into an Instance object
	 * @param fileName the name of the instance file
	 * @return the instance
	 */
	public Instance parseInstance(String fileName) {
		try {
			// open input file
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			// firstly obtain numbers of doctors and hospitals
			String line = scanner.nextLine().trim();
			int numDoctors  = Integer.parseInt(line);
			line = scanner.nextLine().trim();
			int numHospitals = Integer.parseInt(line);
			// create Instance object
			instance = new Instance(numDoctors, numHospitals);
			
			//read in the doctors' preference lists, line by line
			for (int index = 1; index <= numDoctors; index++) {
				line = scanner.nextLine();
				// get Doctor object with id index
				Doctor doctor = instance.getDoctorById(index);
				// split line into tokens delimited by a colon 
				String [] doctorInfo = line.trim().split(":");
				// first token is Doctor id
				// second token should be preference list
				if (doctorInfo.length > 1) {
					// split preference list into tokens, delimited by whitespace
					String [] preferences = doctorInfo[1].trim().split("\\s+"); 
					// iterate over tokens
					for (String preference : preferences) {
						// get hospital id
						int hospId = Integer.parseInt(preference);
						// add corresponding Hospital object to Doctor's preference list
						doctor.addPref(instance.getHospitalById(hospId));
					}
				}
				// initialise Doctor's iterator to start of preference list
				doctor.setIterator();
			}			
			
			//read in the hospitals' capacities and preference lists, line by line
			for (int index = 1; index <= numHospitals; index++) {
				line = scanner.nextLine();
				Hospital hospital = instance.getHospitalById(index);
				String [] hospitalInfo = line.trim().split(":");

				// first token is hospital id
				// second token is hospital capacity
				hospital.setCapacity(Integer.parseInt(hospitalInfo[1].trim()));

				// determine whether preference list is non-empty
				if (hospitalInfo.length > 2) {
					// copy preference list into String, trimming leading / trailing whitespace
					String preferences = hospitalInfo[2].trim();
					// create StringBuilder object from preferences String for faster processing
					StringBuilder prefs = new StringBuilder(preferences);
					// keep track of rank, starting from 1 initially
					int rank = 1;
					// maintain boolean to determine whether current pref list entry is in a tie
					boolean inTie = false;
					// iterate as long as prefs is non-emtpy
					while (prefs.length() > 0) {
						// iterate past a space
						if (prefs.charAt(0)==' ')
							prefs.delete(0,1);
						// if open bracket, we are now entering a tie
						else if (prefs.charAt(0)=='(') {
							inTie = true;
							prefs.delete(0,1);
						}
						// if close bracket, we are now leaving a tie
						else if (prefs.charAt(0)==')') {
							inTie = false;
							// increment rank for next preference list entry
							rank++;
							prefs.delete(0,1);
						}
						else {
							// we should have an integer id representing a doctor
							int index2;
							for (index2 = 0; index2 < prefs.length(); index2++) {
								// read character at position index2 of prefs
								char c = prefs.charAt(index2);
								// if this is not numeric, halt loop
								if (c < '0' || c > '9')
									break;
							}
							// all characters between 0..(index2-1) inclusive are doctor id
							String docIdStr = prefs.substring(0, index2);
							int docId = Integer.parseInt(docIdStr);
							// add Doctor with given id and rank to Hospital preference list
							hospital.addPref(instance.getDoctorById(docId), rank);
							// remove Doctor id from prefs ready for parsing to continue 
							prefs.delete(0,index2);
							// if we are not within a tie, rank must increment
							if (!inTie)
								rank++;
						}						
					}
				}
			}
			scanner.close();
		// catch blocks to deal with potential issues with the input file
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}
		catch (NumberFormatException e) {
			System.out.println("Instance file not formatted correctly!");
			System.exit(0);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Instance file not formatted correctly!");
			System.exit(0);
		}
		return instance;
	}

	/**
	 * Parses the matching file and populates the existing
	 * instance with the matching read in
	 * @return true if the matching is valid, false otherwise
	 */
	public boolean parseMatching(String fileName) {
                String line="";
		try {
			// open input file
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);

			// read in the matching line by line
			while (scanner.hasNextLine()) {
				// read next line
				line = scanner.nextLine();
				// split line into tokens delimited by brackets, 
				// commas and spaces
				String [] tokens = line.trim().split("[(), ]+");
				// second token should be doctor id
				int doctorId = Integer.parseInt(tokens[1]);
				// third token should be hospital id
				int hospitalId = Integer.parseInt(tokens[2]);
				// get Doctor and Hospital objects from ids
				Doctor doctor = instance.getDoctorById(doctorId);
				Hospital hospital = instance.getHospitalById(hospitalId);
			        // determine whether hospital finds doctor acceptable
		                if (hospital.getRank(doctor) < 0) {
				        // hospital finds doctor unacceptable, matching invalid
				        System.out.println("Hospital "+hospital.getId()+" finds doctor "+doctor.getId()+" unacceptable!");
					return false;
				}
				// determine whether doctor is already assigned
				else if (doctor.getAssignment() != null) {
				        System.out.println("Doctor "+doctor.getId()+" is multiply assigned!");
					return false;
				}
				else {
			                // doctor is a legal assignee of hospital	       			       
				        doctor.assignTo(hospital);
					hospital.incrementNumAssignees();
				}

			}
			// now get all hospitals
			Hospital [] hospitals = instance.getAllHospitals();
			// check whether a hospital is oversubscribed
			for (Hospital hospital : hospitals)
			        if (hospital.isOverSubscribed()) {
				        System.out.println("Hospital "+hospital.getId()+" is oversubscribed!");
					return false;
				}
			// we have a valid matching
			scanner.close();
		// catch blocks to deal with potential issues with the input file
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}
		catch (NumberFormatException e) {
			System.out.println("Matching file not formatted correctly!");
			System.out.println(line);
			System.exit(0);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Matching not consistent with instance!");
			System.out.println(line);
			System.exit(0);
		}
		return true;
	}
}
