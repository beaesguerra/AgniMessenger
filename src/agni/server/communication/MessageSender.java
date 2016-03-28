package agni.server.communication;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class MessageSender implements I_MessageSender {
    private Socket tcpSocket;

    public MessageSender() {

    }

    public void sendMessage(String ipAddress, byte[] message) {
        // TODO
    }
}
