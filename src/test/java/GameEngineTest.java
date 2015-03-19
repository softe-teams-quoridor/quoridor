/** GameEngineTest.java - CIS405 - teams
  */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameEngineTest {

    private static final int NUM_PLAYERS = 2;

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
        assertNotNull("p1 null?", players[0]);
        assertNotNull("p2 null?", players[1]);
    }


    @Test
    public void testParseMove() throws Exception {      
        //Test all possible moves! 
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                assertTrue(GameEngine.parseMove((GameEngine.toNumerals(i) 
                                + "-" + GameEngine.toLetters(j))));
            }
        }

        //Test some impossible moves :)
        assertFalse(GameEngine.parseMove("IIII-A"));
        assertFalse(GameEngine.parseMove("A-II"));

    }


    @Test
    public void testGetSquare() throws Exception {
        Square sq = GameEngine.getSquare(board, "III-G");
        assertEquals(sq, board.getSquare(2, 6));
    }


    @Test
    public void testValidate() throws Exception {
       // Make sure you can only move one space 
       assertTrue(GameEngine.validate(board,players[0],"V-B"));
       assertTrue(GameEngine.validate(board,players[0],"VI-A"));
       assertTrue(GameEngine.validate(board,players[0],"IV-A"));
       assertFalse(GameEngine.validate(board,players[0],"I-D"));
    }

    @Test
    public void testGetWinner() throws Exception {
        assertNull(GameEngine.getWinner(board, players));
        board.move(players[1],board.getSquare(3,8));
        for(int i = 1; i < 9; i++)
            board.move(players[0],board.getSquare(4,i));   
        assertNotNull(GameEngine.getWinner(board, players));
    
    }


    @Test
    public void testNextPlayer() throws Exception {
        // Go through each players turn twice
        assertEquals(players[1],GameEngine.nextPlayer(0,players));
        
        if(NUM_PLAYERS == 4) {
            assertEquals(players[2],GameEngine.nextPlayer(1,players));
            assertEquals(players[3],GameEngine.nextPlayer(2,players));
            assertEquals(players[0],GameEngine.nextPlayer(3,players));
        }
        else {
            assertEquals(players[0],GameEngine.nextPlayer(1,players));
        }
            

    }
}
