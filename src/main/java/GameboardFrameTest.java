public class GameboardFrameTest{
    public static void main(String [] args){
	
	GameBoard board = new GameBoard();
	//constructs GameboardFrame
	
	Square s = board.getSquare(2,3);
	
	byte b = 10;
	
	Player p = new Player ("test", s, b);
	
	board.addPlayer(p, 2, 3);
	
	GameboardFrame test = new GameboardFrame(board);
	
	//draws panel (visual test)
	//exits on close (visual test)
	
	Square s2 = board.getSquare(5,8);
	
	byte b2 = 10;
	
	Player p2 = new Player ("test2", s2, b);
	
	board.addPlayer(p2, 5, 8);
	
	test.update(board);
    }
}