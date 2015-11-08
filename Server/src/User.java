class User
{
	String name;
	int password_hash;
	boolean online;
	
	String getName()
	{
		return name;
	}
	int getPasswordHash()
	{
		return password_hash;
	}
	void setName(String s)
	{
		name = s;
	}
	void setPassword(String s)
	{
		password_hash = s.hashCode();
	}
	boolean isOnline()
	{
		return online;
	}
}
