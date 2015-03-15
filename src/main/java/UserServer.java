import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.util.Arrays;

public class UserServer {
    public static boolean SERVER_DISPLAY = false;

    public static void usage(int error) {
        // display usage information then exit and return failure
        System.err.println("usage: java UserServer <port> [--display]");
        System.exit(error);
    }

    public static void main(String[] args) {
        if (args.length != 1 && args.length != 2) { 
            usage(1);
        }

        Deb.initialize("userserver");
        Deb.ug.println(Arrays.toString(args));
        if (args.length == 2) {
            Deb.ug.println("two args detected ");
            if (args[1].equals("-d") || args[1].equals("--display")) {
                Deb.ug.println("enabling display");
                SERVER_DISPLAY = true;
            }
        }

        // process command-line argument
        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (Exception e) {
            usage(2);
        }

        try {
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Accepting connections on " + portNumber);
            Scanner keyboard = new Scanner(System.in);
            Socket currClient;

            while ((currClient = server.accept()) != null) {
                ServerProtocol.init(currClient);
                System.out.println("Connection from " + currClient);

                String clientMessage;

                if (ServerProtocol.hasNextLine()) {
                    clientMessage = ServerProtocol.nextLine();
                } else {
                    System.out.println("expected message from client");
                    currClient = null;
                    break;
                }

                String [] parsey = clientMessage.split(" ");
                int numPlayers;
                if (parsey[0].equals("PLAYERS")) {
                    numPlayers = parsey.length - 1;
                } else {
                    System.out.println("expected PLAYERS from client");
                    currClient = null;
                    break;
                }
                Player [] players = new Player[numPlayers];
                Deb.ug.println("numPlayers: " + numPlayers);
                GameBoard board = new GameBoard();
                board.setupInitialPosition(players);
                Player currentPlayer = players[0];
                GameBoardFrame fserver = null;
                if (SERVER_DISPLAY) {
                    fserver = new GameBoardFrame(board);
                }

                while (ServerProtocol.hasNextLine()) {
                    clientMessage = ServerProtocol.nextLine();
                    System.out.println("received: " + clientMessage);
                    parsey = clientMessage.split(" ");
                    if (clientMessage.equals("GO?")) {
                        System.out.print(">> ");
                        String move = keyboard.nextLine();
                        System.out.println("move: " + move);
                        while (! GameEngine.validate(board, currentPlayer, move)) {
                            System.out.println("looks illegal; you sure?");
                            String confirm = keyboard.nextLine();
                            if (confirm.equals("y")) {
                                break;
                            }
                            System.out.print(">> ");
                            move = keyboard.nextLine();
                            System.out.println("move: " + move);
                        }
                        ServerProtocol.go(move);
                    } else if (parsey[0].equals("WENT")) {
                        Square destination = GameEngine.getSquare(board,
                                                                parsey[2]);
                        assert (currentPlayer != null);
                        board.move(currentPlayer, destination);
                        currentPlayer = 
                            GameEngine.nextPlayer(currentPlayer.getPlayerNo(), 
                                                  players);
                    } else if (parsey[0].equals("BOOT")) {
                        assert parsey[1].equals(currentPlayer.getName());
                        board.bootPlayer(currentPlayer);
                        players[currentPlayer.getPlayerNo()] = null;
                        currentPlayer = 
                            GameEngine.nextPlayer(currentPlayer.getPlayerNo(),
                                                  players);
                    } else if (parsey[0].equals("VICTOR")) {
                        System.out.println("somebody won!");
                    } else {
                        System.out.println( "unknown");
                    }
                    if (SERVER_DISPLAY) {
                        fserver.update(board);
                    }

                }

                System.out.println("Server closing connection from " + 
                                   currClient);
                ServerProtocol.closeStreams();
            }
            System.out.println( "game over");

        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(1);
        }
        System.out.println("done");
    }
}