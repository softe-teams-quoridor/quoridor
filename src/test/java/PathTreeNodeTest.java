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
	assertNull("up should be initialized as null", node.getUp());
	assertNull("down should be initialized as null", node.getDown());
	assertNull("left should be initialized as null", node.getLeft());
	assertNull("right should be initialized as null", node.getRight());
	assertNotNull("location should never be null!", node.getLocation());
	assertNotNull(node.getNum());
    }
    
    /* check that setUp adds a node */
    @Test 
    public void testSetUp() throws Exception{
	node.setUp(new PathTreeNode(new Square(1,1), 0));
	assertNotNull("up should not return null after setUp", node.getUp());
    }
    
    /* check that setDown adds a node */
    @Test 
    public void testSetDown() throws Exception{
	node.setDown(new PathTreeNode(new Square(1,1), 0));
	assertNotNull("down should not return null after setDown", node.getDown());
    }
    
    /* check that setRight adds a node */
    @Test 
    public void testSetRight() throws Exception{
	node.setRight(new PathTreeNode(new Square(1,1), 0));
	assertNotNull("right should not return null after setRight", node.getRight());
    }
    
    /* check that setLeft adds a node */
    @Test 
    public void testSetLeft() throws Exception{
	node.setLeft(new PathTreeNode(new Square(1,1), 0));
	assertNotNull("left should not return null after setLeft", node.getLeft());
    }
}