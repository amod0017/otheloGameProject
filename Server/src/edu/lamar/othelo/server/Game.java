package edu.lamar.othelo.server;

import java.util.LinkedList;

class Game {
	// fields
	// null is no piece
	// true is a white piece
	// false is a black piece
    private enum SpaceState
    {
        empty, black, white
    }

	private SpaceState[][] board = new SpaceState[8][8];
	private User white;
	private User black;
	private int white_score;
	private int black_score;
	private final GameId id;
    private SpaceState current;

	// constructor for Othello's starting position
	Game(final User whitePlayer, final User blackPlayer) {

		white = whitePlayer;
		black = blackPlayer;
        current = SpaceState.black;    //black moves first

		white_score = 2;
		black_score = 2;

		board[3][3] = SpaceState.white;
		board[4][4] = SpaceState.white;
		board[4][3] = SpaceState.black;
		board[3][4] = SpaceState.black;

		id = new GameId(whitePlayer, blackPlayer);
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

	// move is priavte and hence it should be created in the class.
	boolean makeMove(final int xCoordinate, final int yCoordinate) {
		// TODO: First of all you should check whether player is allowed to move
		// or not.
		final Move move = new Move(xCoordinate, yCoordinate, current);
		if (move.isValidMove()) {
			board[move.x][move.y] = current;
            current = (current == SpaceState.black) ? SpaceState.white : SpaceState.black;
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

		for (final SpaceState[] element : board) {
			for (final SpaceState element2 : element) {

			}
		}
		return move_list;
	}

	private class Move {
		int x, y;
        SpaceState color;
        SpaceState enemycolor;

		private Move(final int xCoordinate, final int yCoordinate, final SpaceState color) {
			x = xCoordinate;
			y = yCoordinate;
            this.color = color;
            if(color == SpaceState.black)
                enemycolor = SpaceState.white;
            else if(color == SpaceState.white)
                enemycolor = SpaceState.black;
		}

		boolean isValidMove() {

			// for each direction, iterate until you find a friendly piece
			// if a friendly piece is found then convert all enemy pieces from
			// the friendly to the placed piece
			// keep a running sum of converted pieces and add the sum + 1 at the
			// end to the friendly score and subtract sum from the enemy score
			// if no pieces can be converted in any iteration (sum = 0), then
			// the move is not valid

            int ix = x, iy = y, captureSum = 0;
            boolean foundPiece = false;



            //East check and sum
            ix=x; iy=y;
            do {
                ix++;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (0 <= ix && ix <= 7) || (0 <= iy && iy <= 7));

            if(foundPiece)
            {
                do{
                    ix--;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }

            //South
            ix=x; iy=y;
            do {
                iy++;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (0 <= ix && ix <= 7) || (0 <= iy && iy <= 7));

            if(foundPiece)
            {
                do{
                    iy--;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }


            //West check and sum
            ix=x; iy=y;
            do {
                ix--;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (1 <= ix && ix <= 8) || (1 <= iy && iy <= 8));

            if(foundPiece)
            {
                do{
                    ix++;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }


            //North
            ix=x; iy=y;
            do {
                iy--;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (1 <= ix && ix <= 8) || (1 <= iy && iy <= 8));

            if(foundPiece)
            {
                do{
                    iy++;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }


            //Northwest
            ix=x; iy=y;
            do {
                ix--;
                iy--;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (1 <= ix && ix <= 8) || (1 <= iy && iy <= 8));

            if(foundPiece)
            {
                do{
                    ix++;
                    iy++;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }

            //Southeast
            ix=x; iy=y;
            do {
                ix++;
                iy++;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (1 <= ix && ix <= 8) || (1 <= iy && iy <= 8));

            if(foundPiece)
            {
                do{
                    ix--;
                    iy--;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }

            //Southwest
            ix=x; iy=y;
            do {
                ix--;
                iy++;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (1 <= ix && ix <= 8) || (1 <= iy && iy <= 8));

            if(foundPiece)
            {
                do{
                    ix++;
                    iy--;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }


            //Northeast
            ix=x; iy=y;
            do {
                ix++;
                iy--;
                if(Game.this.board[ix][iy] != color)
                    foundPiece = true;
            }while(!foundPiece || (1 <= ix && ix <= 8) || (1 <= iy && iy <= 8));

            if(foundPiece)
            {
                do{
                    ix--;
                    iy++;
                    if(Game.this.board[ix][iy] == enemycolor)
                    {
                        Game.this.board[ix][iy] = color;
                        captureSum++;
                    }
                }while(Game.this.board[ix][iy] != Game.this.board[x][y]);
            }

            if(captureSum == 0)
                return false;
            else
            {
                Game.this.white_score += captureSum + 1;
                Game.this.black_score -= captureSum;
                return true;
            }


		}

	}

}
