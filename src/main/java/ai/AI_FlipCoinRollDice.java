/* this QuoridorAI flips coins and rolls dice */

import java.util.Random;

public class AI_FlipCoinRollDice implements QuoridorAI {
    
    private Random rand; 

    public AI_FlipCoinRollDice() {
        rand = new Random(System.currentTimeMillis());
    }

    public String getMove(GameBoard board, Player player) {
        
        boolean coin = (rand.nextInt() % 2 == 0);
        Square [] moveSquares;
        int move = 0;
        if (coin) {
            // move pawn
            moveSquares = GameEngine.reachableAdjacentSquares(board,
                             board.getPlayerLoc(player));
            move = rand.nextInt(moveSquares.length);
            return (moveSquares[move].toString());
        } else {
            // place wall
            moveSquares = new Square[2];
            moveSquares[0] = board.getSquare(rand.nextInt(8),rand.nextInt(8));
            if(moveSquares[0].hasWallRight() && moveSquares[0].hasWallBottom())
                moveSquares[0] = board.getSquare(rand.nextInt(8),rand.nextInt(8));
            move = rand.nextInt(2);
            if(move == 0) {
                moveSquares[1] = new Square(moveSquares[0].getX() + 1, moveSquares[0].getY());
            }
            else {
                moveSquares[1] = new Square(moveSquares[0].getX(), moveSquares[0].getY() + 1);
            }
            return ( "(" + moveSquares[0].toString() + "," + moveSquares[1].toString() + ")");

        }
   
    }

    public void reset() {
        rand = new Random(System.currentTimeMillis());
    }

    public String toString() {
        return "FC,RD";
    }
}
