import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Demo{
	public static void main(String[]args)throws InterruptedException{
	GameBoard nBoard=new GameBoard();
	
	Player tylor=new Player("Tylor",nBoard.getSquare(1,1),10);
	Player nTylor=new Player("Not Tylor", nBoard.getSquare(2,2),10);
	Pawn p1=new Pawn(1, 1);
	Pawn p2=new Pawn(2,2);
	GameboardFrame g1=new GameboardFrame(nBoard);
	nBoard.addPlayer(tylor, 5, 1);
	g1.update(nBoard);
	Thread.sleep(1000);
	nBoard.addPlayer(nTylor,5,8);
	g1.update(nBoard);
	Thread.sleep(1000);
	nBoard.removePlayer(5,1);
	nBoard.addPlayer(tylor,5,2);
	g1.update(nBoard);
	Thread.sleep(1000);
	nBoard.removePlayer(5,8);
	nBoard.addPlayer(nTylor,5,7);
	g1.update(nBoard);
	Thread.sleep(1000);
	nBoard.removePlayer(5,2);
	nBoard.addPlayer(tylor,4,2);
	g1.update(nBoard);
	Thread.sleep(1000);	
	nBoard.removePlayer(5,7);
	nBoard.addPlayer(nTylor,6,7);
	g1.update(nBoard);
	Thread.sleep(1000);	
	nBoard.removePlayer(4,2);
	nBoard.addPlayer(tylor,4,3);
	g1.update(nBoard);
	Thread.sleep(1000);	
	nBoard.removePlayer(6,7);
	nBoard.addPlayer(nTylor,6,6);
	g1.update(nBoard);
	Thread.sleep(1000);	
	
	
	
	
	

	
	}
	

}
