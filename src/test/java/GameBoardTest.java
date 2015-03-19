import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameBoardTest {

    private static final int NUM_PLAYERS = 4;
    GameBoard board;
    Player [] players;
    int walls;
    @Before
    public void beef() throws Exception {
        players = new Player[NUM_PLAYERS];
        walls = 20 / NUM_PLAYERS;
        for(int i = 0; i < NUM_PLAYERS; i++)
            players[i] = new Player(i, "player_" + i, walls);
        
        assertNotNull("players should not be null", players);
        board = new GameBoard(players);
        assertNotNull("board should not be null!", board);
        assert(players.length == NUM_PLAYERS);
    }

    @Test
    public void testAddPlayer() {
        board.addPlayer(players[0], 1, 1);
        assertEquals(board.getPlayer(1,1), players[0]);
    }

    @Test
    public void testValidLoc() throws Exception {
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                assertTrue(board.validLoc(i,j));
        assertFalse(board.validLoc(10,12));
    }
    

    @Test
    public void testGameBoardIsOccupied() {
        assertTrue(board.isOccupied(4,0));
        assertTrue(board.isOccupied(4,8));

        if(NUM_PLAYERS == 4) {
            assertTrue(board.isOccupied(0,4));
            assertTrue(board.isOccupied(4,8));
        }

        for(int i = 0; i < NUM_PLAYERS; i++)
            board.removePlayer(players[i]);
        
        // Test on empty board
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ )
                assertFalse(board.isOccupied(i,j));
    }



    @Test
    public void testGameBoardSquares() {
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) {
                assertEquals(board.getSquare(i,j).getX(), i);
                assertEquals(board.getSquare(i,j).getY(), j);
            }
    }


    @Test
    public void testGameBoardGetNULLPlayer() {

        for(int i = 0; i < NUM_PLAYERS; i++)
            board.removePlayer(players[i]);
        
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) 
                assertEquals(board.getPlayer(i,j), null);
    }
    
    
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
    public void testRemovePlayer() {
        board.removePlayer(players[0]);
        assertNull(board.getPlayer(4,0));
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
    
}
