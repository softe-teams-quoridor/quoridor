/* Square.java - CIS 405 - teams 
 * Last Edit: April 10, 2015
 * ____________________________________________________________________________
 *
 * Represents a single cell of a GameBoard grid. It contains a column and row
 *  (or X and Y coordinates), a Player object that is occupying (or assigned to) 
 *  the Square, and the presence of a right and a bottom Wall object.
 *
 *  Wall Placement Visual                   Note: "column and x" and 
 *       _________                                "row and y" are
 *      |        W|                                synonymous, as is
 *      |        A|                               "player and occupant"
 *      |        L|
 *      |  WALL  L|
 *       _________
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * Square(int,int)            --> constructor; assigns column and row
 * int getX()                 --> returns the x coordinate (returns col)
 * int getY()                 --> returns the y coordinate (returns row)
 * Player getPlayer()         --> returns a Player object or null
 * void addPlayer(Player)     --> assigns a Player object
 * void removePlayer()        --> removes a Player object
 * boolean vacant()           --> returns if a Player is occupying this Square
 * void placeWallRight(bool)  --> assigns the right Wall object
 * void placeWallBottom(bool) --> assigns the bottom Wall object
 * boolean hasWallRight()     --> returns if the right Wall is present or null
 * boolean hasWallBottom()    --> returns if the bottom Wall is present or null
 * Wall getWallRight()        --> returns the right Wall object
 * Wall getWallBottom()       --> returns the bottom Wall object
 * void removeWallRight()     --> removes the right Wall object
 * boolean equals(Square)     --> returns the equality of this and another Square
 * String toString()          --> returns the string rep. of this Square
 */

public class Square {

    private int col;          // X coordinate of this Square
    private int row;          // Y coordinate of this Square
    private Player occupant;  // Player occupying this Square
    private Wall rightWall;   // right Wall
    private Wall bottomWall;  // bottom Wall

    /** 
      * Instantiates a Square object by assigning the column and row values to
      * the given parameters x and y, respectively. All other fields are
      * assigned to null; use the appropriate mutators to assign these fields.
      *     @param x column "coordinate"
      *     @param y row "coordinate"
      *     @see GameBoard
     */
    public Square(int x, int y) {
        col = x;
        row = y;
        occupant   = null;
        rightWall  = null;
        bottomWall = null;
    }

    //*************************************************************************

    /**
      * Returns the column (x coordinate) of this Square.
      *     @return the column value
      */
    public int getX() {
        return col;
    }

    /**
      * Returns the row (y coordinate) of this Square.
      *     @return the row value
      */
    public int getY() {
        return row;
    }

    //*************************************************************************

    /**
      * Returns the Player object occupying this Square.
      *     @return the Player object occupying this Square
      *     @see Player
      */
    public Player getPlayer() {
        return occupant;
    }

    /**
      * Assigns a Player object to the Square.
      *     @param player Player object to assign to the Square
      *     @see Player
      */
    public void addPlayer(Player player) {
        occupant = player;
    }

    /** 
      * Removes a Player object from the Square.
      *     @see Player
      */
    public void removePlayer() {
        occupant = null;
    }

    /** 
      * Returns if this Square is occupied by a Player object.
      *     @return if the Square is occupied
      */
    public boolean isOccupied() {
        return (occupant != null);
    }

    //*************************************************************************

    /** 
      * Assigns a Wall object to the right of this Square. This Wall object is
      * oriented vertically and parameter isStart denotes whether the Wall
      * object is considered to be the "starting/top" or "ending/bottom"
      * half of the two-piece Wall object.
      *      @param start is this the top (true) or bottom (false) half of a Wall?
      *      @see Wall
      */
    public void placeWallRight(boolean isStart) {
        if (!hasWallRight())
            rightWall = new Wall(isStart);
    }

    /** 
      * Assigns a Wall object to the bottom of this Square. This Wall object is
      * oriented horizontally and parameter isStart denotes whether the Wall
      * object is considered to be the "starting/left" or "ending/right"
      * half of the two-piece Wall object.
      *     @param start is this the left (true) or right (false) half of a Wall?
      *     @see Wall
      */
    public void placeWallBottom(boolean isStart) {
        if (!hasWallBottom())
            bottomWall = new Wall(isStart);
    }

    //*************************************************************************

    /**
      * Returns if there is a right Wall placed on the Square.
      *     @return if there is a right Wall
      *     @see Wall
      */
    public boolean hasWallRight() {
        return (rightWall != null);
    }

    /**
      * Returns if there is a bottom Wall placed on the Square.
      *     @return if there is a bottom Wall
      */
    public boolean hasWallBottom() {
        return (bottomWall != null);
    }

    //*************************************************************************

    /**
      * Returns the right Wall object or null if there is no Wall assigned.
      *     @see Wall
      */
    public Wall getWallRight() {
        return rightWall;
    }

    /**
      * Returns the bottom Wall object or null if there is no Wall assigned.
      *     @see Wall
      */
    public Wall getWallBottom() {
        return bottomWall;
    }

    //*************************************************************************

    /**
      * Removes the right Wall object.
      *     @see Wall
      */
    public void removeWallRight() {
        rightWall = null;
    }

    /**
      * Removes the bottom Wall object.
      *     @see Wall
      */
    public void removeWallBottom() {
        bottomWall = null;
    }

    //*************************************************************************

    /**
      * Returns if the this Square is equal to the parameter Square
      *     @return if the Square objects are equal
      */
    // Maybe should also check walls? probably?
    // I'll add that later
    public boolean equals(Square square) {
        if(this.getX() == square.getX() && this.getY() == square.getY())
            return true;
        return false;
    }

    //*************************************************************************
    
    /**
      * Returns a String representation of this Square. The column is returned
      * as a numeral and row is returned as a letter.
      *     @return a String representing the column and row of this Square
      *     @see GameEngine
      */
    public String toString() {
        return GameEngine.toNumerals(col) + "-" + GameEngine.toLetters(row);
    }
}
