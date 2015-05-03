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
 *      (FIXED) April 29 - last row is not calculated correctly
 *      (FIXED) April 30 - redundant locations are being calculated
 *          for players 1, 2, and 3. a direction bias is needed
 *      (FIXED) April 30 - ensure appropriate wall checking for players
 *          1, 2, and 3. currently incorrect due to direction bias
 *      May 2 - Player 0 is not detecting walls
 *      April 30 - a Vertex graph is unnecessary, but helps
 *          visualize the algorithm. remove this functionality
 *          once the integrity of the algorithm has been verified
 *
 * ---[Methods]------------------------------------------------------
 *
 *      Graph(int)                   --> constructs the graph 
 *      buildPath(GameBoard, Player) --> builds path for player
 *      printGraph()                 --> prints vis. rep. of graph
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
     *     @param player the Player we want to calculate the path for
     */
    public void buildPath(GameBoard board, Player player) {

        // flag to check if we hit a goal location
        boolean goalVertex = false;

        // Queue of vertices to be checked
        Queue<Vertex> q = new LinkedList<Vertex>();

        // set each vertex to have a distance of -1
        for ( int i = 0; i < graph.length; i++ )
            graph[i] = new Vertex(i,-1);

        // get the start location, i.e. the player's location
        Vertex start = 
            squareToVertex(board.getPlayerLoc(player.getPlayerNo()));
        start.dist = 0;
        q.add(start);

        // while there are still vertices to check
        //while ( !q.isEmpty() ) {
        while ( !goalVertex ) {

            // get the vertex and remove it;
            // we don't want to look at it again
            Vertex v = q.remove();

            // check if this vertex is at a goal row
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
            if (!goalVertex) {

                // retrieve all reachable ajacencies
                Square[] adjacencies = reachableAdjacentSquares
                (board, vertexToSquare(v), player.getPlayerNo());
                
                // for each adjacency...
                for ( Square s : adjacencies ) {

                    // convert to graph location
                    Vertex adjacent = squareToVertex(s);            

                    // modify the vertex if it hasn't been modified
                    if ( adjacent.dist < 0 ) {
                        adjacent.dist = v.dist+1;
                        adjacent.path = v;
                        q.add(adjacent);
                    }
                }
            }
            else
                printPath(v);
        }
    }

    /**
     * Retrieves all locations that are reachable from the given location.
     *     @param board GameBoard to retrieve Squares from
     *     @param currLoc the Square we are checking adjacencies from
     *     @param pno ID number of player to calculate path for
     *     @return an array of Squares adjacent to currLoc
     */
    private static Square[] reachableAdjacentSquares ( GameBoard board, 
            Square currLoc, int pno) {

        // list to store adjacent squares
        List<Square> squareList = new LinkedList<Square>();

        // calculate direction bias
        int direction = 0;
        switch (pno) {
            case 0: direction = 86;  break;
            case 1: direction = Integer.rotateRight(163,1);  break;
            case 2: direction = 171; break;
            case 3: direction = Integer.rotateRight(345, 1); break;
        }
    
        // check each available adajcency
        for ( int i = 0; i < 4; i++ ) {
            
            // calculate the x and y offsets
            int x = ((direction & 8)  >> 3) * Integer.signum(direction);
            int y = ((direction & 16) >> 4) * Integer.signum(direction);

            // retrieve an adjacent square to compare
            Square checkLoc = board.getSquare(currLoc.getX() + x,
                    currLoc.getY() + y);

            // modify bits for the next iteration
            direction = Integer.rotateRight(direction,1);

            // it is possible to check for a square that is outside of the board
            if ( checkLoc != null ) {

                // check for walls
                if      ( y == 1  && currLoc.hasWallBottom())
                        continue;
                else if ( y == -1 && checkLoc.hasWallBottom())
                        continue;
                else if ( x == 1  && currLoc.hasWallRight())
                        continue;
                else if ( x == -1 && checkLoc.hasWallRight())
                        continue;
               
                // add this square to the list
                squareList.add(checkLoc);
            }
        }

        // return the array of adjacent squares
        return squareList.toArray(new Square[squareList.size()]);
    }

    /**
     *  Converts a Square to a Vertex.
     *     @param s Square to convert
     *     @see Vertex
     */
    protected Vertex squareToVertex(Square s) {
        return graph[s.getX() + s.getY() * GameBoard.ROWS];
    }

    /**
      * Converts a Vertex to a Square.
      *     @param v Vertex to convert
      *     @see Square
      */
    protected Square vertexToSquare(Vertex v) {
        return new Square(v.graphLoc%GameBoard.COLUMNS,
                          v.graphLoc/GameBoard.COLUMNS);
    }

    /**
      * Prints the graph as if it were a GameBoard.
      *     @see GameBoard
      */
    public void printGraph() {
        System.out.println( );
        for ( int i = 0; i < graph.length; i++ ) {
            // print extra space if a one character number
            if ( graph[i].dist < 10 && graph[i].dist >= 0 )
                System.out.print(graph[i].dist+"  ");
            // print single space if a two character number
            else
                System.out.print(graph[i].dist+" ");
            // print items to a new row
            if ( (i+1) % GameBoard.COLUMNS == 0 )
                System.out.println();
        }
    }

    public void printPath(Vertex v) {
        if ( v.dist == 0 )
            return;
        printPath(v.path);
        System.out.print(v.graphLoc+" ");
    }

    /* main used for testing algorithm */
    public static void main(String[] orgs) {
        
        /* initial preparation */
        Graph graph = new Graph(GameBoard.ROWS * GameBoard.COLUMNS);
        Queue<Player> players = new LinkedList<Player>();
        for(int i = 0; i < 4; i++)
            players.add(new Player(i, 10));
        GameBoard board = new GameBoard(players);

        // Player 0
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

        // Player 1
/*        players.add(players.remove());
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

        // Player 2
/*        players.add(players.remove());
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

        // Player 3
        players.add(players.remove());
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
*/
        // Test board with walls for player 1
        System.out.println("---Wall test---\n");
 //       players.add(players.remove());
        Square[] walls = {new Square(3,2),new Square(4,2)};
        board.placeWall(walls);
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
    }

}
