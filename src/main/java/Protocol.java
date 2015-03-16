/*
 *
 *
 */
import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

public class Protocol {
    /** display client stuff **/
    public static final String BOOT = "BOOT";
    public static final String GO_Q = "GO?";

    public static PrintStream [] outStreams; // these should both have the
    public static Scanner     [] inStreams;  // same length as players
    public static int [] ports;
    public static String [] hosts; 

    public static String requestMove(int playerNo) {
        outStreams[playerNo].println(GO_Q);
        String response = inStreams[playerNo].nextLine();
        if (! response.startsWith("GO ")) {
            return null;
        }
        Deb.ug.println("requestMove returning: " + response.substring(3));
        return response.substring(3);
    }

    public static String requestMove(Player player) {
        outStreams[player.getPlayerNo()].println(GO_Q);
        if (! inStreams[player.getPlayerNo()].hasNextLine()) {
            return "B-O-O-T-M-E"; // no response from the server!
        }
        String response = inStreams[player.getPlayerNo()].nextLine();
        Deb.ug.println("requestMove saw: " + response);
        if (! response.startsWith("GO ")) {
            return "B-O-O-T-M-E"; // response must begin with GO
        }
        return response.substring(3).trim();
//         return response.substring(3);
    }

    public static void broadcastPlayers(Player [] players) {
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

    public static void broadcastWent(Player player, String move) {
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[i].println("WENT " + player.getName() + " " + move);
            }
        }
    }

//     public static void broadcastBoot(int playerNo) {
    public static void broadcastBoot(Player player) {
        Deb.ug.println("booting player" + player.getName());
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[i].println(BOOT + " " + player.getName());
            }
        }
        closeStreams(player.getPlayerNo());
    }

    public static void broadcastVictor(Player player) {
        for (int i = 0; i < outStreams.length; i++) {
            if (outStreams[i] != null) {
                outStreams[player.getPlayerNo()].println("VICTOR " 
                                                         + player.getName());
            }
        }
    }

    private static void closeStreams(int playerNo) {
        outStreams[playerNo].close();
        outStreams[playerNo] = null;
        inStreams[playerNo].close();
        inStreams[playerNo] = null;
    }

    public static void closeAllStreams(Player [] players) {
//         for (Player p : players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                closeStreams(i);
            }
        }
    }
}
