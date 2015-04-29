/** 
 * display client stuff
 */

import java.io.IOException;
import java.io.PrintStream;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientMessenger {
    private PrintStream [] outStreams; // these should both have the
    private Scanner     [] inStreams;  // same length as players
    private int        [] ports;
    private String     [] hosts; 

    /*
     * constructor
     * parses command-line arguments
     * populates the hosts, ports, inStreams, outStreams arrays
     */
    public ClientMessenger(String [] args) {
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
//                 uhe.printStackTrace();
                System.err.println("unknown host: " + hosts[i]);
                System.exit(1);
            } catch (IOException ioe) {
                // there was a standard input/output error (lower-level)
                // probably due to no moveserver waiting on this host/port pair
                System.err.println("no MoveServer found on " + hosts[i] + 
                                   " on port " + ports[i]);
//                 ioe.printStackTrace();
                System.exit(1);
            }
        }


        // test all connections -- not necessary, just for debugging
        for (int i = 0; i < numPlayers; i++) {
            assert (outStreams[i] != null);
            assert (inStreams[i] != null);
        }

    }

    public String [] getNames() {
        Deb.ug.println("S");
        String [] result = new String[inStreams.length];
        for (int i = 0; i < inStreams.length; i++) {
            Deb.ug.println(i+ "is i");
            if (! inStreams[i].hasNextLine()) {
                // the connection has been closed! we should boot them...
                result[i] = null;
                continue;
            } 
            String line = inStreams[i].nextLine();
            if (! line.startsWith("MOVE-SERVER ")) {
                // non conformant, again, we should boot them...
                result[i] = null;
                continue;
            }
            result[i] = line.substring(12);
        }
        Deb.ug.println("Hi");
        return result;
    }


    /** gets MOVE message from all move servers
     * MOVE is the message a server sends to indicate that it is ready to play
     * blocks until receiving all messages
     */ 
//     public void ready() {
    public boolean [] ready() {
        boolean [] result = new boolean [inStreams.length];
        for (int i = 0; i < inStreams.length; i++) {
            result [i] = false;
            if (inStreams[i] == null) {
                Deb.ug.println("why is inStreams[" + i + "] null?");
                /* player i should be booted, if they haven't already */
                continue;
            } else if (!inStreams[i].hasNextLine()) {
                Deb.ug.println("inStreams[" + i + "] does not have nextline");
                closeStreams(i);
                /* player i should be booted, if they haven't already */
                continue;
            }
            if (inStreams[i].nextLine().equals("MOVE")) {
                result[i] = true;
                Deb.ug.println("received confirmation from player " + i);
            }
            else {
                Deb.ug.println("player " + i + " is not ready to play! :(");
                closeStreams(i);
            }
            /* FIXME player i is noncompliant and needs be booted */
            continue;
        }
        return result;
    }

    /** sends PLAYERS message to all servers to inform them the 
     * names and order of the players
     * @param players the queue of players
     */ 
    public void broadcastPlayers(Queue<Player> players) {
        assert (players.size() == outStreams.length);
        for (Player p : players) {
            int i = p.getPlayerNo();
            if (outStreams[i] == null) {
                continue;
            }
            outStreams[i].print("PLAYERS ");
            for (Player playa : players) {
                outStreams[i].print(playa.getName() + " ");
            }
            outStreams[i].println();
        }
    }

    public String requestMove(Player player) {
        
        if (outStreams[player.getPlayerNo()] == null) {
            return "B-O-O-T-M-E"; // no connection to the server!
        }
        

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
        assert (outStreams[playerNo] != null);
        assert (inStreams [playerNo] != null);
        outStreams[playerNo].close();
        outStreams[playerNo] = null;
        inStreams[playerNo].close();
        inStreams[playerNo] = null;
    }

    public void closeAllStreams(Queue<Player> players) {
        for (Player p = players.remove(); 
             ! players.isEmpty(); p = players.remove()) {
            assert (p != null);
            closeStreams(p.getPlayerNo());
        }
    }
}
