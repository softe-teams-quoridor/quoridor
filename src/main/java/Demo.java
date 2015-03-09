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
	int x=1;
	int y=1;
	for(y=0; y<9; y++)
	for(x=0; x<9; x++){
		nBoard.addPlayer(tylor, x, y);
		g1.update(nBoard);
		Thread.sleep(50);
		nBoard.removePlayer(x,y);
	}
	}
	

}
