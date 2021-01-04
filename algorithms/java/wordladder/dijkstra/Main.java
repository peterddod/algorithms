import java.io.*;
import java.util.*;

/**
 program to find word ladder with shortest distance for two words in a dictionary
 distance between elements of the word ladder is the absolute difference in the
 positions of the alphabet of the non-matching letter
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();

		String inputFileName = args[0]; // dictionary
		String word1 = args[1]; // first word
		String word2 = args[2]; // second word
		ArrayList<String> dict = new ArrayList<String>(); // input structure for dictionary
		
		// read and store data from dictionary file

		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		while (in.hasNextLine()) {
			dict.add(in.nextLine());
		}

		in.close();
		reader.close();

		// create new graph to store dictionary for processing

		GraphW dictGraph = new GraphW(dict, word1, word2);

		System.out.println("#################################\nWord Ladder Finder 2\n\nStart Word: " + word1 + "\nEnd Word: " + word2 + "\n");
		
		// process and print results of dijkstra's algorithm

		if (dictGraph.findLadder() == true) {  // undergoes ladder process
			System.out.println("Ladder found with a distance of " + dictGraph.getEnd().getDistance() + "!\n");

			LinkedList<VertexW> u = dictGraph.getEnd().getPath();

			for (VertexW v:u) {
				System.out.print(v.getWord() + " --> ");
			}

			System.out.println(word2);

		} else {
			System.out.println("No ladder found.");
		}
		
		// end timer and print total time
		
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	
		System.out.println("#################################");
	}
}
