import java.util.*;
public class AI_Maybe implements QuoridorAI {

    /**
      * Constructor.
      */
    public AI_Maybe() { }
    
    //DOCUMENTME
    /**
      * Returns a move.
      *     @param b GameBoard to play on
      *     @param p Player that is moving
      */
    public String getMove(GameBoard b, Player p) {
        Graph virtualBoard = new Graph(b.COLUMNS * b.ROWS);
        Square[] path = virtualBoard.buildPath(b,p);
        Square[] pathShort = path;
        
        int pno = p.getPlayerNo();
        int winningPlayer = pno;
       
        // find which player has the shortest path 
        for(int i = 0; i < b.numPlayersRemaining(); i++){
            Square [] temp = null;
            if(i != pno && b.isPlayerRemaining(i))
                temp = virtualBoard.buildPath(b,b.getPlayer(i));
            if(temp != null && temp.length < (pathShort.length)){
                pathShort = temp;
                winningPlayer = i;
            }
        }

        // if this AI is not the Player with the shortest path,
        //  try to block the opponent with the shortest path
        if(winningPlayer != pno){
            // get a wall-blocking string
            String block = blockPlayer(b, winningPlayer,pno, pathShort);
            // if we have a valid wall-blocking string, return it
            if(block != null) {
                return block;
            }
        }
        
        // otherwise, return the next move in the shortest path
        return path[0].toString();
    }


    /**
      * Attempts to block an opposing player's path.
      *     @param b GameBoard to retrieve locations from
      *     @param opponentNo the ID number of the Player we wish to block
      *     @param us the AI's Player number
      *     @param path the path of the opposing player
      */
    public String blockPlayer(GameBoard b, int opponentNo,int us, Square[] path){
        Square[] wallSquares = new Square [2];
        
        Random rand = new Random(System.currentTimeMillis());
        int r = (rand.nextInt(path.length) - 1) % 1;
        r = -1;
    
        int blockTwoX, blockTwoY;
        Square blockOne;

        // if random is negative, block around opponent's square
        if ( r == -1 ) {
            blockTwoX = path[0].getX();
            blockTwoY = path[0].getY();
            wallSquares[0] = path[0];
            blockOne = b.getPlayerLoc(opponentNo);
        }
        // else, block further along opponent's path
        else {
            blockTwoX = path[r+1].getX();
            blockTwoY = path[r+1 ].getY();
            wallSquares[0] = path[r];
            blockOne = path[r];
        }

        // if the opponent wants to move verticallblockTwoY
        if ( blockTwoX == blockOne.getX() ) {
            blockTwoX++;
            // if the opponent wants to move DOWN
            if ( blockTwoY > blockOne.getY() ) {
                wallSquares[0] = blockOne;
                blockTwoY--;
            }
        }
        // if the opponent wants to move horizontallblockTwoY
        else {
            blockTwoY++;
            // if the opponent wants to move right
            if ( blockTwoX > blockOne.getX() ) {
                wallSquares[0] = blockOne;
                blockTwoX--;
            }
        }

        // assign second wall piece
        wallSquares[1] = b.getSquare(blockTwoX,blockTwoY);

        // validate the bounds of the second wall piece
        if(wallSquares[1] == null) {
            // if blockTwoX exceeds the right boundary
            if ( blockTwoX >= b.COLUMNS ) {
                wallSquares[1] = wallSquares[0];
                wallSquares[0] = b.getSquare(blockTwoX-2,blockTwoY);
            }
            // if blockTwoX exceeds the left boundary
            else if ( blockTwoX < 0 ) { 
                wallSquares[1] = b.getSquare(blockTwoX+2,blockTwoY);
            }
            // if blockTwoY exceeds the bottom boundary
            else if ( blockTwoY >= b.ROWS ) {
                wallSquares[1] = wallSquares[0];
                wallSquares[0] = b.getSquare(blockTwoX,blockTwoY-2);          
            }
            // if blockTwoY exceeds the top boundary
            else {
                wallSquares[1] = b.getSquare(blockTwoX,blockTwoY+2);
            }
        } 

        String wallString = "(" + wallSquares[0].toString() + "," + wallSquares[1].toString() + ")";

        // return the wall string if it is a valid wall placement on the GameBoard
        return (GameEngine.validate(b,b.getPlayer(us), wallString) != null) ? wallString : null;
    }

    /**
      * Reset an ai to its initial state.
      * Used for starting a new game.
      */
    public void reset() { }

    /**
      * Returns name of AI.
      */
    public String toString() {
        return "Maybe?";
    }
}
