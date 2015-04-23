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
    private Square [][] squares;  // The cells of the GameBoard
    private Square [] playerLocs; // locations of the players on the board
    private int playerTurn;       // whichever player's turn it is

    //*************************************************************************

    /** 
      * Constructs the GameBoard by instantiating the array of squares and
      * initializing player locations.
      *     @param players queue of players to be given a start location
      */
    public GameBoard(Queue<Player> players) {
        assert (players.size() == 2 || players.size() == 4);
        // Instantiate squares array, setting X and Y to i and j respectively
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
        assert (pno >= 0 && pno < playerLocs.length);
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
        assert (validLoc(newSqr.getX(), newSqr.getY()));
        removePlayer(player);
        addPlayer(player, newSqr.getX(), newSqr.getY());
    }

    //-------------------------------------------------------------------------

    /**
      * Adds a Player to the given location.
      *     @param p the Player to be added
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
    
    //*************************************************************************

    //DO NOT TRUST THIS METHOD YET!!
    
    /**
     * finds the shortest path on the board to the win condition of a player
     * @param player player whose shortest path you wish to find
     * @return Square[] the shortest path to win
     */
     public Square[] findShortestPath(Player p){
	int playerNo = p.getPlayerNo();
	Square current = getPlayerLoc(p);
	
	PathTreeNode root = new PathTreeNode(current, 0);
	
	if(playerNo == 0)
	    root = buildTree0(root);
	else if(playerNo == 1)
	    root = buildTree1(root);
	else if(playerNo == 2)
	    root = buildTree2(root);
	else
	    root = buildTree3(root);
	
	
	return iteratePath(root);
     }
     
     //************************************************************************
     /**
      * finds the shortest path based on the built tree
      * @param PathTreeNode root of tree
      * @return Square[] list of path
      */
     public Square[] iteratePath(PathTreeNode root){
	Square[] path = new Square[81];
	int index = 0;
	
	//This just needs to find which branch of tree is shortest....
	
	while(root.firstAdjFromHere != null){
	    while(root.nextAdjFromParent != null){
		
	    }
	}
	
	return path;
     }

     //************************************************************************
     
     /**
      * builds the path tree with player 0's win condition
      * @param PathTreeNode where we start from
      * @return PathTreeNode this is the root of the tree
      */
     public PathTreeNode buildTree0(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor0(reach, root.location);
	
	return buildTree0(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree0
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree0(PathTreeNode root, int i, Square[] reachable){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor0(reach, root.location);
	if(reach == null)
	    return root;
	    
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree0(new PathTreeNode(reach[0], i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree0(new PathTreeNode(reachable[0], i), i++, reachable);
	return root;
     }
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 0
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor0(Square [] reach, Square current){
     
	
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	 
	for(int i = 0; i < reach.length; i++){
	    if(reach[i].getY() < y){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
		    reach[g] = reach[g+1];
		}
	    }
	}
	y++;
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( y == 8)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
     //************************************************************************
     
     /**
      * builds the path tree with player 1's win condition
      * @return PathTreeNode this is the root of the tree
      */
    public PathTreeNode buildTree1(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor1(reach, root.location);
	
	return buildTree1(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree1
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree1(PathTreeNode root, int i, Square[] reachable){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor1(reach, root.location);
	if(reach == null)
	    return root;
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree1(new PathTreeNode(reach[0], i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree1(new PathTreeNode(reachable[0], i), i++, reachable);
	return root;
     }
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 1
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor1(Square [] reach, Square current){
     
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	
	for(int i = 0; i < reach.length; i++)
	    if(reach[i].getY() > y){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
			reach[g] = reach[g+1];
		    }
	    }
	y--;
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( y == 0)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
     
     //************************************************************************
     
     /**
      * builds the path tree with player 2's win condition
      * @return PathTreeNode this is the root of the tree
      */
     public PathTreeNode buildTree2(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor2(reach, root.location);
	
	return buildTree2(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree2
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree2(PathTreeNode root, int i, Square[] reachable){
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor2(reach, root.location);
	if(reach == null)
	    return root;
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree2(new PathTreeNode(reach[0], i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree2(new PathTreeNode(reachable[0],i), i++, reachable);
	return root;
     }
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 2
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor2(Square [] reach, Square current){
     
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	for(int i = 0; i < reach.length; i++)
	    if(reach[i].getX() < x){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
		    reach[g] = reach[g+1];
		}
	    }
	x++;
	
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( x == 8)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
     
     //************************************************************************
     
     /**
      * builds the path tree with player 3's win condition
      * @return PathTreeNode this is the root of the tree
      */
     public PathTreeNode buildTree3(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor3(reach, root.location);
	
	return buildTree3(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree3
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree3(PathTreeNode root, int i, Square[] reachable){
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor3(reach, root.location);
	if(reach == null)
	    return root;
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree3(new PathTreeNode(reach[0],i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree3(new PathTreeNode(reachable[0],i), i++, reachable);
	return root;
     }
     
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 3
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor3(Square [] reach, Square current){
     
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	for(int i = 0; i < reach.length; i++)
	    if(reach[i].getX() > x){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
			reach[g] = reach[g+1];
		}
	    }
	x--;
	
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( x == 0)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
    
}
