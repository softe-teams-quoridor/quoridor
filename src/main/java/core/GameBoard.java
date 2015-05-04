/* GameBoard.java - CIS405 - teams
 * Last Edit: April 19, 2015
 * ____________________________________________________________________________
 *
 * GameBoard object to represent a 9x9 grid for the Quoridor game. This handles
 *   operations such as checking if a set of coordinates is valid, adding,
 *   removing, and moving a player, and initializing start locations.
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * GameBoard()                   --> constructor 
 * boolean isOccupied(int,int)   --> returns if Player is at given x and y location 
 * Square getSquare(int,int)     --> returns a Square at the given x and y location
 * Square getSquare(String)      --> returns a Square at the given numeral-character string
 * Player getPlayer(int,int)     --> returns a Player from the board at the given
 *                                      x and y coordinates
 * Player getPlayer(int)         --> returns a Player from the board based on
 *                                      the player's unique number
 * Square getPlayerLoc(Player)   --> returns the current location of the Player
 * Square getPlayerLoc(int)      --> returns the current location of the Player
 * void placeWall(Square,Square) --> places a Wall on the board
 * void removeWall(Square[])     --> removes a Wall from the board
 * void removePlayer(Player)     --> removes a Player from the board
 * void move(Player,Square)      --> moves a Player from one Square to another
 * int getCurrPlayerTurn()       --> returns the turn of whichever player's turn it is 
 * Queue<Player> getNextTurn(Queue<Player>) --> shuffles Player Queue
 *
 * Do not trust:
 * Square[] findShortestPath(Player)--> returns an array of squares that are the shortest path
 */

import java.util.Queue;

public class GameBoard {

    // Constants
    public static final int COLUMNS = 9; // X
    public static final int ROWS = 9;    // Y

    // Data Members
    protected Square [][] squares;  // The cells of the GameBoard
    protected Square [] playerLocs; // locations of the players on the board
    private int playerTurn;       // whichever player's turn it is
    private int startNumPlayers; // how many players when start
    
    //*************************************************************************

    /** 
      * Constructs the GameBoard by instantiating the array of squares and
      * initializing player locations.
      *     @param players queue of players to be given a start location
      */
    public GameBoard(Queue<Player> players) {
        assert (players.size() == 2 || players.size() == 4);
        // Instantiate squares array, setting X and Y to i and j respectively
        
        startNumPlayers = players.size();
        
        squares = new Square[COLUMNS][ROWS];
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++){
                squares[i][j] = new Square(i, j);
            }
        }
        // Initialize player turn
        playerTurn = 0; // player 0 always goes first
        // Instantiate player location array
        playerLocs = new Square[players.size()];
        // Initialize player positions
        setupInitialPositions(players);
    }

    //*************************************************************************
    
    public int getStartNumPlayers(){
	return startNumPlayers;
    }
    //*************************************************************************

    public int numPlayersRemaining() {
        int c = 0;
        for(int i = 0; i < playerLocs.length; i++) {
            if(playerLocs[i] != null) {
                c++;
            }
        }
        return c;
    }

    //*************************************************************************

    /** 
      * Returns if a Square on the board is occupied.
      *     @param x the column of the board
      *     @param y the row of the gameboard
      *     @return true if the cell is occupied, false otherwise
      *     @throws assertion if given coordinates are invalid
      */
    public boolean isOccupied(int x, int y) {
        assert (validLoc(x, y));
        return (this.getPlayer(x, y) != null);
    }

    //*************************************************************************

    /**
      * Returns the Square on the GameBoard at the given x and y coordinates.
      *     @param x the column
      *     @param y the row
      *     @return the Square object
      *     @see Square
      */
    public Square getSquare(int x, int y) {
        return validLoc(x,y) ? squares[x][y] : null;
    }

    /**
      * Returns the Square on the GameBoard at the given column (numeral) and
      * row (character).
      *     @param a string representing a Square, e.g. IV-D
      *     @return the Square object
      *     @throws assertion if the format of the string is inappropriate
      *     @see Square
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
      * Returns a Player at the given x and y coordinates.
      *     @param x column
      *     @param y row
      *     @return the Player at the location, null if unoccupied
      *     @see Player
      */
    public Player getPlayer(int x, int y) {
        return validLoc(x,y) ? squares[x][y].getPlayer() : null;
    }

    /**
      * Returns a Player based on their unique player number.
      *     @param pno the number of the Player you want
      *     @return the player of that number or null
      *     @throws assertion if the Player number is 0 or 3
      *     @throws assertion if the Player has been kicked from the game
      *     @see Player
      */
    public Player getPlayer(int pno) {
        assert (pno >= 0); 
        assert (pno < playerLocs.length);
        if (playerLocs[pno] == null) {
            return null; // this player has been booted
        }
        return playerLocs[pno].getPlayer();
    }

    //*************************************************************************

    /**
      * Returns the given Player's location on the GameBoard.
      *     @param player the Player we want the location from
      *     @return the player's location
      */
    public Square getPlayerLoc(Player player) {
        return playerLocs[player.getPlayerNo()];
    }

    public Square getPlayerLoc(int pno) {
        return playerLocs[pno];
    }

    //*************************************************************************

    /**
      * Places a Wall on the GameBoard.
      *     @param first the starting Wall location
      *     @param second the ending Wall location
      *     @see Wall
      */
    public void placeWall (Square[] wallSquares) {
        // Horiz
        if(wallSquares[0].getY() == wallSquares[1].getY()) {
            wallSquares[0].placeWallBottom(Wall.HORIZ_LEFT);
            wallSquares[1].placeWallBottom(Wall.HORIZ_RIGHT); 
        }
        // Vert
        else {
            wallSquares[0].placeWallRight(Wall.VERT_TOP);
            wallSquares[1].placeWallRight(Wall.VERT_BOT); 
        }
        squares[wallSquares[0].getX()][wallSquares[0].getY()] = wallSquares[0];
        squares[wallSquares[1].getX()][wallSquares[1].getY()] = wallSquares[1];
    }

    /**
      * Removes a Wall from the GameBoard.
      *     @param wallSquares locations of the Wall to be removed
      *     @see Wall
      */
    public void removeWall(Square[] wallSquares) {
        if(wallSquares[0].getY() == wallSquares[1].getY()) {
            wallSquares[0].removeWallBottom();
            wallSquares[1].removeWallBottom();
        }
        else {
            wallSquares[0].removeWallRight();
            wallSquares[1].removeWallRight();
        }

        squares[wallSquares[0].getX()][wallSquares[0].getY()] = wallSquares[0];
        squares[wallSquares[1].getX()][wallSquares[1].getY()] = wallSquares[1];

    }

    //*************************************************************************

    /**
      * Returns the number of whichever Player's turn it is.
      *     @return the player number of the current player's turn
      */
    public int getCurrPlayerTurn() {
        return playerTurn;
    }

    /**
      * Shuffles the Queue of Players and assigns to the board which Player's
      * turn it is.
      *     @param players Queue of Players to shuffle
      */
    public Queue<Player> getNextTurn(Queue<Player> players) {
        players.add(players.remove());
        playerTurn = players.peek().getPlayerNo();
        return players;
    }

    //*************************************************************************

    /**
      * Removes a Player from the given location on the GameBoard.
      *     @param player the player to remove
      */
    public void removePlayer(Player player) {
        Square loc = playerLocs[player.getPlayerNo()];
        assert (validLoc(loc.getX(), loc.getY()));
        playerLocs[player.getPlayerNo()] = null;
        squares[loc.getX()][loc.getY()].removePlayer();
    }

    /**
      * Relocates a Player from their current position to the new given
      * location.
      *     @param player the Player to be relocated
      *     @param newSqr the destination to move the Player to
      *     @throws assertion if the destination is an invalid location
      */
    public void move(Player player, Square newSqr) {
//        assert (player != null);
//        if (newSqr != null){
//            Deb.ug.println("Move sqr is null");
//            System.exit(40);
//        }
        assert (validLoc(newSqr.getX(), newSqr.getY()));
        
        removePlayer(player);
        addPlayer(player, newSqr.getX(), newSqr.getY());
    }

    //-------------------------------------------------------------------------

    /**
      * Adds a Player to the given location.
      *     @param player the Player to be added
      *     @param x column
      *     @param y row
      *     @throws assertion if location is invalid
      *     @throws if param Player is null
      */
    private void addPlayer(Player player, int x, int y) {
        assert (validLoc(x, y));
        squares[x][y].addPlayer(player);
        assert (player != null);
        playerLocs[player.getPlayerNo()] = squares[x][y];
    }

    //*************************************************************************

    /**
      * Validates the given coordinates to make sure they are within the bounds
      * of the GameBoard.
      *      @param x the column of the board
      *      @param y the row of the gameboard
      *      @return if the location within bounds of the GameBoard
      */
    private boolean validLoc(int x, int y) {
        return (x >= 0 && x < COLUMNS && y >= 0 && y < ROWS);
    }

    //*************************************************************************

    /**
      * Initializes the Player locations to their appropriate start locations.
      * Player0 to (4,0); Player1 to (4,8); Player2 to (0,4); Player3 to (8,4)
      *      @param players queue of players to initialize
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

    public boolean isPlayerRemaining(int pno) {
        if(playerLocs.length == 2 && pno >= 2)
            return false;
        return (playerLocs[pno] == null ) ? false : true;
    }

    
}
