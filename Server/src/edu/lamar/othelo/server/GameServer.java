/**
 *
 */
package edu.lamar.othelo.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.lamar.othelo.common.MessageImpl;
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
	final private Map<String, User> connectedUsers = new HashMap<String, User>();

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
		final String requestType = ((MessageImpl) msg).getMessageType();
		if (requestType.equalsIgnoreCase("GAME")) {
			handleGameRequest(msg, client);
		} else if (requestType.equalsIgnoreCase("LOGIN")) {
			handleLoginRequest(msg, client);
		} else if (requestType.equalsIgnoreCase("REGISTER")) {
			handleRegisterRequest(msg, client);
		}
	}

	private void handleRegisterRequest(final Object msg,
			final ConnectionToClient client) {
		if (UserAccessLayer.getInstance().addUser(
				new User(((MessageImpl) msg)
						.getPassword(), ((MessageImpl) msg).getLogin()))) {
			try {
				client.sendToClient("register_success");
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				client.sendToClient("register_failure");
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleLoginRequest(final Object msg, final ConnectionToClient client) {
		final User user = UserAccessLayer.getInstance().getUser(
				((MessageImpl) msg).getLogin(),
				((MessageImpl) msg).getPassword());
		if (user != null) {
			try {
				client.sendToClient("login_success" + user.getName());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				client.sendToClient("login_failure");
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleGameRequest(final Object msg,
			final ConnectionToClient client) {
		final String loginId = ((MessageImpl) msg).getLogin();
		if(((MessageImpl) msg).getMessage().equalsIgnoreCase("STARTGAME")){
			handleStartGameRequest(msg, client);
		} else if (((MessageImpl) msg).getMessage().contains("MakeAMove")) {
			handleMakeAMoveRequest(client, "", loginId); // FIXME should be
			// fixed

		} else if (((MessageImpl) msg).getMessage().equalsIgnoreCase("QUIT")) {
			try {
				client.sendToClient("Lost");
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param client
	 * @param request
	 * @param loginId
	 */
	private void handleMakeAMoveRequest(final ConnectionToClient client,
			final String request, final String loginId) {
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
		final Game ongoingGame = ongoingGames.get(loginId + "_"
				+ oppositionPlayerLoginId);
		final String[] requestedCoordinates = splitedRequest[2].split(",");
		if (ongoingGame.makeMove(Integer.parseInt(requestedCoordinates[0]),
				Integer.parseInt(requestedCoordinates[1]))) {
			try {
				client.sendToClient("true"); // client should understand
				// it and move should be
				// shown.
				connectedClient.get(oppositionPlayerLoginId).sendToClient(
						splitedRequest[2]);// other client
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
	}

	/**
	 * @param msg
	 * @param client
	 */
	private void handleStartGameRequest(final Object msg,
			final ConnectionToClient client) {
		final String loginId = ((String) msg).split("_")[0];
		if (isPlayerWaiting) {
			ongoingGames.put(new GameId(playerWaiting,
					getUser(loginId)), new Game(
							playerWaiting, getUser((String) msg)));
			try {
				final String playerColor = "black"; // since if player
				// is waiting color
				// will be black.
				client.sendToClient("start_" + playerColor); // client
				// should
				// understand
				// this
				// and
				// display
				// the
				// game
				// UI.
				playerWaitingConnection
.sendToClient("start_white");
				connectedClient.put(loginId, client);
				playerWaiting = null; // Since now no player is waiting.
				playerWaitingConnection = null;
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else {
			isPlayerWaiting = true;
			playerWaiting = getUser(loginId);
			playerWaitingConnection = client;
			connectedClient.put(loginId, client);
		}
	}


	private User getUser(final String loginId) {
		// should create a User with login Id.
		// FIXME this is just a temporary solution. It needs to be fixed later
		// with login module.
		if (!connectedUsers.containsKey(loginId)) {
			final User user = new User(loginId, "1234");
			user.setName(loginId);
			connectedUsers.put(loginId, user);
			return user;
		}
		return connectedUsers.get(loginId);
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
