/**
 *
 */
package edu.lamar.othelo.server;

import edu.lamar.othelo.server.irp.ChatIF;
import edu.lamar.othelo.server.irp.IMessage;

/**
 * @author agehlot
 *
 */
public class GameServer extends AbstractServer {

	final public static int DEFAULT_PORT = 5555;
	final private ChatIF serverConsole;

	public GameServer(final int port) {
		super(port);
		serverConsole = new ServerConsole(port, this);
	}

	/* (non-Javadoc)
	 * @see edu.lamar.othelo.server.AbstractServer#handleMessageFromClient(java.lang.Object, edu.lamar.othelo.server.ConnectionToClient)
	 */
	@Override
	protected void handleMessageFromClient(final Object msg, final ConnectionToClient client) {
		System.out.println("Message received: " + msg + " from " + client);
		serverConsole.display(((IMessage) msg).getMessage());

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

		final GameServer sv = new GameServer(port);

		try {
			sv.listen(); // Start listening for connections
			((ServerConsole) sv.serverConsole).accept();
		} catch (final Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
