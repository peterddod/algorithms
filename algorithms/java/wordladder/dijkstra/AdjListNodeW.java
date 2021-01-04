/**
 * stores node information for adjacency lists
 */
public class AdjListNodeW {
    private int vertexIndex;  // index of referenced vertex
    private int weight;  // weight of edge

    // constructor

    public AdjListNodeW(int i, int weight) {
        this.vertexIndex = i;
        this.weight = weight;
    }

    // index getter/setter

    public int getVertexIndex() {return this.vertexIndex;}
    public void setVertexIndex(int i) {this.vertexIndex = i;}

    // weight getter/setter

    public int getWeight() {return this.weight;}
    public void setWeight(int weight) {this.weight = weight;}
}