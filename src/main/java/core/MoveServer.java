/** MoveServer.java
  * ________________________________________________________
  * 
  *  This is the top level class which is used for all moveServers, AI and User
  *    each 
  *
  *
  */




import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

import java.lang.Runtime;

public class MoveServer {
    private static boolean SERVER_DISPLAY = false;
    private static QuoridorAI ai = null;
    private static int portNumber;

    // statistics
    protected static int WIN_RECORD = 0;
    protected static int LOSE_RECORD = 0;

    public static void usage(int error) {
        // display usage information then exit and return failure
        System.err.println("usage: java MoveServer <port> <mode> [--display]");
        System.exit(error);
    }

    public static void parseArgs(String[] args) {
        // process command-line arguments
        if (args.length < 2) {
            usage(1);
        }
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (Exception e) { // NumberFormatException, ArrayIndexOutOfBounds
            usage(2);
        }

        // process AI mode
        if (args[1].equals("user")) {
            ai = new AI_AskUser();
        } else if (args[1].equals("lr")) {
            ai = new AI_LeftRight();
        } else if (args[1].equals("rd")) {
            ai = new AI_RollDice();
        } else if (args[1].equals("fcrd")) {
            ai = new AI_FlipCoinRollDice();
        } else if (args[1].equals("idk")) {
            ai = new AI_IDK();
        } else if (args[1].equals("rip")) {
            ai = new AI_Ripley(); //<--- currently does nothing :(
        } else if (args[1].equals("maybe")) {
            ai = new AI_Maybe();
        } else {
             usage(3);
        }
        assert (ai != null);

        Deb.initialize("moveserver_" + args[1] +  "_" + portNumber);
        Deb.ug.println("args: " + Arrays.toString(args));

        // process optional command-line --display argument 
        if (args.length == 3) {
            Deb.ug.println("three args detected");
            if (args[2].equals("-d") || args[2].equals("--display")) {
                Deb.ug.println("enabling display");
                SERVER_DISPLAY = true;
            }
        }
    }

    public static void main(String[] args) {
        // parseArgs and initialize debug
        parseArgs(args);

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
            System.exit(9);
        }
        assert (server != null); 
        assert (currClient != null); 

        Runtime.getRuntime().addShutdownHook(new PrintStats());

        while (true) {
            playGame(currClient);
            ai.reset();
            try {
                Deb.ug.println("closing connection");
                currClient.close();
                System.out.println("Accepting connections on " + portNumber);
                Deb.ug.println("Accepting connections on " + portNumber);
                currClient = server.accept();
            } catch (IOException ioe) {
                // there was a standard input/output error
                ioe.printStackTrace();
                System.exit(10);
            }
        }
    }

    private static void playGame(Socket currClient) {
        ServerMessenger hermes = new ServerMessenger(currClient);
        System.out.println("Connection from " + currClient);
        Deb.ug.println("Connection from " + currClient);

        String playerName = ai.toString();

        hermes.identify("teams_" + portNumber + playerName);

        String [] playerNames = hermes.players();
        if (playerNames == null) {
            System.out.println("expected PLAYERS from client");
            return;
        }
        int numPlayers = playerNames.length;
        Deb.ug.println("numPlayers: " + numPlayers);

        Queue<Player> players = new LinkedList<Player>();
        int wallsEach = 20 / numPlayers;
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, playerNames[i], wallsEach));
        }
        GameBoard board = new GameBoard(players);
        Player currentPlayer = players.peek();
        GameBoardFrame frame = null;
        if (SERVER_DISPLAY) {
            frame = new GameBoardFrame(board, players, playerName);
        }

        String clientMessage;
        String [] words;

        hermes.ready();
        /* handle different types of messages the client might send */
        while (hermes.hasNextLine()) {
            System.out.println("currentPlayer: " + currentPlayer);
            clientMessage = hermes.nextLine().trim();
            System.out.println("received: " + clientMessage);
            Deb.ug.println("received: " + clientMessage);

            words = clientMessage.split(" ");
            Deb.ug.println("words: " + Arrays.toString(words));

            // GO? --> get a move from this server
            if (clientMessage.startsWith("GO?")) {
                String move = ai.getMove(board, currentPlayer);
                System.out.println("move: " + move);
                Deb.ug.println("sending: " + move);
                hermes.go(move);
                // don't update frame or shuffle players here, because the 
                // client is about to send us a WENT with our move and we'll 
                // take care of that stuff then
                continue;

            // WENT --> a player made a move, update internal board
            } else if (clientMessage.startsWith("WENT")) {
                assert (currentPlayer != null);
                // this assertion fails when the display client boots
                // players at the beginning of the game for a bad name.
                // you know, like a name that has `fuck' in it or something.
                // in other cases this assertion should hold...
                // can we keep it somehow?
//                 assert (currentPlayer.toString().equals(words[1]));

                // move is a string like "V-A" or "(V-A, V-B)"
                // add 6 to the player's name's length to compensate for
                // "WENT" plus 2 spaces
                String move = clientMessage.substring(6 + words[1].length());
                GameEngine.playTurn(move, currentPlayer, board);

                // shuffle players
                //players.add(players.remove());
                players = board.getNextTurn(players);
                currentPlayer = players.peek();

            // BOOT --> current player is no longer player or has been kicked
            } else if (clientMessage.startsWith("BOOT")) {
                Deb.ug.println("currPlayer name " + currentPlayer);
                Deb.ug.println("currPlayer no " + currentPlayer.getPlayerNo());
                // this assertion fails when the display client boots
                // players at the beginning of the game for a bad name.
                // you know, like a name that has `fuck' in it or something.
                // in other cases this assertion should hold...
                // can we keep it somehow?
//                 assert words[1].equals(currentPlayer);
                board.removePlayer(currentPlayer);
                players.remove();
                currentPlayer = players.peek();

            // VICTOR --> a player has won the game
            } else if (clientMessage.startsWith("VICTOR")) {
                System.out.println(words[1] + " won!");
                if (words[1].equals(playerName)) {
                    Deb.ug.println("i won");
                    WIN_RECORD++;
                } else {
                    Deb.ug.println("i lost :(");
                    LOSE_RECORD++;
                }
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
        if (SERVER_DISPLAY) {
            // close the display
            Deb.ug.println("closing display");
        	frame.closeWindow();
        }
    }
}

/* this class exists to print win/loss stats when the server terminates */
class PrintStats extends Thread {
    public void run() {
        int wins = MoveServer.WIN_RECORD;
        int losses = MoveServer.LOSE_RECORD;

        System.out.println("Games won: " + wins); 
        System.out.println("Games lost: " + losses); 
        System.out.println("Total games played: " + (wins+losses));
    }
}
