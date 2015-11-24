package edu.lamar.othelo.client;
import javax.swing.*;
import edu.lamar.othelo.client.GUI.SpaceState;
import java.awt.event.*;

import java.awt.* ;

public class Chessboard extends JFrame {
    private int i;
   
    
    public Chessboard (SpaceState[][] board){ //MDA for storing of board
        this.setLayout(new GridLayout(9,8,0,0)); //rows and column sizes
        this.setSize(400, 450);
        this.setBackground(Color.GREEN);
        this.setVisible(true);
        JFrame Chessboard = new JFrame("Othello"); //added this for the title woo
        this.setTitle("Othello");
        this.setResizable(false);
        
        int blackCounter = 0;
        int whiteCounter = 0;
        
        for(int row = 0; row < 8; row++)
            for(int col = 0; col < 8; col++)
            {
                if(board[row][col] == SpaceState.black)
                    blackCounter++;
                else if(board[row][col] == SpaceState.white)
                    whiteCounter++;
            }        
        
        for (int topRowCount = 0; topRowCount < 8; topRowCount++)
        {
            if(topRowCount == 0)
                add(new JLabel("White:", SwingConstants.CENTER));
            else if(topRowCount == 1)
                add(new JLabel(Integer.toString(whiteCounter), SwingConstants.CENTER));
            else if(topRowCount == 2)
                add(new JLabel("Black:", SwingConstants.CENTER));
            else if(topRowCount == 3)
                add(new JLabel(Integer.toString(blackCounter), SwingConstants.CENTER));
            else
                add(new JLabel(""));
        }
        
        for (int row = 0; row < 8; row++){
	        for (int col = 0; col < 8; col++) {            
	            if(board[row][col] == SpaceState.black)
	            {
	                DrawBlack blackMark = new DrawBlack(row, col);
	                blackMark.addMouseListener(new CustomMouseListener());                
	                add(blackMark);
	            }
	            else if(board[row][col] == SpaceState.white)
	            {
	                DrawWhite whiteMark = new DrawWhite(row, col);
	                whiteMark.addMouseListener(new CustomMouseListener());
	                add(whiteMark);
	            }
	            else
	            {
	                DrawRect emptyRect = new DrawRect(row, col);
	                System.out.println(row + "," + col);
	                emptyRect.addMouseListener(new CustomMouseListener());
	                add(emptyRect);
	            }
	        }    
	    }
    }
}
class DrawRect extends JPanel{
    public DrawRect(int row, int col) {
        this.row = row;
        this.col = col;
    }
    int row;
    int col;
    
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g); 
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight()); 
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        
           //add the square with the specified color

}
}

class DrawBlack extends JPanel{
    public DrawBlack(int row, int col) {
        this.row = row;
        this.col = col;
    }
    int row;
    int col;
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g); 
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());        
        
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, getWidth(), getHeight());
           //add the square with the specified color
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());

}    
}

class DrawWhite extends JPanel{
    public DrawWhite(int row, int col) {
        this.row = row;
        this.col = col;
    }
    int row;
    int col;

    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight()); //fills the rectangles green
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, getWidth(), getHeight());
           //add the square with the specified color
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        }
}
        
class CustomMouseListener implements MouseListener{
    public void mouseClicked(MouseEvent e) {       
    	System.out.println(((DrawRect)e.getSource()).row + "," + ((DrawRect)e.getSource()).col);
    	DrawBlack drawBlack = new DrawBlack(((DrawRect)e.getSource()).row, ((DrawRect)e.getSource()).col);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e)  {
    }
   
}