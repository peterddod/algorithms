import java.util.ArrayList;
import java.util.LinkedList;

/**
 * creates graph of words, where each adjacent word has
 * a distance of 1
 * 
 * the weight of each edge is the absolute difference
 * in alphabet placement
 */
public class GraphW {
    private VertexW[] vertices;  // all vertices in graph
    private int numVertices = 0;  // total number of vertices in graph
    private VertexW start;  // vertex representing start word for ladder process
    private VertexW end;  // vertex representing end word for ladder process

    // constructor

    public GraphW(ArrayList<String> dict, String startWord, String endWord) {
        this.numVertices = dict.size();
        this.vertices = new VertexW[numVertices];
        for (int i=0; i<numVertices; i++) this.vertices[i] = new VertexW(i, dict.get(i).strip());

        // stores words and creates adjList for each vertex

        for (VertexW op_vertex: vertices) {
            for (VertexW vertex: vertices) {
                int distance = distance(op_vertex.getWord(), vertex.getWord());
                if (distance == 1) {
                    op_vertex.addToAdjList(vertex.getIndex(), weight(op_vertex.getWord(), vertex.getWord()));
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
    public VertexW getVertex(int index) {return this.vertices[index];}
    public VertexW getStart() {return this.start;}
    public VertexW getEnd() {return this.end;}

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

    // finds absolute difference in alphabet positions for edge weight

    public int weight(String word1, String word2) {
        int weight = 0;

        for (int i=0; i<5; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                weight += Math.abs(word1.charAt(i) - word2.charAt(i));
                break;
            }
        }

        return weight;
    }

    // returns vertex with lowest distance in list

    public VertexW getLowestDistance(ArrayList<VertexW> list) {
        VertexW low = null;
        int lowVal = Integer.MAX_VALUE;

        for (VertexW v: list) {
            if (v.getDistance() < lowVal) {
                low = v;
                lowVal = v.getDistance();
            }
        }

        return low;
    }

    // sets distance of current vertex to shortest possible and updates path

    public void calcMinDistance(VertexW source, VertexW current, int weight) {
        int sourceDistance = source.getDistance();

        if (sourceDistance + weight < current.getDistance()) {
            current.setDistance(sourceDistance + weight);
            LinkedList<VertexW> path = new LinkedList<>(source.getPath());
            path.add(source);
            current.setPath(path);
        }
    }

    // finds the shortest path to start for each vertex using dijsktras
    // returns true if path is found for end word, false otherwise

    public boolean findLadder() {
        // initialise distances
        for (VertexW v : vertices) {
            v.setDistance(Integer.MAX_VALUE);
        }
        start.setDistance(0);

        // initialise settled and unsettled arrays
        ArrayList<VertexW> settled = new ArrayList<VertexW>();
        ArrayList<VertexW> unsettled = new ArrayList<VertexW>();
        
        // boolean to indicate if path between end and start is possible
        boolean found = false;

        // add start as first unsettled vertex
        unsettled.add(start);

        // loops until all unsettled vertices are settled
        while (unsettled.size() != 0) {  
            VertexW evalVertex = getLowestDistance(unsettled);  // retrieve unsettled vertex with lowest distance
            unsettled.remove(evalVertex);  // remove selected vertex
            if (evalVertex.equals(end)) {  // sets to true if path from start to end is possible
                found = true;
            }

            // loops through adjacency list for selected vertex
            for (AdjListNodeW node: evalVertex.getAdjList()) { 
                VertexW current = vertices[node.getVertexIndex()];

                // calculates distance and path for adjacent vertices if unsettled
                if (!settled.contains(current)) {
                    calcMinDistance(evalVertex, current, node.getWeight());
                    if (!unsettled.contains(current)) {
                        unsettled.add(current);
                    }
                }
            }
            // settles selected vertex
            settled.add(evalVertex);
        }
        return found;
    }

}
