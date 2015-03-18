/* Square.java - CIS 405 - teams 
 * Last Edit: March 17, 2015
 * ____________________________________________________________________________
 *
 * Square represents a single cell of a GameBoard grid. It contains its
 * coordinates (X and Y) as well as the presence of walls on the bottom
 * or right sides.
 *
 *    _________
 *   |        W|
 *   |        A|
 *   |        L|
 *   |  WALL  L|
 *    _________
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * Square()                   --> constructor
 * int getX()                 --> returns the x coordinate
 * int getY()                 --> returns the y coordinate
 * Player getPlayer()         --> returns player on the square
 * void addPlayer(Player)     --> assigns a Player obj to this square
 * void removePlayer()        --> removes a Player obj from this square
 * boolean vacant()           --> returns true if no player on this square
 * boolean isOccupied()       --> returns if the square is occupied by a player
 * void placeWallRight(bool)  --> places a the right-side wall
 * void placeWallBottom(bool) --> places the bottom-side wall
 * boolean hasWallRight()     --> returns if a right wall is present
 * boolean hasWallBottom()    --> returns if a bottom wall is present
 */

public class Square {
    // Data members
    private Player occupant;  // player on this square
    private int row;          // the Y coordinate of this square
    private int col;          // the X coordinate of this square
    private Wall rightWall;   // right wall
    private Wall bottomWall;  // bottom wall

    /** 
      * instantiates a square object
      * teams can we make this constructor private?? only the gameboard should make squares
      *
      * ^ I should slap whoever said this :) - Walling
      */
    public Square(int x, int y) {
        assert (true);
        col = x;
        row = y;
        occupant   = null;
        rightWall  = null;
        bottomWall = null;
    }

    //*************************************************************************    

    /**
      * returns the x coordinate of this square
      * @return the column number
      */
    public int getX() {
        return col;
    }

    //*************************************************************************    

    /**
      * returns the y coordinate of this square
      * @return the row number
      */
    public int getY() {
        return row;
    }

    //*************************************************************************    

    /**
      * returns the player object occupying this square
      * @return player object occupying this square
      */
    public Player getPlayer() {
        return occupant;
    }

    //*************************************************************************    
 
     /* adds a player to the square
      * @param p player to add to the square
      */
    public void addPlayer(Player p) {
        occupant = p;
    }

    //*************************************************************************    

    /** removes a player from the square
      */
    public void removePlayer() {
        occupant = null;
    }

    //*************************************************************************    

    /** @return false if the Square is occupied
      */
    public boolean vacant() {
        return (occupant == null);
    }

    //*************************************************************************    

    /**
      * returns if this square is claimed to be occupied
      * @return true if occupied; false otherwise
      */
    public boolean isOccupied() {
        return (! this.vacant());
    }

    //*************************************************************************    

    /** 
     * places a wall on the bottom of this square
     * @param start is this is the top (true) or bottom (false) of a wall?
     */
    public void placeWallRight(boolean isStart) {
        assert (rightWall == null); 
//         if ( rightWall == null )
        rightWall = new Wall(isStart);
    }

    //*************************************************************************    

    /**
      * places a wall on the right on this square
      * @param start is this is the left (true) or right (false) of a wall?
      */
    public void placeWallBottom(boolean isStart) {
        assert (bottomWall == null); 
//         if ( bottomWall == null )
        bottomWall = new Wall(isStart);
    }

    //*************************************************************************    

    /**
      * returns if the right wall is a start piece or end piece
      * @return returns if wall starts or ends
      */
    public boolean hasWallRight() {
        return (rightWall != null);
    }

    //*************************************************************************    

    /**
      * returns if the bottom wall is a start piece or end piece
      * @return returns if wall starts or ends
      */
    public boolean hasWallBottom() {
        return (bottomWall != null);
    }
}
