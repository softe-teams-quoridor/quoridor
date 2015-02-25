import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameboardFrame extends JFrame{
    
    private JFrame gameboard;
    
    //constructs JFrame
    public GameboardFrame(GameBoard board){
	
	//initialize JFrame
	gameboard = new JFrame("Quoridor");
	gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gameboard.setSize(1000,700);
	gameboard.setLocationRelativeTo(null);
	
	//creates the grid
	GridLayout game = new GridLayout(10,10);
	gameboard.setLayout(game);
	
	//draw the origional board
	draw(board);	
    }
    
    public void update(GameBoard board){
	gameboard.getContentPane().removeAll();	
	draw(board);
	
    }
    
    private void draw(GameBoard board){
	toplayer();	
	rows(board);
	gameboard.pack();
	gameboard.setVisible(true);
    }
    
    //creates rows A-I
    private void rows(GameBoard board){
	row("A", 1, board);
	row("B", 2, board);
	row("C", 3, board);
	row("D", 4, board);
	row("E", 5, board);
	row("F", 6, board);
	row("G", 7, board);
	row("H", 8, board);
	row("I", 9, board);
    }
    
    //creates a row
    private void row(String row, int rownum, GameBoard board){
	
	JLabel labelblank = new JLabel();
 	labelblank.setOpaque(true);
 	labelblank.setBackground(new Color(0, 0, 30));
 	labelblank.setPreferredSize(new Dimension(100, 70));
 	labelblank.setText("    " + row);
	gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);
	
	for (int i = 1; i < 10; i++){
	    if (!board.isOccupied(i,rownum)){
		JLabel labelblue = new JLabel();
		labelblue.setOpaque(true);
		labelblue.setBackground(new Color(0, 0, 150));
		labelblue.setPreferredSize(new Dimension(100, 70));
		labelblue.setBorder(BorderFactory.createLineBorder(Color.black));
		gameboard.getContentPane().add(labelblue, BorderLayout.CENTER);
	    } else {
		printPlayerLabel(board.getPlayer(i,rownum));
	    }
	}
    }
    
    private void printPlayerLabel(Player p){
	JLabel label = new JLabel();
	label.setOpaque(true);
	if(p.getColor() == 1)
	    label.setBackground(new Color(150, 0, 0));
	else if(p.getColor() == 2)
	    label.setBackground(new Color(0, 150, 0));
	else if(p.getColor() == 3)
	    label.setBackground(new Color(150, 0, 150));
	else
	    label.setBackground(new Color(0, 150, 150));
	label.setPreferredSize(new Dimension(100, 70));
	label.setBorder(BorderFactory.createLineBorder(Color.black));
	label.setText("    " + p.getName());
	gameboard.getContentPane().add(label, BorderLayout.CENTER);
    }

    // converts an int to a string of numerals
    private String numerals(int x) {
        switch (x) {
            case 0: return ""; // empty string
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            case 5: return "V";
            case 6: return "VI";
            case 7: return "VII";
            case 8: return "VIII";
            case 9: return "IX";
            case 10: return "X";
            default: return "@@@@@@@"; // this should never happen
        }
    }

    // prints out I-IX
    private void topLayer() {
        JLabel [] labels = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            labels[i].setBackground(new Color(0, 0, 30));
            labels[i].setPreferredSize(new Dimension(100, 70));
            labels[i].setText("    " + numerals(i));
            gameboard.getContentPane().add(labels[i], BorderLayout.CENTER);
        }
    }

    //prints out I-IX
    private void toplayer(){
	JLabel labelblank = new JLabel();
 	labelblank.setOpaque(true);
 	labelblank.setBackground(new Color(0, 0, 0));
 	labelblank.setPreferredSize(new Dimension(100, 70));
 	labelblank.setText("    ");
 	gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);
	JLabel label1 = new JLabel();
 	label1.setOpaque(true);
 	label1.setBackground(new Color(0, 0, 30));
 	label1.setPreferredSize(new Dimension(100, 70));
 	label1.setText("    I");
 	gameboard.getContentPane().add(label1, BorderLayout.CENTER);
 	JLabel label2 = new JLabel();
 	label2.setOpaque(true);
 	label2.setBackground(new Color(0, 0, 30));
 	label2.setPreferredSize(new Dimension(100, 70));
 	label2.setText("    II");
 	gameboard.getContentPane().add(label2, BorderLayout.CENTER);
 	JLabel label3 = new JLabel();
 	label3.setOpaque(true);
 	label3.setBackground(new Color(0, 0, 30));
 	label3.setPreferredSize(new Dimension(100, 70));
 	label3.setText("    III");
 	gameboard.getContentPane().add(label3, BorderLayout.CENTER);
 	JLabel label4 = new JLabel();
 	label4.setOpaque(true);
 	label4.setBackground(new Color(0, 0, 30));
 	label4.setPreferredSize(new Dimension(100, 70));
 	label4.setText("    IV");
 	gameboard.getContentPane().add(label4, BorderLayout.CENTER);
 	JLabel label5 = new JLabel();
 	label5.setOpaque(true);
 	label5.setBackground(new Color(0, 0, 30));
 	label5.setPreferredSize(new Dimension(100, 70));
 	label5.setText("    V");
 	gameboard.getContentPane().add(label5, BorderLayout.CENTER);
 	JLabel label6 = new JLabel();
 	label6.setOpaque(true);
 	label6.setBackground(new Color(0, 0, 30));
 	label6.setPreferredSize(new Dimension(100, 70));
 	label6.setText("    VI");
 	gameboard.getContentPane().add(label6, BorderLayout.CENTER);
 	JLabel label7 = new JLabel();
 	label7.setOpaque(true);
 	label7.setBackground(new Color(0, 0, 30));
 	label7.setPreferredSize(new Dimension(100, 70));
 	label7.setText("    VII");
 	gameboard.getContentPane().add(label7, BorderLayout.CENTER);
 	JLabel label8 = new JLabel();
 	label8.setOpaque(true);
 	label8.setBackground(new Color(0, 0, 30));
 	label8.setPreferredSize(new Dimension(100, 70));
 	label8.setText("    VIII");
 	gameboard.getContentPane().add(label8, BorderLayout.CENTER);
 	JLabel label9 = new JLabel();
 	label9.setOpaque(true);
 	label9.setBackground(new Color(0, 0, 30));
 	label9.setPreferredSize(new Dimension(100, 70));
 	label9.setText("    IX");
 	gameboard.getContentPane().add(label9, BorderLayout.CENTER);
    }
}
