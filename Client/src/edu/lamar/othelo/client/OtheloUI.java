/**
 *
 */
package edu.lamar.othelo.client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
/**
 * @author agehlot
 *
 */

import edu.lamar.othelo.client.GUI.SpaceState;

public class OtheloUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final SpaceState[][] board;
	private final JLabel[][] panelsOnBoard = new JLabel[8][8];
	private static ImageIcon white = new ImageIcon("images/white.gif", "white");
	private static ImageIcon black = new ImageIcon("images/black.gif", "black");
	private static ImageIcon empty = new ImageIcon("images/empty.gif", "empty");
	public static JButton quitButton = new JButton("Quit"); //button for quitting  was added, check GUI for actual usage


	public OtheloUI(final SpaceState[][] board) {
		this.board = board;
		draw();
	}

	private void draw() { // MDA for storing of board
		setLayout(new GridLayout(9, 8, 0, 0)); // rows and column sizes
		this.setSize(400, 450);
		setBackground(Color.GREEN);
		setVisible(true);
		new JFrame("Othello");
		setTitle("Othello");
		setResizable(false);

		int blackCounter = 0;
		int whiteCounter = 0;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == SpaceState.black) {
					blackCounter++;
				} else if (board[row][col] == SpaceState.white) {
					whiteCounter++;
				}
			}
		}

		for (int topRowCount = 0; topRowCount < 8; topRowCount++) {
			if (topRowCount == 0) {
				add(new JLabel("White:", SwingConstants.CENTER));
			} else if (topRowCount == 1) {
				add(new JLabel(Integer.toString(whiteCounter), SwingConstants.CENTER));
			} else if (topRowCount == 2) {
				add(new JLabel("Black:", SwingConstants.CENTER));
			} else if (topRowCount == 3) {
				add(new JLabel(Integer.toString(blackCounter), SwingConstants.CENTER));
			} else if (topRowCount == 4) { //this sets the button on the upper row
				quitButton.setFocusPainted(false);
				quitButton.setMargin( new Insets(0, 0, 0, 0) );
				add(quitButton);
			}

			else {
				add(new JLabel(""));
			}
		}

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == SpaceState.black) {
					panelsOnBoard[row][col] = new JLabel(black);
					panelsOnBoard[row][col].addMouseListener(new OtheloMouseListener(this));
					panelsOnBoard[row][col].setPreferredSize(new Dimension(50, 50));
					panelsOnBoard[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
					add(panelsOnBoard[row][col]);
				} else if (board[row][col] == SpaceState.white) {
					panelsOnBoard[row][col] = new JLabel(white);
					panelsOnBoard[row][col].addMouseListener(new OtheloMouseListener(this));
					panelsOnBoard[row][col].setPreferredSize(new Dimension(50, 50));
					panelsOnBoard[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
					add(panelsOnBoard[row][col]);
				} else {
					panelsOnBoard[row][col] = new JLabel(empty);
					panelsOnBoard[row][col].addMouseListener(new OtheloMouseListener(this));
					panelsOnBoard[row][col].setPreferredSize(new Dimension(50, 50));
					panelsOnBoard[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
					add(panelsOnBoard[row][col]);
				}
			}
		}
	}

	@Override
	public void repaint() {
		super.repaint();
		removeAll();
		draw();
		revalidate();
	}

	public void update(final int rowNeedsToBeUpdated, final int colNeedsToBeUpdated, final String color) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ((row == rowNeedsToBeUpdated) && (col == colNeedsToBeUpdated)) {

					// if (color == "white") {
					// final Point point =
					// panelsOnBoard[row][col].getLocation();
					// remove(panelsOnBoard[row][col]);
					// System.out.println("Drawing color " + color + "on
					// row,col" + row + "," + col);
					// final DrawWhite whiteMark = new
					// DrawWhite(rowNeedsToBeUpdated, colNeedsToBeUpdated);
					// whiteMark.addMouseListener(new
					// OtheloMouseListener(this));
					// whiteMark.setLocation(point);
					// whiteMark.setLayout(null);
					// add(whiteMark);
					// panelsOnBoard[row][col] = whiteMark;
					// }
					// if (color == "black") {
					// final Point point =
					// panelsOnBoard[row][col].getLocation();
					// remove(panelsOnBoard[row][col]);
					// final DrawBlack blackMark = new DrawBlack(row, col);
					// blackMark.addMouseListener(new
					// OtheloMouseListener(this));
					// add(blackMark);
					// panelsOnBoard[row][col] = blackMark;
					// blackMark.setLocation(point);
					// }
					break;
				}
			}
		}
		revalidate();
	}
}


class OtheloMouseListener implements MouseListener {
	OtheloUI chessboard;

	public OtheloMouseListener(final OtheloUI chessboard) {
		this.chessboard = chessboard;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		System.out.println(e.getSource());
		// System.out.println(((DrawRect) e.getSource()).row + "," + ((DrawRect)
		// e.getSource()).col);
		// GameClient.getInstance().handleMakeAMove(((DrawRect)
		// e.getSource()).row, ((DrawRect) e.getSource()).col);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

}
