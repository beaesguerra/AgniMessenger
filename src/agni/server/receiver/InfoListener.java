package agni.server.receiver;
import java.net.InetSocketAddress;


public interface InfoListener {

    public void infoRequest (InetSocketAddress address, String username, String password);
}
