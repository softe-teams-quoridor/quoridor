/* this QuoridorAI flips coins and rolls dice */

import java.util.Random;

public class AI_FlipCoinRollDice implements QuoridorAI {
    
    private Random rand; 

    public AI_FlipCoinRollDice() {
        rand = new Random(System.currentTimeMillis());
    }

    public String getMove(GameBoard board, Player player) {
        
        boolean coin = true /*(rand.nextInt() % 2 == 0) */;
        Square [] moveSquares;
        if (coin) {
            // move pawn
            moveSquares = GameEngine.reachableAdjacentSquares(board,
                             board.getPlayerLoc(player));
            int move = rand.nextInt() % moveSquares.length;
            return (moveSquares[move].toString());
        } else {
            // place wall
        }
        return "FIXME";
    }

    public void reset() {
        rand = new Random(System.currentTimeMillis());
    }

    public String toString() {
        return "FC,RD";
    }
}
