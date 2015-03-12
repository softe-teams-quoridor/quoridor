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

    public static void broadcastPlayers(Player [] players) {
        assert (players.length == outStreams.length);
        for (int i = 0; i < outStreams.length; i++) {
            outStreams[i].print("PLAYERS ");
            for (Player playa : players) {
                outStreams[i].print(playa.getName() + " ");
            }
            outStreams[i].println();
        }
    }

    public static void broadcastWent(Player player, String move) {
        for (int i = 0; i < outStreams.length; i++) {
            outStreams[i].println("WENT " + player.getName() + " " + move);
        }
    }

    public static String requestMove(int playerNo) {
        outStreams[playerNo].println("GO?");
        return inStreams[playerNo].nextLine();
    }

    public static void broadcastBoot(int playerNo) {
        for (int i = 0; i < outStreams.length; i++) {
            outStreams[i].println("BOOT player " + playerNo);
        }
    }

    public static void broadcastVictor(Player player, int playerNo) {
        for (int i = 0; i < outStreams.length; i++) {
            outStreams[playerNo].println("VICTOR " + player.getName());
        }
    }
}
