package agni.server.receiver;

public interface ChatListener {

    public void chatRequest (String ip, byte[] message);
}
