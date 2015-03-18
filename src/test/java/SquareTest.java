/** SquareTest.java - CIS405 - teams
  * Last Edit: February 19, 2014
  * ___________________________________________________
  *
  * tests the Square object's methods
  */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SquareTest {
    @Before
    public void empty() throws Exception {
        // do whatever you want here
        Player.resetPlayerNos();
    }

    @Test
    public void testSquareConstructor() throws Exception {
        Square square = new Square(3, 5);
        assertNotNull("Square() returned null", square);
    }

    @Test
    public void testSquareGetPlayer() throws Exception {
       Square s = new Square(3, 5);
       assertNull(s.getPlayer());
    }

    @Test
    public void testSquareAddPlayer() throws Exception {
       Square s = new Square(3, 5);
       Player p = new Player("name", 0);
       s.addPlayer(p);
       assertEquals(s.getPlayer(),p);
    }

    @Test
    public void testSquareRemovePlayer() throws Exception {
        Square s = new Square(3, 5);
        Player p = new Player("name", 0);
        s.addPlayer(p);
        assertFalse(s.vacant());
        s.removePlayer();
        assertEquals(s.getPlayer(), null);
        assertTrue(s.vacant());
    }

    @Test
    public void testSquarePlaceVertWall() throws Exception {
        Square s = new Square(3, 5);
        s.placeWallBottom(true);
        assertTrue(s.hasWallBottom());
        s.placeWallRight(false);
        assertTrue(s.hasWallRight());
    }

    @Test
    public void testSquareEqualToAnotherSquare() throws Exception {
        Square sq1 = new Square(5,5);
        Square sq2 = sq1;
        assertEquals(sq1, sq2);
        Square sq3 = new Square(8,8);
        assertNotEquals(sq2, sq3);
    }

/*
    @Test
    public void testSquareEqualToXandY() throws Exception {
        /*
        Square s = new Square(5,5);
        assertEquals(s.equals(5,5), true);
        assertEquals(s.equals(8,4), false);
        
    }
    */
}
