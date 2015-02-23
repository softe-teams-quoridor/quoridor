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
	
	Player p2 = new Player ("test2", s2, b2);
	
	board.addPlayer(p2, 5, 8);
	
	Square s3 = board.getSquare(7,1);
	
	byte b3 = 10;
	
	Player p3 = new Player ("test3", s3, b3);
	
	board.addPlayer(p3, 7, 1);
	
	Square s4 = board.getSquare(4,6);
	
	byte b4 = 10;
	
	Player p4 = new Player ("test4", s4, b4);
	
	board.addPlayer(p4, 4, 6);
	
	test.update(board);
    }
}