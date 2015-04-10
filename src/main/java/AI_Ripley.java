/* AI_Ripley.java - CIS405 - teams
 * Version: 0.2 <-- I probably won't keep up with this
 * Last Modified: April 8th
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

    /**
      * constructs Ripley's virtualBoard and other deliciousness
      */
    public AI_Ripley () {
        // fill array with integer values
        virtualBoard = new int[GameBoard.COLUMNS][GameBoard.ROWS];
        for ( int x = 0; x < GameBoard.COLUMNS; x++ )
            for ( int y = 0; y < GameBoard.ROWS; y++ )
                virtualBoard[x][y] = 8 - y;
        // Setup inital position
        currPos = new int[2];
        currPos[X] = 4; 
        currPos[Y] = 0;
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    private void resetBoard() {
        for ( int x = 0; x < GameBoard.COLUMNS; x++ )
            for ( int y = 0; y < GameBoard.ROWS; y++ )
                virtualBoard[x][y] = 8 - y;
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
      * return a move string, be it a player move or wall placement
      */
    public String getMove(GameBoard board, Player p) {
        // the first thing we want to do is to update the virtual
        // board based on what has changed with the GameBoard
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
        // get the array of possible locations from the engine
        Square[] possibleLocs = GameEngine.reachableAdjacentSquares(board,
                                board.getSquare(currPos[X],currPos[Y]));
        // now, compare the distance values of our possible locations 
        int[] compareValues = new int[possibleLocs.length];
        for ( int i = 0; i < possibleLocs.length; i++ )  {
            int x = possibleLocs[i].getX();
            int y = possibleLocs[i].getY();
            compareValues[i] = virtualBoard[x][y];
        }
        // we want to go the square that has the least value
        int possibleLocIndex = 0;
        for ( int i = 0; i < compareValues.length; i++ ) {
            if ( compareValues[possibleLocIndex] > compareValues[i] )
                possibleLocIndex = i; 
            // ^ consider randomly choosing a direction if values are equal
        }
        // alright, we have the direction we want to go in!
        // update ripley's current position
        int x = possibleLocs[possibleLocIndex].getX();
        int y = possibleLocs[possibleLocIndex].getY();
        currPos[X] = x;
        currPos[Y] = y;
        // let's build a string and return it!
        String move = "";
        move = GameEngine.toNumerals(x) + "-" + GameEngine.toLetters(y);
        return move;

        // Currently ripley doesn't make any intelligent moves. It simply
        // makes moves on the board. Wall placements are going to mess with
        // him a bit
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * resets the AI 
      */
    public void reset() {
        resetBoard();
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
    public void printVirtualBoard() {
        for ( int y = 0; y < GameBoard.ROWS; y++ ) {
            System.out.println();
            for ( int x = 0; x < GameBoard.COLUMNS; x++ )
                System.out.print(virtualBoard[x][y] + " ");
        }
        System.out.println();
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * updates the virtual board by adjusting locations based on a wall
      * placement - also moves ripley the boardi
      * @param move player move to update on the board
      */
    private void updateVirtualBoard(String move) {
        // for other players, a transposition will have to take place
        // we're assuming ripley is player 0 for version 1.0

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
      * on a wall placement, increment all appropriate virtual board values to 
      * indicate an increase of distance to the goal
      */
    //PUBLIC FOR TESTING ONLY -- REVERT TO PRIVATE
    public void ripple(GameBoard gameBoard) {
        // pp: virtualBoard depth index ( if made into 3D array )
        //     -- OR --
        //     virtualBoard to update, if we have a separate 2D array for each
        //     the two indices we need to update
        
        // reset the board
        resetBoard();
        // iterate through the board, column-wise left-to-right
        for ( int y = 0; y < GameBoard.ROWS; y++ )
            for ( int x = 0; x < GameBoard.COLUMNS; x++ ) {
                // if horizontal wall detected, rippleUp()
                if ( gameBoard.getSquare(x,y).hasWallBottom() ) {
                    // initial ripple
                    virtualBoard[x][y]++;
                    // ripple upwards
                    rippleUp(gameBoard, gameBoard.getSquare(x,y).getWallBottom().isStart(),x,y-1);
                    // ripple left
                    // ripple right
                }
            }

    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    private void rippleUp(GameBoard gameBoard, boolean wallType, int x, int y) {
        if ( y < GameBoard.ROWS && y >= 0 )
            if ( gameBoard.getSquare(x,y).hasWallBottom() &&
                 gameBoard.getSquare(x,y).getWallBottom().isStart() == wallType ) {
                // do nothing
                return;
            } else {
                virtualBoard[x][y]++;
                rippleUp(gameBoard,wallType,x,y-1);
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


    public static void main(String[] args) {
        AI_Ripley rip = new AI_Ripley();
        Queue<Player> ps = new LinkedList<Player>();
        ps.add(new Player(0,"whoCares",1000));
        ps.add(new Player(1,"iDont",2000000));
        GameBoard board = new GameBoard(ps);

        System.out.println("Initial board");
        rip.printVirtualBoard();

        System.out.println("Adding a wall to the board...");
        Square first = board.getSquare("V-C");
        Square secnd = board.getSquare("VI-C");
        board.placeWall(first,secnd);

        System.out.println("Rippling...");
        rip.ripple(board);

        System.out.println("Board after ripple");
        rip.printVirtualBoard();
    }
}
