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
	Square [] reachable = GameEngine.reachableAdjacentSquares(b, current);
	int playerno = p.getPlayerNo();

	Square newLoc = choose(playerno);
	
	if(b.isOccupied(x,y))
	    newLoc = null;
	
	for(int i = 0; i < reachable.length; i++)
	    if(reachable[i].equals(newLoc))
		 return newLoc.toString();
	
	return reachable[0].toString();
    }

    private Square choose(int playerno){
	if(playerno == 0)
	    y++;
	else if(playerno == 1)
	    y--;
	else if(playerno == 2)
	    x++;
	else
	    x--;
	
	return new Square(x,y);
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