/** GameBoard.java - CIS405 - teams
  * _______________________________________________________
  * GameBoard object to represent a 9x9 grid for the 
  * quoridor game
  *
  * ----------------------- METHODS -----------------------
  *
  *
  * Considerations
  *     Saturday (2/14) 
  *     if this object is only going to represent an array,
  *     then why not just have a Square array called 
  *     gameBoard within the Game class? - Walling
  */

public class GameBoard {

    private Square [][] squares;

    public GameBoard() {
	squares = new Square[9][9];
    }

    public boolean IsOccupied(int x, int y) {

	// Check for valid location
	if(validLoc(x,y)) {

	    // Check to see if a player is there
	    if(squares[x][y].getPlayer() == null)
		return false;

	    return true;
	}
	else
	    return false; //Exception
    }
      
    public Player getPlayeratLoc (int x, int y){
	return squares[x][y].getPlayer();
    }
    
    public boolean validLoc(int x, int y) {
	if(x >= 0 && x < 9)
	    if(y >= 0 && y < 9)
		return true;
	return false;
    }
   
}
