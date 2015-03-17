/* GameEngine.java - CIS405 - teams
 * Last Edit: March 14, 2015
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
 * boolean validate(GameBoard, String)   --> returns if string represents a 
 *                                           legal move
 * boolean parseMove(GameBoard, String)  --> returns if the move string is 
 *                                           valid
 * Sqaure getSquare(GameBoard, String)   --> constructs a square based on the 
 *                                           move string
 * boolean getWinner(GameBoard,Players[])--> checks if a player has won the 
 *                                           game 
 * Player nextPlayer(int, Player[])      --> returns the next player available
 */

public class GameEngine {
    private static final String [] numerals = {"I"  , "II"  , "III", 
                                               "IV" , "V"   , "VI" , 
                                               "VII", "VIII", "IX" };

    /* you can't instantiate this class 
     * this would be private, except then GameEngineTest couldn't extend it
     */
    protected GameEngine() {}

    /** 
      * converts an int to a string of roman numerals
      * @param x: integer to convert to a numeral
      */
    public static String toNumerals(int x) {
        /*if (x < 0 || 8 < x) {
            return "@@@@@@@@@@@@@@"; // this should never happen
        }
        return numerals[x];*/
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
        /*if (x < 0 || 8 < x) {
            return 'Z'; // this should never happen
        }
        return (char) (x + 'A');*/
        return (x < 0 || 8 < x) ? 'Z' : ((char)(x + 'A'));
    }

    //*************************************************************************

    /**
      * converts a character to an integer value
      * @param ch: character to convert
      */
    public static int fromLetters(char ch) {
        /*if (ch < 'A' || 'I' < ch) {
            return -1; // this should never happen
        }
        return (int) (ch - 'A');*/
        return (ch < 'A' || 'I' < ch) ? -1 : ((int) (ch - 'A'));
    }

    //*************************************************************************

    /**
      * returns true if the string represents a legal move on that gameboard
      * @param board: GameBoard object to move on
      * @param p: Player object requesting the move
      * @param move: String that contains the move destination
      */
    public static boolean validate(GameBoard board, Player p, String move) {
        return parseMove(board,move) 
            ? validate(board, p.getLoc(), getSquare(board,move), -1, 0) 
            : false;
    }

    //*************************************************************************

    /**
      * validates a user move by checking adjacent squares and the squares
      *   adjacent to any nearby players
      * @param g GameBoard to validate a move on
      * @param orig the square to check adjacencies from
      * @param dest the destination to loo for
      * @param dontCheckMe flag to prevent recursing to a previous location
      * @param numJumps flag to prevent a 4th jump, in case of clustering
      * @return true true if move is valid, false otherwise 
     */
    private static boolean validate(GameBoard g, Square orig, Square dest, 
                                    int dontCheckMe, int numJumps) {
        int oneZero = 10;   // 1010b
        int sign = 6;       // 0...0110b
        for ( int i = 0; i < 4; i++ ) {
            int x = ((oneZero & 1))       * Integer.signum(sign);
            int y = ((oneZero & 2) >> 1 ) * Integer.signum(sign);
            Square check = g.getSquare(orig.getX() + x, orig.getY() + y);
            oneZero = oneZero >> 1;
            sign = Integer.rotateRight(sign,1);

            if ( check != null ) {
                // Adjacency found check
                if ( check.vacant() && check.equals(dest) )
                    return true;
                // Adjacency occupied check
                if ( !check.vacant() && numJumps !=3 && i != dontCheckMe  
                   && validate(g, check, dest, (i+2)%4, numJumps+1) ) {
                    return true;
                }
            }
       }
       return false;
    } // i don't understand this and i'm grumpy about it

    //*************************************************************************

    /** 
     *  returns true if the string represents a possibly legal move
     *  i.e. the string is of the correct format
     *   FIXME: this currently breaks if you input a wall-placing move
     * @param board
     * @param move: a string representing a move
     */
    // can we make this private?
    protected static boolean parseMove(GameBoard board, String move) {
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
        // See if there is only one player left, and return that victor
        Player lastStanding = onlyOnePlayerRemaining(players);
        if (lastStanding != null)
            return lastStanding;

        // Check if one of the players have met the traditional victory
        // condition
        if (players[0] != null && players[0].getY() == 8)
            return players[0];
        if (players[1] != null && players[1].getY() == 0)
            return players[1];
        if (players.length == 4) {
            if (players[2] != null && players[2].getX() == 8)
                return players[2];
            if (players[3] != null && players[3].getX() == 0)
                return players[3];
        }
        // No player has won, return null
        return null;
    }

        // This loop will count the number of active players.
        // if the number of null players in the players array
        // is one less than the array length, then we only have
        // one active player

    // checks for only one player remaining
    // @param players the array of players in the game
    // @return the only player remaining or null if multiple players in game
    private static Player onlyOnePlayerRemaining(Player [] players) {
        int nullPlayerCount = 0;
        // Check if we only have one player
        for (int i = 0; i < players.length; i++) {
            if ( players[i] == null )
                nullPlayerCount++;
        }
        // If we only have one player left... let's return that player!
        if (nullPlayerCount == players.length - 1) {
            for (int i = 0; i < players.length; i++) 
                if (players[i] != null)
                    return players[i];
        }
        // Else, we have more than one player left
        return null;
    }

    //*************************************************************************

    /**
      * returns the next active player
      * @param current the current player number
      * @param players the players array
      * @return the next available player
      */
    public static Player nextPlayer(int current, Player [] players) {
        /* This loop will make sure that if we are looking at the same 
         * player again, we exit and return null
         * In Game.java, after we call this method, we can check 
         * if player == null then exit the main loop if true
         */
        int nextP = (current + 1) % players.length;
        while ( nextP != current ) {
            if (players[nextP] != null)
                return players[nextP];
            nextP = (nextP + 1) % players.length;
        }
        
        //@DEBUGGING
        //Deb.ug.println("GameEngine.nextPlayer: cannot get next player; no more players");
        return null;
    }

}
