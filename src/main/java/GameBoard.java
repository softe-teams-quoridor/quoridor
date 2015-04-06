/* GameBoard.java - CIS405 - teams
 * Last Edit: April 3, 2015
 * ____________________________________________________________________________
 *
 * GameBoard object to represent a 9x9 grid for the Quoridor game. This handles
 *   operations such as checking if a set of coordinates is valid, adding,
 *   removing, and moving a player, and initializing start locations.
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * PUBLIC:
 *
 * GameBoard()                   --> Default Constructor 
 * boolean isOccupied(int,int)   --> returns if player is at given location 
 * Square getSquare(int,int)     --> returns a square at a given location
 * Player getPlayer(int,int)     --> returns a player from the board array
 * Player getPlayer(int)         --> returns a player from the location array
 * Square getPlayerLoc(Player)   --> returns the current location of the player
 * void placeWall(Square,Square) --> places a wall on the board
 * void removePlayer(Player)     --> removes a player from the given location
 * void move(Player,Square)      --> moves a player from one square to another
 *
 * PRIVATE:
 *
 * void addPlayer(Player)        --> adds a player to a given location
 * boolean validLoc(int, int)    --> returns if coordinates are within bounds
 * void setupInitialPositions(Player []) --> sets initial player locations
 */

import java.util.Queue;

public class GameBoard {

    // Constants
    public static final int COLUMNS = 9; // X
    public static final int ROWS = 9;    // Y

    // Data Members
    private Square [][] squares;  // The cells of the GameBoard
    private Square [] playerLocs; // locations of the players on the board

    //*************************************************************************

    /** 
     * constructs the GameBoard by instantiating the array of squares and
     * initializing player locations
     * @param players queue of players to be given a start location
     */
    public GameBoard(Queue<Player> players) {
        assert (players.size() == 2 || players.size() == 4);
        // Instantiate squares array, setting X and Y to i and j respectively
        squares = new Square[COLUMNS][ROWS];
        for(int i = 0; i < COLUMNS; i++){
            for (int j = 0; j < ROWS; j++){
                squares[i][j] = new Square(i, j);
            }
        }
        // Instantiate player location array
        playerLocs = new Square[players.size()];
        // Initialize player positions
        setupInitialPositions(players);
    }

    //*************************************************************************

    /** Checks to see if a cell/square is occupied
     * @param x the column of the board
     * @param y the row of the gameboard
     * @return true if the cell is occupied, false otherwise
     * don't pass square coordinates that aren't on the board
     */
    public boolean isOccupied(int x, int y) {
        // Check for valid location
        assert (validLoc(x, y));
//         return (! squares[x][y].vacant());
        return (this.getPlayer(x, y) != null);
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

    /**
     * gets the square specified
     * @param a string representing a square, e.g. IV-D
     * @return the square object
     */
    public Square getSquare(String square) {
        String [] separated = square.trim().split("-");
        assert (separated.length == 2);
        int x = GameEngine.fromNumerals(separated[0]);
        assert (separated[1].length() == 1);
        int y = GameEngine.fromLetters(separated[1].charAt(0));
        return getSquare(x, y);
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

    /**
     * gets a player by their player number
     * this returns null if pno is greater than 3 or less than 0
     * also returns null if the player with that number has been booted
     * @param pno the number of the player you want
     * @return the player of that number or null
     */
    public Player getPlayer(int pno) {
        assert (pno >= 0 && pno < playerLocs.length);
        if (playerLocs[pno] == null) {
            return null; // this player has been booted
        }
//         return (pno >= 0 && pno < playerLocs.length) 
//             ? playerLocs[pno].getPlayer() 
//             : null; // If the player number is invalid
        return playerLocs[pno].getPlayer();
            //return squares[playerLocs[pno].getX()][playerLocs[pno].getY()].getPlayer();
    }

    //*************************************************************************

    /**
      * gets the player's location
      * @param player the player we want the location from
      * @return the player's location
      */
    public Square getPlayerLoc(Player player) {
        return playerLocs[player.getPlayerNo()];
    }

    //*************************************************************************

    /**
      * places a wall on the board
      * @param first the starting wall location
      * @param second the ending wall location
      */
    public void placeWall (Square first, Square second) {
        // Horz
        if(first.getY() == second.getY()) {
            first.placeWallBottom(true);
            second.placeWallBottom(false); 
        }
        // Vert
        else {
            first.placeWallRight(true);
            second.placeWallRight(false); 
        }
        squares[first.getX()][first.getY()] = first;
        squares[second.getX()][second.getY()] = second;
    }

    //*************************************************************************

    /**
     * removes a player from the given location
     * this is used for making a player move and for booting a player
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        Square loc = playerLocs[player.getPlayerNo()];
        assert (validLoc(loc.getX(), loc.getY()));
        playerLocs[player.getPlayerNo()] = null;
        squares[loc.getX()][loc.getY()].removePlayer();
    }

    //*************************************************************************

    /**
     * moves a player to the given location
     * @param player the player object
     * @param newSqr the destination square
     */
    public void move(Player player, Square newSqr) {
        assert (validLoc(newSqr.getX(), newSqr.getY()));
        removePlayer(player);
        addPlayer(player, newSqr.getX(), newSqr.getY());
    }

    //*************************************************************************

    /**
     * adds a player to the given location
     * @param p the player to add
     * @param two ints: coordinates on a game board
     */
    private void addPlayer(Player player, int x, int y) {
        assert (validLoc(x, y));
        squares[x][y].addPlayer(player);
        assert (player != null);
        playerLocs[player.getPlayerNo()] = squares[x][y];
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
     * initializes the players in their appropriate start locations
     * Player0 to (4,0); Player1 to (4,8); Player2 to (0,4); Player3 to (8,4)
     * @param players queue of players to initialize
     */
    private void setupInitialPositions(Queue<Player> players) {
        int colInd = 32836; // collin dalling
        int rowInd = 17536;

        for ( Player p : players ) {
            int x = colInd & 15;
            int y = rowInd & 15;

            addPlayer(p, x, y);
            playerLocs[p.getPlayerNo()] = getSquare(x, y);
            
            colInd = colInd >> 4;
            rowInd = rowInd >> 4;
        }
    }
}
