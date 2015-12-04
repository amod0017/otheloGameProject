/**
 *
 */
package edu.lamar.othelo.server;

import java.io.IOException;
import java.net.SocketException;
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
	private User playerWaiting = new User("emptyUser", null);
	final private Map<GameId, Game> ongoingGames = new HashMap<GameId, Game>();
	final private Map<String, ConnectionToClient> connectedClient = new HashMap<String, ConnectionToClient>();
	final private Map<String, User> connectedUsers = new HashMap<String, User>();
	private ConnectionToClient playerWaitingConnection;
	private final Map<String, String> playingWithInfo = new HashMap<String, String>();
	// FIXME: should be a singleton class.
	public GameServer(final int port) {
		super(port);
		serverConsole = new ServerConsole(port, this);
	}

	public ChatIF getServerConsole() {
		return serverConsole;
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
				connectedUsers.put(user.getName(), user);
				client.sendToClient("login_success_" + user.getName());
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
		} else if (((MessageImpl) msg).getMessage().equalsIgnoreCase("MakeAMove")) {
			handleMakeAMoveRequest(client, msg, loginId); // FIXME should be
			// fixed

		} else if (((MessageImpl) msg).getMessage().equalsIgnoreCase("QUIT")) {
			try {
				client.sendToClient("lost");
				connectedClient.get(playingWithInfo.get(loginId)).sendToClient(
						"won");
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
			final Object msg, final String loginId) {
		final String oppositionPlayerLoginId = playingWithInfo.get(loginId);
		final Game ongoingGame = ongoingGames
				.get(new GameId(connectedUsers.get(loginId), connectedUsers.get(oppositionPlayerLoginId)));
		final String[] requestedCoordinates = ((MessageImpl) msg).getMakeAMoveCoordinates().split(",");
		if (ongoingGame.makeMove(Integer.parseInt(requestedCoordinates[0]),
				Integer.parseInt(requestedCoordinates[1]))) {
			try {
				client.sendToClient("move_" + requestedCoordinates[0] + "_" + requestedCoordinates[1] + "_" + loginId); // client
				// should
				// understand
				// it and move should be
				// shown.
				connectedClient.get(oppositionPlayerLoginId).sendToClient(
						("move_" + requestedCoordinates[0] + "_" + requestedCoordinates[1]) + "_" + loginId);// other
				// client
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
			/*
			 * do nothing
			 */

			// move was not successful
			//			try {
			//				//client.sendToClient("move_nothing");
			////				connectedClient.get(oppositionPlayerLoginId).sendToClient("move_nothing");
			//			} catch (final IOException e) {
			//				e.printStackTrace();
			//			}// client should understand this and should not move
			// anything. Can show a pop if needed.
		}
	}

	/**
	 * @param msg
	 * @param client
	 */
	private void handleStartGameRequest(final Object msg,
			final ConnectionToClient client) {
		final String loginId = ((MessageImpl) msg).getLogin();
		if (isPlayerWaiting && (playerWaiting != null)
				&& !(playerWaiting.getName().equals(loginId))) {
			ongoingGames.put(new GameId(playerWaiting,
					getUser(loginId)), new Game(
							playerWaiting, getUser(loginId)));
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
				// sendToAllClients("start_white");
				playerWaitingConnection.sendToClient("start_white");
				connectedClient.put(loginId, client);
				playingWithInfo.put(loginId, playerWaiting.getName());
				playingWithInfo.put(playerWaiting.getName(), loginId);
				playerWaiting = null; // Since now no player is waiting.
			} catch (final Exception e) {
				e.printStackTrace();
			}
		} else {
			if (connectedUsers.containsKey(loginId)) {
				isPlayerWaiting = true;
				playerWaiting = connectedUsers.get(loginId);
				playerWaitingConnection = client;
				connectedClient.put(loginId, client);
				try {
					client.sendToClient("wait");
				} catch (final IOException e) {
					e.printStackTrace();
				}
			} else{
				try {
					client.sendToClient("you_are_in_queue");
				} catch (final SocketException e) {
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

		}
	}


	private User getUser(final String loginId) {
		// should create a User with login Id.
		// FIXME this is just a temporary solution. It needs to be fixed later
		// with login module.
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
