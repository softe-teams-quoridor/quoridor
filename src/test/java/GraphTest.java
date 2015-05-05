import java.util.Queue;
import java.util.LinkedList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {

    Queue<Player> p;
    GameBoard b;
    Graph g;

    /* instantiate necessary objects */
    @Before
    public void buildGraph() throws Exception {
        p = new LinkedList<Player>();
        p.add(new Player(1,5)); p.add(new Player(0,5));
        b = new GameBoard(p);
        g = new Graph(GameBoard.ROWS * GameBoard.COLUMNS);
    }

    /* test conversion of Square coordinates to a Vertex coordinate */
    @Test
    public void testSquareToVertex() throws Exception {
        g.buildPath(b, p.peek());
        int count = 0;
        for ( int y = 0; y < GameBoard.ROWS; y++ )
            for ( int x = 0; x < GameBoard.COLUMNS; x++ ) {
                Vertex v = g.squareToVertex(b.getSquare(x,y));
                //System.out.println(count + " == " + v.graphLoc + "?");
                assertEquals(count, v.graphLoc);
                count++;
            }
    }

    /* test conversion of a Vertex coordinate Squares coordinates */
    @Test
    public void testVertexToSquare() throws Exception {
        int count = 0;
        for ( int y = 0; y < GameBoard.ROWS; y++ )
            for ( int x = 0; x < GameBoard.COLUMNS; x++ ) {
                Vertex v = new Vertex(count, -1);
                Square boardSquare = b.getSquare(x,y);
                Square graphSquare = g.vertexToSquare(v,b);
                //System.out.println(boardSquare.getX() + " == " + graphSquare.getX() + "?");
                //System.out.println(boardSquare.getY() + " == " + graphSquare.getY() + "?");
                assertEquals(boardSquare.getX(),graphSquare.getX());
                assertEquals(boardSquare.getY(),graphSquare.getY());
                count++;
            }
    }

}
