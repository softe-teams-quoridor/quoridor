import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.io.*;


public class ReachableAdjacentSquaresDemo {
    private static int numPlayers;     // how many players are in the game
    private static final int WALL_POOL = 20; // total collection of walls
    private static final Queue<Player> players = new LinkedList<Player>();

    public static void main (String[] args) {
        // Set the number of players
        numPlayers = 2;

        // Instantiate Players
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, WALL_POOL / numPlayers));
        }

        // Instantiate GameBoard
        GameBoard board = new GameBoard(players);

        // Start up the display
        GameBoardFrame frame = new GameBoardFrame(board, numPlayers);
        frame.update(board);

        /** TESTING ONLY */
        players.remove();
        Player p = players.peek();

        Square squa = board.getPlayerLoc(p);
        Square [] sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { }

        board.placeWall(board.getSquare("IV-D"), board.getSquare("V-D"));
        board.move(p, board.getSquare("VIII-F"));
        squa = board.getPlayerLoc(p);
        sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { } 

        board.move(p, board.getSquare("V-D"));
        squa = board.getPlayerLoc(p);
        sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { } 

        board.move(p, board.getSquare("IV-E"));
        squa = board.getPlayerLoc(p);
        sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { } 

        board.placeWall(board.getSquare("IV-E"), board.getSquare("IV-F"));
        sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { } 

        board.placeWall(board.getSquare("III-E"), board.getSquare("IV-E"));
        sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { } 

        board.placeWall(board.getSquare("III-D"), board.getSquare("III-E"));
        sqs = GameEngine.reachableAdjacentSquares(board, squa);
        frame.update(board, sqs);
        try { Thread.sleep(1000); } catch (Exception e) { } 

//         System.out.println(Arrays.toString(sqs));
        /** END OF TESTING ONLY */
    }
}
