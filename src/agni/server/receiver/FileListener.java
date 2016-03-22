package agni.server.receiver;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface FileListener {

    public void fileRequest (SocketChannel channel,byte[] type);
}
