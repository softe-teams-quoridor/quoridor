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

    /* Ensures that the correct number of players are remaining */
    @Test
    public void testNumPlayersRemaning() throws Exception {
        for(int i = NUM_PLAYERS - 1; i >= 0; i--) {
            board.removePlayer(players.peek());
            assertEquals(board.numPlayersRemaining(), i);
            board.getNextTurn(players);
        }
    }

    /* Ensures that squares are unoccupied/occupied */
    @Test
    public void testIsOccupied() throws Exception {
        // Check players initial cells
        assertTrue(board.isOccupied(4,0));
        assertTrue(board.isOccupied(4,8));
        assertTrue(board.isOccupied(0,4));
        assertTrue(board.isOccupied(8,4));
        // Check some unoccupied cells
        assertFalse(board.isOccupied(0,0));
        assertFalse(board.isOccupied(8,8));
        assertFalse(board.isOccupied(0,8));
        assertFalse(board.isOccupied(8,0));
        assertFalse(board.isOccupied(4,4));
    }

    
    /* Ensures all squares have been properly assigned coordinates */
    @Test
    public void testGetSquares() throws Exception {
        String square;
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) {
                // Test using x and y
                assertEquals(board.getSquare(i,j).getX(), i);
                assertEquals(board.getSquare(i,j).getY(), j);
                square = GameEngine.toNumerals(i) + "-" +
                         GameEngine.toLetters(j);
                // Test using string representation
                assertEquals(board.getSquare(square).getX(), i);
                assertEquals(board.getSquare(square).getY(), j);
            }
    }
    
    /* Ensures we can retrieve a player's start location based on coordinates*/
    @Test
    public void testGetPlayerLocWithXYFromStartLocs() throws Exception {
        // test p0
        assertEquals(board.getPlayer(4,0),players.peek());
        // test p1
        players.remove();
        assertEquals(board.getPlayer(4,8),players.peek());
        // test p2
        players.remove();
        assertEquals(board.getPlayer(0,4),players.peek());
        // test p3
        players.remove();
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

    /* Ensures that walls have been actually placed */
    @Test
    public void testPlaceWall() throws Exception {
        Square[] wallSquares = {new Square(0,0), new Square(0,1)};
        board.placeWall(wallSquares);
        
    }

    /* Ensures that walls have been properly removed */
    @Test
    public void testRemoveWall() throws Exception {
        Square[] wallSquares = {new Square(0,0), new Square(0,1)};
        board.placeWall(wallSquares);
        board.removeWall(wallSquares);
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

  
    /* Tests that getNextTurn shuffles through the queue appropriately */
    @Test
    public void testGetNextTurn() throws Exception { 
        // shuffle through players 0-3
        for ( int i = 0; i < players.size(); i++ ) {
            assertEquals(board.getCurrPlayerTurn(), i);
            players = board.getNextTurn(players);
        }
        // do it again, ensuring players are successfully shuffled
        for ( int i = 0; i < players.size(); i++ ) {
            assertEquals(board.getCurrPlayerTurn(), i);
            players = board.getNextTurn(players);
        }
    }
}
