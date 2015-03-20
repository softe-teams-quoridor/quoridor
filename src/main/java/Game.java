/* Game.java (aka Quoridor) - CIS 405 - teams
 * Last Edit: March 14, 2015
 * ____________________________________________________________________________
 * 
 * implements the GameEngine and Messenger to create and run the game Quoridor
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
    private static int numPlayers;     // how many players are in the game
    private static Player [] players ; // the players

    /*
     * prints a friendly message and exits
     * @param an int to return to the OS
     */
    public static void usage(int error) {
        System.err.println("usage: java Game <host> <port> <host> <port> " + 
                           "[<host> <port> <host> <port>]");
        System.exit(error);
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
        Messenger hermes = new Messenger(args);

        // Instantiate Players array
        Deb.ug.println("instantiating Players array");
        players = new Player[numPlayers];
        int wallsEach = 20 / players.length;
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i, wallsEach);
        }

        // Instantiate GameBoard
        Deb.ug.println("instantiating GameBoard");
        GameBoard board = new GameBoard(players);
        Deb.ug.println("players array: " + Arrays.toString(players));

        // tell all move servers who the players are
        hermes.broadcastPlayers(players);

        // Start up the display
        Deb.ug.println("starting GameBoardFrame");
        GameBoardFrame frame = new GameBoardFrame(board);

        // Initialize current player to player 0 (index 0)
        Player currentPlayer = players[0];

        // loop will need to check for a victory condition
        Deb.ug.println("beginning main loop");
        while (true) {
            // Get move from player
            Deb.ug.println("requesting move from player: " + 
                           currentPlayer.getName());
            String response = hermes.requestMove(currentPlayer);
            Deb.ug.println("received: " + response);

            // Validate move
            boolean legal = GameEngine.validate(board, currentPlayer, 
                                                response);

            if (legal) {
                Deb.ug.println("move legal");
                // Parse the move string to a square location
                Square destination = GameEngine.getSquare(board,response);
                // move player on board, broadcast move
                board.move(currentPlayer, destination);
                hermes.broadcastWent(currentPlayer, response);
            } else {
                // if illegal, remove player & broadcast boot to other players
                Deb.ug.println("illegal move attempted");
                board.removePlayer(currentPlayer);
                hermes.broadcastBoot(currentPlayer);
                players[currentPlayer.getPlayerNo()] = null;
            }
        
            // Update the graphical board
            frame.update(board);

            // Retrieve a possibly winning player
            Player winner = GameEngine.getWinner(board, players);
            if (winner != null) {
                // If the retrieved player is a winner, display and exit loop
                hermes.broadcastVictor(winner);
                break;
            }

            //...the game is still going, get the next player and continue
            
            // Get next player's turn 
            currentPlayer = GameEngine.nextPlayer(currentPlayer.getPlayerNo(), players);
 
            // Sleepy time
            sleep(200);
        }

        hermes.closeAllStreams(players);
        // pause board for two seconds before ending
        sleep(2000);
        System.exit(0);
    }

    private static void sleep(int length) {
        try {
            Thread.sleep(length);
        } catch (InterruptedException e) {
            // ignore it
        }
    }
}
