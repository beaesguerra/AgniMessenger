package agni.server.dataguard;

public interface I_UserDataGuard {

    public void registerUser(String username, String salt, String passwordHash);

    public String salt(String user);

    public String getPasswordHash(String user);

    public String[] getFriends(String user);

    public void createFriendship(String user1, String user2);

    public void changeUserCurrentIp(String user, String ip);

    public String userCurrentIp(String user);

    public String getUsername(String ip);

    public void loginUser(String ip, String user);

    public boolean userExists(String user);

    public boolean isOnline(String friend);

    public String getIp(String member);

	String[] usersOnline();
        
}
