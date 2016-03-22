package agni.server.receiver;
import java.nio.channels.SocketChannel;


public interface ChatListener {

    public void chatRequest (SocketChannel channel, byte[] message);
}
