public interface QuoridorAI {
    /* get a move string */
    String getMove(GameBoard b, Player p); 

    /* reset an ai to its initial state; used for starting a new game */
    void reset(); 

    /* used in names 
     * don't make this return anything longer than 10 characters though LOOOOL
     */
    String toString();
}
