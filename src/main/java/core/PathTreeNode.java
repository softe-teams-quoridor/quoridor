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
     
    public int size(){
	return size(this);
    }
    
    public int size(PathTreeNode this){
	if(this.nextAdjFromParent == null && this.firstAdjFromHere == null)
	    return 1;
	if(this.nextAdjFromParent == null)
	    return (1 + size(this.firstAdjFromHere));
	if(this.firstAdjFromHere == null)
	    return (1 + size(this.nextAdjFromParent));
	return (1 + size(this.nextAdjFromParent) + size(this.firstAdjFromHere));
    }
}