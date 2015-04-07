import java.util.Queue;
import java.util.LinkedList;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

public class ExistsPathDemo {
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

        /** TESTING ONLY */
        players.remove();
        Player p = players.peek();
        boolean ans = GameEngine.existsPath(p, board);
        System.out.println("round 1: " + ans);

        board.placeWall(board.getSquare("I-D"), board.getSquare("II-D"));
//         board.move(p, board.getSquare("I-D"));
        ans = GameEngine.existsPath(p, board);
        System.out.println("round 2: " + ans);

        /** END OF TESTING ONLY */


        // Start up the display
        GameBoardFrame frame = new GameBoardFrame(board, numPlayers);
        frame.update(board);
    }
}
