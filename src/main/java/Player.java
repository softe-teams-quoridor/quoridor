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
 * Player()       --> constructor
 * getPlayerNo()  --> returns the player number
 * getLoc()       --> returns the square the player occupies
 * setLoc(Square) --> sets the player's location on a square
 * getName()      --> returns the player's name
 * getNumWalls()  --> returns number of walls
 * placeWall()    --> decrements numWalls if player has > 0 walls
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
    private String playerName = ""; // player's name
    private Square pawnLoc;         // player's pawn location
    private int numWalls;          // number of walls
    private int playerNo;           // unique player I.D. between 0 and 3
    private static int numAssign = 0; // for assigning playerNo

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
        numAssign++;
    } 

    /**
      * returns the player's number
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
      */
    public String getName(){
        return playerName;
    }

    // Consider if this is needed when we start to implement wall placement

    /** 
      * returns the number of walls
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

}
