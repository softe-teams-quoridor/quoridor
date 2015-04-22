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
    
    public int size(PathTreeNode p){
	if(p.nextAdjFromParent == null && p.firstAdjFromHere == null)
	    return 1;
	if(p.nextAdjFromParent == null)
	    return (1 + size(p.firstAdjFromHere));
	if(p.firstAdjFromHere == null)
	    return (1 + size(p.nextAdjFromParent));
	return (1 + size(p.nextAdjFromParent) + size(p.firstAdjFromHere));
    }
}