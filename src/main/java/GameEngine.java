/** GameEngine.java - CIS405 - teams
  * _______________________________________________________
  *
  * this class is used by both the client and the server!
  * 
  * ----------------------- METHODS -----------------------
  *
  * validate(GameBoard, String) --> returns true if the string represents
  *                                 a legal move on that gameboard
  * toNumerals(int), 
  * fromNumerals(String),
  * toLetters(int),
  * fromLetters(char) --> conversions between ints and numerals/letters
  * 
  */

public class GameEngine {
    private static final String [] numerals = {"", "I", "II", "III", 
                                               "IV", "V", "VI", "VII",
                                               "VIII", "IX", "X"};
    private static final char [] letters = {'Z', 'A', 'B', 'C', 'D',
                                            'E', 'F', 'G', 'H', 'I'};

    // converts an int to a string of roman numerals
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

    // converts an int to a char A-I
    public static char toLetters(int x) {
        if (x < 0 || 10 < x) {
            return 'Z'; // this should never happen
        }
        return letters[x];
    }

    // does the opposite of toLetters
    public static int fromLetters(char ch) {
        for (int i = 0; i < 11; i++) {
            if (letters[i] == ch) {
                return i;
            }
        }
        return -1; // this should never happen
    }



    /**
      * returns true if the string represents a legal move on that gameboard
      */
    public static boolean validate(GameBoard board, Player p, String move) {
        // FIXME 
        return false; // no moves are legal
    }

    /* currently this returns a square on the board
     *   FIXME: this currently breaks if you input a wall-placing move
     * @param board
     * @param move: a string representing a move
     */
    public static Square parseMove(GameBoard board, String move) {
        // TESTME 
        String [] strs = move.split("-");
        int y = fromNumerals(strs[0]) - 1;
        int x = fromLetters(strs[1].charAt(0)) - 1;
        return board.getSquare(x, y);
    }
}
