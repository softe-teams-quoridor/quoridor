public class PathTreeNode{
    
    public PathTreeNode nextAdjFromParent;
    public PathTreeNode firstAdjFromHere;
    public Square location;
    public int i;
    
    public PathTreeNode(Square s, int i){
	nextAdjFromParent = null;
	firstAdjFromHere = null;
	location = s;
	this.i = i;
    }
    
}