/* Wall.java - CIS405 - teams
 * Last Edit: March 21, 2015
 * ____________________________________________________________________________
 *
 * consider merging this object with Square.java
 *
 * a wall that is placed on a gameboard square. a square with a wall has either
 *  of three values: null (no wall present), START (this is the beginning-half
 *  of a wall piece, or END (this is the ending-half of a wall piece)
 *
 * for valid wall placement, a two squares should be given a new wall, one 
 *  being passed START, and one being passed END
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * Wall(bool)                 --> constructor using boolean
 * Wall(Orientation)          --> constructor using Orientation enum
 * boolean getIsStart()       --> returns if this wall is a start or end piece
 * Orientation getOrientation --> returns the orientation direction of the wall
 */

public class Wall{

    public enum Orientation {
        START, END 
    }

    private boolean isStart; // is this wall is a beginning or end piece?
    private Orientation direction; 

    //*************************************************************************

    /**
     * instantiates a Wall object
     * @param start indicates if the wall piece is a start or end piece 
     */                 
    public Wall(boolean start){
        isStart = start;
    }

    //*************************************************************************

    /**
     * instantiates a Wall object
     * @param direction indicates if the wall piece is a start or end piece 
     */                 
    public Wall ( Orientation direction ) {
        this.direction = direction;
    }

    //*************************************************************************

    /**
     * returns whether this wall is the start or end point
     * @return true if wall piece is the start-half, false if end-half
     */
    public boolean getIsStart() {
        return isStart;
    }

    //*************************************************************************

    /**
     * returns whether this wall is the start or end point
     * @return the Orientation direction enum
     */
    public Orientation getOrientation ( ) {
        return direction;
    }
}
