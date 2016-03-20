package agni.server.receiver;

public interface StatusListener {

    public void ReceivedHeartBeat (ip, status);
}
