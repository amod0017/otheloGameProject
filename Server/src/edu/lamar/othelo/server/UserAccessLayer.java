package edu.lamar.othelo.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Scanner;

class UserAccessLayer
{
	// it will only have file.
	private final File file;
	private static UserAccessLayer userAccessLayer = null;

	private UserAccessLayer() {
		final URL path = this.getClass().getResource("userInfoFile");
		file = new File(path.getFile());
	}

	public static UserAccessLayer getInstance() {
		if (userAccessLayer == null) {
			userAccessLayer = new UserAccessLayer();
		}
		return userAccessLayer;
	}

	public User getUser(final String loginId) {
		try {
			@SuppressWarnings("resource")
			final Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				final String userDetails = scanner.nextLine();
				// if line contains loginId tht means User is found. Create
				// object and return
				if (userDetails.contains(loginId)) {
					// parse userDetails on basis of ","
					final String[] userInfo = userDetails.split(",");
					return new User(userInfo[0], userInfo[1]);
				}
			}
			scanner.close(); // closing scanner
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return null; // should be handled
	}

	public void addUser(final User user) {
		try {
			final Writer output = new BufferedWriter(new FileWriter(file, true));
			boolean isUserExist = false;
			final Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				if (scanner.nextLine().contains(user.getName())) {
					isUserExist = true;
					break;
				}
			}
			if (!isUserExist) {
				output.append(user.getName() + "," + user.getPassword());
			}
			scanner.close();
			output.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
