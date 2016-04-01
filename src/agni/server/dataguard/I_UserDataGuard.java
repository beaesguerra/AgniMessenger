package agni.server.dataguard;

public interface I_UserDataGuard {

    public void addUser(String username, String salt, String passwordHash);

    public String salt(String user);

    public String getPasswordHash(String user);

    public String[] friends(String user);

    public void createFriendship(String user1, String user2);

    public void changeUserCurrentIp(String user, String ip);

    public String userCurrentIp(String user);

    public void changeUserCurrentChat(String user, String chatname);

    public String userCurrentChat(String user);

	public void userLogin(String ip, String user);

	public boolean userExists(String user);
}
