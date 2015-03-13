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
 * Square()            --> constructor
 * getPlayer()         --> returns player on square
 * addPlayer(Player)   --> assigns a Player obj to this square
 * removePlayer()      --> removes a Player obj from this square
 * vacant()            --> returns true if no player on this square
 * equals(Square)      --> returns if the squares are equal
 * equals(int,int)     -->
 * placeWallVert(bool) --> places a vertical wall
 * placeWallHorz(bool) --> places a horizontal wall
 * getVertWallStatus() --> returns if a vert wall starts or ends
 * getHorzWallStatus() --> returns if a horz wall starts or ends
 */

public class Square {
    // Data members
    private Player occupying; // Indicates which player occupies this square
    private int row;          // the Y coordinate of this square
    private int col;          // the X coordinate of this square
    private Wall vert;        // vertical wall
    private Wall horz;        // horizontal wall

    /** 
      * instantiates a square object
      */
    public Square(int x, int y) {
        occupying = null;
        vert = null;
        horz = null;
        col = x;
        row = y;
    }

    /** 
      * returns the player object occupying this square
      * @return = player object occupying this square
      */
    public Player getPlayer(){
        return occupying;
    }

    /**
      * returns the x coordinate of this square
      */
    public int getX(){
        return col;
    }

    /**
      * returns the y coordinate of this square
      */
    public int getY(){
        return row;
    }

    /** 
      * adds a player to the square
      * @param p player to add to the square
      */
    public void addplayer(Player p){
        occupying = p;
    }

    /** 
     * removes a player from the square
     */
    public void removePlayer() {
        occupying = null;
    }

    /**
      * returns false if this square is occupied by a player
      */
    public boolean vacant() {
        return (this.occupying == null);
    }

    /**
      * returns if the squares are equal
      * @param s square to compare
      * @return true if equal, false otherwise
      */
    public boolean equals ( Square s ) {
        return row == s.getY() && col == s.getX();
    }
    
    /**
      * returns if the square does a thing
      * @param x row
      * @param y column
      * @return true if equal, false otherwise
      */
    public boolean equals ( int x, int y ) {
        return row == y && col == x;
    }

    /** 
     * places a vertical wall on this square
     * @param start = indicates if this is the top (true) or bottom (false)
     *                of a wall
     * TODO: this should not be allowed to be called if there is already 
     * a wall on this square
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
