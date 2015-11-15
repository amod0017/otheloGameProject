package edu.lamar.othelo.server;
class User
{
	private String name;
	private String password;
	private boolean online;

	User(final String name, final String password) {
		this.name = name;
		this.password = password;
	}
	String getName()
	{
		return name;
	}

	String getPassword()
	{
		return password;
	}
	void setName(final String s)
	{
		name = s;
	}
	void setPassword(final String s)
	{
		password = s;
	}
	boolean isOnline()
	{
		return online;
	}

	@Override
	public String toString() {
		return name;
	}
}
