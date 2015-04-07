/* 
 * 
 * show a board, good for debugging
 * implements the GameEngine and Messenger to create and run the game Quoridor
 */

import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

public class GameBoardFrameDemo {

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
    }
}
