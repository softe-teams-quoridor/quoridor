import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

/**
 * @author ECav
 * Rebuilt on 3/19/15
 */
public class PlayerTest {

    private static final int NUM_PLAYERS = 4;
    private Player [] players;
    private int walls;

    @Before
    public void beef() throws Exception {
        // Setup a players array of NUM_PLAYERS players
        //Player.resetPlayerNos();
        players = new Player[NUM_PLAYERS];
        walls = 20 / NUM_PLAYERS;
       for(int i = 0; i < NUM_PLAYERS; i++)
            players[i] = new Player(i, "player_" + i,walls); 
        
    }

    @Test
    public void getPlayerNoTest() throws Exception {
        // Check all player numbers in array
        for(int i = 0; i < NUM_PLAYERS; i++) 
            assertEquals(players[i].getPlayerNo(), i);
         
    }

    @Test
    public void getNameTest() throws Exception {
        // Check all names of players 
        for(int i = 0; i < NUM_PLAYERS; i++)
            assertEquals(players[i].getName(), "player_" + i);
    
    }

    @Test
    public void getNumWallsTest() throws Exception {
        // Make sure all players have correct amount of walls
        for(int i = 0; i < NUM_PLAYERS; i++) 
            assertEquals(players[i].getNumWalls(), walls); 
    }

    @Test
    public void placeWallTest() throws Exception {
        
        // Place all walls for all players
        for(int i = 0; i < NUM_PLAYERS; i++)
            for(int j = 0; j < walls; j++)
                assertTrue(players[i].placeWall());

        // check to make sure all players cannot place walls
        for(int i = 0; i < NUM_PLAYERS; i++)
            assertFalse(players[i].placeWall());
    
    }

  
}
