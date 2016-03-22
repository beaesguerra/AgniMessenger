package agni.server.receiver;
import java.nio.channels.SocketChannel;

public interface LoginListener {

    public void loginRequest (SocketChannel channel, String user,String password);
}
