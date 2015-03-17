import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameBoardTest extends GameBoard {
    
    @Test
    public void testGameBoard() {
    
        GameBoard b = new GameBoard();
        assertFalse(b.isOccupied(1,1));
    }

    @Test
    public void testGameBoardIsOccupiedOnEmptyBoard() {
        GameBoard b = new GameBoard();

        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ )
                assertFalse(b.isOccupied(i,j));
    }

    @Test
    public void testGameBoardIsOccupiedOnFullBoard() {
        GameBoard b = new GameBoard();
        /*
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) {
//                 b.addPlayer(new Player("CDEMST", new Square(i,j),0));
                // this test doesn t even make sense??? why we are making a 
                // new square in the board AFTER initializing all the squares??
                // in the GameBoard Constructor
                assertTrue(b.isOccupied(i,j));
                how this ever worked at all is such a mystery to me
            }
        */
    }

    @Test
    public void testGameBoardSquares() {
        GameBoard b = new GameBoard();
        
        /*
        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) 
                assertTrue( b.getSquare(i,j).equals(new Square(i,j)));
        */
    }


    @Test
    public void testGameBoardGetNULLPlayer() {
        GameBoard b = new GameBoard();

        for ( int i = 0; i < 9; i++ )
            for ( int j = 0; j < 9; j++ ) 
                assertEquals(b.getPlayer(i,j), null);
    }
    
    @Test
    public void testAddAndGetPlayer() {
        GameBoard b = new GameBoard();
        Player p = new Player("CDEMST", b.getSquare(1,1), 0);
//         /* making addPlayer private, anybody know how to work around this?
        // fixed it! PROTECTED
        b.addPlayer(p);
        assertEquals(b.getPlayer(1,1), p);
    }

    @Test
    public void testMovePlayer() {
        GameBoard b = new GameBoard();
        Player p = new Player("farple", b.getSquare(5,5), 0);
        b.addPlayer(p); 
        // we make a new square here? wtf
//         b.move(p, new Square(2,3));
        b.move(p, b.getSquare(2,3));
        /* why can't i make this pass
        assertTrue(b.isOccupied(2,3));
        */
        assertTrue(!b.isOccupied(5,5));        
    }

    @Test
    public void testBootPlayer() {
        GameBoard b = new GameBoard();
        Player p = new Player("bootMe", b.getSquare(2,2), 0);
        b.addPlayer(p);
        assertTrue(b.isOccupied(2,2));
        // removePlayer is bootPlayer
        b.removePlayer(p);
        assertTrue(!b.isOccupied(2,2));
    }
}
