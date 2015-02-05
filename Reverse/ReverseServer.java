import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;

/**
 * author: dan soucy
 * CIS 410, assignment 1
 * This program is a server to be connected to by a client.
 * The clint sends a string, that string is reversed and sent back.
 * This program determines what port to connect to by reading
 * an argument from the command-line. 
 */

/**
 * "Echo server" means that it listens for a connection (on a
 * user-specifiable port), reads in the input on the wire, modifies
 * the information, and writes the modified information back to the
 * client, echoing the input it was given.
 */
public class ReverseServer {
    /**
     * Processes the command-line parameters and then create and run
     * the ReverseServer object.
     *
     * @param args a <code>String</code> value
     */
    public static void main(String[] args) {
        if (args.length != 1) { // one required command-line argument
            usage(1);
        }

        // process command-line argument
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            usage(2);
        }

        run(port);
    }

    public static void usage(int error) {
        // display usage information then exit and return failure
        System.err.println("usage: java ReverseServer <port>");
        System.exit(error);
    }

    /**
     * Primary method of the server: Opens a listening socket on the
     * given port number (parameter)
     * It then loops forever, accepting connections from clients.
     * When a client connects, it is assumed to be sending messages, 
     * one per line. The server will process one client at a time.
     */
    public static void run(int portNumber) {
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
                    cout.println(reverse(clientMessage));
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

    public static String reverse(String original) {
        StringBuffer newString = new StringBuffer(original);
        return newString.reverse().toString();
    }
}
