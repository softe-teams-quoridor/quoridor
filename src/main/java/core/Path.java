public class Path extends GameBoard{
    
    public Square [] path;
    
    public Path(Player p){
	path = findShortestPath(p);
    }
    
       //*************************************************************************
    
    //DO NOT TRUST THIS METHOD YET!!
    
    /**
     * finds the shortest path on the board to the win condition of a player
     * @param player player whose shortest path you wish to find
     * @return Square[] the shortest path to win
     */
     public Square[] findShortestPath(Player p){
	int playerNo = p.getPlayerNo();
	Square current = getPlayerLoc(p);
	
	PathTreeNode root = new PathTreeNode(current, 0);
	
	if(playerNo == 0)
	    root = buildTree0(root);
	else if(playerNo == 1)
	    root = buildTree1(root);
	else if(playerNo == 2)
	    root = buildTree2(root);
	else
	    root = buildTree3(root);
	
	
	return iteratePath(root);
     }
     
     //************************************************************************
     /**
      * finds the shortest path based on the built tree
      * @param PathTreeNode root of tree
      * @return Square[] list of path
      */
     public Square[] iteratePath(PathTreeNode root){
	Square[] path = new Square[81];
	int index = 0;
	
	//This just needs to find which branch of tree is shortest....
	
	while(root.firstAdjFromHere != null || root.nextAdjFromParent != null){
	    if(root.firstAdjFromHere == null){
		path[index] = root.nextAdjFromParent;
		root. = root.nextAdjFromParent;
	    }else if(root.nextAdjFromParent == null){
		path[index] = root.firstAdjFromHere;
		root = root.firstAdjFromHere;
	    }else if(root.size(root.nextAdjFromParent) > root.size(root.firstAdjFromHere)){
		path[index] = root.firstAdjFromHere;
		root = root.firstAdjFromHere;
	    }else{
		path[index] = root.nextAdjFromParent;
		root = root.nextAdjFromParent;
	    }
	    index++;
	}
	
	return path;
     }

     //************************************************************************
     
     /**
      * builds the path tree with player 0's win condition
      * @param PathTreeNode where we start from
      * @return PathTreeNode this is the root of the tree
      */
     public PathTreeNode buildTree0(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor0(reach, root.location);
	
	return buildTree0(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree0
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree0(PathTreeNode root, int i, Square[] reachable){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor0(reach, root.location);
	if(reach == null)
	    return root;
	    
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree0(new PathTreeNode(reach[0], i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree0(new PathTreeNode(reachable[0], i), i++, reachable);
	return root;
     }
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 0
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor0(Square [] reach, Square current){
     
	
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	 
	for(int i = 0; i < reach.length; i++){
	    if(reach[i].getY() < y){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
		    reach[g] = reach[g+1];
		}
	    }
	}
	y++;
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( y == 8)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
     //************************************************************************
     
     /**
      * builds the path tree with player 1's win condition
      * @return PathTreeNode this is the root of the tree
      */
    public PathTreeNode buildTree1(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor1(reach, root.location);
	
	return buildTree1(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree1
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree1(PathTreeNode root, int i, Square[] reachable){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor1(reach, root.location);
	if(reach == null)
	    return root;
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree1(new PathTreeNode(reach[0], i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree1(new PathTreeNode(reachable[0], i), i++, reachable);
	return root;
     }
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 1
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor1(Square [] reach, Square current){
     
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	
	for(int i = 0; i < reach.length; i++)
	    if(reach[i].getY() > y){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
			reach[g] = reach[g+1];
		    }
	    }
	y--;
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( y == 0)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
     
     //************************************************************************
     
     /**
      * builds the path tree with player 2's win condition
      * @return PathTreeNode this is the root of the tree
      */
     public PathTreeNode buildTree2(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor2(reach, root.location);
	
	return buildTree2(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree2
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree2(PathTreeNode root, int i, Square[] reachable){
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor2(reach, root.location);
	if(reach == null)
	    return root;
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree2(new PathTreeNode(reach[0], i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree2(new PathTreeNode(reachable[0],i), i++, reachable);
	return root;
     }
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 2
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor2(Square [] reach, Square current){
     
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	for(int i = 0; i < reach.length; i++)
	    if(reach[i].getX() < x){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
		    reach[g] = reach[g+1];
		}
	    }
	x++;
	
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( x == 8)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
     
     //************************************************************************
     
     /**
      * builds the path tree with player 3's win condition
      * @return PathTreeNode this is the root of the tree
      */
     public PathTreeNode buildTree3(PathTreeNode root){
	
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor3(reach, root.location);
	
	return buildTree3(root, 0, reach);
     } 
     
     //************************************************************************
     
     /**
      * recursive loop for buildTree3
      * @param PathtreeNode root where we are building from 
      * @param integer that doesnt really do anything
      * @param Square[] reachable squares from the root
      * @return PathTreeNode root of built tree
      */
     private PathTreeNode buildTree3(PathTreeNode root, int i, Square[] reachable){
	Square [] reach = GameEngine.reachableAdjacentSquares(this, root.location);
	
	reach = adjustFor3(reach, root.location);
	if(reach == null)
	    return root;
	if(GameEngine.reachableAdjacentSquares(this, reach[0]).length != 0){
	    root.firstAdjFromHere = buildTree3(new PathTreeNode(reach[0],i), i++, reach);
	    return root;
	}
	
	for(int t = 0; t < reachable.length; t++){
	    reachable[t] = reachable[t+1];
	}
	
	root.nextAdjFromParent = buildTree3(new PathTreeNode(reachable[0],i), i++, reachable);
	return root;
     }
     
     
     //************************************************************************
     
     /**
      * adjusts which squares get priority for player 3
      * @param Square[] the reachable square
      * @return Square[] the prioritied reachable squares
      */
     private Square[] adjustFor3(Square [] reach, Square current){
     
	int x = current.getX();
	int y = current.getY();
	 
	Square[] secondChoice = new Square[reach.length];
	int index = 0;
	for(int i = 0; i < reach.length; i++)
	    if(reach[i].getX() > x){
		secondChoice[index] = reach[i];
		index++;
		for(int g = i; g < reach.length; g++){
			reach[g] = reach[g+1];
		}
	    }
	x--;
	
	
	Square newLoc = new Square(x,y);
	
	int done = 0;
	if( x == 0)
	    done++;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] != null && reach[i].equals(newLoc)){
		 reach = new Square[1];
		 reach[0] = newLoc;
		 if(done>0)
		    return null;
		 return reach;
	    }
	}
	
	int j = 0;
	
	for(int i = 0; i < reach.length; i++){
	    if(reach[i] == null)
		j++;
	}
	
	if(j == (reach.length))
	    return secondChoice;
	
	return reach;
     
     }
}