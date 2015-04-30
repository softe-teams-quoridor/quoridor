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
 * ---[Bugs]---------------------------------------------------------
 *
 *      [FIXED] April 29 - last row is not calculated correctly
 *      April 30 - redundant locations are being calculated for 
 *          players 1, 2, and 3. a direction bias is needed
 *
 * ---[Methods]------------------------------------------------------
 *
 *      Graph(int)                --> constructs the graph 
 *      buildPath(GameBoard, int) --> builds path for player
 *
 */

import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

public class Graph {

    private Vertex[] graph;

    /**
     * Constructs a new graph with size.
     *     @param size the size of the graph
     */
    public Graph(int size) {
        graph = new Vertex[size];
    }

    /**
     * Implements Dijkstra's Algorithm to calculate the distances 
     * from a Player's current location to its respective goal row.
     *     @param board GameBoard to retrieve adjacencies from
     *     @param player the Player we want to calculate the 
     *                   path for
     */
    public void buildPath(GameBoard board, Player player) {

        boolean goalVertex = false;

        // Queue of vertices to be checked
        Queue<Vertex> q = new LinkedList<Vertex>();

        // set each vertex to have a distance of -1
        for ( int i = 0; i < graph.length; i++ )
            graph[i] = new Vertex(i,-1);

        // get the start location, i.e. the player's location
        Vertex start = graph[linearXY(board.getPlayerLoc(player))];
        start.dist = 0;
        q.add(start);

        // while there are still vertices to check
        //while ( !q.isEmpty() ) {
        while ( !goalVertex ) {

            // get the vertex and remove it;
            // we don't want to look at it again
            Vertex v = q.remove();

            // retrieve all reachable ajacencies
            Square[] adjacencies = reachableAdjacentSquares
                (board, twoDimXY(v));

            // if this is a goal row, 
            switch ( player.getPlayerNo() ) {
                case 0: if ( v.graphLoc >= 72 ) 
                            goalVertex = true; break;
                case 1: if ( v.graphLoc <= 8  ) 
                            goalVertex = true; break;
                case 2: if ( (v.graphLoc+1) % GameBoard.COLUMNS == 0)
                            goalVertex = true; break;
                case 3: if ( v.graphLoc % GameBoard.COLUMNS == 0)
                            goalVertex = true; break;
            }

            // if we're at a goal vertex, we don't need to calculate
            // its neighboors
            if (!goalVertex)
                
                // for each adjacency...
                for ( Square s : adjacencies ) {

                    // convert to graph location
                    Vertex adjacent = graph[linearXY(s)];            

                    // modify the vertex if it hasn't been modified
                    if ( adjacent.dist < 0 ) {
                        adjacent.dist = v.dist+1;
                        adjacent.path = v;
                        q.add(adjacent);
                    }
                }
            // reset the goal flag for the next cell check
            //goalVertex = false;
        }
    }

    /**
     * Retrieves all locations that are reachable from the given location
     *     @param board GameBoard to retrieve Squares from
     *     @param currLoc the Square we are checking adjacencies from
     *     @return an array of Squares adjacent to currLoc
     */
    private static Square[] reachableAdjacentSquares ( GameBoard board, 
            Square currLoc) {

        List<Square> squareList = new LinkedList<Square>();
        
        //FIXME: direction should be a param. the number that gets sent
        // will depend on the player number. this new direction will be
        // the direction bias.

        // p0 direction = 86
        // p1 direction =
        // p2 direction = 
        // p3 direction =

        int direction = 86; // we use bit shifting to get the coordinates
        for ( int i = 0; i < 4; i++ ) {
            // Calculate the x and y offsets
            int x = ((direction & 8)  >> 3) * Integer.signum(direction);
            int y = ((direction & 16) >> 4) * Integer.signum(direction);
            // Retrieve an adjacent square to compare
            Square checkLoc = board.getSquare(currLoc.getX() + x,
                                              currLoc.getY() + y);
            // Modify bits for the next iteration
            direction = Integer.rotateRight(direction,1);

            //It is possible to check for a square that is outside of the board
            if ( checkLoc != null ) {
                
                //FIXME: with new biases, i will not necessarily reflect
                // the appropriate wall-checking-direction

                switch ( i ) {
                    // If we encounter a wall, continue to the next iteration
                    case 0: if ( currLoc.hasWallBottom() ) continue; break;
                    case 1: if ( currLoc.hasWallRight()  ) continue; break;
                    case 2: if ( checkLoc.hasWallBottom()) continue; break;
                    case 3: if ( checkLoc.hasWallRight() ) continue; break;
                    default: break; //assertion here maybe?
                }
                squareList.add(checkLoc);
            }
        }
        return squareList.toArray(new Square[squareList.size()]);
    }

    //TODO: change the names of these methods to something less ugly
    /**
     * Converts the X and Y coordinates of a Square object to a 
     * linear coordinate.
     *     @param s Square to convert coordinates from
     */
    protected int linearXY(Square s) {
        return s.getX() + s.getY() * GameBoard.ROWS;
    }

    protected Square twoDimXY(Vertex v) {
        return new Square(v.graphLoc%GameBoard.COLUMNS,
                v.graphLoc/GameBoard.COLUMNS);
    }

    public void printGraph() {
        for ( int i = 0; i < graph.length; i++ ) {
            if ( graph[i].dist < 10 && graph[i].dist >= 0 )
                System.out.print(graph[i].dist+"  ");
            else
                System.out.print(graph[i].dist+" ");
            if ( (i+1) % GameBoard.COLUMNS == 0 )
                System.out.println();
        }
    }

    public static void main(String[] orgs) {
        Graph graph = new Graph(GameBoard.ROWS * GameBoard.COLUMNS);

        Queue<Player> players = new LinkedList<Player>();
        for(int i = 0; i < 4; i++)
            players.add(new Player(i, 10));

        GameBoard board = new GameBoard(players);

        // Player 0
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

        players.add(players.remove());

        // Player 1
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

        players.add(players.remove());

        // Player 2
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

        players.add(players.remove());

        // Player 3
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
    }

}
