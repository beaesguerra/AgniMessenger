package agni.server.receiver;

public interface StatusListener {

    public void ReceivedHeartBeat (String ip, byte status);
}
