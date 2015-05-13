/* this QuoridorAI flips coins and rolls dice */

import java.util.Random;

public class AI_FlipCoinRollDice implements QuoridorAI {
    
    private Random rand; 

    public AI_FlipCoinRollDice() {
        rand = new Random(System.currentTimeMillis());
    }

    public String getMove(GameBoard board, Player player) {

        
        boolean coin = true;
        int num = rand.nextInt(100);
        if(num < 15)
            coin = false;
        Square [] moveSquares;
        int move = 0;
        String str;
        if(player.getNumWalls() == 0)
            coin = true;
        if (coin) {
            // move pawn
            do {
                moveSquares = GameEngine.reachableAdjacentSquares(board,
                                 board.getPlayerLoc(player));
                move = rand.nextInt(moveSquares.length);
                str = moveSquares[move].toString();
            } while(GameEngine.validate(board,player,str) == null);
            return (moveSquares[move].toString());
        } else {
            // place wall
            moveSquares = new Square[2];
            do {
                moveSquares[0] = board.getSquare(rand.nextInt(8),rand.nextInt(8));
                if(moveSquares[0].hasWallRight() && moveSquares[0].hasWallBottom())
                    moveSquares[0] = board.getSquare(rand.nextInt(8),rand.nextInt(8));
                move = rand.nextInt(2);
                if(move == 0) {
                    moveSquares[1] = new Square(moveSquares[0].getX() + 1, 
                                                moveSquares[0].getY());
                }
                else {
                    moveSquares[1] = new Square(moveSquares[0].getX(), 
                                                moveSquares[0].getY() + 1);
                }
                str = ( "(" + moveSquares[0].toString() + "," + 
                                     moveSquares[1].toString() + ")");
            } while((GameEngine.validate(board,player,str)) == null);
            return str;
        }

    }

    public void reset() {
        rand = new Random(System.currentTimeMillis());
    }

    public String toString() {
        return "FCRD";
    }
}
