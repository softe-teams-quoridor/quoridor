/* GameBoard.java */

/**
 * Represents a 9x9 grid for the Quoridor board game. This object is 
 * essentially a data structure of Square objects and is used to access 
 * and modify its indexed Square. Such accesses and modifications are 
 * checking if a set of coordinates is valid, adding, removing, and moving
 * a player, and initializing start locations.
 *
 * The number of Players active on the board as well as each of their
 * respective locations of each Player object is also stored, indexed by the
 * Player's unique player number (0-3).
 *
 * @author      Eric Cavanagh
 * @author      Dan Soucy
 * @author      Collin Walling
 */


/* --------------------------------- METHODS ----------------------------------
 *
 * GameBoard()                   --> constructor 
 *
 * boolean isOccupied(int,int)   --> returns if Player is at the given x 
 *                                   and y location
 * 
 * Square getSquare(int,int)     --> returns a Square at the given x 
 *                                   and y location
 *
 * Square getSquare(String)      --> returns a Square at the given 
 *                                   numeral-character string
 *
 * Player getPlayer(int,int)     --> returns a Player from the board at 
 *                                   the given x and y coordinates
 *
 * Player getPlayer(int)         --> returns a Player from the board based on
 *                                   the player's unique number
 *
 * Square getPlayerLoc(Player)   --> returns the current location of the Player
 *
 * Square getPlayerLoc(int)      --> returns the current location of the Player
 *
 * void placeWall(Square,Square) --> places a Wall on the board
 *
 * void removeWall(Square[])     --> removes a Wall from the board
 *
 * void removePlayer(Player)     --> removes a Player from the board
 *
 * void move(Player,Square)      --> moves a Player from one Square to another
 *
 * int getCurrPlayerTurn()       --> returns the turn of whichever player's 
 *                                   turn it is 
 *
 * Queue<Player> getNextTurn
 *            (Queue<Player>)    --> shuffles Player Queue
 */

import java.util.Queue;

public class GameBoard {

    // Constants
    public static final int COLUMNS = 9; // X
    public static final int ROWS = 9;    // Y

    // Data Members
    protected Square [][] squares;  // the cells of the GameBoard
    protected Square [] playerLocs; // locations of the players on the board
    private int playerTurn;         // whichever player's turn it is

    /** 
     * Constructs the GameBoard by instantiating the array of squares and
     * initializing player locations.
     * 
     * @param players    queue of players that will be playing the game
     */

    public GameBoard(Queue<Player> players) {
        // only allow the construction of a GameBoard constituting the
        // locations of two or four players
        assert (players.size() == 2 || players.size() == 4);

        // Instantiate squares array, setting X and Y to i and j respectively
        squares = new Square[COLUMNS][ROWS];
        for (int i = 0; i < COLUMNS; i++)
            for (int j = 0; j < ROWS; j++)
                squares[i][j] = new Square(i, j);

        // Initialize player turn
        playerTurn = 0;

        // Instantiate player location array
        playerLocs = new Square[players.size()];

        // Initialize player positions
        setupInitialPositions(players);
    }

    /**
     * Returns the number of players remaining in the game.
     *
     * @return the number of players remaining
     */

    public int numPlayersRemaining() {
        int c = 0;
        for(int i = 0; i < playerLocs.length; i++) {
            if(playerLocs[i] != null) {
                c++;
            }
        }
        return c;
    }

    /** 
     * Returns if a Square on the board is occupied.
     * 
     * @param x    the column of the board
     * @param y    the row of the gameboard
     *
     * @return if the cell is occupied
     *
     * @throws assertion if given coordinates are invalid
     */

    public boolean isOccupied(int x, int y) {
        assert (validLoc(x, y));
        return (getPlayer(x, y) != null);
    }

    /**
     * Returns the Square on the GameBoard at the given x and y coordinates.
     * 
     * @param x    the column
     * @param y    the row
     *
     * @return the Square object
     *
     * @see Square
     */

    public Square getSquare(int x, int y) {
        return validLoc(x,y) ? squares[x][y] : null;
    }

    /**
     * Returns the Square on the GameBoard at the given column (numeral) and
     * row (character).
     *
     * @param square    String representing a Square, e.g. IV-D
     *
     * @return the Square object
     *
     * @throws assertion if the format of the string is inappropriate
     *
     * @see Square
     */

    public Square getSquare(String square) {
        String [] separated = square.trim().split("-");
        assert (separated.length == 2);
        int x = GameEngine.fromNumerals(separated[0]);
        assert (separated[1].length() == 1);
        int y = GameEngine.fromLetters(separated[1].charAt(0));
        return getSquare(x, y);
    }

    /**
     * Returns a Player at the given x and y coordinates.
     * 
     * @param x    column
     * @param y    row
     *
     * @return the Player at the location, null if unoccupied
     *
     * @see Player
     */

    public Player getPlayer(int x, int y) {
        return validLoc(x,y) ? squares[x][y].getPlayer() : null;
    }

    /**
     * Returns a Player based on their unique player number.
     *  
     * @param pno    the number of the Player you want
     *
     * @return the player of that number or null
     *
     * @throws assertion if the Player number is 0 or 3
     * @throws assertion if the Player has been kicked from the game
     *
     * @see Player
     */

    public Player getPlayer(int pno) {
        assert (pno >= 0); 
        assert (pno < playerLocs.length);
        if (playerLocs[pno] == null) {
            return null; // this player has been booted
        }
        return playerLocs[pno].getPlayer();
    }

    /**
     * Returns the given Player's location on the GameBoard.
     * 
     * @param player    the Player we want the location from
     *
     * @return the player's location
     */

    public Square getPlayerLoc(Player player) {
        return playerLocs[player.getPlayerNo()];
    }

    /**
     * Returns the given Player's Number.
     * 
     * @param pno    the player number to index the location
     *
     * @return the player's location
     */

    public Square getPlayerLoc(int pno) {
        return playerLocs[pno];
    }

    /**
     * Places a Wall on the GameBoard.
     *
     * @param wallSquares    array of Squares representing a wall placement
     *
     * @see Wall
     */

    public void placeWall (Square[] wallSquares) {
        // Horiz
        if(wallSquares[0].getY() == wallSquares[1].getY()) {
            wallSquares[0].placeWallBottom(true);
            wallSquares[1].placeWallBottom(false); 
        }
        // Vert
        else {
            wallSquares[0].placeWallRight(true);
            wallSquares[1].placeWallRight(false); 
        }
        squares[wallSquares[0].getX()][wallSquares[0].getY()] = wallSquares[0];
        squares[wallSquares[1].getX()][wallSquares[1].getY()] = wallSquares[1];
    }

    /**
     * Removes a Wall from the GameBoard.
     *
     * @param wallSquares    locations of the Wall to be removed
     *
     * @see Wall
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

    /**
     * Returns which Player's turn it is.
     * 
     * @return the player number of the current player's turn
     */

    public int getCurrPlayerTurn() {
        return playerTurn;
    }

    /**
     * Shuffles the Queue of Players and assigns to the board which Player's
     * turn it is.
     *
     * @param players    Queue of Players to shuffle
     */

    public Queue<Player> getNextTurn(Queue<Player> players) {
        players.add(players.remove());
        playerTurn = players.peek().getPlayerNo();
        return players;
    }

    /**
     * Determines if a particular player is still active.
     *
     * @param pno    the player number being looked at
     *
     * @return if the player is in the game false othereise
     *
     */

    public boolean isPlayerRemaining(int pno) {
        // If only a 2 player game and sent a number larger than 2
        if(playerLocs.length == 2 && pno >= 2)
            return false;
        return (playerLocs[pno] == null ) ? false : true;
    }

    /**
     * Removes a Player from the given location on the GameBoard.
     * 
     * @param player    the player to remove
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
     *
     * @param player    the Player to be relocated
     * @param newSqr    the destination to move the Player to
     *
     * @throws assertion if the destination is an invalid location
     */

    public void move(Player player, Square newSqr) {
        assert (validLoc(newSqr.getX(), newSqr.getY()));
        removePlayer(player);
        addPlayer(player, newSqr.getX(), newSqr.getY());
    }

    /**
     * Adds a Player to the given location.
     * 
     * @param player    the Player to be added
     * @param x    column
     * @param y    row
     *
     * @throws assertion if location is invalid or if param Player is null
     */

    private void addPlayer(Player player, int x, int y) {
        assert (validLoc(x, y));
        squares[x][y].addPlayer(player);
        assert (player != null);
        playerLocs[player.getPlayerNo()] = squares[x][y];
    }

    /**
     * Validates the given coordinates to make sure they are within the bounds
     * of the GameBoard.
     *
     * @param x    the column of the board
     * @param y    the row of the gameboard
     *
     * @return if the location within bounds of the GameBoard
     */

    private boolean validLoc(int x, int y) {
        return (x >= 0 && x < COLUMNS && y >= 0 && y < ROWS);
    }

    /**
     * Initializes the Player locations to their appropriate start locations.
     * Player0 to (4,0); Player1 to (4,8); Player2 to (0,4); Player3 to (8,4)
     *
     * @param players     queue of players to initialize
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

} // GameBoard
