/*
 * i'm not currently using this at all LOL
 *
 */
import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

/**
 *
 */

public class Protocol {
    /** Commands to quit */
    public final static String COMMAND_BYE = "/quit";
    /** prompt for input */
    public final static String PROMPT = ">> ";

    public static PrintStream [] outStreams; // these should both have the
    public static Scanner     [] inStreams;  // same length as players
    public static int [] ports;
    public static String [] hosts; 

    public static void broadcastMove(int playerNo, String move) {
        for (int i = 0; i < outStreams.length; i++) {
            outStreams[i].println("player " + playerNo + 
                                  " made move: " + move);
        }
    }

    /**
     * Primary method of the server: Opens a listening socket on the
     * given port number (parameter)
     * It then loops forever, accepting connections from clients.
     * When a client connects, it is assumed to be sending messages, 
     * one per line. The server will process one client at a time.
     */
    public static void startServer(int portNumber) {
        try {
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Accepting connections on " + portNumber);
            Socket currClient;

            while ((currClient = server.accept()) != null) {
                System.out.println("Connection from " + currClient);

                Scanner cin = new Scanner(currClient.getInputStream());
                PrintStream cout = 
                    new PrintStream(currClient.getOutputStream());

                String clientMessage;

                while (cin.hasNextLine()) {
                    clientMessage = cin.nextLine();
                    System.out.println("received: " + clientMessage);
                    cout.println(clientMessage);
                }

                System.out.println("Server closing connection from " + 
                                   currClient);
                cout.close();
                cin.close();
            }
        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(1);
        }
        System.out.println("ReverseServer terminates.");
    }

    public static void connectToClient(String machineName, int portNumber) {
        try {
            Socket socket = new Socket(machineName, portNumber);
            PrintStream sout = new PrintStream(socket.getOutputStream());
            Scanner sin = new Scanner(socket.getInputStream());
            Scanner keyboard = new Scanner(System.in);

            System.out.print(PROMPT);

            for ( String serverResponse, msg; keyboard.hasNextLine(); 
                  System.out.print(PROMPT) ) {
                msg = keyboard.nextLine();

                if (msg.equals(COMMAND_BYE)) { 
                    break;
                }
                sout.println(msg);
                serverResponse = sin.nextLine();
                System.out.println(serverResponse);
            }
            sout.close();
            sin.close();
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
}
