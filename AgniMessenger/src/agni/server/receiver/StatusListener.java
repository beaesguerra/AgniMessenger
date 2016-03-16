package agni.server.receiver;

public interface StatusListener {

    /*
    * Receives user requests from the HeartbeatReceiver receiver
    * ip will have been converted from inet to String
    */
    public void ReceivedHeartBeat (String ip, byte status);
}
