import java.util.LinkedList;
import java.util.ArrayList;

/**
 * creates graph of words, where each adjacent word has
 * a distance of 1
 */
public class Graph {
    private Vertex[] vertices;  // all vertices in graph
    private int numVertices = 0;  // total number of vertices in graph
    private Vertex start;  // vertex representing start word for ladder process
    private Vertex end;  // vertex representing end word for ladder process

    // constructor

    public Graph(ArrayList<String> dict, String startWord, String endWord) {
        this.numVertices = dict.size();
        this.vertices = new Vertex[numVertices];
        for (int i=0; i<numVertices; i++) this.vertices[i] = new Vertex(i, dict.get(i).strip());

        // stores words and creates adjList for each vertex
        
        for (Vertex op_vertex: vertices) {
            for (Vertex vertex: vertices) {
                if (distance(op_vertex.getWord(), vertex.getWord()) == 1) {
                    op_vertex.addToAdjList(vertex.getIndex());
                }
            }

            // stores start and end words
            
            if (op_vertex.getWord().equals(startWord)) {
                this.start = op_vertex;
            } else if (op_vertex.getWord().equals(endWord)) {
                this.end = op_vertex;
            }
        }
    }

    // vertex related getters/setters

    public int size() {return this.numVertices;}
    public Vertex getVertex(int index) {return this.vertices[index];}
    public Vertex getStart() {return this.start;}
    public Vertex getEnd() {return this.end;}

    // finds distance between 2 words

    public int distance(String word1, String word2) {
        int distance = 0;

        for (int i=0; i<5; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                distance++;
            }
        }

        return distance;
    }
    
    // finds the shortest path from start to end using BFS
    // returns true if path is found for end word, false otherwise

    public boolean findLadder() {
        // boolean to indicate if path between end and start is possible
        boolean found = false;

        // initialise distances and visited
        for (Vertex v : vertices) {
            v.setVisited(false);
            v.setDistance(0);
        }

        // initialise process queue
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        
        // adds start as first queue vertex and updates visited
        queue.add(start);
        start.setVisited(true);

        // loops until queue is empty or path is found
        while (!queue.isEmpty() & found == false) {
            Vertex u = queue.remove(); // selects vertex
            LinkedList<AdjListNode> list = u.getAdjList(); // get adjacency list of the vertex
            
            // loops through adjacency list of selected vertex
            for (AdjListNode node : list) {
                Vertex w = vertices[node.getVertexIndex()];

                // if current adjacent vertex is the end word, process is ended; updates distance and predecessor
                if (w.equals(end)) {
                    found = true;
                    w.setPredecessor(u.getIndex());
                    w.setDistance(u.getDistance() + 1);
                    break;
                }

                // if verex has not been visited, then updates visited, predecessor and distance
                if (!w.getVisited()) {
                    w.setVisited(true);
                    w.setPredecessor(u.getIndex());
                    w.setDistance(u.getDistance() + 1);
                    queue.add(w); // add to queue for processing
                }
            }
        }

        return found;
    }

}
