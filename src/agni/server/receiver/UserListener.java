package agni.server.receiver;

public interface UserListener {

    public void userRequest(String ip, byte type, String testMessage);
}
