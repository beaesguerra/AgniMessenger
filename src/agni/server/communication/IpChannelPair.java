package agni.server.communication;

import java.nio.channels.SocketChannel;

public class IpChannelPair{
	private final String ipAddress;
	private final SocketChannel channel; 
	
	public IpChannelPair(String ipAddress, SocketChannel channel) {
		this.ipAddress = ipAddress;
		this.channel = channel; 
	}
}
