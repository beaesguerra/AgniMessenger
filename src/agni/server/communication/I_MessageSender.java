package agni.server.communication;

import java.net.InetAddress;

public interface I_MessageSender {
    public void sendMessage(String tEST_IP, byte[] message);
}
