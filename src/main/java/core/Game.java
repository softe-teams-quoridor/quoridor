/* Game.java (aka Quoridor) - CIS 405 - teams
 * ____________________________________________________________________________
 * 
 * implements the GameEngine and Messenger to create and run the game Quoridor
 */

import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

public class Game {

    private static int numPlayers;     // how many players are in the game
    private static final int WALL_POOL = 20; // total collection of walls
    private static Queue<Player> players = new LinkedList<Player>();

    /**
     * prints a friendly message and exits
     * @param an int to return to the OS
     */
    public static void usage(int error) {
        System.err.println("usage: java Game host:port host:port" + 
                           "[host:port host:port]");
        System.exit(error);
    }

    /**
     * sleepy time
     * @param length duration for thread to pause
     */
    private static void sleep(int length) {
        try {
            Thread.sleep(length);
        } catch (InterruptedException e) {
            // ignore it
        }
    }

    public static void main (String[] args) {
        // initialize debug stream
        Deb.initialize("game");

        // quit if bad arguments
        Deb.ug.println("args provided: " + Arrays.toString(args));
        
        if (args.length != 2 && args.length != 4) {
            usage(1);
        }

        // Set the number of players
        numPlayers = args.length;
        Deb.ug.println("number of players: " + numPlayers);

        // Connect to players
        ClientMessenger hermes = new ClientMessenger(args);

        // get initial messages from servers with their names
        String [] names = hermes.getNames();
        Deb.ug.println("names: " + Arrays.toString(names));

        // Instantiate Players
        Deb.ug.println("instantiating Players...");
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, names[i], WALL_POOL / numPlayers));
        }
        assert (players.size() == numPlayers);

        // tell all move servers who the players are
        hermes.broadcastPlayers(players);

        // Instantiate GameBoard
        Deb.ug.println("instantiating GameBoard...");
        GameBoard board = new GameBoard(players);
        Deb.ug.println("players queue: " + players.toString());

        /* check that moveservers are ready to go */
        hermes.ready();

        if (players.size() == 0) {
            // not a single server survived the MOVE message...
            System.out.println("you should fix your move-servers.");
            System.exit(0);
        } else if (players.size() == 1) {
            Player survivor = players.remove();
            hermes.broadcastVictor(survivor);
            System.out.println("by elimination, the winner is " + survivor); 
            System.exit(0);
        }

        // Start up the display
        Deb.ug.println("starting GameBoardFrame...");
        GameBoardFrame frame = new GameBoardFrame(board, players);
        sleep(250);

        // loop will need to check for a victory condition
        Deb.ug.println("beginning main loop");
        Deb.ug.println("first player is: " + players.peek().getPlayerNo());
        while (true) {
            // Get current player
            Player currentPlayer = players.peek();

            // Get move from player
            Deb.ug.println("requesting move from player: " + currentPlayer);
            String response = hermes.requestMove(currentPlayer);
            Deb.ug.println("received: " + response);

            // Validate if the move is legal and make the move on the board
            // else boot the player for trying to make an illegal move
            Square[] moveSquares = GameEngine.validate(board, currentPlayer, 
                                                       response);
            if (moveSquares == null) { // no legal move
                Deb.ug.println("illegal move attempted");
                board.removePlayer(currentPlayer);
                players.remove();
                hermes.broadcastBoot(currentPlayer);
            } else { // legal move 
                GameEngine.playTurn(response, currentPlayer, board); 
                hermes.broadcastWent(currentPlayer, response);
                players = board.getNextTurn(players); // Shuffle queue
            }
            // Update the graphical board
            frame.update(board);

            // Retrieve a possibly winning player and broadcast if winner found
            Player winner = GameEngine.getWinner(board, players);
            if (winner != null) {
                hermes.broadcastVictor(winner);
                break;
            }

            sleep(100); // sleepy time
        }//-----END OF LOOP-----

        hermes.closeAllStreams(players);
        System.exit(0);
    }
}
