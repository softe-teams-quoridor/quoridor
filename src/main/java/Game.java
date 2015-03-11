/* 
   this class tracks the game's state. 
*/
import java.util.*;
public class Game {


    // Constants
    private static final int TWO_PLAYER_WALLS = 10; 
    private static final int FOUR_PLAYER_WALLS = 5; 




    public static void main ( String[] args ) {

        int turn = 0;

        // Connect to players
        // - this will give us the number of players playing (2 or 4)   
        
        GameBoard board = new GameBoard();
       
        // Instantiate Players array
        Player[] players = new Player[args.length];

        GameEngine game = new GameEngine();

        players[0] = new Player("1",board.getSquare(4,0),TWO_PLAYER_WALLS);

        board.addPlayer(players[0],4,0);

         GameboardFrame f = new GameboardFrame(board);

        // Instantiate GameBoard object (with number of players)
        
        // Initialize turn
        // - 0 is player 1
        // - 1 is player 2
        // - 2 is player 3
        // - 3 is player 4
        
         while (true) {

            // get move from player
             Scanner scan = new Scanner(System.in);
             String move = scan.next();

             Square moveTo = game.parseMove(board,move);

             board.move(players[0], moveTo);
             board.removePlayer(4,0);

             f.update(board);
             

            // validate move
            // - if valid, update board & broadcast move to other players

            // - if invalid, boot player & broadcast boot to other players
            
            // if player has won, set victory to true

            // get next player's turn 
            // - make sure turn does not index a booted player
            // - turn = turn + 1 % players.length;

        }

        // broadcast victor
        // - "player ___ has won the game!"

    }

}
