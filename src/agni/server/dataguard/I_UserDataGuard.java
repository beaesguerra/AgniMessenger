package agni.server.dataguard;

public interface I_UserDataGuard {

    public void addUser(String username, String salt, String passwordHash);

    public String salt(String user);

    public String passwordHash(String user);

    public String[] friends(String user);

    public void createFriendship(String user1, String user2);

    public void changeUserCurrentIp(String user, String ip);

    public String userCurrentIp(String user);

    public void changeUserCurrentChat(String user, String chatname);

    public String userCurrentChat(String user);

	public boolean groupExists(String groupName);

	public String getUsername(String ip);

	public void addUserToChat(String username, String groupName);

	public void removeUserFromChat(String username, String groupName);

	public String getFriendList(String username);

	public boolean userExists(String message);

	public void addFriend(String username, String friend);

	public void logoutUser(String username);

	public boolean isOnline(String friend);
}
