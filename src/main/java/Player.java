/** Player.java - CIS405 - teams
  * Last Edit: February 16, 2015
  * represents the data surrounding a player of the game, such
  * as the player's name, number of walls, and the player's
  * location on the gameboard
  * ---------- METHODS ----------
  * Player()      ---> constructor
  * getNumWalls() ---> returns number of walls
  * placeWall()   ---> decrements numWalls if player has > 0 walls
  *
  * NOTE: THIS FILE NEEDS REVIEW; CHECK COMMENTS BELOW
  * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  */

class Player {
    
    // Constants
    private static final byte TWO_PLAYER_WALLS = 10; 
    private static final byte FOR_PLAYER_WALLS = 5; 

    // Members
    private String playerName = "";
    private Square pawnLoc;
    private byte numWalls;



    /** Constructor
      * instantiates a player object
      * @param playerName = name of the player
      * @param startLoc   = starting square on the gameboard
      * @param numWalls   = number of walls this player has to start with
      */
    public Player(String playerName, Square startLoc, byte numWalls) {
        this.playerName = playerName;
        this.pawnLoc    = startLoc;
        this.numWalls   = numWalls;
    } 



    // === Consider if this is needed if we're checking for the number of walls
    // === within method placeWall() - Cavanagh, Walling (2/16)
    /** getNumWalls
      * returns the number of walls
      */
    public getNumWalls() {
        return numWalls;
    }



    /** placeWalls
      * decrements the number of walls this player has if player has more than 0 walls
      */    
    public placeWall() {
        if (numWalls > 0)
            numWalls--;
        else
            // Depending on what we want the object that implements the game rules to
            // do, we may want this method to be a boolean method and return false
            // if the player cannot place any more walls. - Walling (2/16)
            throw new RunTimeException("Cannot place wall; player is out of walls!");
    }
}
