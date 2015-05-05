public class AI_Maybe implements QuoridorAI {

    /**
      * Constructor.
      */
    public AI_Maybe() { }
    
    /**
      * Returns a move.
      *     @param b GameBoard to play on
      *     @param p Player that is moving
      */
    public String getMove(GameBoard b, Player p) {
        Graph virtualBoard = new Graph(b.COLUMNS * b.ROWS);
        Square[] path = virtualBoard.buildPath(b,p);
        
        int pno = p.getPlayerNo();
        
        Square[] pathShort = path;
        int winningPlayer = pno;
        
        for(int i = 0; i < b.getStartNumPlayers(); i++){
            Square [] temp = null;
            if(i != pno)
                temp = virtualBoard.buildPath(b,p);
            if(temp != null && temp.length < (pathShort.length-1)){
                pathShort = temp;
                winningPlayer = i;
            }
        }
        
        if(winningPlayer != pno){
            String block = blockPlayer(b, winningPlayer, pathShort);
            if(block != null)
                return block;
        }
        
        return path[0].toString();
    }
    
    public String blockPlayer(GameBoard b, int pno, Square[] path){
        Square[] wall = new Square [2];
        wall[0] = path [0];
        if(pno == 0 || pno == 1){
            int x = path[0].getX();
            x++;
            if(x > 8)
              x-=2;
            int y = path[0].getY();
            if(pno == 1)
		y--;
	    if(y < 0)
		y+=2;
            wall[1] = b.getSquare(x, y);
        } else {
            int y = path[0].getY();
            y++;
            if(y > 8)
              y-=2;
            int x = path[0].getX();
            if(pno == 3)
		x--;
	    if(x < 0)
		x+=2;
            wall[1] = b.getSquare( x, y);
        }
        
        if(GameEngine.validateWall(b, wall))
            return ("(" + path[0].toString() + "," + path[1].toString() + ")");
        
        return null;
    }

    /**
      * Reset an ai to its initial state.
      * Used for starting a new game.
      */
    public void reset() { }

    /**
      * Returns name of AI.
      */
    public String toString() {
        return "Maybe?";
    }
}
