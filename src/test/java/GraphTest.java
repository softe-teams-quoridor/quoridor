import java.util.Queue;
import java.util.LinkedList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {

    Queue<Player> p;
    GameBoard b;
    Graph g;

    @Before
    public void buildGraph() throws Exception {
        p = new LinkedList<Player>();
        p.add(new Player(0,5));
        p.add(new Player(1,5));
        b = new GameBoard(p);
        g = new Graph(GameBoard.ROWS * GameBoard.COLUMNS);
    }

    @Test
    public void testGraphAndBoardCoordinateCorrespondance() throws Exception {
        int count = 0;
        for ( int y = 0; y < GameBoard.ROWS; y++ )
            for ( int x = 0; x < GameBoard.COLUMNS; x++ ) {
                assertEquals(count, g.linearXY(b.getSquare(x,y)));
                count++;
            }
    }

    @Test
    public void testTwoDimConversion() throws Exception {
        int count = 0;
        for ( int y = 0; y < GameBoard.ROWS; y++ )
            for ( int x = 0; x < GameBoard.COLUMNS; x++ ) {
                Vertex v = new Vertex(count, -1);
                Square boardSquare = b.getSquare(x,y);
                Square graphSquare = g.twoDimXY(v);
                System.out.println(boardSquare.getX() + " == " + graphSquare.getX() + "?");
                System.out.println(boardSquare.getY() + " == " + graphSquare.getY() + "?");
                assertEquals(boardSquare.getX(),graphSquare.getX());
                assertEquals(boardSquare.getY(),graphSquare.getY());
                count++;
            }

    }

}
