/** GameBoard.java - CIS405 - teams
 * _______________________________________________________
 * GameBoard object to represent a 9x9 grid for the 
 *  quoridor game
 *
 * X == COLUMN
 * Y == ROW
 *
 * ----------------------- METHODS -----------------------
 *
 * GameBoard()                -- Default Constructor (initializes the squares of the board)
 * isOccupied(int x, int y)   -- returns true if player is at location, false otherwise 
 * getSquare(int x, int y)    -- returns the squares field
 * addPlayer(int x, int y)    -- adds a player to a given location
 * getPlayer(int x, int y)    -- returns a player at the given cell, return null if unoccupied
 * removePlayer(int x, int y) -- removes a player at the given cell
 * validLoc(int x, int y)     -- returns true if coordinates are in the board 
 * getBoard() IS THIS NEEDED? -- returns the Square array 
 * 
 *
 * Considerations
 *  
 *    Saturday (2/14) 
 *     if this object is only going to represent an array,
 *     then why not just have a Square array called 
 *     GameBoard within the Game class? - Walling
 */

public class GameBoard {

    // Constants
    private static final int  COLUMNS = 9;
    private static final int ROWS = 9;



    // Members
    private Square [][] squares; // The cells of the GameBoard
    private final int players;   // the number of players in the game
    private Square [] playerLocs; // locations of the players


    /** Constructor -- DEPRECATE THIS -- 
     * this constructor is only to be kept until the rest of the codebase 
     * is adjusted to the new one!!!
     * The constructor builds the cells of the board
     */
    public GameBoard() {
        players = 0;
        playerLocs = null;
        squares = new Square[COLUMNS][ROWS];
        for(int i = 0; i < COLUMNS; i++){
            for (int j = 0; j < ROWS; j++){
                squares[i][j] = new Square(i, j);
                // Should also set the bottom and right cells to have walls
            }
        }
    }


    /** Constructor
     * The constructor builds the cells of the board
     * @param: the number of players in the game
     */
    public GameBoard(int players) {
        this.players = players;
        playerLocs = new Square[players];
        squares = new Square[COLUMNS][ROWS];
        for(int i = 0; i < COLUMNS; i++){
            for (int j = 0; j < ROWS; j++){
                squares[i][j] = new Square(i, j);
                // Should also set the bottom and right cells to have walls
            }
        }
    }


    /** Constructor FOR TESTING ONLY, DON'T USE THIS!
     * does all the stuff of the other constructor, but also puts a player
     * on the board. 
     */
    public GameBoard(int x, int y) {
        this();
        Player p = new Player("tylur", getSquare(x, y), 0);
        //         getSquare(x, y).addPlayer(p);
        addPlayer(p, x, y);
    }

    //******************************************************************************

    /**
     * Checks to see if a cell/square is occupied
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return true if the cell is occupied, false otherwise
     */
    public boolean isOccupied(int x, int y) {

        // Check for valid location
        if(validLoc(x,y)) {

            // Check to see if a player is there
            if(squares[x][y].getPlayer() == null)
                return false;
            // A player is there 
            return true;
        }
        else
            return false;
        //throw new RuntimeException("Invalid Location");
    }

    //******************************************************************************

    /**
     * gets the square specified
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return the square object
     */
    public Square getSquare(int x, int y){
        return squares[x][y];
    }

    /**
     * adds a player to the given location
     * @param player = the player object
     * @param x = the column of the board
     * @param y = the row of the gameboard
     */
    public void addPlayer(Player p, int x, int y){
        squares[x][y].addplayer(p);
    }

    //******************************************************************************

    /**
     * removes a player from the given location
     * @param x = the column of the board
     * @param y = the row of the board
     */
    private void removePlayer(int x, int y) {
        squares[x][y].removePlayer();
    }

    //******************************************************************************

    /**
     * gets a player at a given location
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return the player at the location, null if unoccupied
     */
    public Player getPlayer (int x, int y){
        assert (validLoc(x,y));
        return squares[x][y].getPlayer();
    }

    //*******************************************************************************

    /**
     * Checks the location to see if it is actually on the board
     * @param player = the player object
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return true if location is on board, false otherwise
     */
    public boolean validLoc(int x, int y) {
        if(x >= 0 && x < COLUMNS)
            if(y >= 0 && y < ROWS)
                return true;
        return false;
    }

    /**
     * moves a player to the given location
     * @param player = the player object
     * @param Square = the destination square
     */
    public void move(Player player, Square newSqr) {
    
        removePlayer(player.getLoc().getX(),
                     player.getLoc().getY());
        addPlayer(player, newSqr.getX(),newSqr.getY());
        player.setLoc(newSqr);

    }

    public void bootPlayer(Player player) {
        this.removePlayer(player.getLoc().getX(), player.getLoc().getY());
    }


    public void setupInitialPosition(Player [] players) {
        assert (players.length == 2 || players.length == 4);
        int wallsEach = 20 / players.length;

        // Initialization of a two-player game
        Deb.ug.println("initializing player_0");
        players[0] = new Player("player_0", this.getSquare(4,0), wallsEach);
        this.addPlayer(players[0], 4, 0);
        Deb.ug.println("initializing player_1");
        players[1] = new Player("player_1", this.getSquare(4,8), wallsEach);
        this.addPlayer(players[1],4,8);
        if (players.length == 4) {
            // If this is a four player game...
            Deb.ug.println("initializing player_2");
            players[2] = new Player("player_2", this.getSquare(0,4),wallsEach);
            this.addPlayer(players[2],0,4);
            Deb.ug.println("initializing player_3");
            players[3] = new Player("player_3", this.getSquare(8,4),wallsEach);
            this.addPlayer(players[3],8,4);
        }

    }

}
