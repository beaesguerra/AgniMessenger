package agni.server.communication;

import java.net.InetAddress;

public interface I_MessageSender {
    public void sendMessage(InetAddress dest_ip, byte[] message);
}
