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
	String myColor = null;
	String opponentColor = null;

	//GUI gameUI;
	LoginUI loginUI;
	GUI gameUI = null;
	static GameClient instance;

	private static LoginUI loginUi;
	private String[] serverArgs = new String[MAX_ARGS];

	private GameClient(final String host, final int port) throws IOException {
		super(host, port);
		openConnection();
	}

	public static GameClient getInstance(final String host, final int port){
		try {
			if (instance== null) {
				instance = new GameClient(host, port);
			}
			return instance;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(final String[] args) throws IOException {
		loginUi = new LoginUI();
	}

	public void quit() throws IOException {
		closeConnection();
		System.exit(0);
	}

	@SuppressWarnings("static-access")
	@Override
	public void handleMessageFromServer(final Object msg) {
		System.out.println("msg recived" + msg);
		serverArgs = msg.toString().split("_");

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
				myColor = "black";
				opponentColor = "white";
				friend = GUI.SpaceState.black;
				foe = GUI.SpaceState.white;
			}
			if (serverArgs[1].equals("white")) {
				friend = GUI.SpaceState.white;
				foe = GUI.SpaceState.black;
				myColor = "white";
				opponentColor = "black";
			}
			System.out.println("creating game UI");
			gameUI = new GUI(username + "_" + myColor, this);
			loginUi.frame.setVisible(false);
			break;
		case "move":
			final int row = Integer.parseInt(serverArgs[1]);
			final int column = Integer.parseInt(serverArgs[2]);
			if (serverArgs[3].equals(username)) {
				//gameUI.setSpace(friend, row, column);
				//GUI.drawBoard(gameUI.board);
				GUI.update(row, column, myColor);
			}else{
				GUI.update(row, column, opponentColor);
			}
			break;
		case "wait":
			System.out.println("waiting");
			JOptionPane.showMessageDialog(loginUi.frame, "Waiting for other player to play the game.");
			break;
			// Thread.sleep(10000);
			// sendToServer(new
			// MessageImpl("startGame","game",username,null,null));

			//}
		case "lost":
			JOptionPane.showMessageDialog(GUI.chessboard,
					"You lost the game. You won the game. Please come back again to keep playing");
			gameUI.setVisible("false");
			try {
				closeConnection();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		case "won":
			JOptionPane.showMessageDialog(GUI.chessboard,
					"You won the game. Please come back again to keep playing");
			gameUI.setVisible("false");
			try {
				closeConnection();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		case "moves":
			final int row1 = Integer.parseInt(serverArgs[1]);
			final int column1 = Integer.parseInt(serverArgs[2]);
			if (serverArgs[3].equals("white")) {
				GUI.update(row1, column1, "white");
			}else if(serverArgs[3].equals("black")){
				GUI.update(row1, column1, "black");
			}else{
				GUI.update(row1, column1, "empty");
			}
			break;
			
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

	public static GameClient getInstance() {
		return instance;
	}

	public void handleMakeAMove(final int row, final int col) {
		try {
			sendToServer(new MessageImpl("makeAMove", "game", username, row+ "," + col, null));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void sendQuit() {
		try {
			sendToServer(new MessageImpl("quit", "game", username, null, null));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
