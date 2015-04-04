/** 
 * display client stuff
 */

import java.io.IOException;
import java.io.PrintStream;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Arrays;
import java.util.Scanner;

public class Messenger {
    public PrintStream [] outStreams; // these should both have the
    public Scanner     [] inStreams;  // same length as players
    private int        [] ports;
    private String     [] hosts; 

    /*
     * constructor
     * parses command-line arguments
     * populates the hosts, ports, inStreams, outStreams arrays
     */
    public Messenger(String [] args) {
        int numPlayers = args.length/2;
        this.ports = new int[numPlayers];
        this.hosts = new String[numPlayers];
        Deb.ug.println("args: " + Arrays.toString(args));

        //reconsider
        for (int i = 0; i < args.length; i++) { 
            hosts[i/2] = args[i];
            i++;
            try {
                ports[i/2] = Integer.parseInt(args[i]);
            } catch (Exception e) {
                Game.usage(2);
            }
        }
        Deb.ug.println("hosts found: " + Arrays.toString(hosts));
        Deb.ug.println("ports found: " + Arrays.toString(ports));

        this.outStreams = new PrintStream [numPlayers];
        this.inStreams  = new Scanner     [numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            try {
                Socket socket = new Socket(hosts[i], ports[i]);
                outStreams[i] = new PrintStream(socket.getOutputStream());
                inStreams[i] = new Scanner(socket.getInputStream());
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

        // test all connections -- not necessary, just for debugging
        for (int i = 0; i < numPlayers; i++) {
            assert (outStreams[i] != null);
            assert (inStreams[i] != null);
        }
    }

    public String requestMove(Player player) {
        outStreams[player.getPlayerNo()].println("GO?");
        if (! inStreams[player.getPlayerNo()].hasNextLine()) {
            return "B-O-O-T-M-E"; // no response from the server!
        }
        String response = inStreams[player.getPlayerNo()].nextLine();
        Deb.ug.println("requestMove saw: " + response);
        if (! response.startsWith("GO ")) {
            return "B-O-O-T-M-E"; // response must begin with GO
        }
        return response.substring(3).trim();
    }

    public void broadcastPlayers(Player [] players) {
        assert (players.length == outStreams.length);
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[i].print("PLAYERS ");
                for (Player playa : players) {
                    outStreams[i].print(playa.getName() + " ");
                }
                outStreams[i].println();
            }
        }
    }

    public void broadcastWent(Player player, String move) {
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[i].println("WENT " + player.getName() + " " + move);
            }
        }
    }

    public void broadcastBoot(Player player) {
        Deb.ug.println("booting player: " + player.getName());
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[i].println("BOOT " + player.getName());
            }
        }
        closeStreams(player.getPlayerNo());
    }

    public void broadcastVictor(Player player) {
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[player.getPlayerNo()].println("VICTOR " 
                                                         + player.getName());
            }
        }
    }

    private void closeStreams(int playerNo) {
        outStreams[playerNo].close();
        outStreams[playerNo] = null;
        inStreams[playerNo].close();
        inStreams[playerNo] = null;
    }

    public void closeAllStreams(Player [] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                closeStreams(i);
            }
        }
    }
}
