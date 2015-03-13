/** GameEngineTest.java - CIS405 - teams
  */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameEngineTest {
    GameEngine ge;
    GameBoard board;
    Player p1, p2;
    @Before
    public void beef() throws Exception {
        ge = new GameEngine();
        assertNotNull("ge should not be null!", ge);
        board = new GameBoard(4, 6);
        assertNotNull("board should not be null!", board);
    }

    @Test
    public void testToNumerals() throws Exception {
        //assertEquals("", GameEngine.toNumerals(0));
        assertEquals("I", GameEngine.toNumerals(0));
        assertEquals("V", GameEngine.toNumerals(4));
        assertEquals("IX", GameEngine.toNumerals(8));
        assertEquals("@@@@@@@@@@@@@@", GameEngine.toNumerals(11));
    }

    @Test
    public void testFromNumerals() throws Exception {
        //assertEquals(0, GameEngine.fromNumerals(""));
        assertEquals(0, GameEngine.fromNumerals("I"));
        assertEquals(4, GameEngine.fromNumerals("V"));
        assertEquals(8, GameEngine.fromNumerals("IX"));
        assertEquals(-1, GameEngine.fromNumerals("@@@@@@@@@@@@@@"));
        assertEquals(-1, GameEngine.fromNumerals("arbitrary string"));
    }

    @Test
    public void testToLetters() throws Exception {
        //assertEquals("", GameEngine.toLetters(0));
        assertEquals('A', GameEngine.toLetters(0));
        assertEquals('E', GameEngine.toLetters(4));
        assertEquals('I', GameEngine.toLetters(8));
        assertEquals('Z', GameEngine.toLetters(9));
        assertEquals('Z', GameEngine.toLetters(11));
    }

    @Test
    public void testFromLetters() throws Exception {
        //assertEquals(0, GameEngine.fromLetters(""));
        assertEquals(0, GameEngine.fromLetters('A'));
        assertEquals(4, GameEngine.fromLetters('E'));
        assertEquals(8, GameEngine.fromLetters('I'));
        assertEquals(-1, GameEngine.fromLetters('J'));
        assertEquals(-1, GameEngine.fromLetters('Q'));
        assertEquals(-1, GameEngine.fromLetters(';'));
    }

    @Test
    public void testMakePlayer() throws Exception {
        p1 = new Player("tylEr", board.getSquare(4, 0), 10);
        p2 = new Player("tylAr", board.getSquare(4, 8), 10);
        assertNotNull("p1 null?", p1);
        assertNotNull("p2 null?", p2);
    }

    @Test
    public void testParseMove() throws Exception {
        board = new GameBoard();
        p1 = new Player("tylUr", board.getSquare(3, 6), 10);
        assertTrue(ge.parseMove(board, "III-G"));
    }

    @Test
    public void testGetSquare() throws Exception {
        Square sq = ge.getSquare(board, "III-G");
        assertEquals(sq, board.getSquare(2, 6));
    }

    @Test
    public void testValidate() throws Exception {
        board = new GameBoard();
        p1 = new Player("semi-collin",board.getSquare(4,0), 10);
        
    }
}
