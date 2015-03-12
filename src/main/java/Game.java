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


    // Constants
    private static final int TWO_PLAYER_WALLS = 10; 
    private static final int FOUR_PLAYER_WALLS = 5; 

    private static int numPlayers; // how many players are in the game

    private static PrintStream debug;

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
        if (args.length < 2) {
            usage(1);
        }

        Protocol.ports = new int[args.length/2];
        Protocol.hosts = new String[args.length/2];
        for (int i = 0; i < args.length; i++) { 
            Protocol.hosts[i/2] = args[i];
            i++;
            try {
                Protocol.ports[i/2] = Integer.parseInt(args[i]);
            } catch (Exception e) {
                usage(2);
            }
            numPlayers++;
        }
        debug.println("number of players: " + numPlayers);
        debug.println("hosts found: " + Arrays.toString(Protocol.hosts));
        debug.println("ports found: " + Arrays.toString(Protocol.ports));

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

        // initialize debug stream
        try {
            debug = new PrintStream("Game_debug");
        } catch (FileNotFoundException e) {
            debug = System.out;
        }

        // Connect to players
        debug.println("parsing arguments");
        parseArgs(args);

        // Instantiate GameBoard
        debug.println("instantiating GameBoard");
        GameBoard board = new GameBoard();

        // Instantiate Players array
        debug.println("instantiating Players array");
        Player[] players = new Player[args.length/2];

        // ***HARDCODE TEST***
        // build a player and place him on player 1's spot
//         numPlayers = 1;

        if(numPlayers == 2) {
            players[0] = new Player("player 1",board.getSquare(4,0),TWO_PLAYER_WALLS);
            board.addPlayer(players[0],4,0);
            players[1] = new Player("player 2",board.getSquare(4,8),TWO_PLAYER_WALLS);
            board.addPlayer(players[1],4,8);
            

        // tell all move servers who the players are
        Protocol.broadcastPlayers(players);

        // Start up the display
        debug.println("starting GameboardFrame");
        GameboardFrame f = new GameboardFrame(board);

        // Initialize current player to player 1 (index 0)
        int currentPlayer = 0;


        boolean victory = false;

        // ***FIXME***
        // loop will need to check for a victory condition
        debug.println("beginning main loop");
        while (!victory) {

            // Get move from player
            debug.println("requesting move from player: " + currentPlayer);
            String response = Protocol.requestMove(currentPlayer);
            debug.println("received: " + response);

            // make sure the response can reasonably represent a move
            boolean correctFormat = GameEngine.parseMove(board,response);
            if (! correctFormat) {
                // FIXME: boot the player
                debug.println("incorrect format");
                continue;
            }

            // Parse the move string to a square location
            Square moveTo = GameEngine.getSquare(board,response);

            //***FIXME***
            // Validate move
            boolean legal = 
                GameEngine.validate(board, players[currentPlayer], response);


            

            // update board & broadcast move to other players

            if ( legal ) {
                // Move is valid, make the move!
                board.move(players[currentPlayer], moveTo);
                Protocol.broadcastWent(players[currentPlayer], response);

                // Update the board with our new moves
                f.update(board);
            }
            else {
                // FIXME: if illegal, boot player & broadcast boot to other players
                System.out.println("HERE");
                board.removePlayer(players[currentPlayer].getX(),players[currentPlayer].getY());
                f.update(board);
                Protocol.broadcastBoot(currentPlayer);
                debug.println("illegal move attempted");
                continue;
            }
            
            // if player has won, set victory to true

            // get next player's turn 
            // - make sure turn does not index a booted player
            // - turn = turn + 1 % players.length;

            // Check for victory
            victory = GameEngine.checkVictory(board,players);

            currentPlayer = (currentPlayer + 1) % numPlayers;

        }


        
    }
}
