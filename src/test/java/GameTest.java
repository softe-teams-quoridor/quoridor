import java.util.*;
import java.util.Queue;
import java.util.LinkedList;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameTest {
    @Before
    public void beef() throws Exception {
    }

    @Test
    public void testValidName() {
        assertTrue(Game.isValidName("hello"));
        assertTrue(Game.isValidName("Stake-1"));
        assertTrue(Game.isValidName("SnakeA0DF97"));
        assertFalse(Game.isValidName(null));
        assertFalse(Game.isValidName("Sn akeA0DF97"));
        assertFalse(Game.isValidName("SnhtunsahtenhakeA0DF97"));
        assertFalse(Game.isValidName("S ht nsa ten "));
    }

    @Test
    public void testCountValidNames() {
        String [] sampleNames = {null};
        assertEquals(0, Game.countValidNames(sampleNames));

        String [] sampleNames2 = {"hello", null};
        assertEquals(1, Game.countValidNames(sampleNames2));

        String [] sampleNames3 = {"hello", "hutnesa"};
        assertEquals(2, Game.countValidNames(sampleNames3));

        String [] sampleNames4 = {"hello", "hutnesa", null, "what"};
        assertEquals(3, Game.countValidNames(sampleNames4));

        String [] sampleNames5 = {"hello", "hutnesa", "null", "what"};
        assertEquals(4, Game.countValidNames(sampleNames5));
    }
}
