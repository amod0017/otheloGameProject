package edu.lamar.othelo.server;

import java.util.LinkedList;

class Game {
	// fields
	// null is no piece
	// true is a white piece
	// false is a black piece
	Boolean[][] board = new Boolean[8][8];
	int wins_needed = 1;
	User white;
	User black;
	int white_score;
	int black_score;

	// constructor for Othello's starting position
	Game(final User w, final User b) {
		white = w;
		black = b;

		white_score = 2;
		black_score = 2;

		board[3][3] = true;
		board[4][4] = true;
		board[4][3] = false;
		board[3][4] = false;
	}

	// methods
	int getWhiteScore() {
		return white_score;
	}

	int getBlackScore() {
		return black_score;
	}

	User getWhite() {
		return white;
	}

	User getBlack() {
		return black;
	}

	void setWhiteScore(final int i) {
		white_score = i;
	}

	void setBlackScore(final int i) {
		black_score = i;
	}

	void setWhite(final User u) {
		white = u;
	}

	void setBlack(final User u) {
		black = u;
	}

	boolean makeMove(final Move m) {
		if (m.isValidMove()) {
			board[m.x][m.y] = m.color;
			return true;
		} else {
			return false;
		}
	}

	// two games are equal if they have the same players
	boolean equals(final Game g) {
		if ((white == g.getWhite()) && (black == g.getBlack())) {
			return true;
		} else {
			return false;
		}
	}

	LinkedList<Move> seeMoves() {
		final LinkedList<Move> move_list = new LinkedList<>();

		for (final Boolean[] element : board) {
			for (final Boolean element2 : element) {

			}
		}
		return move_list;
	}

	private class Move {
		int x, y;
		Boolean color;

		boolean isValidMove() {
			// STUB
			// for each direction, iterate until you find a friendly piece
			// if a friendly piece is found then convert all enemy pieces from
			// the friendly to the placed piece
			// keep a running sum of converted pieces and add the sum + 1 at the
			// end to the friendly score and subtract sum from the enemy score
			// if no pieces can be converted in any iteration (sum = 0), then
			// the move is not valid
			return true;
		}

	}

}
