<<<<<<< HEAD
/** Wall.java - CIS405 - teams
  * Last Edit: February 16, 2014
  * a wall that is placed on a gameboard square
  * Note: this indicates part of a wall; two squares should
  *    be given a wall object upon a valid wall placement
  *    one wall on a square should be set to true and the
  *    other wall on an adjacent square should be set to
  *    true in order to maintain the 1x2 size of a Quoridor
  *    wall
  * ---------- METHODS ----------
  * Wall(isStart) ---> constructor
  */

public class Wall{

    private boolean isStart;
    
    /** Constructor
      * instantiates a Wall object
      * @param start = indicates if this wall begins at the 
      *                top or left (true), or bottom or right (false)
      */                 
    public Wall(boolean start){
	    isStart = start;
    }
}
