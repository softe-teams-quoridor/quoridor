/*
 * protocol stuff used by the move server
 */
import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;
import java.util.Arrays;

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
            System.exit(8);
        }
    }

    /* tells the display client what our ROCKIN name is
    * @param a string that we send to identify us
     */
    public void identify(String name) {
        outStream.println("MOVE-SERVER " + name);
    }

    public void go(String move) {
        outStream.println("GO " + move);
    }

    /* tells display client that this server is ready to play */
    public void ready() {
        outStream.println("MOVE");
    }

    public boolean hasNextLine() {
        return inStream.hasNextLine();
    }

    public String nextLine() {
        return inStream.nextLine();
    }

    public String [] players() {
        if (! inStream.hasNextLine()) {
            return null;
        }
        String clientMessage = inStream.nextLine();
        assert (clientMessage != null);

        String [] words = clientMessage.split(" ");

        if (! words[0].equals("PLAYERS")) {
            Deb.ug.println("expected PLAYERS from client, got: "
                           + clientMessage);
            return null;
        }

//         int numPlayers = words.length - 1;
//         Deb.ug.println("numPlayers: " + numPlayers);
//         String [] players = new String[words.length-1];
        String [] players = Arrays.copyOfRange(words, 1, words.length);
        return players;
    }

    public void closeStreams() {
        outStream.close();
        inStream.close();
    }
}
