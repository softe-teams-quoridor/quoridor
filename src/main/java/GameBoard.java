public class GameBoard {

    private Square [][] squares;

    public GameBoard() {
	squares = new Square[9][9];
    }

    public boolean IsOccupied(int x, int y) {

	// Check for valid location
	if(validLoc(x,y)) {

	    // Check to see if a player is there
	    if(squares[x][y].getPawn() == null)
		return false;

	    return true;
	}
	else
	    return false; //Exception
    }

    public boolean validLoc(int x, int y) {
	if(x >= 0 && x < 9)
	    if(y >= 0 && y < 9)
		return true;
	return false;
    }
   
}
