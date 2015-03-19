/* Player.java - CIS405 - teams
 * Last Edit: March 13, 2015
 * _______________________________________________________ 
 *
 * represents the data of a player of the game, such
 *   as the player's name, number of walls, and the player's
 *   location on the gameboard
 * 
 * ----------------------- METHODS -----------------------
 * 
 * Player()              --> constructor
 * int getPlayerNo()     --> returns the player number
 * Sqaure getLoc()       --> returns the square the player occupies
 * void setLoc(Square)   --> sets the player's location on a square
 * String getName()      --> returns the player's name
 * int getNumWalls()     --> returns number of walls
 * boolean mayPlaceWall()--> decrements numWalls if player has > 0 walls
 * void resetPlayerNos() --> resets numAssign to zero
 * 
 * Considerations
 *     
 *     Tuesday (2/17)
 *      Is getNumWalls() needed if we're checking for the number of walls
 *      within method placeWall()? - Cavanagh, Walling (2/16)
 *     
 *      Depending on what the object that implements the game rules does,
 *      we may want placeWall() to be a boolean method and return false
 *      if the player cannot place any more walls - Walling (2/16)
 *
 *     oh yeah that's way better
 */

class Player {

    // Data Members
    private String playerName = "";    // player's name
    private int numWalls;              // number of walls
    private int playerNo;              // unique player I.D. between 0 and 3
    
    // Changed to protected for testing purposes -- Eric
    protected static int numAssign = 0;  // for assigning playerNo

    /*

    public Player(String playerName, int numWalls) {
        this.playerName = playerName;
        this.numWalls   = numWalls;
        this.playerNo = numAssign;
        numAssign++;
    }

    */

    public Player(int pno, String playerName, int numWalls) {
        this.playerName = playerName;
        this.numWalls = numWalls;
        this.playerNo = pno;
    } 

    /**
      * returns the player's number
      * @return the players number
      */
    public int getPlayerNo() {
        return playerNo;
    }

    /**
      * returns the player's name
      * @return the name of the player
      */
    public String getName() {
        return playerName;
    }

    // Consider if this is needed when we start to implement wall placement
    // possibly useful for display if we wanna show walls in the hand
    /** 
      * returns the number of walls
      * @return the number of walls remaining
      */
    public int getNumWalls() {
        return numWalls;
    }

    /**
      * Checks to see if the player can place a wall
      * @return true if this player has any walls left to place     
      */    
    private boolean mayPlaceWall() {
        return(numWalls != 0);        
    }

    /**
      * Decrements the number of walls if possible
      * @return true if walls have been decremented, false otherwise
      */
    public boolean placeWall() {
        if(mayPlaceWall()) {
            numWalls--;
            return true;
        }
        return false;
    } 
}
