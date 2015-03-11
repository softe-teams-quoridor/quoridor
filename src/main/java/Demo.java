import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Demo{
    static Scanner UserInput = new Scanner (System.in);
    public static void main(String[]args)throws InterruptedException{
        GameBoard nBoard=new GameBoard();
        Player tylor=new Player("Tylor",nBoard.getSquare(1,1),10);
        Player nTylor=new Player("Not Tylor", nBoard.getSquare(2,2),10);
        GameboardFrame g1=new GameboardFrame(nBoard);
        int x=4, y=8, iExit=0, i = 0;
        while (iExit==0){
	    if(y==0)
	    {
		iExit=1;
	    }
	    nBoard.addPlayer(tylor, x, y);
	    g1.update(nBoard);
	    Thread.sleep(50);
	    nBoard.removePlayer(x,y);
	    i = UserInput.nextInt();
	    switch (i){
		case 8: y--;
		    break;
		    
		case 2: y++;
		    break;
		    
		case 4: x--;
		    break;
		
		case 6: x++;
		    break;
	    }
            //for(x=0; x<9; x++){
	}
    }
} //End Demo
