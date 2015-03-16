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
        assertEquals("I",    GameEngine.toNumerals(0));
        assertEquals("II",   GameEngine.toNumerals(1));
        assertEquals("III",  GameEngine.toNumerals(2));
        assertEquals("IV",   GameEngine.toNumerals(3));
        assertEquals("V",    GameEngine.toNumerals(4));
        assertEquals("VI",   GameEngine.toNumerals(5));
        assertEquals("VII",  GameEngine.toNumerals(6));
        assertEquals("VIII", GameEngine.toNumerals(7));
        assertEquals("IX",   GameEngine.toNumerals(8));
        assertEquals("@@@@@@@@@@@@@@", GameEngine.toNumerals(11));
    }

    @Test
    public void testFromNumerals() throws Exception {
        assertEquals(0, GameEngine.fromNumerals("I"));
        assertEquals(1, GameEngine.fromNumerals("II"));
        assertEquals(2, GameEngine.fromNumerals("III"));
        assertEquals(3, GameEngine.fromNumerals("IV"));
        assertEquals(4, GameEngine.fromNumerals("V"));
        assertEquals(5, GameEngine.fromNumerals("VI"));
        assertEquals(6, GameEngine.fromNumerals("VII"));
        assertEquals(7, GameEngine.fromNumerals("VIII"));
        assertEquals(8, GameEngine.fromNumerals("IX"));
        assertEquals(-1, GameEngine.fromNumerals("@@@@@@@@@@@@@@"));
        assertEquals(-1, GameEngine.fromNumerals("arbitrary string"));
    }

    @Test
    public void testToLetters() throws Exception {
        assertEquals('A', GameEngine.toLetters(0));
        assertEquals('B', GameEngine.toLetters(1));
        assertEquals('C', GameEngine.toLetters(2));
        assertEquals('D', GameEngine.toLetters(3));
        assertEquals('E', GameEngine.toLetters(4));
        assertEquals('F', GameEngine.toLetters(5));
        assertEquals('G', GameEngine.toLetters(6));
        assertEquals('H', GameEngine.toLetters(7));
        assertEquals('I', GameEngine.toLetters(8));
        assertEquals('Z', GameEngine.toLetters(9));
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
