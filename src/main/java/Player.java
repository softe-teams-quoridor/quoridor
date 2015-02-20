/** Player.java - CIS405 - teams
  * Last Edit: February 17, 2015
  * _______________________________________________________ 
  *
  * represents the data of a player of the game, such
  *  as the player's name, number of walls, and the player's
  *  location on the gameboard
  * 
  * ----------------------- METHODS -----------------------
  * 
  * Player()      --> constructor
  * getLoc()      --> returns the square the player occupies
  * getNumWalls() --> returns number of walls
  * placeWall()   --> decrements numWalls if player has > 0 walls
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
    
    // Constants
    private static final int TWO_PLAYER_WALLS = 10; 
    private static final int FOR_PLAYER_WALLS = 5; 



    // Data Members
    private String playerName = ""; // player's name
    private Square pawnLoc;         // player's pawn location
    private int numWalls;          // number of walls



    /** Constructor
      * instantiates a player object
      * @param playerName = name of the player
      * @param startLoc   = starting square on the gameboard
      * @param numWalls   = number of walls this player has to start with
      */
    public Player(String playerName, Square startLoc, int numWalls) {
        this.playerName = playerName;
        this.pawnLoc    = startLoc;
        this.numWalls   = numWalls;
    } 



    /** getLoc
      * returns the square that the player's pawn is occupying
      * @return = returns a square
      */
    public Square getLoc(){
	    return pawnLoc; 
    }



    /** getNumWalls
      * returns the number of walls
      */
    public int getNumWalls() {
        return numWalls;
    }



    /** placeWalls
      * decrements the number of walls this player has if player has more than 0 walls
      */    
    public void placeWall() {
        if (numWalls > 0)
            numWalls--;
        else
            throw new RuntimeException("Cannot place wall; player is out of walls!");
    }

}
