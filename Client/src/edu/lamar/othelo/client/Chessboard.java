package edu.lamar.othelo.client;

import edu.lamar.othelo.client.GUI.SpaceState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** Updated by Colton Carter on 11/18/14, 20:18
 */

public class Chessboard extends JFrame {
    private int i;


    public Chessboard (SpaceState[][] board){ //MDA for storing of board
        SpaceState[][] tempBoard = board; //inbetween board for showing potential moves


        this.setLayout(new GridLayout(9,8,0,0)); //rows and column sizes
        this.setSize(400, 450);
        this.setBackground(Color.GREEN);
        this.setVisible(true);
        JFrame Chessboard = new JFrame("Othello"); //added this for the titlee
        this.setTitle("Othello");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++) {
                if(board[row][col] == SpaceState.black)
                {
                    DrawBlack blackMark = new DrawBlack(row, col);
                    blackMark.addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {

                        }
                        public void mouseEntered(MouseEvent e) {

                        }
                        public void mouseExited(MouseEvent e) {

                        }


                    });
                    add(blackMark);
                }
                else if(board[row][col] == SpaceState.white)
                {
                    DrawWhite whiteMark = new DrawWhite(row, col);
                    whiteMark.addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {

                        }
                        public void mouseEntered(MouseEvent e) {

                        }
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                    add(whiteMark);
                }
                else if(board[row][col] == SpaceState.translucentWhite)
                {
                    DrawWhite whiteMark = new DrawWhite(row, col);
                    add(whiteMark);
                }
                else if(board[row][col] == SpaceState.translucentBlack)
                {
                    DrawWhite whiteMark = new DrawWhite(row, col);
                    add(whiteMark);
                }
                else
                {
                    DrawRect emptyRect = new DrawRect(row, col);
                    emptyRect.addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {

                        }
                    });


                    add(emptyRect);
                }
            }
    }

    public void update() {
        getContentPane().validate();
        getContentPane().repaint();
    }

}
class DrawRect extends JPanel{
    int row;
    int col;
    public DrawRect(int row, int col) {
        this.row = row;
        this.col = col;
    }

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
    int row;
    int col;
    public DrawBlack(int row, int col) {
        this.row = row;
        this.col = col;
    }

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
    int row;
    int col;
    public DrawWhite(int row, int col) {
        this.row = row;
        this.col = col;
    }

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
class DrawTranslucentWhite extends JPanel{
    public DrawTranslucentWhite(int row, int col) {
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
        g.drawOval(0, 0, getWidth(), getHeight());
        //add the square with the specified color
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
    }
}
//for potential moves
class DrawTranslucentBlack extends JPanel{
    public DrawTranslucentBlack(int row, int col) {
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
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, getWidth(), getHeight());
        //add the square with the specified color
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
    }
}



