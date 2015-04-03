public interface QuoridorAI {
    /* get a move string */
    String getMove(GameBoard b, Player p); 

    /* reset an ai to its initial state; used for starting a new game */
    void reset(); 
}
