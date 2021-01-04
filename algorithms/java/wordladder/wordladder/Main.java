import java.io.*;
import java.util.*;

/**
 program to find word ladder with shortest path (i.e. minimum number edges
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

		Graph dictGraph = new Graph(dict, word1, word2);

		System.out.println("#################################\nWord Ladder Finder 1\n\nStart Word: " + word1 + "\nEnd Word: " + word2 + "\n");
		
		// process and print results of BFS algorithm

		if (dictGraph.findLadder() == true) {  // undergoes ladder process
			System.out.println("Ladder found in " + dictGraph.getEnd().getDistance() + " steps!\n");

			Vertex u = dictGraph.getEnd();

			while (!u.getWord().equals(word1)) {
				System.out.print(u.getWord() + " <-- ");
				u = dictGraph.getVertex(u.getPredecessor());
			}

			System.out.println(word1);
		} else {
			System.out.println("No ladder found.");
		}
		
		// end timer and print total time
		
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	
		System.out.println("#################################");
	}
}
