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

    public int totalUsers() {
        return -1;
    }
}
