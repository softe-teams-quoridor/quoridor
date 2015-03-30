
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.io.*;

//Handles the construction of the GUI
public class GameBoardFrame extends JFrame{

    private JFrame gameboard;

    //constructs JFrame
    public GameBoardFrame(GameBoard board){

        //initialize JFrame
        gameboard = new JFrame("Quoridor");
        gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameboard.setSize(1000,700);
        gameboard.setLocationRelativeTo(null);
        

        //creates the grid
        GridLayout game = new GridLayout(10,10);
        gameboard.setLayout(game);

        //draw the original board
        draw(board);
    }
    
    //Removes contents of frame and calls draw in order to update
    public void update(GameBoard board){
        gameboard.getContentPane().removeAll();
        draw(board);

    }
    
    //Constructs the gameboard and makes it visible
    private void draw(GameBoard board){
        topLayer();
        rows(board);
        gameboard.pack();
        gameboard.setFocusableWindowState(false);	//prevents window from
        gameboard.setVisible(true);					
        gameboard.setFocusableWindowState(true);	//autofocusing
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
        labelblank.setBackground(new Color(150, 0, 0));
        labelblank.setPreferredSize(new Dimension(100, 70));
        labelblank.setText("    " + row);
        gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);
        
        //Fills frame with blue Squares if unoccupied 
        for (int i = 1; i < 10; i++){
            if (!board.isOccupied(i-1,rownum-1)){ //Tylor Changed this
                JLabel labelblue = new JLabel();
                labelblue.setOpaque(true);
                labelblue.setBackground(new Color(0, 0, 150));
                labelblue.setPreferredSize(new Dimension(100, 70));
                //labelblue.setBorder(BorderFactory.createLineBorder(Color.black));
                labelblue=setBoarder(labelblue,board.getSquare(i-1,rownum-1));			//Untested
                gameboard.getContentPane().add(labelblue, BorderLayout.CENTER);
            } else {
                printPlayerLabel(board.getPlayer(i-1,rownum-1), board.getSquare(i-1,rownum-1));//And this.
            }
        }
    }
    
    
    //Changes the color of the squares that contain a player and shows 
    //the player name
    private void printPlayerLabel(Player p, Square tSquare){
        Icon pl1 = new ImageIcon("/images/player_1.jpg");
        Icon pl2 = new ImageIcon("/images/player_2.jpg");
        Icon pl3 = new ImageIcon("/images/player_3.jpg");
        Icon pl4 = new ImageIcon("/images/player_4.jpg");
        JLabel label = new JLabel();
        if(p.getPlayerNo() == 1)
            label.setIcon(pl2); 
        else if(p.getPlayerNo() == 2)
            label.setIcon(pl3);
        else if(p.getPlayerNo() == 3)
            label.setIcon(pl4);
        else
            label.setIcon(pl1);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(100, 70));
        //label.setBorder(BorderFactory.createLineBorder(Color.black));
        label=setBoarder(label,  tSquare);
        gameboard.getContentPane().add(label, BorderLayout.CENTER);
    }

    // prints out I-IX labels
    private void topLayer() {
        JLabel [] labels = new JLabel[10];
        // This bit will print a blank for the first spot
        labels[0] = new JLabel();
        labels[0].setOpaque(true);
        labels[0].setBackground(new Color(0, 0, 0));
        labels[0].setPreferredSize(new Dimension(100, 70));
        labels[0].setText("    ");
        gameboard.getContentPane().add(labels[0], BorderLayout.CENTER);
        // Index starts at 1, toNumerals starts at i-1
        for (int i = 1; i < 10; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            labels[i].setBackground(new Color(150, 0, 0));
            labels[i].setPreferredSize(new Dimension(100, 70));
            labels[i].setText("    " + GameEngine.toNumerals(i-1));
            gameboard.getContentPane().add(labels[i], BorderLayout.CENTER);
        }
    }
    
    
    //Ok team, I know one of these loops is redundant, but it was a just in case kind of thing.
    private JLabel setBoarder(JLabel someLabel,Square tSquare){
    	    if(!tSquare.hasWallRight()&&!tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createMatteBorder(1,1, 1, 1, Color.BLACK));
    	    }else if(!tSquare.hasWallRight()&&tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createMatteBorder(1,1, 3, 1, Color.BLACK));
    	    }else if(tSquare.hasWallRight()&&!tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createMatteBorder(1,1, 1, 3, Color.BLACK));
    	    }else if(tSquare.hasWallRight()&&tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createMatteBorder(1,1, 3, 3, Color.BLACK));
    	    }
    	    return someLabel;
    }
}
