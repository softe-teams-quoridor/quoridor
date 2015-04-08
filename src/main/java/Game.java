/* Game.java (aka Quoridor) - CIS 405 - teams
 * Last Edit: March 29, 2015
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
    private static final Queue<Player> players = new LinkedList<Player>();

    /**
     * prints a friendly message and exits
     * @param an int to return to the OS
     */
    public static void usage(int error) {
        System.err.println("usage: java Game <host> <port> <host> <port> " + 
                           "[<host> <port> <host> <port>]");
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

    protected static int countValidNames(String [] names) {
        int count = 0;
        for (int i = 0; i < names.length; i++) {
            if (isValidName(names[i])) {
                count++;
            }
        }
        return count;
    }

    protected static boolean isValidName(String name) {
        return (! (name == null || name.contains(" ") || name.length() > 20));
    }

    public static void main (String[] args) {
        // initialize debug stream
        Deb.initialize("game");

        // quit if bad arguments
        Deb.ug.println("args provided: " + Arrays.toString(args));
        
        if (args.length !=  4 && args.length != 8) {
            usage(1);
        }

        // Set the number of players
        numPlayers = args.length/2;
        Deb.ug.println("number of players: " + numPlayers);

        // Connect to players
        ClientMessenger hermes = new ClientMessenger(args);

        // get initial messages from servers with their names
        String [] names = hermes.getNames();
        Deb.ug.println("names: " + Arrays.toString(names));

        // Instantiate Players
        Deb.ug.println("instantiating Players...");
        for (int i = 0; i < numPlayers; i++) {
            if (isValidName(names[i])) {
                players.add(new Player(i, names[i], WALL_POOL / numPlayers));
            } else {
                // this move server couldn't tell us its name! 
                players.add(new Player(i, WALL_POOL / numPlayers));
            }
        }

        // Instantiate GameBoard
        Deb.ug.println("instantiating GameBoard...");
        GameBoard board = new GameBoard(players);
        Deb.ug.println("players queue: " + players.toString());

        // tell all move servers who the players are
        hermes.broadcastPlayers(players);

        // can we boot players for giving us the wrong name before first turn?
        // 'cause that's we're going to do
        for (int i = 0; i < numPlayers; i++) {
            if (! isValidName(names[i])) {
                Player badlyNamed = players.remove();
                Deb.ug.println("badly named: " + badlyNamed.getName());
                board.removePlayer(badlyNamed);
                hermes.broadcastBoot(badlyNamed);
            } else {
                players.add(players.remove()); // shuffle queue, next in line..
            }
            // this loop basically plays russian roulette, hahAHAH!
        }

        if (players.size() == 0) {
            // not a single server survived the first message...
            System.out.println("you should fix your move-servers.");
            System.exit(0);
        } else if (players.size() == 1) {
            Player survivor = players.remove();
            hermes.broadcastVictor(survivor);
            System.out.println("by elimination, the winner is " + survivor); 
            System.exit(0);
        }

        /*
        if (countValidNames(names) == 0) {
            // not a single server survived the first message...
            System.out.println("you should fix your move-servers.");
            System.exit(0);
        } else if (countValidNames(names) == 1) {
            Player survivor = players.remove();
            hermes.broadcastVictor(survivor);
            System.out.println("by elimination, the winner is " + survivor); 
            System.exit(0);
        }
        */

        // Start up the display
        Deb.ug.println("starting GameBoardFrame...");
        GameBoardFrame frame = new GameBoardFrame(board, players);

        // loop will need to check for a victory condition
        Deb.ug.println("beginning main loop");
        while (true) {
            // Get current player
            Player currentPlayer = players.peek();

            // Get move from player
            Deb.ug.println("requesting move from player: " + 
                           currentPlayer.getName());
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
                players.add(players.remove()); // Shuffle queue
            }
            // Update the graphical board
            frame.update(board);

            // Retrieve a possibly winning player and broadcast if winner found
            Player winner = GameEngine.getWinner(board, players);
            if (winner != null) {
                hermes.broadcastVictor(winner);
                break;
            }

            sleep(200); // sleepy time

        }//-----END OF LOOP-----

        hermes.closeAllStreams(players);

        // pause board for two seconds before ending
        sleep(2000);
        System.exit(0);
    }
}
