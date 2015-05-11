/* Player.java - CIS405 - teams
 * ____________________________________________________________________________
 *
 * Symbolizes a Player of the game Quoridor. Includes fields such as the 
 *  player's name, the number of Walls the player has to be able to place on
 *  the GameBoard, and a unique identification number of the Player, which is
 *  typically used to index an array.
 * 
 * --------------------------------- METHODS ----------------------------------
 * 
 * Player(int,int)        --> constructs player with an ID and wall count
 * Player(int,string,int) --> constructs player with ID, name, and wall count
 * int getPlayerNo()      --> returns the Player's number
 * String toString()       --> returns the Player's name
 * int getNumWalls()      --> returns number of walls remaining
 * boolean mayPlaceWall() --> returns if a player has walls that they may place
 * void useWall()         --> decrements numWalls
 */

public class Player {

    private String playerName = "";  // player's name
    private int numWalls;            // number of walls remaining
    private int playerNo;            // unique player I.D. between 0 and 3 inc

    //*************************************************************************

    /**
      * Constructs a Player object with a player number and the number of walls
      * that this Player will be able to use in the game. The Player's name is 
      * based on the player number.
      *     @param pno Player number
      *     @param numWalls number of walls the Player is given
      */
    public Player(int pno, int numWalls) {
        this(pno, "player_" + pno, numWalls);
    } 

    /**
      * Constructs a Player object with a player number, a name, and a quantity
      * of Wall objects that this Player will be able to use during the game.
      *     @param pno player number
      *     @param playerName name of player
      *     @param numWalls number of walls the player is given
      *     @throws assertion if name given is null
      *     @throws assertion if the number of walls aren't 0, 5, or 10
      *     @throws assertion if the player number isn't between 0 and 3 inclusive
      */
    public Player(int pno, String playerName, int numWalls) {
        assert (playerName != null);
        assert (numWalls == 0 || numWalls == 5 || numWalls == 10);
        assert (pno >= 0 && pno < 4);
        this.playerName = playerName;
        this.numWalls = numWalls;
        this.playerNo = pno;
    } 

    //*************************************************************************

    /**
      * Returns the Player's unique identification number
      *     @return the Player's ID number
      */
    public int getPlayerNo() {
        return playerNo;
    }

    //*************************************************************************

    /**
      * Returns the name of the Player
      *     @return the name of the Player
      */
    public String toString() {
        return playerName;
    }

    //*************************************************************************

    /** 
      * Returns the number of walls this Player has left.
      *     @return the number of walls remaining
      */
    public int getNumWalls() {
        return numWalls;
    }
    
    /**
      * Checks to see if the player can place a Wall
      *     @return if this player has any walls left to place     
      */    
    public boolean mayPlaceWall() {
        return (numWalls > 0);        
    }

    /**
      * Decrements the number of walls
      *     @throws assertion error if Player no longer has any walls
      */
    public void useWall() {
        assert (mayPlaceWall());
        numWalls--;
    } 
}
