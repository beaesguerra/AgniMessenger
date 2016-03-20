package agni.server.receiver;

public interface MessageParser {
	public void receiveMessage(InetAddres ip, byte[] message);
}
