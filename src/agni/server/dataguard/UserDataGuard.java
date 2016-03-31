package agni.server.dataguard;

public class UserDataGuard implements I_UserDataGuard {
//  private database;

    public UserDataGuard(String dbname, String username, String password) {

    }

    public void addUser(String username, String salt, String passwordHash) {

    }

    public String salt(String user) {
        return null;
    }

    public String passwordHash(String user) {
        return null;
    }

    public String[] friends(String user) {
        return null;
    }

    public void createFriendship(String user1, String user2) {

    }

    public void changeUserCurrentIp(String user, String ip) {

    }

    /* return null if user not online */
    public String userCurrentIp(String user) {
        return null;
    }

    public void changeUserCurrentChat(String user, String chatname) {

    }

    /* return null if user not in a chat */
    public String userCurrentChat(String user) {
        return null;
    }

	@Override
	public boolean checkGroup(String groupName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsername(String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUserToChat(String username, String groupName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromChat(String username, String groupName) {
		// TODO Auto-generated method stub
		
	}
}
