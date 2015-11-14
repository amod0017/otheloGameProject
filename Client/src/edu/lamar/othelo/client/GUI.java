package edu.lamar.othelo.client;

import javax.swing.*;

public class GUI {
    SpaceState[][] board = new SpaceState[8][8];

    GUI() {
        initializeGUI();
        drawBoard(board);
        setSpace(SpaceState.white, 1, 1);
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

    public final void initializeGUI() {//Set all cells to empty
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = SpaceState.empty;
            }
        }
        //Set the 4 starting stones
        board[4][3] = SpaceState.black;
        board[3][4] = SpaceState.black;
        board[3][3] = SpaceState.white;
        board[4][4] = SpaceState.white;
    }

    public void setSpace(SpaceState state, int row, int column) {
        board[row][column] = state;
    }

    public enum SpaceState {
        empty, black, white
    }

}



