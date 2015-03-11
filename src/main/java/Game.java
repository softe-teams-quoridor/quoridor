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



    // Data members
    private int turn;         // whose turn is it; used to index players array
    private GameBoard board;  // board used to keep track of game progress
    private Player[] players; // array of players

    /*
     * prints a friendly message and exits
     * @param an int to return to the OS
     */
    private static void usage(int error) {
        System.err.println("usage: java Game <port> <port>");
        System.exit(error);
    }

    public static void main ( String[] args ) {
        if (args.length < 2) {
            usage(1);
        }

        int [] ports = new int[args.length];
        for (int i = 0; i < args.length; i++) { 
            try {
                ports[i] = Integer.parseInt(args[i]);
            } catch (Exception e) {
                usage(2);
            }
        }
        System.out.println(Arrays.toString(ports));


        // Connect to players
        PrintStream [] outStreams = new PrintStream [ports.length];
        Scanner     [] inStreams  = new Scanner     [ports.length];
//         for (int port : ports) {
        for (int i = 0; i < ports.length; i++) {
            try {
                Socket socket = new Socket("localhost", ports[i]);
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
        for (int i = 0; i < ports.length; i++) {
            if (outStreams[i] == null) {
                System.out.println("outStreams[" + i + "] is null!");
            }
            if (inStreams[i] == null) {
                System.out.println("inStreams[" + i + "] is null!");
            }
        }
        for (int i = 0; i < ports.length; i++) {
            outStreams[i].println("make your move");
            String response = inStreams[i].nextLine();
            System.out.println("received: " + response);
        }
        System.out.println("done!");


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
