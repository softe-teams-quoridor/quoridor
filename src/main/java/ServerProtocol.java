/*
 * protocol stuff used by the move server
 */
import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

public class ServerProtocol {
    /** display client stuff **/
    private static PrintStream outStream; // connection to the display client
    private static Scanner     inStream;  // connection to the display client

    public static void init(Socket sock) {
        try {
            inStream = new Scanner(sock.getInputStream());
            outStream = new PrintStream(sock.getOutputStream());
        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(2);
        }
    }

    public static void go(String move) {
        outStream.println("GO " + move);
    }

    /* tells display client that this server is ready to play */
    public static void move(String move) {
        outStream.println("MOVE");
    }

    public static boolean hasNextLine() {
        return inStream.hasNextLine();
    }

    public static String nextLine() {
        return inStream.nextLine();
    }

    public static void closeStreams() {
        outStream.close();
        inStream.close();
    }

}
