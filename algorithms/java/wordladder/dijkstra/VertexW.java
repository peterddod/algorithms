import java.util.LinkedList;

/**
 * stores information for vertices in graph
 */
public class VertexW {
    private int index;  // index of vertex in vertices array
    private String word;  // word stored in vertex
    private LinkedList<AdjListNodeW> adjList;  // list of adjacent nodes

    private LinkedList<VertexW> path;  // shortest path to start word for dijkstras
    private int distance;  // edge weight distance to start word for dijkstra

    // constructor 

    public VertexW(int i, String word) {
        this.index = i;
        this.word = word;
        this.path = new LinkedList<VertexW>();;
        this.adjList = new LinkedList<AdjListNodeW>();
    }

    // index getter/setter

    public int getIndex() {return this.index;}
    public void setIndex(int i) {this.index = i;}

    // adjList getters/setters

    public LinkedList<AdjListNodeW> getAdjList() {return adjList;}
    public void addToAdjList(int j, int weight) {this.adjList.addLast(new AdjListNodeW(j, weight));}
    public int vertexDegree() {return this.adjList.size();}

    // word getter/setter

    public String getWord() {return this.word;}
    public void setWord(String word) {this.word = word;}

    // path getter/setter

    public LinkedList<VertexW> getPath() {return path;}
    public void setPath(LinkedList<VertexW> path) {this.path = path;}

    // distance getter/setter

    public int getDistance() {return distance;}
    public void setDistance(int distance) {this.distance = distance;}
}
