/**
 * Main class containing main method
 */
public class Main {
	/**
	 * The main method
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		// parse instance from first input file
		Parser parser = new Parser();
		Instance instance = parser.parseInstance(args[0]);
		// create Algorithm object, supplying instance
		Algorithm algorithm = new Algorithm(instance);
		boolean matchingValid;
		if (args.length > 1) // matching given
			// parse matching from second input file
			matchingValid = parser.parseMatching(args[1]);
		else {
			// run RGS algorithm / Kiraly's algorithm
			algorithm.run();
			// check constructed matching for validity
			matchingValid = algorithm.checkMatching();
		}
		if (matchingValid) {
			// print matching to console
			algorithm.printMatching();
			// check matching for stability
			algorithm.checkStability();
		} else
		    System.out.println("The matching is invalid!");
	}
}

