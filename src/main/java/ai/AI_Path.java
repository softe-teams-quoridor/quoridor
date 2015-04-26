public class AI_Path implements QuoridorAI{
    
    /* constructor:
	-no parameters */
    public AI_Path(){}
    
    /* get a move string */
    public String getMove(GameBoard b, Player p){
	int startNum = b.getStartNumPlayers();
	//if (startNum == 2)
	//    return getMove2(b,p);
	//else
	    return getMove4(b,p);
    }
    
//     public String getMove2(GameBoard b, Player p){
// 	//my shortest path
// 	Square[] me = b.findShortestPath(p);
// 	//what player i am
// 	int num = p.getPlayerNo();
// 	Player player;
// 	
// 	//if im player 0, they are player 1 and vice versa
// 	if(num == 0)
// 	    player = b.getPlayer(1);
// 	else
// 	    player = b.getPlayer(0);
// 	
// 	//thier path
// 	Square[] other = b.findShortestPath(player);
// 	
// 	//if my path is shorter than thiers, continue on
// 	if(other.length > me.length)
// 	    return me[0].toString();
// 	
// 	//thier shortest path is shorter
// 	//we need to place a wall
// 	//so for each square in thier shortest path
// 	for(int i = 0; i < other.length; i++){
// 	    //check if you can block that square and the one to its right
// 	    int x = other[i].getX();
// 	    int y = other[i].getY();
// 	    
// 	    Square temp = new Square(x++, y);
// 	    
// 	    Square [] temporary = new Square [2];
// 	    temporary[0] = other[i];
// 	    temporary[1] = temp;
// 	    
// 	    //if it is a valid wall, place
// 	    if(GameEngine.validateWall(b, temporary))
// 		return ("(" + temporary[0].toString() +"," + temporary[1].toString() + ")");
// 	}	
// 	
// 	//no legal walls. we have to continue
// 	return me[0].toString();
//     }
    
    public String getMove4(GameBoard b, Player p){
	Square loc = b.getPlayerLoc(p);
	
	Square[] reach = GameEngine.reachableAdjacentSquares(b, loc);
	
	return reach[0].toString();
    }
    
    /* reset an ai to its initial state; used for starting a new game */
    public void reset(){}
    
    /* used in names 
     * don't make this return anything longer than 10 characters though LOOOOL
     */
    public String toString(){
	return "path";
    }
}