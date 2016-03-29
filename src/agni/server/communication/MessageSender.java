package agni.server.communication;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class MessageSender implements I_MessageSender {
    private Socket tcpSocket;

    public MessageSender(Vector<IpChannelPair> channels) {

    }

    public void sendMessage(String ipAddress, byte[] message) {
        // TODO
    }
}
