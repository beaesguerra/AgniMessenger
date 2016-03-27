package agni.server.receiver;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface MessageParser {
	
	public void receiveMessage(String ip, ByteBuffer message);
}
