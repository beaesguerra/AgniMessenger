package agni.server.dataguard;

public class InfoDataGuard implements I_InfoDataGuard {
//  private database;

    public InfoDataGuard(String dbname, String username, String password) {

    }

    public String serverName() {
        return null;
    }

    public int currentOnline() {
        return -1;
    }

    public void incrementUsersOnline() {

    }

    public void decrementUsersOnline() {

    }

    public int totalUsers() {
        return -1;
    }
    /*
     * should return names of all users online 
     */
	@Override
	public String[] usersOnline() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * should return name of chats available
	 */
	@Override
	public String[] availableChats() {
		// TODO Auto-generated method stub
		return null;
	}
}
