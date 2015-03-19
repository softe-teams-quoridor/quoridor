import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameBoardTest {

    GameBoard board;
    Player [] players;
    @Before
    public void beef() throws Exception {
        Player.resetPlayerNos();
        players = new Player[4];
        players[0] = new Player("a",10);
        players[1] = new Player("b",10);
        players[2] = new Player("c",10);
        players[3] = new Player("d",10);
        assertNotNull("players should not be null", players);
        board = new GameBoard(players);
        assertNotNull("board should not be null!", board);
        assert(players.length == 4);
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
        
        board.removePlayer(players[0]);
        board.removePlayer(players[1]);
        board.removePlayer(players[2]);
        board.removePlayer(players[3]);
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
        board.removePlayer(players[0]);
        board.removePlayer(players[1]);
        board.removePlayer(players[2]);
        board.removePlayer(players[3]);
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
        assertEquals(board.getPlayerLoc(players[2]), board.getSquare(0,4));
        assertEquals(board.getPlayerLoc(players[3]), board.getSquare(8,4));
    }
}
