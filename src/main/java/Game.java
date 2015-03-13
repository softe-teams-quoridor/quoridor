/* 
   this class tracks the game's state. 
 */

import java.util.*;
import java.io.*;

// networking stuff... 
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Game {
    private static int numPlayers; // how many players are in the game
    private static Player [] players ; // the players

//     private static PrintStream Deb.ug;

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
     * populates the inStreams and outStreams arrays in Protocol
     * counts the number of players, stores it in numPlayers
     */
    public static void parseArgs (String[] args) {
        
        // ********* FIX ME *********************
        // Cannot start a a two player game with this in
        // need 4 arguments for a two player game
        if (args.length !=  4 && args.length != 8) {
            usage(1);
        }

        Protocol.ports = new int[args.length/2];
        Protocol.hosts = new String[args.length/2];

        //reconsider
        for (int i = 0; i < args.length; i++) { 
            Protocol.hosts[i/2] = args[i];
            i++;
            try {
                Protocol.ports[i/2] = Integer.parseInt(args[i]);
            } catch (Exception e) {
                usage(2);
            }
           
        }

        // Set the number of players
        numPlayers = args.length/2;
        
        Deb.ug.println("number of players: " + numPlayers);
        Deb.ug.println("hosts found: " + Arrays.toString(Protocol.hosts));
        Deb.ug.println("ports found: " + Arrays.toString(Protocol.ports));

        // Connect to players
        Protocol.outStreams = new PrintStream [Protocol.ports.length];
        Protocol.inStreams  = new Scanner     [Protocol.ports.length];
//         Protocol.inStreams = inStreams;
//         Protocol.outStreams = outStreams;
        for (int i = 0; i < Protocol.ports.length; i++) {
            try {
                Socket socket = new Socket(Protocol.hosts[i], Protocol.ports[i]);
                Protocol.outStreams[i] = new PrintStream(socket.getOutputStream());
                Protocol.inStreams[i] = new Scanner(socket.getInputStream());
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
        for (int i = 0; i < Protocol.ports.length; i++) {
            if (Protocol.outStreams[i] == null) {
                System.out.println("outStreams[" + i + "] is null!");
            }
            if (Protocol.inStreams[i] == null) {
                System.out.println("inStreams[" + i + "] is null!");
            }
        }
    }

    public static void main (String[] args) {
        Deb.initialize();

//         // initialize debug stream
//         try {
//             debug = new PrintStream("Game_debug");
//         } catch (FileNotFoundException e) {
//             debug = System.err;
//         }
// 
        // Connect to players
        Deb.ug.println("parsing arguments");
        System.out.println("args provided: " + Arrays.toString(args));
        parseArgs(args);

        // Instantiate GameBoard
        Deb.ug.println("instantiating GameBoard");
        GameBoard board = new GameBoard();

        // Instantiate Players array
        Deb.ug.println("instantiating Players array");
//         Player[] players = new Player[args.length/2];
        players = new Player[args.length/2];
        assert (players.length == numPlayers);

        board.setupInitialPosition(players);
        // tell all move servers who the players are
        Protocol.broadcastPlayers(players);

        // Start up the display
        Deb.ug.println("starting GameboardFrame");
        GameboardFrame f = new GameboardFrame(board);

        // Initialize current player to player 0 (index 0)
//         int currentPlayer = 0;
        Player currentPlayer = players[0];

        // ***FIXME***
        // loop will need to check for a victory condition
        Deb.ug.println("beginning main loop");
        while (true) {
            // Get move from player
            Deb.ug.println("requesting move from player: " + currentPlayer);
//             String response = Protocol.requestMove(players[currentPlayer]);
            String response = Protocol.requestMove(currentPlayer);
            Deb.ug.println("received: " + response);

            // *** NOTE: the following validation is also taking place in
            //              GameEngine.validate. Consider one or the other
            // make sure the response can reasonably represent a move
            /*
            boolean correctFormat = GameEngine.parseMove(board,response);
            if (! correctFormat) {
                Deb.ug.println("incorrect format");
                board.bootPlayer(currentPlayer);
                Protocol.broadcastBoot(currentPlayer);
                players[currentPlayer.getPlayerNo()] = null;
                f.update(board);
                if (GameEngine.checkVictory(board,players)) {
                    break;
                }
                currentPlayer = nextPlayer(currentPlayer.getPlayerNo());
                continue;
            }

            */
            // Validate move
            boolean legal = GameEngine.validate(board, currentPlayer, 
                                                response);

            if (!legal) {
                // if illegal, boot player & broadcast boot to other players
                Deb.ug.println("illegal move attempted");
                board.bootPlayer(currentPlayer);
                Protocol.broadcastBoot(currentPlayer);
                players[currentPlayer.getPlayerNo()] = null;
            } else {
                // if the move is legal...
                // move player on board, broadcast move
                // Parse the move string to a square location
                Square moveTo = GameEngine.getSquare(board,response);
                board.move(currentPlayer, moveTo);
                Protocol.broadcastWent(currentPlayer, response);
            }
            f.update(board);

            // Check for victory
            if (GameEngine.checkVictory(board,players)) {
                break;
            }

            // get next player's turn 
            currentPlayer = nextPlayer(currentPlayer.getPlayerNo());
        }
        Protocol.broadcastVictor(currentPlayer);
        // maybe sleep here?
        System.exit(0);
    }

    public static Player nextPlayer(int current) {
        current = (current + 1) % numPlayers;
        if (players[current] != null) {
            return players[current];
        }
        return nextPlayer(current);
    }

}
