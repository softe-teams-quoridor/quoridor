import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.util.Arrays;
import java.util.*;

public class UserServer {
    private static boolean SERVER_DISPLAY = false;
    private static final Scanner keyboard = new Scanner(System.in);
    private static int portNumber;

    public static void usage(int error) {
        // display usage information then exit and return failure
        System.err.println("usage: java UserServer <port> [--display]");
        System.exit(error);
    }

    public static void main(String[] args) {
        // process command-line arguments
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (Exception e) { // NumberFormatException, ArrayIndexOutOfBounds
            usage(1);
        }

        Deb.initialize("userserver_" + portNumber);

        // process optional command-line --display argument 
        Deb.ug.println(Arrays.toString(args));
        if (args.length == 2) {
            Deb.ug.println("two args detected ");
            if (args[1].equals("-d") || args[1].equals("--display")) {
                Deb.ug.println("enabling display");
                SERVER_DISPLAY = true;
            }
        }

        ServerSocket server = null;
        Socket currClient = null;

        try {
            server = new ServerSocket(portNumber);
            System.out.println("Accepting connections on " + portNumber);
            Deb.ug.println("Accepting connections on " + portNumber);
            currClient = server.accept();
        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(2);
        }
        assert (server != null); 
        assert (currClient != null); 

        while (true) {
            playGame(currClient);
            try {
                Deb.ug.println("closing connection");
                currClient.close();
                System.out.println("Accepting connections on " + portNumber);
                Deb.ug.println("Accepting connections on " + portNumber);
                currClient = server.accept();
            } catch (IOException ioe) {
                // there was a standard input/output error
                ioe.printStackTrace();
                System.exit(3);
            }
        }
    }

    private static void playGame(Socket currClient) {
        ServerMessenger hermes = new ServerMessenger(currClient);
        System.out.println("Connection from " + currClient);
        Deb.ug.println("Connection from " + currClient);

        String clientMessage = null;

        if (! hermes.hasNextLine()) {
            System.out.println("expected message from client");
            Deb.ug.println("expected message from client");
            return;
        }
        clientMessage = hermes.nextLine();
        assert (clientMessage != null);

        String [] words = clientMessage.split(" ");
        int numPlayers = 0;
        if (words[0].equals("PLAYERS")) {
            numPlayers = words.length - 1;
            Deb.ug.println("numPlayers: " + numPlayers);
        } else {
            System.out.println("expected PLAYERS from client");
            Deb.ug.println("expected PLAYERS from client");
            return;
        }

        Queue<Player> players = new LinkedList<Player>();
        int wallsEach = 20 / numPlayers;
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, wallsEach));
        }
        GameBoard board = new GameBoard(players);
        Player currentPlayer = players.peek();
        GameBoardFrame frame = null;
        if (SERVER_DISPLAY) {
            frame = new GameBoardFrame(board);
        }

        /* handle different types of messages the client might send */
        while (hermes.hasNextLine()) {
            clientMessage = hermes.nextLine();
            System.out.println("received: " + clientMessage);
            Deb.ug.println("received: " + clientMessage);

            words = clientMessage.split(" ");
            
            String wall = null;
            // See if the client message contains a wall move
            if ( clientMessage.contains("(") && clientMessage.contains(")") ) {
                wall = clientMessage.substring(clientMessage.indexOf("("),
                                               clientMessage.indexOf(")")+1);
            }            

            // GO? --> get a move from the player
            if (clientMessage.contains("GO?")) {
                String move = getMove(board, currentPlayer);
                System.out.println("move: " + move);
                Deb.ug.println("sending: " + move);
                hermes.go(move);
            // WENT --> a player made a move, update internal board
            } else if (clientMessage.contains("WENT")) {
                assert (currentPlayer != null);
                Square[] destination;
                // if wall move, handle accordingly
                if ( wall != null ) {
                    destination = GameEngine.validate(board,currentPlayer,wall);
                    board.placeWall(destination[0], destination[1]);
                    currentPlayer.useWall();
                }
                // else it is a player move
                else {    
                    destination = GameEngine.validate(board,currentPlayer,words[2]);
                    board.move(currentPlayer, destination[0]);
                }
                // shuffle players
                players.add(players.remove());
                currentPlayer = players.peek();
            // BOOT --> current player is no longer player or has been kicked
            } else if (clientMessage.contains("BOOT")) {
                assert words[1].equals(currentPlayer.getName());
                board.removePlayer(currentPlayer);
                Deb.ug.println("currentPlayer.getName()" +
                               currentPlayer.getName());
                Deb.ug.println("currentPlayer.getPlayerNo()" + 
                                currentPlayer.getPlayerNo());
                players.remove();
                currentPlayer = players.peek();
            // VICTOR --> a player has won the game
            } else if (clientMessage.contains("VICTOR")) {
                System.out.println("somebody won!");
            // ??? --> who the heck knows what happend?
            } else {
                System.out.println("unknown message from client");
            }
            if (SERVER_DISPLAY) {
                frame.update(board);
            }
        }
        System.out.println("game over");
        Deb.ug.println("game over");
        System.out.println("Server closing connection from " + currClient);
        hermes.closeStreams();
    }

    private static String getMove(GameBoard b, Player p) {
        System.out.print(">> ");
        String move = keyboard.nextLine().trim();
        System.out.println("move: " + move);
        Square[] squares = GameEngine.validate(b, p, move);
        if(squares != null) {
            return move;
        }
        System.out.println("that looks illegal; are you sure?");
        String confirm = keyboard.nextLine();
        if (confirm.equals("y")) {
            return move;
        }
        return getMove(b, p);
    }
}
