package agni.server.receiver;
import java.nio.channels.SocketChannel;

public interface UserListener {

    public void userRequest (SocketChannel channel, byte[] type);
}
