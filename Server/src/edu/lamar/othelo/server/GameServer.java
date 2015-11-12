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
	private ConnectionToClient playerWaitingConnection = null;
	final private Map<GameId, Game> ongoingGames = new HashMap<GameId, Game>();
	final private Map<String, ConnectionToClient> connectedClient = new HashMap<String, ConnectionToClient>();

	// FIXME: should be a singleton class.
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
		// parse message object and get request type.
		final String requestType = getRequestType(msg);
		if (requestType.equalsIgnoreCase("GAME")) {
			final String request = getRequest(msg);
			if(request.equalsIgnoreCase("START")){
				if (isPlayerWaiting) {
					ongoingGames.put(new GameId(playerWaiting,
							getUser((String) msg)), new Game(
									playerWaiting, getUser((String) msg)));
					try {
						final String playerColor = "black"; // since if player
						// is waiting color
						// will be black.
						client.sendToClient("game-started_" + playerColor); // client
						// should
						// understand
						// this
						// and
						// display
						// the
						// game
						// UI.
						playerWaitingConnection
						.sendToClient("game-started_white");
						connectedClient.put(getLoginId((String) msg), client);
						playerWaiting = null; // Since now no player is waiting.
						playerWaitingConnection = null;
					} catch (final IOException e) {
						e.printStackTrace();
					}
				} else {
					isPlayerWaiting = true;
					playerWaiting = getUser((String) msg); // TODO: here it
					// should be loginId
					playerWaitingConnection = client;
					connectedClient.put(getLoginId((String) msg), client);
				}
			} else if (request.contains("MakeAMove")) {
				// TODO: remove not required comments after developer testing.
				// find a game which it is requesting for by using the opponent
				// game id.
				// sample msg: rahul0017_game_MakeAMove-jason007 3,5 sample
				// request: MakeAMove-jason007 3,5
				final String[] splitedRequest = request.split("-");
				System.out.println(splitedRequest); // printing for debugging
				// purpose
				// splitted requested: MakeAMove jason007 3,5"
				final String oppositionPlayerLoginId = splitedRequest[1];
				final Game ongoingGame = ongoingGames.get(getLoginId((String) msg) + "_"
						+ oppositionPlayerLoginId);
				final String[] requestedCoordinates = splitedRequest[2].split(",");
				if (ongoingGame.makeMove(
						Integer.parseInt(requestedCoordinates[0]),
						Integer.parseInt(requestedCoordinates[1]))) {
					try {
						client.sendToClient("true"); // client should understand
						// it and move should be
						// shown.
						connectedClient.get(oppositionPlayerLoginId)
						.sendToClient(splitedRequest[2]);// other client
						// should
						// understand
						// this and
						// be able
						// to make
						// move
						// successful.
					} catch (final IOException e) {
						e.printStackTrace();
					}

				} else {
					// move was not successful
					try {
						client.sendToClient("false");
					} catch (final IOException e) {
						e.printStackTrace();
					}// client should understand this and should not move
						// anything. Can show a pop if needed.
				}

			} else if (request.equalsIgnoreCase("QUIT")) {
				try {
					client.sendToClient("Lost");
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getLoginId(final String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getRequest(final Object msg) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getRequestType(final Object msg) {
		// TODO Auto-generated method stub
		return null;
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
