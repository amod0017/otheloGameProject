package edu.lamar.othelo.client;

import javax.swing.*;

/** Updated by Colton Farter on 11/18/14, 20:18
 */

public class GUI {

    //TODO: Making moves correctly work on the GUI
    GameClient client;

    SpaceState[][] board = new SpaceState[8][8];
    Chessboard boardView;

    SpaceState friend;
    SpaceState foe;

    GUI(SpaceState friend, SpaceState foe) {
        this.friend = friend;
        this.foe = foe;
        initializeGUI();
        drawBoard(board);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI board = new GUI(SpaceState.black, SpaceState.white);
            }
        });
    }

    public void drawBoard(SpaceState[][] gameBoard) {
        boardView = new Chessboard(gameBoard);
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
        boardView.update();

    }

    public enum SpaceState {
        empty, black, white, translucentWhite, translucentBlack //added enums transclucents
    }

}



