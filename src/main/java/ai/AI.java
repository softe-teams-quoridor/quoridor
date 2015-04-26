/* This is the actual AI */

public class AI implements QuoridorAI{

    /* constructor:
	-no parameters */
    public AI(){}
    
    /* get a move string */
    public String getMove(GameBoard b, Player p){
	
	//get our player's current location
	Square current = b.getPlayerLoc(p);
	int x = current.getX();
	int y = current.getY();
	
	//get number of walls we have
	int numWalls = p.getNumWalls();
	
	//if the player doesnt have a wall, there is no use checking for 
	// a need to use one. move on
	if(numWalls > 0){
	    //check if a wall needs to be used
	    String useWall = checkWalls();
	    
	    //if null, move on
	    if(useWall != null)
		return useWall;
	}
	
	//get recheable squares from our location
	Square[] reacheable = GameEngine.reachableAdjacentSquares(b, current);
	Square[] secondChoice = new Square[reacheable.length];
	int index = 0;
	
	int playerno = p.getPlayerNo();
	
	//based on player no, set any moves that are going backwards aside
	//as secondchioce. Also, set x and y for ideal move
	if(playerno == 0){
	    for(int i = 0; i < reacheable.length; i++)
		if(reacheable[i].getY() < y){
		    secondChoice[index] = reacheable[i];
		    index++;
		    reacheable[i] = null;
		}
	    y++;
	}else if(playerno == 1){	
	    for(int i = 0; i < reacheable.length; i++)
		if(reacheable[i].getY() > y){
		    secondChoice[index] = reacheable[i];
		    index++;
		    reacheable[i] = null;
		}
	    y--;
	}else if(playerno == 2){	
	    for(int i = 0; i < reacheable.length; i++)
		if(reacheable[i].getX() < x){
		    secondChoice[index] = reacheable[i];
		    index++;
		    reacheable[i] = null;
		}
	    x++;
	}else{	
	    for(int i = 0; i < reacheable.length; i++)
		if(reacheable[i].getX() > x){
		    secondChoice[index] = reacheable[i];
		    index++;
		    reacheable[i] = null;
		}
	    x--;
	}
	
	//create a square object based on ideal move
	Square newLoc = new Square(x,y);
	
	//if we have the ideal move, return that
	for(int i = 0; i < reacheable.length; i++){
	    if(reacheable[i] != null && reacheable[i].equals(newLoc))
		 return newLoc.toString();
	}
	
	//otherwise return a first choice move
	for(int i = 0; i < reacheable.length; i++){
	    if(reacheable[i] != null)
		 return reacheable[i].toString();
	}
	
	//if both of these are busts, we have to have a second choice move.
	//All players have at least one move. So return that.
	return secondChoice[0].toString();
    }

    /* reset an ai to its initial state; used for starting a new game */
    public void reset(){}

    /* used in names 
     * don't make this return anything longer than 10 characters though LOOOOL
     */
    public String toString(){
	return "teams";
    }
    
    /* check for a need to use a wall. If there is a need, 
        return a string that says to use one. Otherwise return null */
    public String checkWalls(){
	return null;
    }
}
