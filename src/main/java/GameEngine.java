/** GameEngine.java - CIS405 - teams
  * _______________________________________________________
  *
  * this class is used by both the client and the server!
  * 
  * ----------------------- METHODS -----------------------
  *
  * validate(GameBoard, String)  --> returns true if the string represents
  *                                    a legal move on that gameboard
  * toNumerals(int)              --> converts int to a numeral 
  * fromNumerals(String)         --> converts string to an int
  * toLetters(int)               --> converts int to a letter ex 0 -> A
  * fromLetters(char)            --> conversions between ints and numerals/letters
  * getSquare(GameBoard, string) --> constructs a square based on the move string
  * checkVictory(GameBoard,      --> checks if a player has won the game 
  *              Players[])
  */

public class GameEngine {
    private static final String [] numerals = {"I", "II", "III", 
                                               "IV", "V", "VI", "VII",
                                               "VIII", "IX", ""};
    private static final char [] letters = {'A', 'B', 'C', 'D',
                                            'E', 'F', 'G', 'H', 'I', 'Z'};

    /** 
      * converts an int to a string of roman numerals
      * @param x: integer to convert to a numeral
      */
    public static String toNumerals(int x) {
        if (x < 0 || letters.length < x) {
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
        if (x < 0 || letters.length < x) {
            return 'Z'; // this should never happen
        }
        return letters[x];
    }

    /**
      * converts a character to an integer value
      * @param ch: character to convert
      */
    public static int fromLetters(char ch) {
        for (int i = 0; i < numerals.length; i++) {
            if (letters[i] == ch) {
                return i;
            }
        }
        return -1; // this should never happen
    }

    /**
      * returns true if the string represents a legal move on that gameboard
      * @param board: GameBoard object to move on
      * @param p: Player object requesting the move
      * @param move: String that contains the move destination
      */
    public static boolean validate(GameBoard board, Player p, String move) {
        //***TEST ME****
        // Check if the square-to-move-to is adjacent to the player
        if (! parseMove ( board, move )) {
            return false;
        }
        Square moveTo = getSquare(board, move);
        Square moveFrom = p.getLoc();
        // Check up
        if ( moveTo.getX() == moveFrom.getX()    &&
             moveTo.getY() == moveFrom.getY() +1 )
            return moveTo.vacant(); // && moveTo.
        // Check down
        if ( moveTo.getX() == moveFrom.getX()    &&
             moveTo.getY() == moveFrom.getY() -1 )
            return moveTo.vacant();
        // Check right
        if ( moveTo.getX() == moveFrom.getX() +1 &&
             moveTo.getY() == moveFrom.getY()    )
            return moveTo.vacant();
        // Check left
        if ( moveTo.getX() == moveFrom.getX() -1 &&
             moveTo.getY() == moveFrom.getY()    )
            return moveTo.vacant();
        // non-adjacent location
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
            if (players[0] != null && players[0].getY() == 8) {
                return true;
            }
            else if (players[1] != null && players[1].getY() == 0) {
                return true;
            } 
        }
        return false;
    }
}
