public class GameBoardFrameTest{
    public static void main(String [] args){
	
	GameBoard board = new GameBoard();
	//constructs GameBoardFrame
	
	Square s = board.getSquare(2,3);
	
	byte b = 10;
	
	Player p = new Player ("test", s, b);
	
	board.addPlayer(p);
	
	GameBoardFrame test = new GameBoardFrame(board);
	
	//draws panel (visual test)
	//exits on close (visual test)
	
	Square s2 = board.getSquare(5,8);
	
	byte b2 = 10;
	
	Player p2 = new Player ("test2", s2, b2);
	
	board.addPlayer(p2);
	
	Square s3 = board.getSquare(7,1);
	
	byte b3 = 10;
	
	Player p3 = new Player ("test3", s3, b3);
	
	board.addPlayer(p3);
	
	Square s4 = board.getSquare(4,6);
	
	byte b4 = 10;
	
	Player p4 = new Player ("test4", s4, b4);
	
	board.addPlayer(p4);
	
	test.update(board);
    }
}
