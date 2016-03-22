package agni.server.dataguard;

public class UserDataGuard implements I_UserDataGuard {
//  private database;

    public UserDataGuard(String dbname, String username, String password) {

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

    public String currentChat(String user) {
        return null;
    }
}
