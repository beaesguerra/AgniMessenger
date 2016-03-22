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

    public String userCurrentIp(String user) {
        return null;
    }

    public void changeUserCurrentChat(String user, String chatname) {

    }

    public String userCurrentChat(String user) {
        return null;
    }
}
