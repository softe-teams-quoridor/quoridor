/* GameBoard.java - CIS405 - teams
 * Last Edit: March 13, 2015
 * _________________________________________________________________________________
 *
 * GameBoard object to represent a 9x9 grid for the Quoridor game
 *
 * X == COLUMN
 * Y == ROW
 *
 * ------------------------------------ METHODS ------------------------------------
 *
 * [DEPRECATED] GameBoard()        --> Default Constructor 
 * GameBoard(int)                  --> Constructor: initalizes board and player locs
 * isOccupied(int, int)            --> returns if player is at given location 
 * getSquare(int, int)             --> returns a square at a given location
 * getPlayer(int, int)             --> returns a player at the given location
 * addPlayer(player, int, int)     --> adds a player to a given location
 * removePlayer(int, int)          --> removes a player from the given location
 * validLoc(int, int)              --> returns if coordinates are within bounds
 * move(Player, Square)            --> moves a player from one square to another
 * bootPlayer(Player)              --> boots given player from game
 * setupInitialPosition(Player []) --> sets initial player locations
 */

public class GameBoard {

    // Constants
    private static final int COLUMNS = 9;
    private static final int ROWS = 9;

    // Data Members
    private Square [][] squares;  // The cells of the GameBoard
    private Square [] playerLocs; // locations of the players
    private final int players;    // the number of players in the game
        
    //******************************************************************************

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

    //******************************************************************************

    /**
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

    //******************************************************************************

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
    }

    //******************************************************************************

    /**
     * gets the square specified
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return the square object
     */
    public Square getSquare(int x, int y) {
        return validLoc(x,y) ? squares[x][y] : null;
    }
    
    //******************************************************************************
    
    /**
     * gets a player at a given location
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return the player at the location, null if unoccupied
     */
    public Player getPlayer (int x, int y){
        return validLoc(x,y) ? squares[x][y].getPlayer() : null;
    }

    //******************************************************************************

    /**
     * adds a player to the given location
     * @param player = the player object
     * @param x = the column of the board
     * @param y = the row of the gameboard
     */
    public void addPlayer(Player p, int x, int y) {
        if (validLoc(x,y)) { squares[x][y].addplayer(p); }
    }

    //******************************************************************************

    /**
     * removes a player from the given location
     * @param x = the column of the board
     * @param y = the row of the board
     */
    private void removePlayer(int x, int y) {
        if (validLoc(x,y)) squares[x][y].removePlayer();
    }

    //*******************************************************************************

    /**
     * Checks the location to see if it is actually on the board
     * @param player = the player object
     * @param x = the column of the board
     * @param y = the row of the gameboard
     * @return true if location is on board, false otherwise
     */
    private boolean validLoc(int x, int y) {
        return (x >= 0 && x < COLUMNS && y >= 0 && y < ROWS);
    }

    //*******************************************************************************

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

    //*******************************************************************************

    /**
     * boots a player from the game
     * @param player player to be removed
     */
    public void bootPlayer(Player player) {
        this.removePlayer(player.getLoc().getX(), player.getLoc().getY());
    }

    //*******************************************************************************

    /**
     * initializes the players in their appropriate start locations
     * @param players array of players to initialize
     */
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
