package agni.server.receiver;
import java.nio.channels.SocketChannel;


public interface StatusListener {

    public void ReceivedHeartBeat (SocketChannel channel, byte[] status);
}
