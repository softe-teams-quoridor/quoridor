
/**   GameBoardFrame.java - CIS405 - teams
 * ____________________________________________________________________________
 *
 *   GameBoardFrame object to construct a GUI for the Quoridor game. 
 *
 * --------------------------------- METHODS ----------------------------------
 *
 * PUBLIC:
 *
 * GameBoardFrame()             --> Default Constructor 
 * update()   					--> Removes contents of board and updates locations 
 * closeWindow()    			--> Closes the window at the end of a game
 *
 * PRIVATE:
 * draw()						--> Constructs the gameboard and makes it visible
 * row()       					--> creates a single row of the GameBoardFrame
 * printPlayerLabel()			--> Shows an image of a pawn in player squares
 * topLayer()					--> draws the borders of the gridlayout
 * JLabel setBoarder()			--> sets the borders of the  grid
 * makeInfoPane()				--> HOPING TO make an info pane on the RHS of grid
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
    private String playName;

    //constructs JFrame
    public GameBoardFrame(GameBoard board, Queue<Player> players) {
        numPlayers = players.size();
        this.players = players;
        playName = null;

        //initialize JFrame
        gameboard = new JFrame("Quoridor: "+"Server window. :)");
        gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameboard.setSize(1000,700);
        gameboard.setLocationRelativeTo(null);


        //creates the grid
        GridLayout game = new GridLayout(10,10);
        gameboard.setLayout(game);

        //draw the original board
        draw(board);
    }
    
     //******************************************************************************************
    
     //constructs JFrame
    public GameBoardFrame(GameBoard board, Queue<Player> players, String playName) {
        numPlayers = players.size();
        this.players = players;
        this.playName = playName;

        //initialize JFrame
        gameboard = new JFrame("Quoridor:" +playName+"'s Window. :)");
        gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameboard.setSize(1000,700);
        gameboard.setLocationRelativeTo(null);


        //creates the grid
        GridLayout game = new GridLayout(10,10);
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
            gameboard.getContentPane().removeAll();
            draw(board);
            JOptionPane.showMessageDialog(gameboard, p.getName() + " HAS WON!", "We have a winner!", JOptionPane.PLAIN_MESSAGE);
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
            draw(board, reachable);
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
        int wThick=2;
        JLabel labelblank = new JLabel();
        labelblank.setOpaque(true);
        labelblank.setBackground(new Color(150, 0, 0));
        labelblank.setPreferredSize(new Dimension(100, 70));
        labelblank.setText("" + GameEngine.toLetters(row));
        labelblank.setForeground(Color.WHITE);
        labelblank.setFont(new Font("Serif",1,30));
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
        
        	//makes the infopane section black
            Color pColor=Color.BLACK;
            JLabel superLabel=new JLabel("");
            superLabel.setForeground(Color.WHITE);
            JLabel label = new JLabel();
            
            //makes it so each player has two separate squares
            int playInt=(row-1)/2;
            label.setLayout(new GridLayout(3,1));
            label.setOpaque(true);
            label.setPreferredSize(new Dimension(100, 70));
            
            //determine what color the player squares should be
            if(row==0){
            }else if(playInt==0){
                pColor=Color.YELLOW;
            }else if(playInt==1){
                pColor=Color.BLUE;
            }else if(playInt==2){
                pColor=Color.GREEN;
            }else if(playInt==3){
                pColor=Color.RED;
            }

            //if it is a players turn, print it's infopane walls thicker 
            if(playInt==board.getCurrPlayerTurn())
                wThick=5;
            if(row%2==0&&row!=0){
                label.setBorder(BorderFactory.createMatteBorder(0,wThick,wThick,wThick,pColor));
                
                //if the player is still in the game, display the number of walls they have
                //if not, display "No player!"
                if(board.isPlayerRemaining(playInt)){
                    superLabel.setText("Walls: "+board.getPlayer(playInt).getNumWalls());
                }else{
                    superLabel.setText("Player!");
                }
            }else if(row!=0){
                if(board.isPlayerRemaining(playInt)){
                    String name=board.getPlayer(playInt).getName();
                    superLabel.setText(name.substring(Math.max(0, name.length()-9)));
                }else{
                    superLabel.setText("No");
                }
                label.setBorder(BorderFactory.createMatteBorder(wThick,wThick,0,wThick,pColor));
            }
            label.add(superLabel);
            label.setBackground(new Color(0, 0, 0));
            label.setForeground(Color.WHITE);
            gameboard.getContentPane().add(label,BorderLayout.CENTER);
            
            
        

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
        labelblank.setForeground(Color.WHITE);
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
        for (int i = 9; i < 10; i++) {
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
    	    String fileLoc=findIcons();
    	int pawnFail=-1;
    	//if(f.exists()){
        ImageIcon bg=new ImageIcon(fileLoc+"/player_4_scaled.jpg");
        Color pC=new Color(0,0,0);
        if(p.getPlayerNo()==0){
        	pC=Color.YELLOW;
        	pawnFail=1;
            bg=new ImageIcon(fileLoc+"/player_1_scaled.jpg");
        }else if(p.getPlayerNo()==1){
        	pC=Color.BLUE;
        	pawnFail=2;
            bg=new ImageIcon(fileLoc+"/player_2_scaled.jpg");
        }else if(p.getPlayerNo()==2){
        	pC=Color.GREEN;
        	pawnFail=3;
            bg=new ImageIcon(fileLoc+"/player_3_scaled.jpg");
        }else if(p.getPlayerNo()==3){
        	pC=Color.RED;
        	pawnFail=4;
            bg=new ImageIcon(fileLoc+"/player_4_scaled.jpg");
        }
	if(!fileLoc.equals("Didn't work")){        
        label.setIcon(bg);
        }else{
        label.setBackground(pC);
        label.setText(""+pawnFail);
        label.setFont(new Font("Serif",1,30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        }
        
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
        labels[0].setBackground(new Color(150, 0, 0));
        labels[0].setPreferredSize(new Dimension(100, 70));

        gameboard.getContentPane().add(labels[0], BorderLayout.CENTER);
        // Index starts at 1, toNumerals starts at i-1
        for (int i = 1; i < 10; i++) {

            labels[i] = new JLabel();;
            labels[i].setFont(new Font("Serif", 1, 30));
            labels[i].setOpaque(true);
            labels[i].setBackground(new Color(150, 0, 0));
            labels[i].setForeground(Color.WHITE);
            labels[i].setPreferredSize(new Dimension(100, 70));
            labels[i].setText(GameEngine.toNumerals(i-1));
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            gameboard.getContentPane().add(labels[i], BorderLayout.CENTER);
        }

        for (int i=10; i<11; i++){
            labels[i]=new JLabel();
            //labels[i].setOpaque(true);
            if(i==10){
                labels[i].setOpaque(true);
                labels[i].setVisible(true);
            }
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
            someLabel.setBorder(BorderFactory.createBevelBorder(0));

        }else if(!tSquare.hasWallRight()&&tSquare.hasWallBottom()){
            someLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),
                        BorderFactory.createMatteBorder(0,0,6,0,new Color(150, 0, 0))));

        }else if(tSquare.hasWallRight()&&!tSquare.hasWallBottom()){
            someLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),
                        BorderFactory.createMatteBorder(0,0, 0, 6, new Color(150, 0, 0))));

        }else if(tSquare.hasWallRight()&&tSquare.hasWallBottom()){
            someLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),
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

    private String findIcons(){
    	    String filePath="./images";

    	    for(int x=0; x<5; x++){
    	    	    if(new File(filePath).exists()){
    	    	    	    return filePath;
    	    		
    	    	    }else{
    	    		filePath="../"+filePath;
    	    	    }
    }
    return "Didn't work";
    }
}
