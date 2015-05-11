
/* message to author: getStartNumPlayers has been replaced
   with numPlayersRemaining. it returns an int of the number
   of players still playing the game. */


public class AI_IDK implements QuoridorAI{

    /* constructor:
        -no parameters */
    public AI_IDK(){}
    
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
            String useWall = checkWalls(b, p, current);
    
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
     * return a string that says to use one. Otherwise return null 
     */
    public String checkWalls(GameBoard b, Player p, Square current){

        //get which player we are
        int playerNo = p.getPlayerNo();

        //save players distances. Start with them at -1, if 
        //player exists the number will become positive.
        //int [] distances = new int [b.getStartNumPlayers()];
        int [] distances = new int [b.numPlayersRemaining()];
        for(int i = 0; i < b.numPlayersRemaining(); i++){
            distances[i] = -1;
        }

        //for each player calculate distance to win.
        //(this is arbitrary, doesnt account for walls 
        // in the way of a players win.)
        for(int i = 0; i < b.numPlayersRemaining(); i++){
    
            //get the player
            Player tempP = b.getPlayer(i);
    
            //if there is no player, continue to the next one
            if (tempP == null)
                continue;

            //otherwise get its player no.
            int tempNo = tempP.getPlayerNo();
            
            //and location
            Square tempLoc = b.getPlayerLoc(tempP);
    
            //and based on its num, calculate how far from win condition
            if(tempNo == 0)
                distances[i] = (8 - tempLoc.getY());
            else if(tempNo == 1)
                distances[i] = (tempLoc.getY());
            else if(tempNo == 2)
                distances[i] = (8 - tempLoc.getX());
            else
                distances[i] = (tempLoc.getX());
        }

        //create a value for which player has the least distance til win
        //and set it for the first one.
        int leastDist = 0;

        //now, based on earlier calculations. figure out which player it is
        for (int i = 1; i < b.numPlayersRemaining(); i++){
            if( distances[leastDist] > distances[i] )
                leastDist = i;
        }

        distances[playerNo]--;

        //if which player we are has the same distance, set leastDist to us
        if(distances[playerNo] == distances[leastDist])
            leastDist = playerNo;
    
        //if leastDist == playerNo, we do not need to use a wall. return null
        if(leastDist == playerNo)
            return null;
    
        //someone is beating us.

        //if nothing is returned by now, no wall is to be used.
        return null;
    }
}
