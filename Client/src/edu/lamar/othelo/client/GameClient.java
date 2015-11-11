/*
 * Jason Hatton
 */

package edu.lamar.othelo.client;

import java.io.IOException;


public class GameClient extends AbstractClient
{

    private final static int MAX_ARGS = 5;

    private String username, password;
    private String[] serverArgs = new String[MAX_ARGS];

    //called upon the client logging in
	public GameClient(String username, String password,
	String host, int port) throws IOException
	{
		super(host, port);
		openConnection();
		sendToServer("connect");
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
