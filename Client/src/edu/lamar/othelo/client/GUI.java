package edu.lamar.othelo.client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GUI {
	private static OtheloUI chessboard;
	public enum SpaceState {
		empty, black, white
	}

	static SpaceState[][] board = new SpaceState[8][8];

	GUI ()
	{
		initializeGUI();
		drawBoard(board);
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

	public void setSpace(final SpaceState state, final int row, final int column)
	{
		board[row][column] = state;
	}


	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new GUI();
			}
		});
	}

	public static void drawBoard(final SpaceState[][] gameBoard)
	{
		if (chessboard!=null) {
			//chessboard.dispose();
			//			SwingUtilities.updateComponentTreeUI(chessboard);
			//			chessboard.invalidate();
			//			chessboard.validate();
			chessboard.repaint();
			return;
		}
		chessboard = new OtheloUI(gameBoard);
	}
	
	public static void update(int row, int col, String color){
		chessboard.update(row, col, color);
	}

	public static void test(final int row, final int col, final String color){
		chessboard.update(row,col, color);
		chessboard.dispose();
		//chessboard.draw();
		//chessboard.revalidate();
		// return;
		chessboard = new OtheloUI(board);
		OtheloUI.quitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e) //needs to tell the server it quit as well
			{
				final int n = JOptionPane.showConfirmDialog(
						chessboard,
						"Are you sure you want to quit? This will cause you to forfeit the game!",
						"Warning!",
						JOptionPane.YES_NO_OPTION);

				if(n == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

}



