package agni.server.receiver;

public interface LoginListener {

    public void loginRequest (String ip, String user, String password);
    public void newUserRequest(String ip, String user, String password);
}
