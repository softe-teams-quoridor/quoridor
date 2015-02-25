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
  * validLoc(int x, int y)     -- returns true if coordiantes are in the board 
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
    private static final int  COLOUMNS = 9;
    private static final int ROWS = 9;
    


    // Members
    private Square [][] squares; // The cells of the GameBoard



    /** Constructor
      * The constructor builds the cells of the board
      */
    public GameBoard() {
        squares = new Square[COLOUMNS][ROWS];
        for(int i = 0; i < COLOUMNS; i++){
            for (int j = 0; j < ROWS; j++){
                squares[i][j] = new Square();
            // Should also set the bottom and right cells to have walls
            }
        }
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
            //throw new RuntimeException("Invaild Location");
    }

    //******************************************************************************

    /**
      * gets the sqaure specified
      * @param x = the column of the board
      * @param y = the row of the gameboard
      * @return the square object
      */
    public Square getSquare(int x, int y){
        return squares[x][y];
    }

    //******************************************************************************
    
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
    public void removePlayer(int x, int y) {
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
        if(validLoc(x,y))
            return squares[x][y].getPlayer();
        else 
            return null; // Exception
    }

    //*******************************************************************************
    
    /**
      * Checks the location to see if it is acutally on the board
      * @param player = the player object
      * @param x = the column of the board
      * @param y = the row of the gameboard
      * @return true if location is on board, false otherwise
      */
    public boolean validLoc(int x, int y) {
        if(x >= 0 && x < COLOUMNS)
            if(y >= 0 && y < ROWS)
                return true;
        return false;
    }

    //*******************************************************************************

    /** --- CONSIDER IF NEEDED ---
      * returns the squares array that represents the game board
      * @return = returns the array of squares
      */
    public Square[][] getBoard() {
        return squares;
    }

    //*******************************************************************************
   
}

