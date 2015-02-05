import java.net.Socket;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.PrintStream;

import java.util.Scanner;

/**
 * author: dan soucy
 * CIS 410, assignment 1
 * This program is a client to connect to a server. Prompt user for input, 
 * send that input to the server, output the response. This
 * program determines what machine and port to connect to by reading
 * arguments from the command-line. 
 */

public class ReverseClient {
    /** Commands to quit */
    public final static String COMMAND_BYE = "/quit";
    /** prompt for input */
    public final static String PROMPT = ">> ";

    /**
     * Processes the command-line parameters and then calls run()
     *
     * @param args array of String; the command-line parameters.
     */
    public static void main(String[] args) {
        if (args.length != 2) { // must have two args
            usage(1);
        }

        // parse invocation arguments
        String machine = args[0];
        int port = 0;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            usage(2);
        }

        run(machine, port); 
    }

    /**
     * Client program opens a socket to the server machine:port
     * pair. It then sends the message "Hello", reads the line the
     * server is expected to respond with, and then sends
     * "Goodbye". After sending the final message it closes the socket
     * stream.
     */
    public static void run(String machineName, int portNumber) {
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

    /**
     * Print the usage message for the program on standard error stream.
     */
    private static void usage(int error) {
        // show invocation instructions and exit returning unique failure code
        System.err.println("usage: java ReverseClient <host> <port>");
        System.exit(error);
    }
}
