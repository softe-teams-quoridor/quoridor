/* Wall.java - CIS405 - teams
 * Last Edit: February 19, 2014
 * _______________________________________________________
 *
 * a wall that is placed on a gameboard square
 * 
 * Note: this indicates part of a wall; two squares should
 *    be given a wall object upon a valid wall placement
 *    one wall on a square should be set to true and the
 *    other wall on an adjacent square should be set to
 *    true in order to maintain the 1x2 size of a Quoridor
 *    wall
 *
 * ----------------------- METHODS -----------------------
 *
 * Wall(bool)   --> constructor
 * getIsStart() --> returns if this wall is a start or end piece
 */

public class Wall{

    // Data Members
    private boolean isStart; // Indicates if this wall is a beginning
    // or end piece    

    /** Constructor
     * instantiates a Wall object
     * @param start = indicates if this wall begins at the 
     *                top or left (true), or bottom or right (false)
     */                 
    public Wall(boolean start){
        isStart = start;
    }

    /**
     * returns whether this wall is the start or end point
     * @return returns boolean
     */
    public boolean getIsStart() {
        return isStart;
    }
}
