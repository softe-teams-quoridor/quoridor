import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameboardFrame extends JFrame{
    public GameboardFrame(GameBoard board){
	//oracle.com
	JFrame gameboard = new JFrame("Quoridor");
	gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//menu bar?
	JMenuBar menuBar = new JMenuBar();
	menuBar.setOpaque(true);
        menuBar.setBackground(new Color(0, 0, 20));
        menuBar.setPreferredSize(new Dimension(1000, 20));
	gameboard.setJMenuBar(menuBar);
	
	//creates the grid
	GridLayout game = new GridLayout(10,10);
	gameboard.setLayout(game);
	
	toplayer(gameboard);
	
	rows(gameboard);
	
	gameboard.setSize(1000,700);
	gameboard.setLocationRelativeTo(null);
	
	//makes gameboard
	gameboard.pack();
	gameboard.setVisible(true);
    }
    
    //creates rows A-I
    private void rows(JFrame gameboard){
	row("A", 1, gameboard);
	row("B", 0, gameboard);
	row("C", 1, gameboard);
	row("D", 0, gameboard);
	row("E", 1, gameboard);
	row("F", 0, gameboard);
	row("G", 1, gameboard);
	row("H", 0, gameboard);
	row("I", 1, gameboard);
    }
    
    //creates a row
    private void row(String row, int blueorblackfirst, JFrame gameboard){
	
	JLabel labelblank = new JLabel();
 	labelblank.setOpaque(true);
 	labelblank.setBackground(new Color(0, 0, 0));
 	labelblank.setPreferredSize(new Dimension(100, 70));
 	labelblank.setText("    " + row);
	gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);
	
	if (blueorblackfirst == 0){
	    JLabel labelblack1 = new JLabel();
	    labelblack1.setOpaque(true);
	    labelblack1.setBackground(new Color(0, 0, 0));
	    labelblack1.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(labelblack1, BorderLayout.CENTER);
	}
	
	for (int i = 0; i < 4; i++){
	    
	    JLabel labelblack = new JLabel();
	    labelblack.setOpaque(true);
	    labelblack.setBackground(new Color(0, 0, 150));
	    labelblack.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(labelblack, BorderLayout.CENTER);
	    JLabel labelblue = new JLabel();
	    labelblue.setOpaque(true);
	    labelblue.setBackground(new Color(0, 0, 0));
	    labelblue.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(labelblue, BorderLayout.CENTER);
	}
	
	if (blueorblackfirst == 1){
	    JLabel labelblue1 = new JLabel();
	    labelblue1.setOpaque(true);
	    labelblue1.setBackground(new Color(0, 0, 150));
	    labelblue1.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(labelblue1, BorderLayout.CENTER);
	}
    }
    
    //prints out I-IX
    private void toplayer(JFrame gameboard){
	JLabel labelblank = new JLabel();
 	labelblank.setOpaque(true);
 	labelblank.setBackground(new Color(0, 0, 0));
 	labelblank.setPreferredSize(new Dimension(100, 70));
 	labelblank.setText("    ");
 	gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);
	JLabel label1 = new JLabel();
 	label1.setOpaque(true);
 	label1.setBackground(new Color(0, 0, 0));
 	label1.setPreferredSize(new Dimension(100, 70));
 	label1.setText("    I");
 	gameboard.getContentPane().add(label1, BorderLayout.CENTER);
 	JLabel label2 = new JLabel();
 	label2.setOpaque(true);
 	label2.setBackground(new Color(0, 0, 0));
 	label2.setPreferredSize(new Dimension(100, 70));
 	label2.setText("    II");
 	gameboard.getContentPane().add(label2, BorderLayout.CENTER);
 	JLabel label3 = new JLabel();
 	label3.setOpaque(true);
 	label3.setBackground(new Color(0, 0, 0));
 	label3.setPreferredSize(new Dimension(100, 70));
 	label3.setText("    III");
 	gameboard.getContentPane().add(label3, BorderLayout.CENTER);
 	JLabel label4 = new JLabel();
 	label4.setOpaque(true);
 	label4.setBackground(new Color(0, 0, 0));
 	label4.setPreferredSize(new Dimension(100, 70));
 	label4.setText("    IV");
 	gameboard.getContentPane().add(label4, BorderLayout.CENTER);
 	JLabel label5 = new JLabel();
 	label5.setOpaque(true);
 	label5.setBackground(new Color(0, 0, 0));
 	label5.setPreferredSize(new Dimension(100, 70));
 	label5.setText("    V");
 	gameboard.getContentPane().add(label5, BorderLayout.CENTER);
 	JLabel label6 = new JLabel();
 	label6.setOpaque(true);
 	label6.setBackground(new Color(0, 0, 0));
 	label6.setPreferredSize(new Dimension(100, 70));
 	label6.setText("    VI");
 	gameboard.getContentPane().add(label6, BorderLayout.CENTER);
 	JLabel label7 = new JLabel();
 	label7.setOpaque(true);
 	label7.setBackground(new Color(0, 0, 0));
 	label7.setPreferredSize(new Dimension(100, 70));
 	label7.setText("    VII");
 	gameboard.getContentPane().add(label7, BorderLayout.CENTER);
 	JLabel label8 = new JLabel();
 	label8.setOpaque(true);
 	label8.setBackground(new Color(0, 0, 0));
 	label8.setPreferredSize(new Dimension(100, 70));
 	label8.setText("    VIII");
 	gameboard.getContentPane().add(label8, BorderLayout.CENTER);
 	JLabel label9 = new JLabel();
 	label9.setOpaque(true);
 	label9.setBackground(new Color(0, 0, 0));
 	label9.setPreferredSize(new Dimension(100, 70));
 	label9.setText("    IX");
 	gameboard.getContentPane().add(label9, BorderLayout.CENTER);
    }
}