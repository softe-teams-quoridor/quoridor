/* AI_Ripley.java - CIS405 - teams
 * Version: 0.3 <-- I probably won't keep up with this
 * Last Modified: April 10th
 * ____________________________________________________________________________
 *
 *      current version will assume AI is player 0; there is currently no
 *      transpositioning of walls and other player movements
 *
 *      Ripley doesn't know how to behave as player1,2, or 3 yet. 
 *      expect fuck ups if you set him as those players
 *
 *      Note: Future versions of Ripley will need to know which player it is so
 *       it can transpose its moves and placements accordingly
 *
 */

// FOR TESTING ONLY
import java.util.Queue;
import java.util.LinkedList;

public class AI_Ripley implements QuoridorAI {

    private final int X = 0;
    private final int Y = 1;

    private int[][] virtualBoard;
    // use a 3D array in the future, using depth as a board for each player 
    private int[] currPos;
    // consider as a 2D array in the future, 2 x numPlayers

    public Deb deb;
    // used for debugging

    /**
      * constructs Ripley's virtualBoard and other deliciousness
      */
    public AI_Ripley () {
        // fill array with integer values
        virtualBoard = new int[GameBoard.COLUMNS][GameBoard.ROWS];
        resetBoard();
        // Setup inital position(s)
        currPos = new int[2];
        currPos[X] = 4; 
        currPos[Y] = 0;

        //@@DEBUGGING
        deb = new Deb("ripley");
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * constructs Ripley's virtualBoard and the board of the other players
      */
    public AI_Ripley ( int numPlayers ) {
        // this will be completed at a later version
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * resets all virtual board values
      */
    private void resetBoard() {
        for ( int x = 0; x < GameBoard.COLUMNS; x++ )
            for ( int y = 0; y < GameBoard.ROWS; y++ )
                virtualBoard[x][y] = 8 - y;
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    public String getMove(GameBoard board, Player p) {
        // -- this will require receiving the previous move(s) from
        //    the other players so we can make an appropriate adjustment,
        //    otherwise ripley will have to rebuild a new virtualBoard
        //    from scratch and iterate through the GameBoard... bleh!
        // Directional compass
        /*
                       Y-1
                        ^
                        |
                X-1 <-- o --> X+1
                        |
                        v
                       Y+1
        */               
        //TODO: encapsulate this code in some sort of "makeMove" method
        
        // the first thing we want to do is to update the virtual
        // board based on what has changed with the GameBoard
        deb.ug("Updating board...");
        
        ripple(board);

        //@@DEBUGGING
        deb.ug(printVirtualBoard());

        // get the array of possible locations from the engine
        Square[] reachableLocs = GameEngine.reachableAdjacentSquares(board,
                                 board.getSquare(currPos[X],currPos[Y]));
        // now, get a listing of the distance values of our possible locations 

        //@@DEBUGGING
        deb.ug("Currently at: " + board.getSquare(currPos[X],currPos[Y]).toString());
        deb.ug("Reachable Vals:");

        int[] reachableVals = new int[reachableLocs.length];
        for ( int i = 0; i < reachableLocs.length; i++ )  {
            int x = reachableLocs[i].getX();
            int y = reachableLocs[i].getY();
            reachableVals[i] = virtualBoard[x][y];
            //@@DEBUGGING
            deb.ug(i+"="+reachableVals[i] + " ");
        }
        // we want to go the square that has the least value
        int bestLoc = 0;
        for ( int i = 0; i < reachableVals.length; i++ ) {
            if ( reachableVals[bestLoc] > reachableVals[i] )
                bestLoc = i; 
            // ^ consider randomly choosing a direction if values are equal
        }

        //@@DEBUGGING
        deb.ug("BestLoc=" + bestLoc);

        // alright, we have the direction we want to go in!
        // update ripley's current position
        currPos[X] = reachableLocs[bestLoc].getX();
        currPos[Y] = reachableLocs[bestLoc].getY();
        // let's build a string and return it!

        //@@DEBUGGING
        deb.ug("Square to go to: " + reachableLocs[bestLoc].toString());

        return reachableLocs[bestLoc].toString();
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * resets the AI 
      */
    public void reset() {
        resetBoard();
        // TODO: add resetPositions() method
        currPos = new int[2];
        currPos[X] = 4; 
        currPos[Y] = 0;
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * returns the name of this AI
      */
    public String toString() {
        return "Ripley";
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /** 
      * prints a string representation of the virtualBoard
      * -- used primarily for testing
      */
    public String printVirtualBoard() {
        String vb = "";
        for ( int y = 0; y < GameBoard.ROWS; y++ ) {
            //System.out.println();
            vb = vb + "\n";
            for ( int x = 0; x < GameBoard.COLUMNS; x++ )
                if ( virtualBoard[x][y] < 10)
                    //System.out.print(virtualBoard[x][y] + "  ");
                    vb = vb + virtualBoard[x][y] + "  ";
                else
                    //System.out.print(virtualBoard[x][y] + " ");
                    vb = vb + virtualBoard[x][y] + " ";
        }
        return vb;
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /** NOTE: this may not be used anymore since we won't be receiving strings
      *       as they are made during the live game; use ripple instead
      *
      * updates the virtual board by adjusting locations based on a wall
      * placement - also moves ripley the boardi
      * @param move player move to update on the board
      */
    public void update(GameBoard gameBoard) {
        ripple(gameBoard);
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * transposes the given player moves and wall placements to be placed on
      * the AI's virtual board
      */
    private void transposeIn() {
        // pp: int playerNo <-- so we know how to transpose
        // Unsure of type and parameters right now
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * tranposes the AI's player moves to the appropriate format for 
      * GameBoard to understand
      */
    private void transposeOut() {
        // pp: int playerNo <-- so we know how to transpose
        // Unsure of type and parameters right now
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * On a wall placement, increment all appropriate virtual board values to 
      * indicate an increase of distance to the goal
      *     @param gameBoard game board to check for walls on
      */
    private void ripple(GameBoard gameBoard) {
        // reset the board
        resetBoard();
        // iterate through the board, row-wise
        for (int y = 0; y < GameBoard.ROWS; y++) {
            // calculate the row so we can figure out how to place walls
            int[] rippleValues = calculateRow(gameBoard,y);
            // "place horizontal walls" on the virtual board
            for (int x = 0; x < GameBoard.COLUMNS; x++) {
                // if horizontal wall detected, rippleUp()
                if (gameBoard.getSquare(x,y).hasWallBottom()) {
                    //initial ripple
                    virtualBoard[x][y] += rippleValues[x];
                    rippleUp(gameBoard, 
                             gameBoard.getSquare(x,y).getWallBottom().isStart(),
                             x, y-1, rippleValues[x]);
                }
            }
            // "place vertical walls" on the virtual board
            for (int x = 0; x < GameBoard.COLUMNS-1; x++) {
                // if a right wall is present
                if (gameBoard.getSquare(x,y).hasWallRight()
                    && gameBoard.getSquare(x,y).getWallRight().isEnd()) {
                    
                    if (virtualBoard[x][y] > virtualBoard[x+1][y]) {
                        //rippleLeft(x,y,value);
                        System.out.println("left of wall is greater");
                    }
                    else if (virtualBoard[x][y] < virtualBoard[x+1][y]) {
                        //rippleRight(x,y,value);
                        System.out.println("right of wall is greater");
                    }
                    else
                        //no rippling
                        System.out.println("sides are equal, no rippling");
                }
            }

            //TODO:
            // if vertical wall
            // vertical walls are odd, we need to check starting from
            // the end piece, and then the start piece

            // if the end piece is next to a horizontal wall, increment
            // it by 4, and the loctions next to the start piece by 2.
            // this is not necessarily the best value to inc,
            // but it will make the AI dislike this location very much
            // ---- skip the next case if this happens ----

            // if the start piece is next to a horizontal wall, increment
            // it by 2. same as above, but the AI should dislike it
        }
    }

    private int[] calculateRow(GameBoard gameBoard, int y) {
        int[] fromLeft  = countSquaresFromLeft (gameBoard,y);
        int[] fromRight = countSquaresFromRight(gameBoard,y);
        int[] row = new int[GameBoard.COLUMNS]; // calculated row
        int rightInd = GameBoard.ROWS-1;        // right-most index
        int leftInd  = 0;                       // left-most index
        // If there is a wall against the right-most edge of the game board,
        //  get the values from right-to-left
        if ( fromLeft[GameBoard.COLUMNS-1] > 0 )
            for ( ; fromLeft[rightInd] != 0; rightInd--)
                row[rightInd] = fromLeft[rightInd];
        // If there is a wall against the left-most edge of the game board,
        //  get the values from left-to-right
        if ( fromRight[0] > 0 )
            for ( ; fromRight[leftInd] != 0; leftInd++ )
                row[leftInd] = fromRight[leftInd];
        // Get the smallest at each index of the remaining values from both 
        //  arrays
        for ( int i = leftInd; i < rightInd; i++ )
            if ( fromLeft[i] > fromRight[i] )
                row[i] = fromRight[i];
            else
                row[i] = fromLeft[i];
        return row;
    }

    /* sum of walls on squares from left-to-right. sum resets when a square
       without a bottom wall is encountered */
    private int[] countSquaresFromLeft(GameBoard gameBoard, int y) {
        int[] fromLeft = new int[GameBoard.ROWS];
        int ct = 0;
        for ( int x = 0; x < GameBoard.COLUMNS; x++ )
            if ( gameBoard.getSquare(x,y).hasWallBottom() ) {
                ct++;
                fromLeft[x] = ct;
            }
            else ct = 0;
        return fromLeft;
    }

    /* sum of walls on squares from right-to-left. sum resets when a square
       without a bottom wall is encountered */
    private int[] countSquaresFromRight(GameBoard gameBoard, int y) {
        int[] fromRight = new int[GameBoard.ROWS];
        int ct = 0;
        for ( int x = GameBoard.COLUMNS-1; x >= 0; x-- )
            if ( gameBoard.getSquare(x,y).hasWallBottom() ) {
                ct++;
                fromRight[x] = ct;
            }
            else ct = 0;
        return fromRight;
    }

    /**
      * Recursively increases the values of a column on the virtualBoard from
      * the start point, denoted by parameters x and y, by the amount given.
      *     @param gameBoard GameBoard to check for Walls on
      *     @param wallType stop rippling if we encounter the same Wall section
      *     @param x column to look at
      *     @param y row to look at
      */
    private void rippleUp(GameBoard gameBoard, boolean wallType, int x, int y,
                          int amount) {
        // make sure we don't go out of bounds
        if ( y < GameBoard.ROWS && y >= 0 )
            // stop rippling if we encounter a wall that is the same section
            if ( gameBoard.getSquare(x,y).hasWallBottom() &&
                 gameBoard.getSquare(x,y).getWallBottom().isStart() == wallType ) {
                return; // do nothing
            } else {
                // just keep rippling up
                virtualBoard[x][y] += amount;
                rippleUp(gameBoard,wallType,x,y-1,amount);
            }
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * makes a decision if the AI should move forward or place wall based on
      * every player's relative distance to their respective goal
      */
    private void decide() {
        // Unsure of type and parameters right now
        // true = move, false = wall?
        // if currentLocationVal > anyoneElsesVal
        //      (return false) place a wall!
        // else
        //      (return true) I mean... I guess we can just move forward?
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    public static void main(String[] args) {
        AI_Ripley rip = new AI_Ripley();
        Queue<Player> ps = new LinkedList<Player>();
        ps.add(new Player(0,"whoCares",1000));
        ps.add(new Player(1,"iDont",2000000));
        GameBoard board = new GameBoard(ps);

        System.out.println("Initial board");
        System.out.println(rip.printVirtualBoard());

        System.out.println("Adding a wall to the board...");
        Square first = board.getSquare("III-C");
        Square secnd = board.getSquare("IV-C");
        board.placeWall(new Square[] {first,secnd});

        System.out.println("Rippling...");
        rip.update(board);

        System.out.println("Board after ripple");
        System.out.println(rip.printVirtualBoard());

        System.out.println("Adding a neighboring wall...");
        first = board.getSquare("V-C");
        secnd = board.getSquare("VI-C");
        board.placeWall(new Square[] {first,secnd});

        System.out.println("Rippling...");
        rip.update(board);

        System.out.println("Board after ripple");
        System.out.println(rip.printVirtualBoard());

        System.out.println("Adding a neighboring wall...");
        first = board.getSquare("VII-C");
        secnd = board.getSquare("VIII-C");
        board.placeWall(new Square[] {first,secnd});

        System.out.println("Rippling...");
        rip.update(board);

        System.out.println("Board after ripple");
        System.out.println(rip.printVirtualBoard());

        System.out.println("Adding a neighboring wall...");
        first = board.getSquare("I-C");
        secnd = board.getSquare("II-C");
        board.placeWall(new Square[] {first,secnd});

        System.out.println("Rippling...");
        rip.update(board);

        System.out.println("Board after ripple");
        System.out.println(rip.printVirtualBoard());

        System.out.println("Adding a new row of walls...");
        first = board.getSquare("VIII-D");
        secnd = board.getSquare("IX-D");
        board.placeWall(new Square[] {first,secnd});
        first = board.getSquare("VI-D");
        secnd = board.getSquare("VII-D");
        board.placeWall(new Square[] {first,secnd});
        first = board.getSquare("IV-D");
        secnd = board.getSquare("V-D");
        board.placeWall(new Square[] {first,secnd});
        first = board.getSquare("II-D");
        secnd = board.getSquare("III-D");
        board.placeWall(new Square[] {first,secnd});

        System.out.println("Rippling...");
        rip.update(board);

        System.out.println("Board after ripple");
        System.out.println(rip.printVirtualBoard());

        System.out.println();
        //--------------------
        System.out.println();

        System.out.println("Vertical Wall Testing");
        System.out.println("placing a vertical wall...");
        first = board.getSquare("V-B");
        secnd = board.getSquare("V-C");
        board.placeWall(new Square[] {first,secnd});

        System.out.println("Riplling...");
        rip.update(board);

        System.out.println("Board after ripple");
        System.out.println(rip.printVirtualBoard());
    }
}
