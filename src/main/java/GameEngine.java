/* GameEngine.java - CIS405 - teams
 * Last Edit: March 25, 2015
 * ____________________________________________________________________________
 *
 * used for game rules and validation. capable of converting numeral/character
 *   to appropriate array index, validate proper player moves, check victory
 *   conditions, and rotate player turn-order.
 *
 * this class is used by both the client and the server!
 * 
 * --------------------------------- METHODS ----------------------------------
 *
 * String toNumerals(int)    --> converts int to a numeral 
 * int fromNumerals(String)  --> converts string(numeral) to an int
 * char toLetters(int)       --> converts int to a letter ex 0 -> A
 * int fromLetters(char)     --> conversions between ints and numerals/letters
 * boolean parseMove(String) --> returns if the move string is valid
 * boolean parseWall(String) --> returns if the wall placement string is valid
 * boolean validate(GameBoard, String)  
 *                           --> returns if string represents a legal move
 * Sqaure getSquare(GameBoard, String) 
 *                           --> constructs a square based on the move string
 * Player getWinner(GameBoard,Players[])
 *                           --> checks if a player has won the game 
 * Player nextPlayer(int, Player[])      
 *                           --> returns the next player available
 *
 * ----------------------------- PRIVATE METHODS ------------------------------
 *
 * Player getLastPlayerStanding(Player[])
 *                           --> returns if a player is the last one alive
 * 
 */

public class GameEngine {

    private static final String [] numerals = 
        {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };

    //*************************************************************************

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

    /** @deprecated use parseMove that returns a Square instead 
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

    /** !!!!NEEDS TESTING!!!!
      * parses an input player move and returns a square from the board if it
      *   is valid 
      * @param board the board to get a square from
      * @param move a string representing a move
      */
    protected static Square parseMove ( GameBoard board, String move ) {
        move = move.trim();
        String [] strs = move.split("-");
        // Reject any string that has more than one "-"
        if (strs.length != 2)
            return null;   
        // Reject any string that has more than four numerals and/or more than
        //   one letter
        if (strs[0].length() > 4 && strs[1].length() != 1)
            return null;
        // Parse the string into the respective x and y coordinates
        int x = fromNumerals(strs[0]);
        int y = fromLetters(strs[1].charAt(0));
        // Reject any coordinate that is not within the game board
        if (x == -1 || y == -1) {
            return null;  
        }
        return board.getSquare ( x, y );
    }

    //*************************************************************************
   
    /** @deprecated use parseWall that returns an array of squares
      * returns true if a string represents a correctly formatted wall
      *  placement
      * @param move the string to parse
      */
    protected static boolean parseWall ( String move ) {
        move = move.trim();
        // (V-A, V-B)

        // Reject any move that does not start and end with parenthesis
        if ( !move.startsWith("(") && !move.endsWith(")") )
            return false;

        String[] commaSep = move.split(",");
        // [0] == (V-A
        // [1] == V-B)

        // Make sure the string array has only 2 elements
        if ( commaSep.length != 2 )
            return false;

        // Remove parentheses
        commaSep[0] = commaSep[0].replace ( "(", "" );
        commaSep[1] = commaSep[1].replace ( ")", "" );
        // [0] == V-A
        // [1] == V-B
       
        commaSep[0] = commaSep[0].trim();
        String[] firstW = commaSep[0].split("-");
        // [0] == V
        // [1] == A
        commaSep[1] = commaSep[1].trim();
        String[] secndW = commaSep[1].split("-");
        // [0] == V
        // [1] == B

        // Make sure the two string arrays have only 2 elements
        if ( firstW.length != 2 && secndW.length != 2 )
            return false;

        int firstX = fromNumerals ( firstW[0] );
        int firstY = fromLetters  ( firstW[1].charAt(0) );
        // X == 4
        // Y == 0
        int secndX = fromNumerals ( secndW[0] );
        int secndY = fromLetters  ( secndW[1].charAt(0) );
        // X == 4
        // Y == 1

        // Check if the conversions returned an erroneous value
        if ( firstX == -1 || firstY == -1 || secndX == -1 || secndY == -1 )
            return false;

        // Check if the second location is to the RIGHT of the first,
        //  or if it BELOW the first
        // also make sure if horizontal, we don't place on the bottom row
        //  and make sure if vertical, we don't place on the right-most row
        if ( firstX+1 == secndX && firstY == secndY && firstY != 8 ||
             firstY+1 == secndY && firstX == secndX && firstX != 8)
            return true;
        
        return false;
    }
    
    //*************************************************************************

    /**
      * parses a player input wall action and returns an array of two squares
      *   if the action is valid
      * @param board board to getSquares from
      * @param move the string to parse
      */
    protected static Square[] parseWall ( GameBoard board, String move ) {
        move = move.trim();
        // (V-A, V-B)

        // Reject any move that does not start and end with parenthesis
        if ( !move.startsWith("(") && !move.endsWith(")") )
            return null;

        String[] commaSep = move.split(",");
        // [0] == (V-A
        // [1] == V-B)

        // Make sure the string array has only 2 elements
        if ( commaSep.length != 2 )
            return null;

        // Remove parentheses
        commaSep[0] = commaSep[0].replace ( "(", "" );
        commaSep[1] = commaSep[1].replace ( ")", "" );
        // [0] == V-A
        // [1] == V-B
       
        commaSep[0] = commaSep[0].trim();
        String[] firstW = commaSep[0].split("-");
        // [0] == V
        // [1] == A
        commaSep[1] = commaSep[1].trim();
        String[] secndW = commaSep[1].split("-");
        // [0] == V
        // [1] == B

        // Make sure the two string arrays have only 2 elements
        if ( firstW.length != 2 && secndW.length != 2 )
            return null;

        int firstX = fromNumerals ( firstW[0] );
        int firstY = fromLetters  ( firstW[1].charAt(0) );
        // X == 4
        // Y == 0
        int secndX = fromNumerals ( secndW[0] );
        int secndY = fromLetters  ( secndW[1].charAt(0) );
        // X == 4
        // Y == 1

        // Check if the conversions returned an erroneous value
        if ( firstX == -1 || firstY == -1 || secndX == -1 || secndY == -1 )
            return null;

        // Check if the second location is to the RIGHT of the first,
        //  or if it BELOW the first
        // also make sure if horizontal, we don't place on the bottom row
        //  and make sure if vertical, we don't place on the right-most row
        if ( firstX+1 == secndX && firstY == secndY && firstY != 8 ||
             firstY+1 == secndY && firstX == secndX && firstX != 8) {
            Square[] wallSquares = new Square[2];
            wallSquares[0] = board.getSquare ( firstX, firstY );
            wallSquares[1] = board.getSquare ( secndX, secndY );
            return wallSquares; 
        }
        
        return null;
    }

    //*************************************************************************

    /**
      * returns true if the string represents a legal move on that gameboard
      * @param board GameBoard object to move on
      * @param p Player object requesting the move
      * @param move String that contains the move destination
      */
    public static boolean validateMove ( GameBoard b, Player p, String move ) {
        Square moveSquare = parseMove ( b, move );
        return ( moveSquare != null ) ?
            validateMove ( b, b.getPlayerLoc(p), moveSquare, -1, 0 ) 
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
    private static boolean validateMove ( GameBoard board, Square currLoc, 
                                Square dest, int dontCheckMe, int numJumps ) {
        int direction = 86; // we use bit shifting to get the coordinates
        for ( int i = 0; i < 4; i++ ) {
            // This is the order in which we check for adjacencies:
            //    ITERATION         COORDINATES
            //  i = 0 -> down   |  x = 0;  y = 1
            //  i = 1 -> right  |  x = 1;  y = 0
            //  i = 2 -> up     |  x = 0;  y = -1
            //  i = 3 -> left   |  x = -1; y = 0

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
                    && validateMove (board,checkLoc,dest,(i+2)%4,numJumps+1) )
                    return true;
            }
       }
       return false;
    } // i don't understand this and i'm grumpy about it
      // I added more comments and I can offer to explain it if you'd like :)

    //*************************************************************************

    /** @deprecated this method is redunant, parseMove now gets the square
     * returns a square on the board that we want to move to
     * FIXME: this currently breaks if you input a wall-placing move
     * @param board GameBoard object to retrieve a square from
     * @param move a string representing a legal move
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
     * @param board GameBoard to check
     * @param players array of players to check if they have won
     * @return a player if that player has won the game, null otherwise
     */
    public static Player getWinner(GameBoard board, Player[] players) {
        // Check if there is only one player left
        Player lastStanding = getLastPlayerStanding(players);
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
     * @return the last player remaining or null if more players exist
     */
    private static Player getLastPlayerStanding(Player [] players) {
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
}
