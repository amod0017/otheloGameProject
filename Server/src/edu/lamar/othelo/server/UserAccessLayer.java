package edu.lamar.othelo.server;

import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

class UserAccessLayer
{
	File filehandler;

	public void writeUser(User u, String s) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("users/" + u.getName()), "utf-8"))) {
			writer.write("something");
		} catch (Exception e) {
		}

	}

	public void readUser(User u) {

	}

	public boolean userExists(User u) {
		return true;
	}

	public void registerUser(User u) {

	}

	public boolean isValidUserName(String s) {
		return true;
	}


	
}
