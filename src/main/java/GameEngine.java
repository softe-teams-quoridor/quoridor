/** GameEngine.java - CIS405 - teams
  * _______________________________________________________
  *
  * this class is used by both the client and the server!
  * 
  * ----------------------- METHODS -----------------------
  *
  * validate(GameBoard, String) --> returns true if the string represents
  *                                 a legal move on that gameboard
  * 
  */

public class GameEngine {
    private static final String [] numerals = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    // converts an int to a string of fromNumerals
    public static String toNumerals(int x) {
        if (x < 0 || 10 < x) {
            return "@@@@@@@@@@@@@@"; // this should never happen
        }
        return numerals[x];
    }

    // does the opposite of toNumerals
    public static int fromNumerals(String str) {
        for (int i = 0; i < 11; i++) {
            if (str.equals(numerals[i])) {
                return i;
            }
        }
        return -1; // this should never happen
    }


    /**
      * returns true if the string represents a legal move on that gameboard
      */
    public boolean validate(GameBoard board, String move) {
        // FIXME 
        return false; // no moves are legal
    }

    private int parseMove(String move) {
        // FIXME 
        return 0; // not implemented yet
    }
}
