/** Square.java - CIS 405 - teams 
  * Last Edit: February 19, 2015
  * _______________________________________________________
  *
  * represents a single cell on a GameBoard grid
  *
  * --------------------- METHODS -------------------------
  *
  * Square()            --> constructor
  * getPlayer()         --> returns player on square
  * addPlayer(Player)   --> assigns a Player obj to this square
  * removePlayer()      --> removes a Player obj from this square
  * vacant()            --> returns true if no player on this square
  * placeWallVert(bool) --> places a vertical wall
  * placeWallHorz(bool) --> places a horizontal wall
  * getVertWallStatus() --> returns if a vert wall starts or ends
  * getHorzWallStatus() --> returns if a horz wall starts or ends
  */

public class Square {
    // Data members
    private Player occupying; // Indicates which player occupies this square
    private Wall vert;        // vertical wall
    private Wall horz;        // horizontal wall



    /** Constructor
      * instantiates a square object
      */
    public Square() {
        occupying = null;
        vert = null;
        horz = null;
    }

    
    
    /** getPlayer
      * returns the player object occupying this square
      * @return = player object occupying this square
      */
    public Player getPlayer(){
	    return occupying;
    }



    /** addPlayer
      * adds a player to the square
      */
    public void addplayer(Player p){
	    occupying = p;
    }



    /** removePlayer
      * removes a player from the square
      */
    public void removePlayer() {
        occupying = null;
    }

    /** vacant
      * returns false if this square is occupied by a player
      */
    public boolean vacant() {
        return (this.occupying == null);
    }



    /** placeWallVert
      * places a vertical wall on this square
      * @param start = indicates if this is the top (true) or bottom (false)
      *                of a wall
      */
    public void placeWallVert(boolean start) {
        vert = new Wall(start);
    }



    /** placeWallHorz
      * places a horizontal wall on this square
      * @param start = indicates if this is the left (true) or right (false)
      *                of a wall
      */
    public void placeWallHorz(boolean start) {
        horz = new Wall(start);
    }



    /**
      * returns if the vertical wall starts or ends
      * throws a RuntimeException if there is no vertical
      * wall on this square
      * @return returns if wall starts or ends
      */
    public boolean getVertWallStatus() {
        if (vert != null)
            return vert.getIsStart();
        else
            throw new RuntimeException("There is no vertical wall on this square");
    }


    /**
      * returns if the horizontal wall starts or ends
      * throws a RuntimeException if there is no horizontal
      * wall on this square
      * @return returns if wall starts or ends
      */
    public boolean getHorzWallStatus() {
        if (horz != null)
            return horz.getIsStart();
        else
            throw new RuntimeException("There is no horizontal wall on this square");
    }
}
