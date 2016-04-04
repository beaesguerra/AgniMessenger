package agni.server.dataguard;

public interface I_InfoDataGuard {
    public String serverName();
    public int currentOnline();
    public void incrementUsersOnline();
    public void decrementUsersOnline();
    public int totalUsers();
    public String [] usersOnline();
    public String [] availableChats(); 
}
