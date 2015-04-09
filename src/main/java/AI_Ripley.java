/* AI_Ripley.java - CIS405 - teams
 * Version: 0.1 <-- I probably won't keep up with this
 * Last Modified: April 8th
 * ____________________________________________________________________________
 *
 *      current version will assume AI is player 0; there is currently no
 *      transpositioning of walls and other player movements
 *
 *      Note: Future versions of Ripley will need to know which player it is so
 *       it can transpose its moves and placements accordingly
 *
 */

public class AI_Ripley implements QuoridorAI {

    private int[][] virtualBoard;

    /**
      * constructs Ripley's virtualBoard and other deliciousness
      */
    public AI_Ripley () {
        // fill array with integer values
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * constructs Ripley's virtualBoard and the board of the other players
      */
    public AI_Ripley ( int numPlayers ) {

    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * return a move string, be it a player move or wall placement
      */
    public String getMove(GameBoard b, Player p) {
        return "";
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * resets the AI (might not do anything)
      */
    public void reset() {
        // This may or may not do anything meaningful
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    /**
      * this needs to return the name of this AI
      */
    public String toString() {
        return "Ripley";
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
      * makes a decision if the AI should move forward or place wall based on
      * every player's relative distance to their respective goal
      */
    private void decide() {
        // Unsure of type and parameters right now
        // true move, false wall?
        // if currentLocationVal > anyoneElsesVal
        //      (return false) place a wall!
        // else
        //      (return true) I mean... I guess we can just move forward?
    }

}
