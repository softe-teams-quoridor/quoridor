/* Wall.java - CIS405 - teams
 * Last Edit: April 10, 2015
 * ____________________________________________________________________________
 *
 * Serves as the Wall obstacle that is placed on a Square. Walls block a Player
 *  object from advancing on the GameBoard.
 *  
 *  A Square with a Wall has one of three values: 
 *      null ---- (no wall present)
 *      true ---- (this is the beginning-half)
 *      false --- (this is the ending-half)
 *
 * For a valid wall placement, two Square objects should be given a new Wall
 *  object, one being passed true to denote it as the starting half, and one
 *  being passed false to denote it as the ending half.
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * Wall(bool)              --> constructor; boolean determines if start or end
 * boolean isStart()       --> returns if this wall is the starting half
 * boolean isEnd()         --> returns if this wall is the ending half
 */

public class Wall{

    private boolean start; // is this Wall is a beginning or ending half?

    //*************************************************************************

    /**
      * Instantiates a Wall object. Parameter start denotes if this Wall
      * half is the starting piece (the top part of a vertical or right Wall,
      * or the left part of a horizontal or bottom Wall) or the ending piece
      * (the bottom part of a vertical or right Wall, or the right part of a
      * horizontal or bottom Wall).
      *     @param start indicates if the wall piece is a start or end piece 
      */                 
    public Wall(boolean start){
        this.start = start;
    }

    //*************************************************************************

    /**
      * Returns if this Wall is the start half.
      *     @return if this Wall object the starting half
      */
    public boolean isStart() {
        return start;
    }

    /**
      * Returns if this Wall is the end half.
      *     @return if this Wall object the ending half
      */
    public boolean isEnd() {
        return !start;
    }

}
