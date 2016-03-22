package agni.server.communication;

import java.net.InetAddress;
import java.net.Socket;

public class MessageSender implements I_MessageSender {
    private Socket tcpSocket;

    public MessageSender() {

    }

    public void sendMessage(InetAddress dest_ip, byte[] message) {
        // TODO
    }
}
