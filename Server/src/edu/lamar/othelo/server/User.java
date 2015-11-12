package edu.lamar.othelo.server;
class User
{
	String name;
	int password_hash;
	boolean online;
	String loginId;

	String getName()
	{
		return name;
	}
	int getPasswordHash()
	{
		return password_hash;
	}
	void setName(final String s)
	{
		name = s;
	}
	void setPassword(final String s)
	{
		password_hash = s.hashCode();
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
