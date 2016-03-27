package agni.server.receiver;

import java.nio.ByteBuffer;

public interface MessageParser {	
	public void receiveMessage(String ip, ByteBuffer message);
}
