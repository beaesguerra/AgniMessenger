package agni.server.receiver;

public interface UserListener {

    public void infoRequest(String ip, byte type, String testMessage);
}
