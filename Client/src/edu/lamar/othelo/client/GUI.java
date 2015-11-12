package edu.lamar.othelo.client;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import edu.lamar.othelo.client.Chessboard;

public class GUI {
    SpaceState[][] board = new SpaceState[8][8];
    
    GUI ()
    {
        initializeGUI();
        drawBoard(board);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI board = new GUI();
            }
        });
    }

    public static void drawBoard(SpaceState[][] gameBoard) {
        Chessboard blah = new Chessboard(gameBoard);
    }
    
    public final void initializeGUI()
    {//Set all cells to empty
        for (int x = 0; x < 8; x++) {
             for (int y = 0; y < 8; y++) {
        board[x][y] = SpaceState.empty; }
        }
        //Set the 4 starting stones
        board[4][3] = SpaceState.black;
        board[3][4] = SpaceState.black;
        board[3][3] = SpaceState.white;
        board[4][4] = SpaceState.white;
    }
    
    public void setSpace(SpaceState state, int row, int column)
    {
        board[row][column] = state;
    }

    public enum SpaceState {
        empty, black, white
    }
    
}



