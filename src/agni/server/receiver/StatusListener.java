package agni.server.receiver;

public interface StatusListener {

    public void receiveStatusChange (String ip, byte status);
}
