/*
 * Jason Hatton
 */

package edu.lamar.othelo.client;

import java.io.IOException;

import javax.swing.JOptionPane;

import edu.lamar.othelo.common.MessageImpl;

public class GameClient extends AbstractClient {

	private final static int MAX_ARGS = 5;

	private String username;
	public int port = 5555;

	public GUI.SpaceState friend;
	public GUI.SpaceState foe;

	//GUI gameUI;
	LoginUI loginUI;

	private String[] serverArgs = new String[MAX_ARGS];

	public GameClient(final String host, final int port) throws IOException {
		super(host, port);
		openConnection();
	}

	//FIXME this should be removed as constructor should only do one thing.

	//	public GameClient(String host, int port, String username, String password,
	//			String kind) throws IOException {
	//
	//		super(host, port);
	//
	//		try {
	//			openConnection();
	//		} catch (ConnectException ce) {
	//			JOptionPane
	//					.showMessageDialog(
	//							null,
	//							"Could not find a server on the specified address and port.\nPlease ensure you have the right address and port.");
	//		}
	//
	//		this.username = username;
	//		this.password = password;
	//		this.host = host;
	//		this.port = port;
	//
	//		// e.g. register_jason_password
	//		// e.g. login_jason_password
	//
	//		sendToServer(kind + "_" + username + "_" + password);
	//	}

	public static void main(final String[] args) throws IOException {
		new LoginUI();
	}

	public void quit() throws IOException {
		closeConnection();
		System.exit(0);
	}

	@Override
	public void handleMessageFromServer(final Object msg) {
		System.out.println("msg recived" + msg);
		serverArgs = msg.toString().split("_");
		boolean isGameStarted = false;
		GUI gameUI = null;
		switch (serverArgs[0]) {
		case "login":
			if (serverArgs[1].equals("success"))
			{
				try {
					username = serverArgs[2];
					// JOptionPane.showConfirmDialog(null, "Test I am here");
					sendToServer(new MessageImpl("startGame","game",username,null,null));
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (serverArgs[1].equals("failure")) {
				JOptionPane.showMessageDialog(null,
						"Invalid login.\nAre you registered?");
			}

			break;
		case "register":
			if (serverArgs[1].equals("success")) {
				JOptionPane.showMessageDialog(null, "Registration successful "
						+ ".\nYou may now log in");
			}
			if (serverArgs[1].equals("failure")) {
				JOptionPane
				.showMessageDialog(
						null,
						"Invalid registration.\n");
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
			System.out.println("creating game UI");
			gameUI = new GUI(friend, foe);
			isGameStarted = true;
			break;
		case "move":
			final int row = Integer.parseInt(serverArgs[1]);
			final int column = Integer.parseInt(serverArgs[2]);
			gameUI.setSpace(foe, row, column);
			break;
		case "wait":
			//while(!isGameStarted){
				try {
					System.out.println("waiting");
					Thread.sleep(10000);
					//sendToServer(new MessageImpl("startGame","game",username,null,null));
				} catch (final InterruptedException e) {
					e.printStackTrace();
				//} catch (final IOException e) {
					e.printStackTrace();
				}

			//}
		}

		// here's the idea, split the string and use args[0] as the command
		// and args[1-n] as the arguments.
		// no big elseif block, no case where "register login" breaks the system
		// no need for a message interface

	}

	public void register(final String username2, final String pd) {
		try {
			sendToServer(new MessageImpl("register"+username2+"pd", "register", username2, null, pd));
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	public void login(final String username2, final String pd) {
		try {
			sendToServer(new MessageImpl("login"+"_"+pd, "Login", username2, null, pd));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
