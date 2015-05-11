/* Graph.java - teams - CIS405 - May 10, 2015
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
 *      (FIXED) May 2 - Player 0 is not detecting walls
 *      April 30 - a Vertex graph is unnecessary, but helps
 *          visualize the algorithm. remove this functionality
 *          once the integrity of the algorithm has been verified
 *
 * ---[Methods]------------------------------------------------------
 *
 *      Graph(int)                   --> constructs the graph 
 *      buildPath(GameBoard, Player) --> builds path for player
 *      printGraph()                 --> prints vis. rep. of graph
 *      printPath(Vertex)            --> prints the path
 *
 */

import java.util.Stack;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

public class Graph {

    // FOR NICE OUTPUT COLORS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private Vertex[] graph;

    /**
     * Constructor.
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
    public Square[] buildPath(GameBoard board, Player player) {

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
                case 2: if ( (v.graphLoc+1) % GameBoard.COLUMNS == 0 )
                            goalVertex = true; break;
                case 3: if ( v.graphLoc % GameBoard.COLUMNS == 0 )
                            goalVertex = true; break;
            }

            // if we're at a goal vertex, we don't need to calculate
            // its neighboors
            if ( !goalVertex ) {

                // retrieve all reachable ajacencies
                Square[] adjacencies = reachableAdjacentSquares
                (board, vertexToSquare(v, board), player.getPlayerNo());

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
                return returnPath(v,board);
        
        }
        // should never get here
        return null;
    }

    /**
      * Prints the graph as if it were a GameBoard.
      *     @see GameBoard
      */
    public void printGraph() {
        System.out.println(   );
        // the cell number corresponding to a location on a GameBoard
        String cell = ""; 
        // the distance a cell is from the starting location
        String dist = "";  

        // for every cell in the graph
        for ( int i = 0; i < graph.length; i++ ) {

            // format graph by prefixing a 0 if the number is less
            //  than 10. this will ensure output is uniform
            if ( graph[i].graphLoc < 10 )
                cell = "0" + graph[i].graphLoc;
            else
                cell = graph[i].graphLoc + "";

            // format distance by prefixing a space if the number
            //  is between 0 and 9 inclusive
            if ( graph[i].dist < 10 && graph[i].dist >= 0 )
                dist = graph[i].dist + " ";
            // give red color if the cell was never checked
            else if ( graph[i].dist == -1 )
                dist = ANSI_RED + graph[i].dist + "" + ANSI_RESET;
            else
                dist = graph[i].dist + "";

            // print the cell and distance
            //  an example output is [13: 5]
            System.out.print("["+cell+":"+dist+"]");

            // create a new line for the next row
            if ( (i+1) % GameBoard.COLUMNS == 0 )
                System.out.println("\n");
        }
    }

    /**
      * Prints the shortest path
      *     @param v the Vertex to start at
      */
    public void printPath(Vertex v) {
        if ( v.dist == 0 )
            return;
        printPath(v.path);
        System.out.print("-> " + v.graphLoc+" ");
    }

    /**
      * Returns an array of adjacent Squares from a Player's current
      * location.
      *     @param board GameBoard to read Squares from
      *     @param currLoc the location to start at
      *     @param pno the Player ID
      */
    private static Square[] reachableAdjacentSquares
        (GameBoard board, Square currLoc, int pno) {
            return reachableAdjacentSquares(board, currLoc, pno, -1, 0,true);
    }

    /**
     * Retrieves all locations that are reachable from the given
     * location.
     *     @param board GameBoard to retrieve Squares from
     *     @param currLoc where we are checking adjacencies from
     *     @param pno ID number of player to calculate path for
     *     @param dontCheckMe flag to prevent checking same location
     *     @param numJumps prevents checking more than 3 adjacencies
     *     @param adjacentToPlayer consider opponent adjacencies iff 
     *                             directly next to current player
     *     @return an array of Squares adjacent to currLoc
     */
    private static Square[] reachableAdjacentSquares
        (GameBoard board, Square currLoc, int pno, 
         int dontCheckMe, int numJumps, boolean adjacentToPlayer) {

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

            // if check is not an invalid location...
            if ( checkLoc != null ) {

                // skip this check if there is a wall in the way
                if ( ( y == 1  && currLoc.hasWallBottom() ) ||
                     ( y == -1 && checkLoc.hasWallBottom()) ||
                     ( x == 1  && currLoc.hasWallRight()  ) ||
                     ( x == -1 && checkLoc.hasWallRight() )   )
                       continue;
 
                // check if there is a player adjacent to where we are
                else if ( adjacentToPlayer && checkLoc.isOccupied() 
                          && i != dontCheckMe && numJumps < 3 ) {
                    // Get the squares from the adjacent player
                    Square[] adjToPlayer = reachableAdjacentSquares(board,
                            checkLoc, pno, (i+2)%4, numJumps++, adjacentToPlayer);
                    // Add the adjacent player's squares to the list
                    for ( int j = 0; j < adjToPlayer.length; j++ )
                        squareList.add(adjToPlayer[j]);
                } 

                else
                    // add this square to the list
                    squareList.add(checkLoc);

                adjacentToPlayer = true;
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
      *     @param b GameBoard to retrieve the Square from
      *     @see Square
      */
    protected Square vertexToSquare(Vertex v, GameBoard b) {
        return b.getSquare(v.graphLoc % GameBoard.COLUMNS,
                           v.graphLoc / GameBoard.ROWS);
    }

    /**
      * Returns the shortest path
      *     @param v the Vertex to build the path from
      *     @param b the GameBoard to retrieve Squares from
      */
    private Square[] returnPath(Vertex v, GameBoard b) {
        // because we're starting at the end point,
        //  we need build the path in 'reverse'
        Stack<Square> path = new Stack<Square>();

        // while we're not at the start point
        while ( v.dist != 0 ) {
            path.push(vertexToSquare(v,b));
            v = v.path;
        }

        // place the locations in the proper order
        Square[] road = new Square[path.size()];
        for ( int i = 0; i < path.size(); i++ )
            road[i] = path.pop();

        return road;
    }

}
