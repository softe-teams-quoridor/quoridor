public class GameboardFrameTest{
    public static void main(String [] args){
	
	GameBoard board = new GameBoard();
	//constructs GameboardFrame
	
	Square s = board.getSquare(2,3);
	
	byte b = 10;
	
	Player p = new Player ("test", s, b);
	
	board.addPlayer(p);
	
	GameboardFrame test = new GameboardFrame(board);
	
	//draws panel (visual test)
	//exits on close (visual test)
    }
}