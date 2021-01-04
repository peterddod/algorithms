/**
 * stores node information for adjacency lists
 */
public class AdjListNode {
    private int vertexIndex; // index of referenced vertex

    // constructor

    public AdjListNode(int i) {
        this.vertexIndex = i;
    }

    // index getter/setter

    public int getVertexIndex() {return this.vertexIndex;}
    public void setVertexIndex(int i) {this.vertexIndex = i;}
}