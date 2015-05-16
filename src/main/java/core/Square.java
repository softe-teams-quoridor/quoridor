/* Square.java */

/**
 * Represents a single cell of a GameBoard grid. It contains a column and row
 * (or X and Y coordinate pair), a Player object that is occupying the Square,
 * and the presence of a right and a bottom wall.
 *
 * Note: "column and x" and "row and y" are synonymous, as is "player and 
 *        occupant"
 *
 * Wall Placement Visual
 *       _________
 *      |        W|
 *      |        A|
 *      |        L|
 *      |  WALL  L|
 *       _________
 *
 * @author      Eric Cavanagh
 * @author      Dan Soucy
 * @author      Collin Walling
 */

/* --------------------------------- METHODS ----------------------------------
 *
 * Square(int,int)            --> constructor; assigns column and row
 *
 * int getX()                 --> returns the x coordinate (returns col)
 *
 * int getY()                 --> returns the y coordinate (returns row)
 *
 * Player getPlayer()         --> returns a Player object or null
 *
 * void addPlayer(Player)     --> assigns a Player object
 *
 * void removePlayer()        --> removes a Player object
 *
 * boolean isOccupied()       --> returns if a Player is occupying this Square
 *
 * void placeWallRight(bool)  --> assigns the right wall
 *
 * void placeWallBottom(bool) --> assigns the bottom wall 
 *
 * Wall getWallRight()        --> returns the status of the right wall
 *
 * Wall getWallBottom()       --> returns the status of the bottom wall
 *
 * void removeWallRight()     --> removes the right wall
 *
 * void removeWallBotttom()   --> removes the bottom wall
 *
 * String toString()          --> returns the string rep. of this Square
 */

public class Square {

    private int col;             // X coordinate of this Square
    private int row;             // Y coordinate of this Square
    private Player occupant;     // Player occupying this Square
    private Boolean rightWall;   // right Wall
    private Boolean bottomWall;  // bottom Wall

    /** 
     * Instantiates a Square object by assigning the column and row values to
     * the given parameters x and y, respectively. All other fields are
     * assigned to null; use the appropriate mutators to assign these fields.
     *
     * @param x    column "coordinate"
     * @param y    row "coordinate"
     *
     * @see GameBoard
     */

    public Square(int x, int y) {
        col = x;
        row = y;
        occupant   = null;
        rightWall  = null;
        bottomWall = null;
    }

    /**
     * Returns the column (x coordinate) of this Square.
     *
     * @return the column value
     */

    public int getX() {
        return col;
    }

    /**
     * Returns the row (y coordinate) of this Square.
     *
     * @return the row value
     */

    public int getY() {
        return row;
    }

    /**
     * Returns the Player object occupying this Square.
     *
     * @return the Player object occupying this Square
     *
     * @see Player
     */

    public Player getPlayer() {
        return occupant;
    }

    /**
     * Assigns a Player object to the Square.
     *
     * @param player    Player object to assign to the Square
     *
     * @see Player
     */

    public void addPlayer(Player player) {
        occupant = player;
    }

    /** 
     * Removes a Player object from the Square.
     *
     * @see Player
     */

    public void removePlayer() {
        occupant = null;
    }

    /** 
     * Returns if this Square is occupied by a Player object.
     *
     * @return if the Square is occupied
     */

    public boolean isOccupied() {
        return (occupant != null);
    }

    /** 
     * Assigns a wall to the right of this Square. This wall is oriented
     * vertically and parameter isStart denotes whether the wall is considered
     * to be the "starting/top" or "ending/bottom" half of the two-piece wall.
     *
     * @param start    is this the top (true) or bottom (false) half of a wall?
     */

    public void placeWallRight(boolean isStart) {
        if (getWallRight() == null)
            rightWall = new Boolean(isStart);
    }


    /** 
     * Assigns a wall to the bottom of this Square. This wall is oriented
     * horizontally and parameter isStart denotes whether the wall is 
     * considered to be the "starting/left" or "ending/right" half of the
     * two-piece wall.
     *
     * @param start    is this the left (true) or right (false) half of a wall?
     */

    public void placeWallBottom(boolean isStart) {
        if (getWallBottom() == null)
            bottomWall = new Boolean(isStart);
    }

    public void placeWall(String orientation, boolean isStart) {
        assert(!orientation.equals("right") || !orientation.equals("bottom"));
        if (orientation.equals("right") && getWallStatus("right") == null)
            rightWall = new Boolean(isStart);
        if (orientation.equals("bottom") && getWallStatus("bottom") == null)
            bottomWall = new Boolean(isStart);
    }

    /**
     * Returns the status of a right wall.
     */

    public Boolean getWallRight() {
        return rightWall;
    }

    /**
     * Returns the status of a bottom wall.
     */

    public Boolean getWallBottom() {
        return bottomWall;
    }

    public Boolean getWallStatus(String orientation) {
        assert(!orientation.equals("right") || !orientation.equals("bottom"));
        if (orientation.equals("right"))
            return rightWall;
        if (orientation.equals("bottom"))
            return bottomWall;
        return null; // the assertion shouldn't allow the code to reach this
    }

    /**
     * Removes the right wall.
     */

    public void removeWallRight() {
        rightWall = null;
    }

    /**
     * Removes the bottom wall.
     */

    public void removeWallBottom() {
        bottomWall = null;
    }

    /**
     * Returns a String representation of this Square. The column is returned
     * as a numeral and row is returned as a letter.
     *
     * @return a String representing the column and row of this Square
     *
     * @see GameEngine
     */

    public String toString() {
        return GameEngine.toNumerals(col) + "-" + GameEngine.toLetters(row);
    }

}
