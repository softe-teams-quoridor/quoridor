/* this QuoridorAI moves in a random direction */

import java.util.Random;

public class AI_RollDice implements QuoridorAI {
    private Random rand; 
    public AI_RollDice() {
        rand = new Random(System.currentTimeMillis());
    }

    public String getMove(GameBoard board, Player player) {
        Square loc = board.getPlayerLoc(player);
        Square [] options = GameEngine.reachableAdjacentSquares(board, loc);
        if (options.length == 0) {
            return "no moves :(";
        }
        return options[rand.nextInt(options.length)].toString();
    }

    public void reset() {
        rand = new Random(System.currentTimeMillis());
    }

    public String toString() {
        return "RollDice";
    }
}
