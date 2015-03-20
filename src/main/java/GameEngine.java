/* GameEngine.java - CIS405 - teams
 * Last Edit: March 20, 2015
 * ____________________________________________________________________________
 *
 * this class is used by both the client and the server!
 * 
 * --------------------------------- METHODS ----------------------------------
 *
 * String toNumerals(int)   --> converts int to a numeral 
 * int fromNumerals(String) --> converts string(numeral) to an int
 * char toLetters(int)      --> converts int to a letter ex 0 -> A
 * int fromLetters(char)    --> conversions between ints and numerals/letters
 * boolean parseMove(GameBoard, String) 
 *                          --> returns if the move string is valid
 * boolean validate(GameBoard, String)  
 *                          --> returns if string represents a legal move
 * Sqaure getSquare(GameBoard, String) 
 *                          --> constructs a square based on the move string
 * boolean getWinner(GameBoard,Players[])
 *                          --> checks if a player has won the game 
 * Player nextPlayer(int, Player[])      
 *                          --> returns the next player available
 * ----------------------------- PRIVATE METHODS ------------------------------
 *
 * Player onlyOnePlayerRemaining(Player[])
 *                          --> returns if a player is the last one alive
 * 
 */

public class GameEngine {
    private static final String [] numerals = {"I"  , "II"  , "III", 
                                               "IV" , "V"   , "VI" , 
                                               "VII", "VIII", "IX" };

    /** 
      * converts an int to a string of roman numerals
      * @param x: integer to convert to a numeral
      */
    public static String toNumerals(int x) {
        return (x < 0 || 8 < x) ? "@@@@@@@@@@@@@@" : numerals[x];
    }

    //*************************************************************************

    /**
      * converts a roman numeral to an integer
      * @param str: string to convert
      */
    public static int fromNumerals(String str) {
        for (int i = 0; i < numerals.length; i++) {
            if (str.equals(numerals[i])) {
                return i;
            }
        }
        return -1; // this should never happen
    }

    //*************************************************************************

    /**
      * converts an int to a char A-I
      * @param x: integer to convert to a numeral
      */
    public static char toLetters(int x) {
        return (x < 0 || 8 < x) ? 'Z' : ((char)(x + 'A'));
    }

    //*************************************************************************

    /**
      * converts a character to an integer value
      * @param ch: character to convert
      */
    public static int fromLetters(char ch) {
        return (ch < 'A' || 'I' < ch) ? -1 : (ch - 'A');
    }

    //*************************************************************************

    /** 
     *  returns true if the string represents a possibly legal move
     *  i.e. the string is of the correct format
     *   FIXME: this currently breaks if you input a wall-placing move
     * @param board
     * @param move: a string representing a move
     */
    // can we make this private? we have it now protected
    protected static boolean parseMove(String move) {
        move = move.trim();
        String [] strs = move.split("-");
        if (strs.length == 1) {
            // no '-' in the string; this move is a wall placement
            return false; // walls are not implemented yet
        } 
        else if (strs.length == 2) {
            // one '-' in the string; this is a possible pawn movement
            if (move.length() > 6) {
                return false; // longest square is VIII-A 
            }
            if (strs[1].length() != 1) {
                return false; // reject strings like "I-AHI"
            }
            int x = fromNumerals(strs[0]);
            int y = fromLetters(strs[1].charAt(0));
            if (x == -1 || y == -1) {
                return false; // at least one of the halves of the move string 
                              // could not be parsed 
            }
            return true;
        } 
        // yeah, anything else is not allowed
        return false;
    }

    //*************************************************************************

    /**
      * returns true if the string represents a legal move on that gameboard
      * @param board: GameBoard object to move on
      * @param p: Player object requesting the move
      * @param move: String that contains the move destination
      */
    public static boolean validate(GameBoard board, Player p, String move) {
        return parseMove(move) 
            ? validate(board, board.getPlayerLoc(p), getSquare(board,move), -1, 0) 
            : false;
    }

    //*************************************************************************

    /**
      * validates a user move by checking for walls that might be obstructing
      *   the direction we want to jump to, checking if the destination is
      *   a square adjacent to the player's current location, or if the dest is
      *   adjacent to another player (who is adjacent to the player making the 
      *   move)
      * @param board GameBoard to validate a move on
      * @param currLoc the square we are checking adjacent squares from
      * @param dest the square we wish to move to
      * @param dontCheckMe flag to prevent recursing to a previous location
      * @param numJumps flag to prevent a 4th jump, inc. of player clustering
      * @return true if move is valid, false otherwise 
     */
    private static boolean validate(GameBoard board, Square currLoc, Square dest, 
                                    int dontCheckMe, int numJumps) {
        int direction = 86; 
        for ( int i = 0; i < 4; i++ ) {
            // Calculate the x and y offsets
            int x = ((direction & 8)  >> 3) * Integer.signum(direction);
            int y = ((direction & 16) >> 4) * Integer.signum(direction);
            
            // Retrieve a square to compare
            Square checkLoc = board.getSquare(currLoc.getX() + x,
                                              currLoc.getY() + y);
            // Modify bits
            direction = Integer.rotateRight(direction,1);
            //It is possible to check for a square that is outside of the board
            if ( checkLoc != null ) {


                // NEEDS TESTING
                //* [Hint: remove one of the slashes to comment this piece out]
                // Confirms if there is a wall obstructing the direction we
                //   want to check for our destination
                // Note: 0 = down, 1 = right, 2 = up, 3 = left
                switch ( i ) {
                    // If we encounter a wall, continue to the next iteration
                    case 0: if ( currLoc.hasWallBottom() ) continue; break;
                    case 1: if ( currLoc.hasWallRight()  ) continue; break;
                    case 2: if ( checkLoc.hasWallBottom()) continue; break;
                    case 3: if ( checkLoc.hasWallRight() ) continue; break;
                    default: break; //assertion here maybe?
                }
                //*/


                // If checkLoc is adjacent and where we want to go...
                if ( checkLoc.vacant() && checkLoc == dest )
                    return true;
                // If the spot is occupied, this isn't our third jump, and the
                // adjacent spot to check isn't the spot we were just in, check
                // if our destination could possibly be adjacent to that player
                if ( !checkLoc.vacant() && numJumps !=3 && i != dontCheckMe  
                    && validate(board, checkLoc, dest, (i+2)%4, numJumps+1) )
                    return true;
            }
       }
       return false;
    } // i don't understand this and i'm grumpy about it

    //*************************************************************************

    /**
     * returns a square on the board that we want to move to
     * FIXME: this currently breaks if you input a wall-placing move
     * @param board
     * @param move: a string representing a legal move
     * @return a square on the board
     */
    public static Square getSquare(GameBoard board, String move) {
        // TESTME 
        String [] strs = move.split("-");
        if (strs.length == 1) {
            // no '-' in the string; this move is a wall placement
            System.exit(101); // walls are not implemented yet
            return null; // lol unreachable
        } else if (strs.length == 2) {
            int x = fromNumerals(strs[0]);
            int y = fromLetters(strs[1].charAt(0));
            return board.getSquare(x, y);
        } else {
            // yeah, anything else is not allowed
            // this should never happen, because the move string passed to this
            // function should have already been checked for the correct format
            return null;
        }
    }

    //*************************************************************************

    /**
     * @param board: GameBoard to check
     * @param players: array of players to check if they have won
     * @return a player if that player has won the game, null otherwise
     */
    public static Player getWinner(GameBoard board, Player[] players) {
        // Check if there is only one player left
        Player lastStanding = onlyOnePlayerRemaining(players);
        if (lastStanding != null)
            return lastStanding;

        // Check if one of the players have met the traditional victory
        // condition
        if (players[0] != null && board.getPlayerLoc(players[0]).getY() == 8)
            return players[0];
        if (players[1] != null && board.getPlayerLoc(players[1]).getY() == 0)
            return players[1];
        if (players.length == 4) {
            if (players[2]!=null && board.getPlayerLoc(players[2]).getX() == 8)
                return players[2];
            if (players[3]!=null && board.getPlayerLoc(players[3]).getX() == 0)
                return players[3];
        }
        // No player has won, return null
        return null;
    }

    //*************************************************************************

    /**  
     * checks if there is only one player remaining
     * @param players the array of players in the game
     * @return the the last player remaining or null if more players exist
     */
    private static Player onlyOnePlayerRemaining(Player [] players) {
        int nullPlayerCount = 0; // keeps count of the null players
        int playerFound = 0;     // used to index a player in the array
        
        // Check if we only have one player
        for (int i = 0; i < players.length; i++) {
            // Count the null players in the array
            if ( players[i] == null )
                nullPlayerCount++;
            // Keep track of a player if one is found to be returned
            else 
                playerFound = i;
        }
        // If there are 3 null players, return the only player left
        return ( nullPlayerCount == players.length -1 )
            ? players[playerFound] 
            : null;
    }

    //*************************************************************************

    /**
     * returns the next active player
     * @param current the current player number
     * @param players the players array
     * @return the next available player
     */
    public static Player nextPlayer(int current, Player [] players) {
        int nextP = (current + 1) % players.length; // index of player array
        // Iterate through player array, skipping null players
        while ( nextP != current ) {
            if (players[nextP] != null)
                return players[nextP];
            nextP = (nextP + 1) % players.length;
        }
        //@DEBUGGING
        Deb.ug.println("GameEngine.nextPlayer: " + 
                       "cannot get next player; no more players");
        // No players left
        return null;
    }

    //*************************************************************************

}
