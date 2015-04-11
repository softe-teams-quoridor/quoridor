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
	
	//get recheable squares from our location
	Square[] reacheable = GameEngine.reachableAdjacentSquares(b, current);
	Square[] secondChoice = new Square[reacheable.length];
	int index = 0;
	
	int playerno = p.getPlayerNo();
		
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
	
	Square newLoc = new Square(x,y);
	
	for(int i = 0; i < reacheable.length; i++){
	    if(reacheable[i] != null && reacheable[i].equals(newLoc))
		 return newLoc.toString();
	}
	
	for(int i = 0; i < reacheable.length; i++){
	    if(reacheable[i] != null)
		 return reacheable[i].toString();
	}
	
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
}
