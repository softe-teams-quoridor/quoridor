/*
 * protocol stuff used by the move server
 */
import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

public class ServerMessenger {
    /** display client stuff **/
    private PrintStream outStream; // connection to the display client
    private Scanner     inStream;  // connection to the display client

    public ServerMessenger(Socket sock) {
        try {
            this.inStream = new Scanner(sock.getInputStream());
            this.outStream = new PrintStream(sock.getOutputStream());
        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(2);
        }
    }

    public void go(String move) {
        outStream.println("GO " + move);
    }

    /* tells display client that this server is ready to play */
    public void move(String move) {
        outStream.println("MOVE");
    }

    public boolean hasNextLine() {
        return inStream.hasNextLine();
    }

    public String nextLine() {
        return inStream.nextLine();
    }

    public void closeStreams() {
        outStream.close();
        inStream.close();
    }
}
