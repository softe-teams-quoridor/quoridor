/* Graph.java - teams - CIS405 - April 29, 2015
 * ---[Contributors]-------------------------------------------------
 *
 *      Collin Walling
 *
 * ---[Description]--------------------------------------------------
 *
 *      Implements Dijkstra's algorithm to calculate the shortest
 *      path from a Player's location to their respective goal row.
 *
 * ---[Methods]------------------------------------------------------
 *
 *      Graph(int)                --> constructs the graph 
 *      buildPath(GameBoard, int) --> builds path for player
 *
 */

import java.util.Queue;
import java.util.LinkedList;

public class Graph {



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



    private Vertex[] graph;



    /**
      * Constructs a new graph with size.
      *     @param size the size of the graph
      */
    public Graph(int size) {
        graph = new Vertex[size];
    }



    /**
      * Implements Dijkstra's Algorithm to calculate the shortest 
      * path from a Player's current location to its respective 
      * goal row.
      *     @param board GameBoard to retrieve adjacencies from
      *     @param player the Player we want to calculate the 
      *                   path for
      */
    public void buildPath(GameBoard board, Player player) {

        // Queue of vertices to be checked
        Queue<Vertex> q = new LinkedList<Vertex>();

        // set each vertex to have a distance of -1
        //for ( Vertex v : graph )
        for ( int i = 0; i < graph.length; i++ )
            graph[i] = new Vertex(i,-1);

        // get the start location, i.e. the player's location
        Vertex start = graph[linearXY(board.getPlayerLoc(player))];
        start.dist = 0;
        q.add(start);

        // while there are still vertices to check
        while ( !q.isEmpty() ) {

            // get the vertex and remove it;
            // we don't want to look at it again
            Vertex v = q.remove();

            // retrieve all reachable ajacencies
            Square[] adjacencies = GameEngine.reachableAdjacentSquares
                                   (board, twoDimXY(v));

            // for each adjacency...
            for ( Square s : adjacencies ) {

                // convert to graph location
                Vertex adjacent = graph[linearXY(s)];            

                // modify the vertex if it wasn't previously
                if ( adjacent.dist < 0 ) {
                    adjacent.dist = v.dist+1;
                    adjacent.path = v;
                    q.add(adjacent);
                }
            }
        }
    }



    /**
      * Converts the X and Y coordinates of a Square object to a 
      * linear coordinate.
      *     @param s Square to convert coordinates from
      */
    private int linearXY(Square s) {
        System.out.println( s.getX() + s.getY() * GameBoard.ROWS );
        return s.getX() + s.getY() * GameBoard.ROWS;
    }



    private Square twoDimXY(Vertex v) {
        return new Square(v.graphLoc/GameBoard.COLUMNS,
                          v.graphLoc%GameBoard.COLUMNS);
    }

    public void printGraph() {
        for ( int i = 0; i < graph.length; i++ ) {
            System.out.print(graph[i].dist+" ");
            if ( (i+1) % GameBoard.COLUMNS == 0 )
                System.out.println();
        }
    }



    public static void main(String[] orgs) {
        Graph graph = new Graph(GameBoard.ROWS * GameBoard.COLUMNS);
        
        Queue<Player> players = new LinkedList<Player>();
        for(int i = 0; i < 2; i++)
            players.add(new Player(i, 10));

        GameBoard board = new GameBoard(players);

        graph.buildPath(board, players.peek());
        graph.printGraph();

    }

 
}
