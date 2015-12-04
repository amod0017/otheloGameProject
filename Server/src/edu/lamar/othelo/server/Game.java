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

	private final SpaceState[][] board = new SpaceState[8][8];
	private User white;
	private User black;
	private int white_score;
	private int black_score;
	private final GameId id;
	private SpaceState current = SpaceState.black;

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
    boolean makeMove(final int xCoordinate, final int yCoordinate)
    {
        return makeMove(xCoordinate,yCoordinate,SpaceState.black);
    }
	boolean makeMove(final int xCoordinate, final int yCoordinate, SpaceState player) {
		// TODO: First of all you should check whether player is allowed to move
		// or not.
		final Move move = new Move(xCoordinate, yCoordinate, player);
		if (move.isValidMove() /*&& current == player*/) {
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
			if(color == SpaceState.black) {
				enemycolor = SpaceState.white;
			} else if(color == SpaceState.white) {
				enemycolor = SpaceState.black;
			}
		}

		boolean isValidMove() {

			{

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
                foundPiece = false;
                while(true){
					ix++; if(foundPiece || !((0 <= ix) && (ix <= 7)) || !((0 <= iy) && (iy <= 7)))break;
					if(board[ix][iy] == color) {
						foundPiece = true;
					}
				}

				if(foundPiece)
				{
					do{
						ix--;
						if(board[ix][iy] == enemycolor)
						{
							board[ix][iy] = color;
							captureSum++;
						}
					}while(ix != x || iy != y);
				}

                //WEST
                ix=x; iy=y;
                foundPiece = false;
                while(true){
                    ix--; if(foundPiece || !((0 <= ix) && (ix <= 7)) || !((0 <= iy) && (iy <= 7)))break;
                    if(board[ix][iy] == color) {
                        foundPiece = true;
                    }
                }

                if(foundPiece)
                {
                    do{
                        ix++;
                        if(board[ix][iy] == enemycolor)
                        {
                            board[ix][iy] = color;
                            captureSum++;
                        }
                    }while(ix != x || iy != y);
                }

                //south
                ix=x; iy=y;
                foundPiece = false;
                while(true){
                    iy++; if(foundPiece || !((0 <= ix) && (ix <= 7)) || !((0 <= iy) && (iy <= 7)))break;
                    if(board[ix][iy] == color) {
                        foundPiece = true;
                    }
                }

                if(foundPiece)
                {
                    do{
                        iy--;
                        if(board[ix][iy] == enemycolor)
                        {
                            board[ix][iy] = color;
                            captureSum++;
                        }
                    }while(ix != x || iy != y);
                }

                //North
                ix=x; iy=y;
                foundPiece = false;
                while(true){
                    iy--; if(foundPiece || !((0 <= ix) && (ix <= 7)) || !((0 <= iy) && (iy <= 7)) )break;
                    if(board[ix][iy] == color) {
                        foundPiece = true;
                    }
                }

                if(foundPiece)
                {
                    do{
                        iy++;
                        if(board[ix][iy] == enemycolor)
                        {
                            board[ix][iy] = color;
                            captureSum++;
                        }
                    }while(ix != x || iy != y);
                }




				if(captureSum == 0) {
					return false;
				} else
				{
					white_score += captureSum + 1;
					black_score -= captureSum;
					return true;
				}


			}

		}
	}

}
