/* 
   this class tracks the game's state. 
 */

import java.util.*;

// networking stuff... 
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Game {


    // Constants
    private static final int TWO_PLAYER_WALLS = 10; 
    private static final int FOUR_PLAYER_WALLS = 5; 

    private static PrintStream [] outStreams; // these should both have the
    private static Scanner     [] inStreams;  // same length as players

    /*
     * prints a friendly message and exits
     * @param an int to return to the OS
     */
    private static void usage(int error) {
        System.err.println("usage: java Game <host> <port> [<host> <port>]");
        System.exit(error);
    }

    /*
     * parses command-line arguments
     * populates the inStreams and outStreams arrays
     */
    public static void parseArgs (String[] args) {
        if (args.length < 2) {
            usage(1);
        }

        int [] ports = new int[args.length/2];
        String [] hosts = new String[args.length/2];
        for (int i = 0; i < args.length; i++) { 
            hosts[i/2] = args[i];
            i++;
            try {
                ports[i/2] = Integer.parseInt(args[i]);
            } catch (Exception e) {
                usage(2);
            }
        }
        System.out.println("ports found: " + Arrays.toString(ports));



        // Connect to players
        outStreams = new PrintStream [ports.length];
        inStreams  = new Scanner     [ports.length];
        for (int i = 0; i < ports.length; i++) {
            try {
                Socket socket = new Socket(hosts[i], ports[i]);
                PrintStream sout = new PrintStream(socket.getOutputStream());
                outStreams[i] = sout;
                Scanner sin = new Scanner(socket.getInputStream());
                inStreams[i] = sin;
            } catch (UnknownHostException uhe) {
                // the host name provided could not be resolved
                uhe.printStackTrace();
                System.exit(1);
            } catch (IOException ioe) {
                // there was a standard input/output error (lower-level)
                ioe.printStackTrace();
                System.exit(1);
            }
        }
        // test all connections -- not necessary, just for debugging
        for (int i = 0; i < ports.length; i++) {
            if (outStreams[i] == null) {
                System.out.println("outStreams[" + i + "] is null!");
            }
            if (inStreams[i] == null) {
                System.out.println("inStreams[" + i + "] is null!");
            }
        }
    }

    public static void main (String[] args) {
        // - this will give us the number of players playing (2 or 4)
        parseArgs(args);

        // Connect to players
        // - this will give us the number of players playing (2 or 4)   

        GameBoard board = new GameBoard();

        // Instantiate Players array
        Player[] players = new Player[args.length/2];

        players[0] = new Player("1",board.getSquare(4,0),TWO_PLAYER_WALLS);

        board.addPlayer(players[0],4,0);

        GameboardFrame f = new GameboardFrame(board);


        int currentPlayer = 0;

        int m = 0;

        while (true) {

            // get move from player
            outStreams[currentPlayer].println("make your move");
            String response = inStreams[currentPlayer].nextLine();
            System.out.println("received: " + response);

            Square moveTo = GameEngine.parseMove(board,response);

            // validate move


            board.move(players[currentPlayer], moveTo);

            if(m ==0) //***FIXME****
                board.removePlayer(4,0);
            m++;
            f.update(board);


            // - if valid, update board & broadcast move to other players
            for (int i = 0; i < outStreams.length; i++) {
                outStreams[i].println("player " + currentPlayer + 
                        " made move: " + response);
            }

            // - if invalid, boot player & broadcast boot to other players

            // if player has won, set victory to true

            // get next player's turn 
            // - make sure turn does not index a booted player
            // - turn = turn + 1 % players.length;

            //currentPlayer = (currentPlayer + 1) % outStreams.length;


        }

    }
}
