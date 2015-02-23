import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;

/**
 * @author blad
 *
 */
public class PawnTest {
    @Before
    public void greet() throws Exception {
        System.out.println( "hello");
    }

    @Test
    public void testPawnConstructor() throws Exception {
        Pawn pawn = new Pawn(3, 1);
        assertNotNull("Pawn() returned null", pawn);
        System.out.println( "hole");
    }
}
