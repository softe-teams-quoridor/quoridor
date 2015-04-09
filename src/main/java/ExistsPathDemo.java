import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

public class ExistsPathDemo {
    private static int numPlayers;     // how many players are in the game
    private static final int WALL_POOL = 20; // total collection of walls
    private static final Queue<Player> players = new LinkedList<Player>();
    private static GameBoardFrame frame;

    private static Square [] allVisitedSquares = new Square[81];
    private static int allVisitedSquarescount = 0;

    public static void main (String[] args) {
        // Set the number of players
        numPlayers = 4;

        // Instantiate Players
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, WALL_POOL / numPlayers));
        }

        // Instantiate GameBoard
        GameBoard board = new GameBoard(players);

        // Start up the display
        frame = new GameBoardFrame(board, players);
        frame.update(board);

        /** TESTING ONLY */
        Player p = players.peek();

        boolean ans = existsPath(p, board);
        System.out.println("round 1: " + ans);

        resetAllVisitedSquares();
        board.move(p, board.getSquare("VIII-D"));
        ans = existsPath(p, board);
        System.out.println("round 2: " + ans);

        resetAllVisitedSquares();
        board.placeWall(board.getSquare("IV-E"), board.getSquare("V-E"));
        ans = existsPath(p, board);
        System.out.println("round 3: " + ans);

        resetAllVisitedSquares();
        board.placeWall(board.getSquare("I-D"), board.getSquare("II-D"));
        board.move(p, board.getSquare("I-D"));
        ans = existsPath(p, board);
        System.out.println("round 4: " + ans);

        resetAllVisitedSquares(); 
        board.placeWall(board.getSquare("II-E"), board.getSquare("III-E"));
        board.placeWall(board.getSquare("I-F"), board.getSquare("II-F"));
        board.placeWall(board.getSquare("II-F"), board.getSquare("II-G"));
        ans = existsPath(p, board);
        System.out.println("round 5: " + ans);

        resetAllVisitedSquares(); 
        board.placeWall(board.getSquare("IV-D"), board.getSquare("IV-E"));
        ans = existsPath(p, board);
        System.out.println("round 6: " + ans);

        // now demo player 1
        players.add(players.remove());
        p = players.peek();

        resetAllVisitedSquares();
        ans = existsPath(p, board);
        System.out.println("round 7: " + ans);

        resetAllVisitedSquares(); 
        board.placeWall(board.getSquare("V-F"), board.getSquare("V-G"));
        ans = existsPath(p, board);
        System.out.println("round 8: " + ans);

        resetAllVisitedSquares();
        board.placeWall(board.getSquare("VI-D"), board.getSquare("VII-D"));
        board.placeWall(board.getSquare("V-C"), board.getSquare("VI-C"));
        ans = existsPath(p, board);
        System.out.println("round 9: " + ans);

        resetAllVisitedSquares();
        board.placeWall(board.getSquare("VII-C"), board.getSquare("VIII-C"));
        board.placeWall(board.getSquare("VIII-D"), board.getSquare("VIII-E"));
        ans = existsPath(p, board);
        System.out.println("round 10: " + ans);

        resetAllVisitedSquares();
        board.move(p, board.getSquare("VII-F"));
        ans = existsPath(p, board);
        System.out.println("round 11: " + ans);

        resetAllVisitedSquares();
        board.placeWall(board.getSquare("VIII-E"), board.getSquare("IX-E"));
        ans = existsPath(p, board);
        System.out.println("round 12: " + ans);


        // now demo player 2
        players.add(players.remove());
        p = players.peek();

        resetAllVisitedSquares();
        ans = existsPath(p, board);
        System.out.println("round 13: " + ans);

        resetAllVisitedSquares();
        board.move(p, board.getSquare("VIII-D"));
        ans = existsPath(p, board);
        System.out.println("round 14: " + ans);


        // now demo player 3
        players.add(players.remove());
        p = players.peek();

        resetAllVisitedSquares();
        ans = existsPath(p, board);
        System.out.println("round 15: " + ans);

        resetAllVisitedSquares();
        board.move(p, board.getSquare("VI-D"));
        ans = existsPath(p, board);
        System.out.println("round 16: " + ans);


        // now demo player 0
        players.add(players.remove());
        p = players.peek();

        resetAllVisitedSquares();
        ans = existsPath(p, board);
        System.out.println("round 17: " + ans);
        /** END OF TESTING ONLY */
    }

    /**  this stuff is copied from GameEngine */

    /** should return true if there is any path from a player to their goal
     * @param player the player who is about to make the move 
     * @param board the board
     */
    public static boolean existsPath(Player player, GameBoard board) {
        // reachable keeps track of all squares that can be visited
        boolean[][] reachable = new boolean[GameBoard.COLUMNS][GameBoard.ROWS];
        for (int i = 0; i < GameBoard.COLUMNS; i++) {
            for (int j = 0; j < GameBoard.ROWS; j++) {
                reachable[i][j] = false;
            }
        }
        Square currentSquare = board.getPlayerLoc(player);
        reachable[currentSquare.getX()][currentSquare.getY()] = true;
        return existsPathRecurse(board, player.getPlayerNo(), 
                                 currentSquare, reachable);
    }

    /** inner recursive lopo for existsPath
     */
    private static boolean existsPathRecurse(GameBoard board, int pno,
                                             Square square,
                                             boolean[][] visited) {
        try { Thread.sleep(50); } catch (Exception e) { };
        frame.update(board, allVisitedSquares);
        int x, y;
        Square [] squares = GameEngine.reachableAdjacentSquares(board, square);
        for (Square sq : squares) {
            x = sq.getX();
            y = sq.getY();
            System.out.println("checking square: (" + x + ", " + y + ")");
            if (visited[x][y]) {
                System.out.println("been here!");
                continue;
            }
            allVisitedSquares[allVisitedSquarescount] = sq;
            allVisitedSquarescount++;
            if (pno == 0 && y == 8) {
                return true; // player 0 has reached the bottom rank!
            } else if (pno == 1 && y == 0) {
                return true; // player 1 has reached the top rank!
            } else if (pno == 2 && x == 8) {
                return true; // player 2 has reached right side!
            } else if (pno == 3 && x == 0) {
                return true; // player 3 has reached left side!
            }
            visited[x][y] = true;
            if (existsPathRecurse(board, pno, sq, visited)) {
                return true;
            };
        }
        return false;
    }

    private static void resetAllVisitedSquares() {
        allVisitedSquares = new Square[81];
        allVisitedSquarescount = 0;
    }
}
