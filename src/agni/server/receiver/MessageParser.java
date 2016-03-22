package agni.server.receiver;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface MessageParser {
	
	public void receiveMessage(SocketChannel channel, ByteBuffer message);
}
