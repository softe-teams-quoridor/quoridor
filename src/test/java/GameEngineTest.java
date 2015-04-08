/** GameEngineTest.java - CIS405 - teams
  */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.*;

public class GameEngineTest {

    private static final int NUM_PLAYERS = 2;

    GameBoard board;
    Queue<Player> players;
    int walls;
    
    @Before
    public void beef() throws Exception {
        players = new LinkedList<Player>();
        walls = 20 / NUM_PLAYERS;
        for(int i = 0; i < NUM_PLAYERS; i++) {
            players.add(new Player(i, walls));
        }
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
        assertNotNull("p1 null?", players.peek());
        players.add(players.remove());
        assertNotNull("p2 null?", players.peek());
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
    public void testValidateMove() throws Exception {
       // Make sure you can only move one space 
       assertNotNull(GameEngine.validate(board,players.peek(),"V-B"));
       assertNotNull(GameEngine.validate(board,players.peek(),"VI-A"));
       assertNotNull(GameEngine.validate(board,players.peek(),"IV-A"));
       assertNull(GameEngine.validate(board,players.peek(),"I-D"));
    }
    
    
    @Test
    public void testValidateWall() throws Exception {
        Square [] squares = new Square[2];
        // Set a wall at (II-A,II-B)
        squares[0] = board.getSquare(1,1);
        squares[1] = board.getSquare(1,2);
        board.placeWall(squares[0],squares[1]);
        // Try and place a wall at (II-A,III-A)
        squares[1] = board.getSquare(2,1); 
        assertFalse(GameEngine.validateWall(board, squares));
    }

    @Test
    public void testValidate() throws Exception {
        Square[] squares = new Square[2];
        squares[0] = board.getSquare(4,0);
        squares[1] = board.getSquare(5,0);
        board.placeWall(squares[0],squares[1]);

        assertNull(GameEngine.validate(board,players.peek(),"V-B"));
        assertNull(GameEngine.validate(board,players.peek(),"(V-A,V-B)"));
    }
    
    

    @Test
    public void testGetWinner() throws Exception {
        // Make sure there isn't a winner yet
        assertNull(GameEngine.getWinner(board, players));

        // Move player 2 out of the way
        players.add(players.remove());
        board.move(players.peek(),board.getSquare(3,8));

        // Set player 1 to the head of the queue
        players.add(players.remove());

        if(players.peek().getPlayerNo() != 0) {
            players.add(players.remove());
            players.add(players.remove());
        }
        
        //Move player 1 to a winner pos
        for(int i = 1; i < 9; i++)
            board.move(players.peek(),board.getSquare(4,i));
        assertNotNull(GameEngine.getWinner(board, players));
    }

    @Test
    public void testExistsPathPlayer0() throws Exception {
        Player p = players.peek();
        assertEquals(0, p.getPlayerNo());
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("I-D"), board.getSquare("II-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("III-D"), board.getSquare("IV-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("V-D"), board.getSquare("VI-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VII-D"), board.getSquare("VIII-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VII-D"), board.getSquare("VIII-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VIII-D"), board.getSquare("VIII-E"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VIII-E"), board.getSquare("IX-E"));

        // a wall has been made across the middle
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("III-C"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-D"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("I-A"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("I-B"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("III-D"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-D"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-E"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("VIII-D"));
        assertFalse(GameEngine.existsPath(p, board));

        // put the player on the other side of the great wall
        board.move(p, board.getSquare("II-G"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-I"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("I-I"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("VIII-E"));
        assertTrue(GameEngine.existsPath(p, board));
    }

    @Test
    public void testExistsPathPlayer1() throws Exception {
        players.remove();
        Player p = players.peek();
        assertEquals(1, p.getPlayerNo());
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("I-D"), board.getSquare("II-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("III-D"), board.getSquare("IV-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("V-D"), board.getSquare("VI-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VII-D"), board.getSquare("VIII-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VII-D"), board.getSquare("VIII-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VIII-D"), board.getSquare("VIII-E"));
        assertTrue(GameEngine.existsPath(p, board));
        board.placeWall(board.getSquare("VIII-E"), board.getSquare("IX-E"));

        // a wall has been made across the middle
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("II-G"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-I"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("I-I"));
        assertFalse(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("VIII-E"));
        assertFalse(GameEngine.existsPath(p, board));

        // put the player on the other side of the great wall
        board.move(p, board.getSquare("I-B"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("III-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-D"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("IX-E"));
        assertTrue(GameEngine.existsPath(p, board));
        board.move(p, board.getSquare("VIII-D"));
        assertTrue(GameEngine.existsPath(p, board));
    }

    @Test
    public void testReachableAdjacentSquares() throws Exception {
        Square [] result = new Square[4];
        result[0] = board.getSquare(5,4);
        result[1] = board.getSquare(5,6);
        result[2] = board.getSquare(4,5);
        result[3] = board.getSquare(6,5);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(5,5)));
        result[0] = board.getSquare(1,4);
        result[1] = board.getSquare(1,6);
        result[2] = board.getSquare(0,5);
        result[3] = board.getSquare(2,5);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(1,5)));
        result = new Square[3];
        result[0] = board.getSquare(0,4);
        result[1] = board.getSquare(0,6);
        result[2] = board.getSquare(1,5);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(0,5)));
        result[0] = board.getSquare(8,4);
        result[1] = board.getSquare(8,6);
        result[2] = board.getSquare(7,5);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(8,5)));
        result[0] = board.getSquare(4,7);
        result[1] = board.getSquare(3,8);
        result[2] = board.getSquare(5,8);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(4,8)));
        result[0] = board.getSquare(4,1);
        result[1] = board.getSquare(3,0);
        result[2] = board.getSquare(5,0);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(4,0)));
        
        // walls
        board.placeWall(board.getSquare(4,0), board.getSquare(4,1));
        // now we shouldn't be able to move to 5,0 aka VI-A
        result = new Square[2];
        result[0] = board.getSquare(4,1);
        result[1] = board.getSquare(3,0);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(4,0)));

        board.placeWall(board.getSquare(4,4), board.getSquare(5,4));
        // now we shouldn't be able to move to 4,5 aka V-F
        result = new Square[3];
        result[0] = board.getSquare(4,3);
        result[1] = board.getSquare(3,4);
        result[2] = board.getSquare(5,4);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(4,4)));

        // and the opposite way...
        result = new Square[3];
        result[0] = board.getSquare(4,6);
        result[1] = board.getSquare(3,5);
        result[2] = board.getSquare(5,5);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(4,5)));

        board.placeWall(board.getSquare(5,4), board.getSquare(5,5));
        result = new Square[2];
        result[0] = board.getSquare(5,3);
        result[1] = board.getSquare(4,4);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(5,4)));

        board.placeWall(board.getSquare(5,3), board.getSquare(6,3));
        result = new Square[1];
        result[0] = board.getSquare(4,4);
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(5,4)));

        board.placeWall(board.getSquare(4,3), board.getSquare(4,4));
        // now the square 5,4 is boxed in!
        result = new Square[0];
        assertEquals(result, GameEngine.reachableAdjacentSquares(board,
                                                board.getSquare(5,4)));
    }
}
