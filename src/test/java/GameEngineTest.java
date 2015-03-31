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
            players[i] = new Player(i, walls);
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
                assertEquals(GameEngine.parseMove(board, (GameEngine.toNumerals(i) 
                                + "-" + GameEngine.toLetters(j))), board.getSquare(i,j));
            }
        }

        //Test some impossible moves :)
        assertNull(GameEngine.parseMove(board, "IIII-A"));
        assertNull(GameEngine.parseMove(board, "A-II"));
        assertNull(GameEngine.parseMove(board, "X-T"));
        assertNull(GameEngine.parseMove(board, "hello"));
        assertNull(GameEngine.parseMove(board, "Brian C. Ladd"));
    }

    @Test
    public void testParseWall() throws Exception {

        Square[] wallSquares = new Square[2];
        
        // Test all vetical walls
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                wallSquares[0] = board.getSquare(x,y);
                wallSquares[1] = board.getSquare(x,y+1);
                assertEquals(GameEngine.parseWall(board,
                            "(" + GameEngine.toNumerals(x)  + "-" 
                                + GameEngine.toLetters(y)   + ","
                                + GameEngine.toNumerals(x)  + "-" 
                                + GameEngine.toLetters(y+1) + ")" )
                                                    , wallSquares);
            }
        }

        // Test all horizontal walls
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                wallSquares[0] = board.getSquare(x,y);
                wallSquares[1] = board.getSquare(x+1,y);
                assertEquals(GameEngine.parseWall(board,
                            "(" + GameEngine.toNumerals(x)   + "-"
                                + GameEngine.toLetters(y)    + ","
                                + GameEngine.toNumerals(x+1) + "-" 
                                + GameEngine.toLetters(y)    + ")" )
                                                     , wallSquares);
            }
        }

        //Test some impossible wall placements
        assertNull(GameEngine.parseWall(board, "(III-B,III-D)"));
        assertNull(GameEngine.parseWall(board, "(II-A,I-B"));
        assertNull(GameEngine.parseWall(board, "(Collin-Keyboard,PretendTo-Type)"));
        //Board Walls
        assertNull(GameEngine.parseWall(board, "(IX-A,IX-B)"));
        assertNull(GameEngine.parseWall(board, "(I-I,II-I"));

        
    }   

    
    @Test
    public void testParseWallOnInsertingHorizontalWalls() throws Exception {
        // Test all horizontal wall strings
        // Iterate through rows
        for ( int r = 0; r < 8; r++ )
            // Iterate through columns, except last
            for ( int c = 0; c < 8; c++ ) {
                String wall = "(" + GameEngine.toNumerals(c)   + "-"
                                  + GameEngine.toLetters(r)    + ","
                                  + GameEngine.toNumerals(c+1) + "-"
                                  + GameEngine.toLetters(r)    + ")";
                // FIXME
                //assertEquals(GameEngine.parseWall(board,wall), board.getSquare(c,r)); 
            }

        // Test invalid horizontals here
    }


    // FIXME
    @Test
    public void testParseWallOnInsertingVerticalWalls() throws Exception {
        // Test all vertical wall strings
        // Iterate through columns
        for ( int c = 0; c < 8; c++ )
            // Iterate through rows, except last
            for ( int r = 0; r < 8; r++ ) {
                String wall = "(" + GameEngine.toNumerals(c)   + "-"
                                  + GameEngine.toLetters(r)    + ","
                                  + GameEngine.toNumerals(c+1) + "-"
                                  + GameEngine.toLetters(r)    + ")";
                assertTrue ( GameEngine.parseWall ( wall ) ); 
            }

        // Test invalid verticals here
    }


    @Test
    public void testGetSquare() throws Exception {
        Square sq = GameEngine.getSquare(board, "III-G");
        assertEquals(sq, board.getSquare(2, 6));
    }


    @Test
    public void testvalidateMove() throws Exception {
       // Make sure you can only move one space 
       assertTrue(GameEngine.validateMove(board,players[0],"V-B"));
       assertTrue(GameEngine.validateMove(board,players[0],"VI-A"));
       assertTrue(GameEngine.validateMove(board,players[0],"IV-A"));
       assertFalse(GameEngine.validateMove(board,players[0],"I-D"));
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
