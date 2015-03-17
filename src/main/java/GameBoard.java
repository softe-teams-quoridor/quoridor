/* GameBoard.java - CIS405 - teams
 * Last Edit: March 16, 2015
 * ____________________________________________________________________________
 *
 * GameBoard object to represent a 9x9 grid for the Quoridor game. This handles
 * operations such as checking if a set of coordinates is valid, adding,
 * removing, and moving a player, and initializing start locations.
 *
 * ------------------------------- METHODS ------------------------------------
 *
 * GameBoard()                   --> Default Constructor 
 * boolean isOccupied(int, int)  --> returns if player is at given location 
 * Square getSquare(int, int)    --> returns a square at a given location
 * Player getPlayer(int, int)    --> returns a player at the given location
 * void addPlayer(Player)        --> adds a player to a given location
 * void removePlayer(Player)   --> removes a player from the given location
 * boolean validLoc(int, int)    --> returns if coordinates are within bounds
 * void move(Player, Square)     --> moves a player from one square to another
 * void setupInitialPosition(Player []) --> sets initial player locations
 */

public class GameBoard {

    // Constants
    private static final int COLUMNS = 9; // X
    private static final int ROWS = 9;    // Y

    // Data Members
    private Square [][] squares;  // The cells of the GameBoard
        
    //*************************************************************************

    /** 
     * constructs the GameBoard by instantiating the array of squares
     */
    public GameBoard() {
        squares = new Square[COLUMNS][ROWS];
        for(int i = 0; i < COLUMNS; i++){
            for (int j = 0; j < ROWS; j++){
                squares[i][j] = new Square(i, j);
            }
        }
    }

    //*************************************************************************

    /**
     * Checks to see if a cell/square is occupied
     * @param x the column of the board
     * @param y the row of the gameboard
     * @return true if the cell is occupied, false otherwise
     * don't pass square coordinates that aren't on the board
     */
    public boolean isOccupied(int x, int y) {
        // Check for valid location
//         if (validLoc(x,y)) {
        assert (validLoc(x,y));
        return (! squares[x][y].vacant());
    }

    //*************************************************************************

    /**
     * gets the square specified
     * @param x the column of the board
     * @param y the row of the gameboard
     * @return the square object
     */
    public Square getSquare(int x, int y) {
        return validLoc(x,y) ? squares[x][y] : null;
    }

    //*************************************************************************

    /**
     * gets a player at a given location
     * @param x the column of the board
     * @param y the row of the gameboard
     * @return the player at the location, null if unoccupied
     */
    public Player getPlayer(int x, int y) {
        return validLoc(x,y) ? squares[x][y].getPlayer() : null;
    }

    //*************************************************************************

    /**
     * adds a player to the given location
     * @param p the player to add
     */
    protected void addPlayer(Player player) {
        assert (validLoc(player.getX(), player.getY()));
        squares[player.getX()][player.getY()].addPlayer(player);
    }

    //*************************************************************************

    /**
     * removes a player from the given location
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        assert (validLoc(player.getX(), player.getY()));
        squares[player.getX()][player.getY()].removePlayer();
    }

    //*************************************************************************

    /**
     * Checks the location to see if it is actually on the board
     * @param x the column of the board
     * @param y the row of the gameboard
     * @return true if location is on board, false otherwise
     */
    private boolean validLoc(int x, int y) {
        return (x >= 0 && x < COLUMNS && y >= 0 && y < ROWS);
    }

    //*************************************************************************

    /**
     * moves a player to the given location
     * @param player the player object
     * @param newSqr the destination square
     */
    public void move(Player player, Square newSqr) {
        assert (validLoc(player.getX(), player.getY()));
        removePlayer(player);
        player.setLoc(newSqr);
        assert (player.getX() == newSqr.getX()); 
        assert (player.getY() == newSqr.getY());
//         squares[player.getX()][player.getY()].addPlayer(player);
//         this.addPlayer(player);
    }

    //*************************************************************************

    /**
     * boots a player from the game
     * @param player player to be removed
     * this is literally identical equivalent to removePlayer
     * why did i even think this was a good idea
   sorry
     * /
    public void bootPlayer(Player player) {
        this.removePlayer(player);
    }
    */

    //*************************************************************************

    /**
     * initializes the players in their appropriate start locations
     * Player0 to (4,0); Player1 to (4,8); Player2 to (0,i4); Player3 to (8,4)
     * @param players array of players to initialize
     */
    public void setupInitialPosition(Player [] players) {
        // Test to ensure that the Player array is 2 or 4
        assert (players.length == 2 || players.length == 4);
        
        int wallsEach = 20 / players.length;
        int colInd = 32836; // collin dalling
        int rowInd = 17536;

        for ( int i = 0; i < players.length; i++ ) {
            int x = colInd & 15;
            int y = rowInd & 15;
            players[i] = new Player(("player_" + i),
                                     this.getSquare(x,y), 
                                     wallsEach);

            this.addPlayer(players[i]);
            colInd = colInd >> 4;
            rowInd = rowInd >> 4;
        }

    }
}
