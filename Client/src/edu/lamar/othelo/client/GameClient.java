/*
 * Jason Hatton
 */

package edu.lamar.othelo.client;

import java.io.IOException;


public class GameClient extends AbstractClient
{

    private final static int MAX_ARGS = 5;
    public String username, password, host = "127.0.0.1";
    public int port = 5555;
    public boolean loginDone = false;
    GUI gameUI;
    LoginUI loginUI;
    private String[] serverArgs = new String[MAX_ARGS];

    //called upon the client logging in if client has no ID
    public GameClient(String host, int port) throws IOException {
        super(host, port);
		openConnection();
        sendToServer("connect_guest");
    }


    public GameClient(String host, int port, String username, String password, String kind) throws IOException {
        super(host, port);
        openConnection();
        sendToServer(kind + "_" + username + "_" + password);
    }

    public static void main() throws IOException {
        LoginUI loginUI = new LoginUI();
    }

    public void quit() throws IOException
	{
		closeConnection();
		System.exit(0);
	}
	
	public void handleMessageFromServer(Object msg)
	{
		serverArgs = msg.toString().split(" ");
		switch (serverArgs[0])
		{
			case "login":
			
				break;
			case "register":
				break;
			case "start":
				break;
			case "move":
				break;
		}
		
		//here's the idea, split the string and use args[0] as the command
		//and args[1-n] as the arguments.
		//no big elseif block, no case where "register login" breaks the system
		//no need for a message interface
		
	}
	
}
