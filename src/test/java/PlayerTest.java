import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import java.util.Queue;
import java.util.LinkedList;

/**
 * teams
 * Rebuilt on 3/19/15
 */
public class PlayerTest {

    private static final int NUM_PLAYERS = 4;
//    private Player [] players;
    private Queue<Player> players;
    private int walls;

    @Before
    public void beef() throws Exception {
        // Setup a players queue of NUM_PLAYERS players
        players = new LinkedList<Player>();
        walls = 20 / NUM_PLAYERS;
       for(int i = 0; i < NUM_PLAYERS; i++)
            players.add(new Player(i, walls)); 
        
    }

    @Test
    public void getPlayerNoTest() throws Exception {
        // Check all player numbers in array
        for(int i = 0; i < NUM_PLAYERS; i++) {
            assertEquals(players.peek().getPlayerNo(), i);
            players.add(players.remove());
        }         
    }

    @Test
    public void getNameTest() throws Exception {
        // Check all names of players 
        for(int i = 0; i < NUM_PLAYERS; i++) {
            assertEquals(players.peek().getName(), "player_" + i);
            players.add(players.remove());
        }
    
    }

    @Test
    public void getNumWallsTest() throws Exception {
        // Make sure all players have correct amount of walls
        for(int i = 0; i < NUM_PLAYERS; i++) { 
            assertEquals(players.peek().getNumWalls(), walls);
            players.add(players.remove());
        }
    }

    @Test
    public void useWallTest() throws Exception {
        
        // Place all walls for all players
        for(int i = 0; i < NUM_PLAYERS; i++)
            for(int j = 0; j < walls; j++) {
//                 assertTrue(players.peek().useWall());
                assertTrue(players.peek().mayPlaceWall());
                players.peek().useWall();
                players.add(players.remove());
            }

        // check to make sure all players cannot place walls
        for(int i = 0; i < NUM_PLAYERS; i++) {
            assertFalse(players.peek().mayPlaceWall());
            players.add(players.remove());
        }
    
    }

  
}
