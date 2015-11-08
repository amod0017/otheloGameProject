package edu.lamar.othelo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author agehlot
 */
public class ServerConsole implements edu.lamar.othelo.server.irp.ChatIF {
	static GameServer server;

	public ServerConsole(final int portNumber, final GameServer server) {
		ServerConsole.server = server;
		try {
			server.listen();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void display(final String message) {
		System.out.println("> " + message);
	}

	public void accept() {

		try {
			final BufferedReader fromConsole = new BufferedReader(
					new InputStreamReader(System.in));
			String message;
			while (true) {
				message = fromConsole.readLine();
				if (message.contains("#")) {
					executeCommand(message);
					// return;
					continue;
				}
				display("SERV MESSG: " + message);
				server.sendToAllClients("SERV MESSG: " + message);
			}
		} catch (final Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}

	}

	/**
	 * this methods check if message is a command. If yes it execute it.
	 *
	 * @param message
	 */
	private void executeCommand(final String message) {
		if (message.equals("#quit")) {
			try {
				server.sendToAllClients("Server is quitting");
				display("Server is quitting");
				server.close();
				System.exit(0);
			} catch (final IOException e) {
				e.printStackTrace();
				System.out.println("Error in quitting server");
			}
		} else if (message.equals("#stop")) {
			server.sendToAllClients("Server stopped listening");
			display("Server stopped listening");
			server.stopListening();
		} else if (message.equals("#close")){
			try {
				server.sendToAllClients("Server is closing.");
				display("Server is closing");
				server.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else if(message.startsWith("#setport")){
			final int port = Integer.parseInt(message.split("\\s+")[1]);
			server.setPort(port);
			display("Port set!");
			display("Type #stop then #start to apply the change.");
		} else if (message.equalsIgnoreCase("#start")) {
			try {
				server.listen();
				display("Server started");
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else if (message.equals("#getport")) {
			display("Get Port:"
					+String.valueOf(server.getPort()));
		}
	}

}
