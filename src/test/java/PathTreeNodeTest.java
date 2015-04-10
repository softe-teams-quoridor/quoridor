import java.util.*;
import java.util.Queue;
import java.util.LinkedList;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PathTreeNodeTest{
    
    PathTreeNode node;
    Square sq;
    
    /* Instantiates the PathTreeNode for all tests */
    @Before
    public void instantiatePathTreeNode() throws Exception {
        sq = new Square(4,0);
        node = new PathTreeNode(sq,1);
        assertNotNull("node should not be null!", node);
    }
    
    /*assert that constructor builds correctly. All nodes should be null*/
    @Test
    public void testConstructor() throws Exception{
	assertNull("up should be initialized as null", node.nextAdjFromParent);
	assertNull("down should be initialized as null", node.firstAdjFromHere);;
	assertNotNull("location should never be null!", node.location);
	assertNotNull(node.i);
    }
}