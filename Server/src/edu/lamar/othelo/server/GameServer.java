/**
 *
 */
package edu.lamar.othelo.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.lamar.othelo.server.irp.ChatIF;

/**
 * @author agehlot
 *
 */
public class GameServer extends AbstractServer {

	final public static int DEFAULT_PORT = 5555;
	final private ChatIF serverConsole;
	private boolean isPlayerWaiting = false;
	private User playerWaiting = null;
	// FIXME int should be fixed with GameId class
	final private Map<GameId, Game> ongoingGames = new HashMap<GameId, Game>();
	public GameServer(final int port) {
		super(port);
		serverConsole = new ServerConsole(port, this);
	}

	/* (non-Javadoc)
	 * @see edu.lamar.othelo.server.AbstractServer#handleMessageFromClient(java.lang.Object, edu.lamar.othelo.server.ConnectionToClient)
	 */
	@Override
	protected void handleMessageFromClient(final Object msg, final ConnectionToClient client) {
		// TODO a common message object needs to be created.
		if (msg.toString().contains("login")) {
			// code to read user and validate it using data access layer. Should
			// be done latter.
		} else if (msg.toString().contains("start a game")) {
			if (isPlayerWaiting) {
				ongoingGames.put(new GameId(playerWaiting,
						getUser("get login id from message")), new Game(
								playerWaiting, getUser("get login id from message")));
				try {
					client.sendToClient("game started");
				} catch (final IOException e) {
					e.printStackTrace();
				}
			} else {
				isPlayerWaiting = true;
				playerWaiting = getUser("login");
			}
		} else if (msg.toString().contains("make a move")) {
			// 1. get the referred game.
			if (ongoingGames.containsKey("game id")) {
				final Game game = ongoingGames.get("game id");
				// 2. check if valid.
				if (true /* ideally we should check for a move here. */) {
					// 3. update game.
					game.makeMove(null);
				}
			}
			// 4. send to client.
			try {
				client.sendToClient("move sucessful");
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	private User getUser(final String string) {
		// TODO should call user access layer and return the user object.
		return null;
	}

	/**
	 * This method is responsible for the creation of the server instance (there
	 * is no UI in this phase).
	 */
	public static void main(final String[] args) {
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		} catch (final Throwable t) {
			port = DEFAULT_PORT; // 5555
		}

		final GameServer gameServer = new GameServer(port);
		try {
			gameServer.listen(); // Start listening for connections
			((ServerConsole) gameServer.serverConsole).accept();
		} catch (final Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
