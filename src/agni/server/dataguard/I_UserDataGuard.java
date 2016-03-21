package agni.server.dataguard;

public interface I_UserDataGuard {

    public String salt(String user);

    public String passwordHash(String user);

    public String[] friends(String user);

    public String currentChat(String user);
}
