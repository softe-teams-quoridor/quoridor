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
 * void addPlayer(player)        --> adds a player to a given location
 * void removePlayer(int, int)   --> removes a player from the given location
 * boolean validLoc(int, int)    --> returns if coordinates are within bounds
 * void move(Player, Square)     --> moves a player from one square to another
 * void bootPlayer(Player)       --> boots given player from game
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
    public Player getPlayer (int x, int y){
        return validLoc(x,y) ? squares[x][y].getPlayer() : null;
    }

    //*************************************************************************

    /**
     * adds a player to the given location
     * @param p the player to add
     */
    public void addPlayer(Player player) {
        if (validLoc(player.getLoc().getX(), player.getLoc().getY()))
             squares[player.getLoc().getX()]
                    [player.getLoc().getY()].addplayer(player);
    }

    //*************************************************************************

    /**
     * removes a player from the given location
     * @param player the player to remove
     */
    private void removePlayer(Player player) {
        if (validLoc(player.getLoc().getX(), player.getLoc().getY()))
             squares[player.getLoc().getX()]
                    [player.getLoc().getY()].removePlayer();
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
        removePlayer(player);
        player.setLoc(newSqr);
        addPlayer(player);
    }

    //*************************************************************************

    /**
     * boots a player from the game
     * @param player player to be removed
     */
    public void bootPlayer(Player player) {
        this.removePlayer(player);
    }

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
        int colInd = 32836;
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

    //*************************************************************************

}
