/**
 *
 */
package edu.lamar.othelo.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author agehlot
 *
 */
import javax.swing.*;

import edu.lamar.othelo.client.GUI.SpaceState;

public class OtheloUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final SpaceState[][] board;
	private final JLabel[][] panelsOnBoard = new JLabel[8][8];
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
			} else if (topRowCount == 7) { //this sets the button on the upper row
				quitButton.setFocusPainted(false);
                quitButton.setMargin(new Insets(0,0,0,0));
                add(quitButton);
			}
			else {
				add(new JLabel(""));
			}
		}

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == SpaceState.black) {
					panelsOnBoard[row][col] = new BlackLabel(row, col);
					panelsOnBoard[row][col].addMouseListener(new OtheloMouseListener(this));
					panelsOnBoard[row][col].setPreferredSize(new Dimension(50, 50));
					panelsOnBoard[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
					add(panelsOnBoard[row][col]);
				} else if (board[row][col] == SpaceState.white) {
					panelsOnBoard[row][col] = new WhiteLabel(row, col);
					panelsOnBoard[row][col].addMouseListener(new OtheloMouseListener(this));
					panelsOnBoard[row][col].setPreferredSize(new Dimension(50, 50));
					panelsOnBoard[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
					add(panelsOnBoard[row][col]);
				} else {
					panelsOnBoard[row][col] = new EmptyLabel(row, col);
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
					if (color.equals("white")) {
						panelsOnBoard[row][col].setIcon(WhiteLabel.white);
					} else if (color.equals("black")) {
						panelsOnBoard[row][col].setIcon(BlackLabel.black);
					} else if (color.equals("empty")) {
						panelsOnBoard[row][col].setIcon(EmptyLabel.empty);
					}
					break;
				}
			}
		}
		revalidate();
	}
}

class WhiteLabel extends JLabel implements MyLabel {
	int row;
	int col;
	private static final long serialVersionUID = 1L;
	public static ImageIcon white = new ImageIcon("/home/colton/otheloGameProject/Client/images/white.gif", "white");

	public WhiteLabel(final int row, final int col) {
		super(white);
		this.row = row;
		this.col = col;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}

}

class BlackLabel extends JLabel implements MyLabel {
	int row;
	int col;
	private static final long serialVersionUID = 1L;
	static ImageIcon black = new ImageIcon("/home/colton/otheloGameProject/Client/images/black.gif", "black");

	public BlackLabel(final int row, final int col) {
		super(black);
		this.row = row;
		this.col = col;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}
}

interface MyLabel {
	int getRow();

	int getCol();
}

class EmptyLabel extends JLabel implements MyLabel {
	int row;
	int col;
	private static final long serialVersionUID = 1L;
	static ImageIcon empty = new ImageIcon("/home/colton/otheloGameProject/Client/images/empty.gif", "empty");

	public EmptyLabel(final int row, final int col) {
		super(empty);
		this.row = row;
		this.col = col;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}
}
class OtheloMouseListener implements MouseListener {
	OtheloUI chessboard;

	public OtheloMouseListener(final OtheloUI chessboard) {
		this.chessboard = chessboard;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		GameClient.getInstance().handleMakeAMove(((MyLabel) e.getSource()).getRow(),
				((MyLabel) e.getSource()).getCol());
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
