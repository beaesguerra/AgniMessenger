package agni.server;

import java.nio.channels.SocketChannel;

public class IPChannelPair{
	private final String ipAddress;
	private final SocketChannel channel; 
	
	public IPChannelPair(String ipAddress, SocketChannel channel) {
		this.ipAddress = ipAddress;
		this.channel = channel; 
	}
	
}
