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
       Player p = new Player("name", s,0);
       s.addplayer(p);
       assertEquals(s.getPlayer(),p);
    }

    @Test
    public void testSquareRemovePlayer() throws Exception {
        Square s = new Square(3, 5);
        Player p = new Player("name",s,0);
        s.addplayer(p);
        assertFalse(s.vacant());
        s.removePlayer();
        assertEquals(s.getPlayer(),null);
        assertTrue(s.vacant());
    }

    @Test
    public void testSquarePlaceVertWall() throws Exception {
        Square s = new Square(3, 5);
        s.placeWallVert(true);
        assertEquals(s.getVertWallStatus(), true);
        s.placeWallVert(false);
        assertEquals(s.getVertWallStatus(), false);
    }

    @Test
    public void testSquarePlaceHorzWall() throws Exception {
        // if you try to place a wall on the same place twice, then explosions
        Square s = new Square(3, 5);
        s.placeWallHorz(true);
        assertEquals(s.getHorzWallStatus(), true);
        s.placeWallHorz(false);
        assertEquals(s.getHorzWallStatus(), false);
    }

    @Test
    public void testSquareEqualToAnotherSquare() throws Exception {
        Square s = new Square(5,5);
        Square q = new Square(5,5);
        assertEquals(s.equals(q), true);
        Square u = new Square(8,8);
        assertEquals(s.equals(u), false);
    }
}
