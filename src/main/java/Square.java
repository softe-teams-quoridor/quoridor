/** Square.java - CIS 405 - teams 
  * Last Edit: February 17, 2015
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
  * placeWallVert(bool) --> places a vertical wall
  * placeWallHorz(bool) --> places a horizontal wall
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
 
}
