/*
 * Jason Hatton
 */

package edu.lamar.othelo.client;

import javax.swing.*;

import edu.lamar.othelo.common.MessageImpl;

import java.io.IOException;
import java.net.ConnectException;

public class GameClient extends AbstractClient {

	private final static int MAX_ARGS = 5;

	private String username;
	@SuppressWarnings("unused")
	private String password; // FIXME if it is need to be used thn add it else
								// remove
	@SuppressWarnings("unused")
	private String host = "127.0.0.1";
	public int port = 5555;

	public GUI.SpaceState friend;
	public GUI.SpaceState foe;

	GUI gameUI;
	LoginUI loginUI;

	private String[] serverArgs = new String[MAX_ARGS];

	// called upon the client logging in if client has no ID
	public GameClient(String host, int port) throws IOException {
		super(host, port);
		openConnection();
		//sendToServer("connect_guest");
	}

	public GameClient(String host, int port, String username, String password,
			String kind) throws IOException {

		super(host, port);

		try {
			openConnection();
		} catch (ConnectException ce) {
			JOptionPane
					.showMessageDialog(
							null,
							"Could not find a server on the specified address and port.\nPlease ensure you have the right address and port.");
		}

		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;

		// e.g. register_jason_password
		// e.g. login_jason_password

		sendToServer(kind + "_" + username + "_" + password);
	}

	public static void main(String[] args) throws IOException {
		LoginUI loginUI = new LoginUI();
	}

	public void quit() throws IOException {
		closeConnection();
		System.exit(0);
	}

	public void handleMessageFromServer(Object msg) {
		serverArgs = msg.toString().split("_");
		switch (serverArgs[0]) {
		case "login":
			if (serverArgs[1].equals("success"))
				JOptionPane.showMessageDialog(null, "Login successful!");
			if (serverArgs[1].equals("failure"))
				JOptionPane.showMessageDialog(null,
						"Invalid login.\nAre you registered?");
			try {
				GameClient.main(new String[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "register":
			if (serverArgs[1].equals("success"))
				JOptionPane.showMessageDialog(null, "Registration successful, "
						+ username + ".\nYou may now log in");
			if (serverArgs[1].equals("failure"))
				JOptionPane
						.showMessageDialog(
								null,
								"Invalid registration.\nYour username must be 5 letters or digits.\nYour password must be 5 digits.");
			try {
				GameClient.main(new String[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "start":
			if (serverArgs[1].equals("black")) {
				friend = GUI.SpaceState.black;
				foe = GUI.SpaceState.white;
			}
			if (serverArgs[1].equals("white")) {
				friend = GUI.SpaceState.white;
				foe = GUI.SpaceState.black;
			}
			gameUI = new GUI(friend, foe);
			break;
		case "move":
			int row = Integer.parseInt(serverArgs[1]);
			int column = Integer.parseInt(serverArgs[2]);
			gameUI.setSpace(foe, row, column);
			break;
		}

		// here's the idea, split the string and use args[0] as the command
		// and args[1-n] as the arguments.
		// no big elseif block, no case where "register login" breaks the system
		// no need for a message interface

	}

	public void register(String username2, String pd) {
		try {
			sendToServer(new MessageImpl("register"+username2+"pd", "register", username2, null, pd));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
