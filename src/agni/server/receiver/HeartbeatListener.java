package agni.server.receiver;

public interface HeartbeatListener {
	
	public void receivedHeartBeat (String ip, byte status);
}
