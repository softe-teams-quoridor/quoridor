import java.util.*;
import java.util.Queue;
import java.util.LinkedList;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameBoardTest {

    private static final int NUM_PLAYERS = 4;
    private static final int NUM_WALLS = 20 / NUM_PLAYERS;
    GameBoard board;
    Queue<Player> players;
   

    /* Fills Player Queue for all tests */
    @Before
    public void addPlayersToQueue() throws Exception {
        players = new LinkedList<Player>();
        for(int i = 0; i < NUM_PLAYERS; i++)
            players.add(new Player(i, NUM_WALLS));
        
        assertNotNull("players should not be null", players.peek());
        assert(players.size() == NUM_PLAYERS);
    }

    /* Instantiates the GameBoard for all tests */
    @Before
    public void instantiateGameBoard() throws Exception {
        board = new GameBoard(players);
        assertNotNull("board should not be null!", board);
    } 
    
    /* Ensures all squares have been properly assigned coordinates */
    @Test
    public void testGetSquares() throws Exception {
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) {
                assertEquals(board.getSquare(i,j).getX(), i);
                assertEquals(board.getSquare(i,j).getY(), j);
            }
    }
    
    /* Ensures we can retrieve a player's start location based on coordinates*/
    @Test
    public void testGetPlayerLocWithXYFromStartLocs() throws Exception {
        // Test p0
        assertEquals(board.getPlayer(4,0),players.peek());
        // Shuffle; test p1
        players.add(players.remove());
        assertEquals(board.getPlayer(4,8),players.peek());
        // Shuffle; test p2
        players.add(players.remove());
        assertEquals(board.getPlayer(0,4),players.peek());
        // Shuffle; test p3
        players.add(players.remove());
        assertEquals(board.getPlayer(8,4),players.peek());
    }
    
    /* Ensures we can retrieve a player's start location based on player num */
    @Test
    public void testGetPlayerLocWithPNOFromStartLocs() throws Exception {
        // test p0
        assertEquals(board.getPlayer(players.peek().getPlayerNo()),players.peek());
        // test p1
        players.remove();
        assertEquals(board.getPlayer(players.peek().getPlayerNo()),players.peek());
        // test p2
        players.remove();
        assertEquals(board.getPlayer(players.peek().getPlayerNo()),players.peek());
        // test p3
        players.remove();
        assertEquals(board.getPlayer(players.peek().getPlayerNo()),players.peek());
    }

    /* Ensure we can remove players from the board */
    @Test
    public void testRemovePlayer() throws Exception {
        // test p0 was removed
        board.removePlayer(players.peek());
        assertNull(board.getPlayer(4,0));
        // test p1 was removed
        players.remove();
        board.removePlayer(players.peek());
        assertNull(board.getPlayer(4,8));
        // test p2 was removed
        players.remove();
        board.removePlayer(players.peek());
        assertNull(board.getPlayer(0,4));
        // test p3 was removed
        players.remove();
        board.removePlayer(players.peek());
        assertNull(board.getPlayer(8,4));
    }

    /* Ensures that getPlayer can return null */
    @Test
    public void testGetPlayerReturnsNull() throws Exception {
        // remove all players from the board
        for(int i = 0; i < NUM_PLAYERS; i++) {
            board.removePlayer(players.peek());
            players.remove();
        }
        // assert all squares do not have a player
        for ( int i = 0; i < GameBoard.COLUMNS; i++ )
            for ( int j = 0; j < GameBoard.ROWS; j++ ) 
                assertNull(board.getPlayer(i,j));
    }
    /*
    
    @Test
    public void testAddAndGetPlayer() {
        assertEquals(board.getPlayer(4,0), players[0]);
    }

    
    @Test
    public void testMovePlayer() {
        board.move(players[0],board.getSquare(4,1));
        assertEquals(players[0],board.getPlayer(4,1));        
    }

    
    

    @Test
    public void testSetUpIntialPosition() throws Exception {
        assertEquals(board.getPlayerLoc(players[0]), board.getSquare(4,0));
        assertEquals(board.getPlayerLoc(players[1]), board.getSquare(4,8));
        if(NUM_PLAYERS == 4) {
            assertEquals(board.getPlayerLoc(players[2]), board.getSquare(0,4));
            assertEquals(board.getPlayerLoc(players[3]), board.getSquare(8,4));
        }
    }
*/    
}
