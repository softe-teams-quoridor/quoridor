
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.*;

public class ReachableAdjacentSquares {

    private static final int NUM_PLAYERS = 2;

    GameBoard board;
    Queue<Player> players;
    int walls;
    
    @Before
    public void before() throws Exception {
        players = new LinkedList<Player>();
        walls = 20 / NUM_PLAYERS;
        for(int i = 0; i < NUM_PLAYERS; i++) {
            players.add(new Player(i, walls));
        }
        assertNotNull("players should not be null", players);

        board = new GameBoard(players);
        assertNotNull("board should not be null!", board);
    }

    @Test
    public void ensureReachableSquaresDoNotTrackCurrentLocation() throws Exception {
        board.move(players.peek(), board.getSquare("V-E"));
        players = board.getNextTurn(players);

        board.move(players.peek(), board.getSquare("V-F"));
        players = board.getNextTurn(players);  

        Square[] adjacencies = GameEngine.reachableAdjacentSquares(board, board.getPlayerLoc(players.peek()));
        
        for ( Square s : adjacencies )
            System.out.println(s);

        for ( Square s : adjacencies )
            assertNotEquals(s, board.getPlayerLoc(players.peek()));


    }


} 
