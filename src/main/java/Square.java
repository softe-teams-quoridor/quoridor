/* Square.java - CIS 405 - teams 
 * Last Edit: March 13, 2015
 * _______________________________________________________
 *
 * represents a single cell on a GameBoard grid
 *
 *   ROW == Y
 *   COL == X
 *
 * --------------------- METHODS -------------------------
 *
 * Square()                    --> constructor
 * Player getPlayer()          --> returns player on square
 * void addPlayer(Player)      --> assigns a Player obj to this square
 * void removePlayer()         --> removes a Player obj from this square
 * boolean vacant()            --> returns true if no player on this square
 * void placeWallVert(bool)    --> places a vertical wall
 * void placeWallHorz(bool)    --> places a horizontal wall
 * boolean getVertWallStatus() --> returns if a vert wall starts or ends
 * boolean getHorzWallStatus() --> returns if a horz wall starts or ends
 */

public class Square {
    // Data members
    private Player occupant; // Indicates which player occupies this square
    private int row;          // the Y coordinate of this square
    private int col;          // the X coordinate of this square
    private Wall vert;        // vertical wall
    private Wall horz;        // horizontal wall

    /** 
      * instantiates a square object
      * teams can we make this constructor private?? only the gameboard should
      make squares
      */
    public Square(int x, int y) {
        occupant = null;
        vert = null;
        horz = null;
        col = x;
        row = y;
    }

    /** 
      * returns the player object occupying this square
      * @return = player object occupying this square
      */
    public Player getPlayer() {
        return occupant;
    }

    /**
      * returns the x coordinate of this square
      * @return the column number
      */
    public int getX() {
        return col;
    }

    /**
      * returns the y coordinate of this square
      * @return the row number
      */
    public int getY() {
        return row;
    }

    /** 
      * adds a player to the square
      * @param p player to add to the square
      */
    public void addPlayer(Player p) {
        occupant = p;
    }

    /** 
     * removes a player from the square
     */
    public void removePlayer() {
        occupant = null;
    }

    /**
      * @return false if the Sqaure is occupied
      */
    public boolean vacant() {
        return (this.occupant == null);
    }

    /** 
     * places a vertical wall on this square
     * @param start = indicates if this is the top (true) or bottom (false)
     *                of a wall
     * TODO: this should not be allowed to be called if there is already 
     * a wall on this square
     * Maybe should be checked first? not validate here but in Game.java using GameEngine.java? --Eric
     */
    public void placeWallVert(boolean start) {
        vert = new Wall(start);
    }

    /** placeWallHorz
     * places a horizontal wall on this square
     * @param start = indicates if this is the left (true) or right (false)
     *                of a wall
     * TODO: this should not be allowed to be called if there is already 
     * a wall on this square
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
