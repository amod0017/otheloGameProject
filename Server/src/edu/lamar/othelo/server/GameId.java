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
}
