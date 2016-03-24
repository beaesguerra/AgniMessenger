package agni.server.communication;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class MessageSender {
    private Socket tcpSocket;

    public MessageSender() {

    }

    public void sendMessage(SocketChannel channel, byte[] message) {
        // TODO
    }
}
