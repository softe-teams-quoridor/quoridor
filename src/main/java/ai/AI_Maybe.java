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
        return path[0].toString();
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
