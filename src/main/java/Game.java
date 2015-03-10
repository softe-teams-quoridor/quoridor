/* 
   this class tracks the game's state. 
*/

public class Game {



    // Data members
    private int turn;         // whose turn is it; used to index players array
    private GameBoard board;  // board used to keep track of game progress
    private Player[] players; // array of players



    public static void main ( String[] args ) {

        // Connect to players
        // - this will give us the number of players playing (2 or 4)

        // Instantiate Players array

        // Instantiate GameBoard object (with number of players)
        
        // Initialize turn
        // - 0 is player 1
        // - 1 is player 2
        // - 2 is player 3
        // - 3 is player 4
        
        /* while ( !victory ) {

            // get move from player

            // validate move
            // - if valid, update board & broadcast move to other players

            // - if invalid, boot player & broadcast boot to other players
            
            // if player has won, set victory to true

            // get next player's turn 
            // - make sure turn does not index a booted player
            // - turn = turn + 1 % players.length;

        }*/

        // broadcast victor
        // - "player ___ has won the game!"

    }

}
