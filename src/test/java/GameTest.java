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
        assertFalse(Game.isValidName("Sn akeA0DF97"));
        assertFalse(Game.isValidName("SnhtunsahtenhakeA0DF97"));
        assertFalse(Game.isValidName("S ht nsa ten "));
    }
}
