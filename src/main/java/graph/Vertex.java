/* Composite type for graph */
public class Vertex {

    public int graphLoc; // location on graph
    public int dist;     // distance
    public Vertex path;  // connecting node

    public Vertex(int graphLoc, int dist) {
        this.graphLoc = graphLoc;
        this.dist = dist;
    }
}
