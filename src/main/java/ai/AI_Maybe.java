import java.util.*;
public class AI_Maybe implements QuoridorAI {

    /**
      * Constructor.
      */
    public AI_Maybe() { }

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
     *     @param aiNO this AI's Player number
     *     @param path the path of the opposing player
     */
    public String blockPlayer(GameBoard b, int opponentNo, int aiNo, Square[] path){

        int blockTwoX = path[0].getX();
        int blockTwoY = path[0].getY();
        Square blockOne = b.getPlayerLoc(opponentNo);
        boolean outOfBounds = false;
        Square blockZero;
        Random rand = new Random(System.currentTimeMillis());

        // if the opponent wants to move vertically (assume UP)
        if ( blockTwoX == blockOne.getX() ) {
            blockTwoX++;
            // check if the opponent wants to move DOWN
            if ( blockTwoY > blockOne.getY() ) 
                blockTwoY--;
            else
                blockOne = path[0];
        }
        // if the opponent wants to move horizontally (assume LEFT)
        else {
            blockTwoY++;
            // check if the opponent wants to move RIGHT
            if ( blockTwoX > blockOne.getX() ) 
                blockTwoX--;
            else
                blockOne = path[0];
        }



        // assign second wall piece
        Square blockTwo = b.getSquare(blockTwoX,blockTwoY);

        // validate the bounds of the second wall piece
        if ( blockTwo == null ) {
            outOfBounds = true;
            
            // if blockTwoX exceeds the right boundary
            if ( blockTwoX >= b.COLUMNS ) {
                blockTwo = blockOne;
                blockOne = b.getSquare(blockTwoX-2,blockTwoY);
            }
            // if blockTwoX exceeds the left boundary
            else if ( blockTwoX < 0 ) { 
                blockTwo = b.getSquare(blockTwoX+2,blockTwoY);
            }
            // if blockTwoY exceeds the bottom boundary
            else if ( blockTwoY >= b.ROWS ) {
                blockTwo = blockOne;
                blockOne = b.getSquare(blockTwoX,blockTwoY-2);
            }
            // if blockTwoY exceeds the top boundary
            else {
                blockTwo = b.getSquare(blockTwoX,blockTwoY+2);
            }
        } 


        if(blockOne.getY() == blockTwo.getY()) {
            blockZero = b.getSquare(blockOne.getX() - 1,blockOne.getY());
        }
        else {
            blockZero = b.getSquare(blockOne.getX(), blockOne.getY() - 1);
        }


        String wallString12 = "(" + blockOne + "," + blockTwo + ")";
        String wallString01 = "(" + blockZero + "," + blockOne + ")";


        if(rand.nextInt(2) == 0 && !outOfBounds) {
            if(GameEngine.validate(b,b.getPlayer(aiNo), wallString01) != null) {
                return wallString01;
            }
            if(GameEngine.validate(b,b.getPlayer(aiNo), wallString12) != null) {
                return wallString12;
            }
        }
        else {
            if(GameEngine.validate(b,b.getPlayer(aiNo), wallString12) != null) {
                return wallString12;
            }
//            if(GameEngine.validate(b,b.getPlayer(aiNo), wallString01) != null) {
//                return wallString01;
//            }

        }

        // return the wall string if it is a valid wall placement on the GameBoard
        return null;
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
