/* GameEngine.java - CIS405 - teams
 * Last Edit: March 13, 2015
 * _________________________________________________________________________________
 *
 * this class is used by both the client and the server!
 * 
 * ----------------------------------- METHODS -------------------------------------
 *
 * validate(GameBoard, String)  --> returns if string represents a legal move
 * toNumerals(int)              --> converts int to a numeral 
 * fromNumerals(String)         --> converts string(numeral) to an int
 * toLetters(int)               --> converts int to a letter ex 0 -> A
 * fromLetters(char)            --> conversions between ints and numerals/letters
 * getSquare(GameBoard, string) --> constructs a square based on the move string
 * checkVictory(GameBoard,      --> checks if a player has won the game 
 *              Players[])
 */

public class GameEngine {
    private static final String [] numerals = {"I", "II", "III", 
                                               "IV", "V", "VI", "VII",
                                               "VIII", "IX"};

    /** 
      * converts an int to a string of roman numerals
      * @param x: integer to convert to a numeral
      */
    public static String toNumerals(int x) {
        if (x < 0 || 8 < x) {
            return "@@@@@@@@@@@@@@"; // this should never happen
        }
        return numerals[x];
    }

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

    /**
      * converts an int to a char A-I
      * @param x: integer to convert to a numeral
      */
    public static char toLetters(int x) {
        if (x < 0 || 8 < x) {
            return 'Z'; // this should never happen
        }
        return (char) (x + 'A');
    }

    /**
      * converts a character to an integer value
      * @param ch: character to convert
      */
    public static int fromLetters(char ch) {
        if (ch < 'A' || 'I' < ch) {
            return -1; // this should never happen
        }
        return (int) (ch - 'A');
    }

    /**
      * returns true if the string represents a legal move on that gameboard
      * @param board: GameBoard object to move on
      * @param p: Player object requesting the move
      * @param move: String that contains the move destination
      */
    public static boolean validate(GameBoard board, Player p, String move) {
        //***TEST ME****
        // make sure the response can reasonably represent a move
  /*      if (! parseMove ( board, move )) {
            return false;
        }
        Square destination = getSquare(board, move);
        Square origin = p.getLoc();
        // Check if the square-to-move-to is adjacent to the player
        // Check up
        if ( destination.getX() == origin.getX()    &&
             destination.getY() == origin.getY() +1 )
            return destination.vacant(); // && moveTo.
        // Check down
        if ( destination.getX() == origin.getX()    &&
             destination.getY() == origin.getY() -1 )
            return destination.vacant();
        // Check right
        if ( destination.getX() == origin.getX() +1 &&
             destination.getY() == origin.getY()    )
            return destination.vacant();
        // Check left
        if ( destination.getX() == origin.getX() -1 &&
             destination.getY() == origin.getY()    )
            return destination.vacant();
        // non-adjacent location
        return false; 
*/
        
        if ( parseMove ( board, move ) ) {
            return validate ( board, p.getLoc(), getSquare(board, move), -1);
        }
        return false;
        
    }
    
    /**
      * please don't tamper with this...
      * this should hopefully recursively check adjacent squares of the
      * player's very first position and any positions it can jump to.
      * this should evaluate x and y to the following values respectively:
      *                       0, 1    square below
      *                       1, 0    square right
      *                       0,-1    square above
      *                      -1, 0    square left
      */
    private static boolean validate ( GameBoard g, Square orig, Square dest, int dontCheck ) {
        int oneZero = 10;
        int sign = 14;
        // Theoretically, this should check all 4 directions using bitwise ops
        for ( int i = 0; i < 4; i++ ) {
            int x = ((oneZero & 1))       * Integer.signum(sign);
            int y = ((oneZero & 2) >> 1 ) * Integer.signum(sign);
            Square check = g.getSquare(orig.getX() + x,
                                       orig.getY() + y);
            //@DEBUGGING
            if ( check != null )
                Deb.ug.println("validate: checking square@ " + check.getX() 
                                                      + ", " + check.getY());
            else
                Deb.ug.println("validate: square is null");
    
            oneZero = oneZero >> 1;
            sign = Integer.rotateRight(sign,1);

            if ( check != null   && (check.equals(dest) ||
                 !check.vacant() && i != dontCheck && validate (g, check, dest,(i+2)%4)))
            {
                //@DEBUGGING
                Deb.ug.println("validate: true");
                return true;
            }
       }
       //@DEBUGGING
       Deb.ug.println("validate: false");
       return false;
    }
    

    /** 
     *  returns true if the string represents a possibly legal move
     *  i.e. the string is of the correct format
     *   FIXME: this currently breaks if you input a wall-placing move
     * @param board
     * @param move: a string representing a move
     */
    public static boolean parseMove(GameBoard board, String move) {
        // TESTME 
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

    /**
     * this returns a square on the board
     * FIXME: this currently breaks if you input a wall-placing move
     * @param board
     * @param move: a string representing a legal move
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

    /**
      * Checks if any player has won the game
      * @param board: GameBoard to check
      * @param players: array of players to check if they have won
      * FIXME: implement 4-player mode 
      */
    public static boolean checkVictory(GameBoard board, Player[] players) {
        if (players.length == 2) {
            if (players[0] != null && players[0].getLoc().getY() == 8) {
                return true;
            }
            else if (players[1] != null && players[1].getLoc().getY() == 0) {
                return true;
            } 
        }
        return false;
    }

    public static Player nextPlayer(int current, Player [] players) {
        current = (current + 1) % players.length;
        if (players[current] != null) {
            return players[current];
        }
        return nextPlayer(current, players);
    }

}
