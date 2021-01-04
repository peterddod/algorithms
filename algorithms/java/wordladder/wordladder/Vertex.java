import java.util.LinkedList;

/**
 * stores information for vertices in graph
 */
public class Vertex {
    private int index;  // index of vertex in vertices array
    private String word;  // word stored in vertex
    private LinkedList<AdjListNode> adjList;  // list of adjacent nodes

    boolean visited;  // states whether vertex has been visited during BFS
    int predecessor;  // predecessor in path during BFS
    int distance; // distance to start vertex for BFS; 1 distance represents 1 letter change

    // constructor

    public Vertex(int i, String word) {
        this.index = i;
        this.word = word;
        this.predecessor = -1;
        adjList = new LinkedList<AdjListNode>();
    }

    // index getter/setter

    public int getIndex() {return this.index;}
    public void setIndex(int i) {this.index = i;}

    // adjList getters/setters

    public LinkedList<AdjListNode> getAdjList() {return adjList;}
    public void addToAdjList(int j) {this.adjList.addLast(new AdjListNode(j));}
    public int vertexDegree() {return this.adjList.size();}

    // word getter/setter

    public String getWord() {return this.word;}
    public void setWord(String word) {this.word = word;}

    // visited getter/setter

    public boolean getVisited() {return this.visited;}
    public void setVisited(boolean visited) {this.visited = visited;}

    // predecessor getter/setter

    public int getPredecessor() {return predecessor;}
    public void setPredecessor(int predecessor) {this.predecessor = predecessor;}

    // distance getter/setter

    public int getDistance() {return distance;}
    public void setDistance(int distance) {this.distance = distance;}
}
