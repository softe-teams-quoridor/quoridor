/** GameEngine.java - CIS405 - teams
  * Last Edit: February 23, 2014
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

    // converts an int to a string of numerals
    public static String numerals(int x) {
        switch (x) {
            case 0: return ""; // empty string
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            case 5: return "V";
            case 6: return "VI";
            case 7: return "VII";
            case 8: return "VIII";
            case 9: return "IX";
            case 10: return "X";
            default: return "@@@@@@@"; // this should never happen
        }
    }

    // does the opposite of numerals
    // FIXME
    public static int antiNumerals(String str) {
        /*
        switch (str) {
            case "": return 0; // empty string
            case "I": return 1;
            case "II": return 2;
            case "III": return 3;
            case "IV": return 4;
            case "V": return 5;
            case "VI": return 6;
            case "VII": return 7;
            case "VIII": return 8;
            case "IX": return 9;
            case "X": return 10;
            default: return -1; // this should never happen
        }
        */
        return -1; // FIXME
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
