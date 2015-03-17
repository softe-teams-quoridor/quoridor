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
 * boolean isActive()    --> returns the player's active status
 * int getNumWalls()     --> returns number of walls
 * void placeWall()      --> decrements numWalls if player has > 0 walls
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
 */

class Player {

    // Data Members
    private String playerName = "";    // player's name
    private Square pawnLoc;            // player's pawn location
    private int numWalls;              // number of walls
    private int playerNo;              // unique player I.D. between 0 and 3
    private static int numAssign = 0;  // for assigning playerNo
    private boolean isActive;          // If the player is active

    /**
     * instantiates a player object
     * @param playerName = name of the player
     * @param startLoc   = starting square on the gameboard
     * @param numWalls   = number of walls this player has to start with
     */
    public Player(String playerName, Square startLoc, int numWalls) {
        this.playerName = playerName;
        this.pawnLoc    = startLoc;
        this.numWalls   = numWalls;
        this.playerNo = numAssign;
        isActive = true;
        numAssign++;
    } 

    /**
      * returns the player's number
      * @return the players number
      */
    public int getPlayerNo() {
        return playerNo;
    }

    /** 
      * returns the square that the player's pawn is occupying
      * @return = returns a square
      */
    public Square getLoc(){
        return pawnLoc; 
    }
    
    /**
      * sets the player's location
      * @param sqr square to set player to
      */
    public void setLoc(Square sqr) {
        pawnLoc = sqr;
    }

    /**
      * returns the player's name
      * @return the name of the player
      */
    public String getName(){
        return playerName;
    }

    /**
      * returns a player's active status
      * @return if the player is still active
      */
    public boolean isActive() {
        return this.isActive;
    }

    // Consider if this is needed when we start to implement wall placement

    /** 
      * returns the number of walls
      * @return the number of walls remaining
      */
    public int getNumWalls() {
        return numWalls;
    }

    // Consider if this should be a boolean

    /**
      * decrements the number of walls this player has
      * @exception RuntimeException thrown if player has 0 walls
      */    
    public void placeWall() {
        if (numWalls > 0)
            numWalls--;
        else
            throw new RuntimeException("Cannot place wall; player is out of walls!");
    }

    /**
      * resets the current assignment number to zero
      * useful for a move server to reset the count of players after each game
      */
    public static void resetPlayerNos() {
        numAssign = 0;
    }
}
