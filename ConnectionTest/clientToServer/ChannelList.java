import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class ChannelList {
    private HashMap<String, SocketChannel> channels; 
    
    public ChannelList() {
        channels = new HashMap<String, SocketChannel>();
    }

    public void addPair(String IPaddress, SocketChannel channel) {
        channels.put(IPaddress, channel); 
    }
    public void removeIP(String IPaddress) {
        channels.remove(IPaddress); 
    }
    
    public SocketChannel getChannel(String IPaddress){
        return channels.get(IPaddress);
    }
    
}
