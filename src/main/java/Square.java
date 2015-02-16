/** Square.java - CIS 405 - teams 
  * Last Edit: February 16, 2015
  * a cell on the gameboard grid
  * ---------- METHODS ----------
  * Square()             --> constructor
  * placeWallVert(start) --> places a vertical wall
  * placeWallHorz(start) --> places a horizontal wall
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



    /** placeWallVert
      * places a vertical wall on this square
      * @param start = indicates if this is the top (true) or bottom (false)
      *                of a wall
      */
    public placeWallVert(boolean start) {
        vert = new Wall(start);
    }


    
    /** placeWallHorz
      * places a horizontal wall on this square
      * @param start = indicates if this is the left (true) or right (false)
      *                of a wall
      */
    public placeWallHorz(boolean start) {
        horz = new Wall(start);
    }
}
