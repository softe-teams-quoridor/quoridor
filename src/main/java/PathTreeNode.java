public class PathTreeNode{
    
    private PathTreeNode up;
    private PathTreeNode down;
    private PathTreeNode left;
    private PathTreeNode right;
    private Square location;
    private int i;
    
    public PathTreeNode(Square s, int i){
	up = null;
	down = null;
	left = null;
	right = null;
	location = s;
	this.i = i;
    }
    
    public int getNum(){
	return i;
    }
    
    public PathTreeNode getUp(){
	return up;
    }
    
    public PathTreeNode getDown(){
	return down;
    }
    
    public PathTreeNode getLeft(){
	return left;
    }
    
    public PathTreeNode getRight(){
	return right;
    }
    
    public Square getLocation(){
	return location;
    }
    
    public void setUp(PathTreeNode p){
	up = p;
    }
    
    public void setDown(PathTreeNode p){
	down = p;
    }
    
    public void setRight(PathTreeNode p){
	right = p;
    }
    
    public void setLeft(PathTreeNode p){
	left = p;
    }
}