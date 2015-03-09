import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameBoardTest {
    @Test
    public void testGameBoard() {
    
        GameBoard board = new GameBoard();
        assertFalse(board.isOccupied(1,1));
    }
}
