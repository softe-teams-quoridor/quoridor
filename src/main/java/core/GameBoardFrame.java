/*   GameBoardFrame.java - CIS405 - teams
 * ____________________________________________________________________________
 *
 *   GameBoardFrame object to construct a GUI for the Quoridor game. 
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * PUBLIC:
 *
 * GameBoardFrame()                   --> Default Constructor 
 * update()   					    --> Removes contents of board and updates locations 
 * closeWindow()    			    --> Closes the window at the end of a game
 *
 * PRIVATE:
 * draw()						        --> Constructs the gameboard and makes it visible
 * row()       						--> creates a single row of the GameBoardFrame
 * printPlayerLabel()			        --> Shows an image of a pawn in player squares
 * topLayer()						--> draws the borders of the gridlayout
 * JLabel setBoarder()			--> sets the borders of the  grid
 * makeInfoPane()				--> GOING TO make an info pane on the RHS of grid
 *
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.io.*;

//Handles the construction of the GUI
public class GameBoardFrame extends JFrame{

    private JFrame gameboard;
    private int numPlayers;
    private Queue<Player> players;

    //constructs JFrame
    public GameBoardFrame(GameBoard board, Queue<Player> players) {
        numPlayers = players.size();
        this.players = players;
	  
        //initialize JFrame
        gameboard = new JFrame("Quoridor");
        gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameboard.setSize(1000,700);
        gameboard.setLocationRelativeTo(null);
        

        //creates the grid
        GridLayout game = new GridLayout(10,11);
        gameboard.setLayout(game);

        //draw the original board
        draw(board);
    }
    
    
    //******************************************************************************************
    
    
    //Removes contents of frame and calls draw in order to update
    public void update(GameBoard board){
        Player p = GameEngine.getWinner(board, players);
        if(p != null){
	    //print out win
	    JOptionPane.showMessageDialog(gameboard, p.getName() + " HAS WON!!!");
	    closeWindow();
        } else{
	    gameboard.getContentPane().removeAll();
	    draw(board);
	}
    }
    
    /** dan's version: with colours for specific squares!!
     * Removes contents of frame and calls draw in order to update
     */
    public void update(GameBoard board, Square [] reachable){
        Player p = GameEngine.getWinner(board, players);
        if(p != null){
	    //print out win
	    JOptionPane.showMessageDialog(gameboard, p.getName() + " HAS WON!!!");
	    closeWindow();
        } else{
	    gameboard.getContentPane().removeAll();
	    draw(board, reachable);
	}
    }
    
    
    //******************************************************************************************
    

    //Constructs the gameboard and makes it visible
    private void draw(GameBoard board) {
        topLayer(board);

        //creates rows A-I
        for (int i = 0; i < 9; i++) {
            row(i, board);
        }

        gameboard.pack();
        gameboard.setFocusableWindowState(false);	//prevents window from
        gameboard.setVisible(true);					
        gameboard.setFocusableWindowState(true);	//autofocusing
    }
    
    
    //***********************************************************************************
    
    
    /** dan's version: with colours for specific squares!!
     * Constructs the gameboard and makes it visible
     */
    private void draw(GameBoard board, Square [] reachable) {
        topLayer(board);

        // creates rows A-I
        for (int i = 0; i < 9; i++) {
            row(i, board, reachable);
        }

        gameboard.pack();
        gameboard.setFocusableWindowState(false);	//prevents window from
        gameboard.setVisible(true);					
        gameboard.setFocusableWindowState(true);	//autofocusing
    }
    
    
    //*************************************************************************
    

    //creates a row
    private void row(int row, GameBoard board) {
        JLabel labelblank = new JLabel();
        labelblank.setOpaque(true);
        labelblank.setBackground(new Color(150, 0, 0));
        labelblank.setPreferredSize(new Dimension(100, 70));
        labelblank.setText("" + GameEngine.toLetters(row));
        labelblank.setHorizontalAlignment(SwingConstants.CENTER);
        gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);
        
        //Fills frame with GREY Squares if unoccupied 
        for (int i = 0; i < 9; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setPreferredSize(new Dimension(100, 70));
            label=setBoarder(label,board.getSquare(i, row));
            if (board.isOccupied(i, row)) {
                printPlayerLabel(board.getPlayer(i, row), label);
            } else {
                label.setBackground(new Color(140, 130, 130));
            }
            gameboard.getContentPane().add(label, BorderLayout.CENTER);
        }
        for (int i = 9; i < 11; i++) {
         	 JLabel label = new JLabel();
         	  label.setOpaque(true);
         	  label.setPreferredSize(new Dimension(100, 70));
         	  label.setBackground(new Color(0, 0, 0));
         	  gameboard.getContentPane().add(label,BorderLayout.CENTER);
         }
    }
    
    
    
    //********************************************************************************

    /** dan's version: with colours for specific squares!!
     * creates a row
     * squares passed in via the array are drawn white (unless occupied)
     */
    private void row(int row, GameBoard board, Square [] reachable) {
        JLabel labelblank = new JLabel();
        labelblank.setOpaque(true);
        labelblank.setBackground(new Color(150, 0, 0));
        labelblank.setPreferredSize(new Dimension(100, 70));
        labelblank.setText(""+GameEngine.toLetters(row));
        labelblank.setHorizontalAlignment(SwingConstants.CENTER);
        gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);

        //Fills frame with GREY Squares if unoccupied 
        for (int i = 0; i < 9; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setPreferredSize(new Dimension(100, 70));
            label=setBoarder(label,board.getSquare(i, row));

            // different colours depending on whether it is occupied
            if (board.isOccupied(i, row)) {
                printPlayerLabel(board.getPlayer(i, row), label);
            } else if (Arrays.asList(reachable)
                                .contains(board.getSquare(i, row))) {
                label.setBackground(Color.WHITE);
            } else {
                label.setBackground(new Color(140, 130, 130));
            }

            gameboard.getContentPane().add(label, BorderLayout.CENTER);
        }
         for (int i = 9; i < 11; i++) {
         	 JLabel label = new JLabel();
         	  label.setOpaque(true);
         	  label.setPreferredSize(new Dimension(100, 70));
         	  gameboard.getContentPane().add(label,BorderLayout.CENTER);
         }
    }
    
    
    
    //*********************************************************************************

    /** 
     * Shows an image of a pawn in the player squares. 
     * No more Text overlay.
     */ 
    private void printPlayerLabel(Player p, JLabel label) { 
    	    ImageIcon bg=new ImageIcon("./../../images/player_4_scaled.jpg");
    	    if(p.getPlayerNo()==0){
    	    	    bg=new ImageIcon("./../../images/player_1_scaled.jpg");
    	    }else if(p.getPlayerNo()==1){
    	    	    bg=new ImageIcon("./../../images/player_2_scaled.jpg");
    	    }else if(p.getPlayerNo()==2){
    	    	    bg=new ImageIcon("./../../images/player_3_scaled.jpg");
    	    }else if(p.getPlayerNo()==3){
    	    	    bg=new ImageIcon("./../../images/player_4_scaled.jpg");
    	    }
    	    label.setIcon(bg);
    	   //label.setIconTextGap(-100);
    	 label.setLayout(new FlowLayout());
        //label.setBackground(new Color(0, 200, 200)); 
        //label.setText("     " + p.getName());
        	label.setOpaque(true); 
        	label.setLayout(null); 
        label.setPreferredSize(new Dimension(100, 70)); 
        gameboard.getContentPane().add(label, BorderLayout.CENTER); 
    } 


    //*****************************************************************************
    
    
    // prints out I-IX labels
    private void topLayer(GameBoard board) {
        assert (board != null);
        JLabel [] labels = new JLabel[12];
        // This bit will print a blank for the first spot
        labels[0] = new JLabel();
        labels[0].setOpaque(true);
        labels[0].setBackground(new Color(255, 255, 255));
        labels[0].setPreferredSize(new Dimension(100, 70));

        // display the number of walls for each player
        // Tylor changed and maybe broke this part.
        //String labelText = "";
        if (numPlayers==2){
        	labels[0].setLayout(new GridLayout(2,1));
        }else{
        	labels[0].setLayout(new GridLayout(4,1));
        }
        
        	for(int i=0; i< numPlayers; i++){
        		Player p=board.getPlayer(i);
        		JLabel onePlayerWall=new JLabel("    P"+i+1+" "+ p.getNumWalls());
        		labels[0].add(onePlayerWall);
        }

        gameboard.getContentPane().add(labels[0], BorderLayout.CENTER);
        // Index starts at 1, toNumerals starts at i-1
        for (int i = 1; i < 10; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            labels[i].setBackground(new Color(150, 0, 0));
            labels[i].setPreferredSize(new Dimension(100, 70));
            labels[i].setText(GameEngine.toNumerals(i-1));
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            gameboard.getContentPane().add(labels[i], BorderLayout.CENTER);
        }
        for (int i=10; i<12; i++){
        labels[i]=new JLabel();
        labels[i].setOpaque(true);
        labels[i].setBackground(new Color(150, 0, 0));
        labels[i].setPreferredSize(new Dimension(100, 70));
        gameboard.getContentPane().add(labels[i],BorderLayout.CENTER);
        }
    }
    
    //****************************************************************************************
    
    
    //Sets the border around squares that have walls.
    //makes walls show up as red compound borders.
    private JLabel setBoarder(JLabel someLabel,Square tSquare){
    	    if(!tSquare.hasWallRight()&&!tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createMatteBorder(1,1, 1, 1, Color.BLACK));
    	    	    
    	    }else if(!tSquare.hasWallRight()&&tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,1, 0, 1, Color.BLACK),
    	    	    	    BorderFactory.createMatteBorder(0,0,6,0,new Color(150, 0, 0))));
    	    	    
    	    }else if(tSquare.hasWallRight()&&!tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,1, 1, 0, Color.BLACK),
    	    	    	    BorderFactory.createMatteBorder(0,0, 0, 6, new Color(150, 0, 0))));
    	    	    
    	    }else if(tSquare.hasWallRight()&&tSquare.hasWallBottom()){
    	    	    someLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,1,0,0,Color.BLACK),
    	    	    	    BorderFactory.createMatteBorder(0,0, 6, 6, new Color(150, 0, 0))));
    	    }
    	    
    	    return someLabel;
    }
    
    
    //*******************************************************************************************
    
    
    	//closes the GameBoardFrame. Could also use dispose method.
    public void closeWindow(){
        gameboard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	gameboard.dispatchEvent(new WindowEvent(gameboard, WindowEvent.WINDOW_CLOSING));
    }
    
    
    
    //*******************************************************************************************
    
    
    private  void makeInfoPane(){
    	    GridLayout info=new GridLayout(1,4);
    	    //int pTurn=gameboard.getPlayerTurn();			//int of which player turn it is
    	    
    	    
    }
}
