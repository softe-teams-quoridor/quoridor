
// Composite type for graph
public class Vertex {
    public int graphLoc;
    public int dist;
    public Vertex path;

    public Vertex(int graphLoc, int dist) {
        this.graphLoc = graphLoc;
        this.dist = dist;
    }
}
