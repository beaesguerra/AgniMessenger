package agni.server.communication;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class ChannelList {
    private HashMap<String, SocketChannel> channels; 
 
    public ChannelList() {
        channels = new HashMap<String, SocketChannel>();
    }
   
    public void addPair(String IPaddress, SocketChannel channel) {
    	System.out.println("ChannelList Adding " + IPaddress);
        channels.put(IPaddress, channel); 
    }
    public void removeIP(String IPaddress) {
    	System.out.println("ChannelList Removing " + IPaddress);
        channels.remove(IPaddress); 
    }
    
    public SocketChannel getChannel(String IPaddress){
    	System.out.println("ChannelList Getting " + IPaddress);
        return channels.get(IPaddress);
    }
    
}
