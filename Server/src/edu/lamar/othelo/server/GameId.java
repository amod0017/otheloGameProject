/**
 *
 */
package edu.lamar.othelo.server;

/**
 * Use to uniquely identify the game object
 *
 * @author agehlot
 *
 */
public class GameId {
	private final User whitePlayer;
	private final User blackPlayer;

	GameId(final User whiteUser, final User blackUser) {
		whitePlayer = whiteUser;
		blackPlayer = blackUser;
	}

	public String getGameId() {
		return (whitePlayer.toString() + "_" + blackPlayer.toString());
	}

	@Override
	public boolean equals(final Object obj) {
		if (((obj instanceof String) || (obj instanceof GameId))
				&& obj.toString().equals(getGameId())) {
			return true;
		} else if (((obj instanceof String) || (obj instanceof GameId))
				&& obj.toString().equals(getSecondPossibleGameId())) {
			return true;
		}
		return false;
	}

	// TODO: confirm whether two players can play game simultaneously. Ideally
	// should not be allowed.
	private Object getSecondPossibleGameId() {
		return blackPlayer.toString() + "_" + whitePlayer.toString();
	}

	@Override
	public String toString() {
		return getGameId();
	}
}
