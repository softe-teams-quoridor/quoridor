import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameboardFrame extends JFrame{
    public GameboardFrame(){
	//oracle.com
	JFrame gameboard = new JFrame("Quoridor");
	gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	JMenuBar menuBar = new JMenuBar();
	menuBar.setOpaque(true);
        menuBar.setBackground(new Color(0, 0, 20));
        menuBar.setPreferredSize(new Dimension(1000, 20));
	gameboard.setJMenuBar(menuBar);
	
	GridLayout game = new GridLayout(10,9);
	gameboard.setLayout(game);
	
	for (int i = 0; i < 40; i++){
	    //Create a label to put in the content pane.
	    JLabel label = new JLabel();
	    label.setOpaque(true);
	    label.setBackground(new Color(0, 0, 0));
	    label.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(label, BorderLayout.CENTER);
	    JLabel label2 = new JLabel();
	    label2.setOpaque(true);
	    label2.setBackground(new Color(0, 0, 100));
	    label2.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(label2, BorderLayout.CENTER);
	    
	}
	
	JLabel label = new JLabel();
	label.setOpaque(true);
	label.setBackground(new Color(0, 0, 0));
	label.setPreferredSize(new Dimension(100, 70));
	gameboard.getContentPane().add(label, BorderLayout.CENTER);
	
	for (int i = 0; i < 2; i++){
	    JLabel label2 = new JLabel();
	    label2.setOpaque(true);
	    label2.setBackground(new Color(0, 0, 20));
	    label2.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(label2, BorderLayout.CENTER);
	}
	
	gameboard.add(new JButton("Move Down"));
	gameboard.add(new JButton("Move Left"));
	gameboard.add(new JButton("Move Right"));
	gameboard.add(new JButton("Move Up"));
	gameboard.add(new JButton("Place a Wall"));
	
	for (int i = 0; i < 2; i++){
	    JLabel label2 = new JLabel();
	    label2.setOpaque(true);
	    label2.setBackground(new Color(0, 0, 20));
	    label2.setPreferredSize(new Dimension(100, 70));
	    gameboard.getContentPane().add(label2, BorderLayout.CENTER);
	}
	
	gameboard.setSize(1000,700);
	gameboard.setLocationRelativeTo(null);
	
	gameboard.pack();
	gameboard.setVisible(true);
    }
}