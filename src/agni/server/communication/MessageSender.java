package agni.server.communication;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Vector;

import agni.server.IPChannelPair;

public class MessageSender implements I_MessageSender {
    private Socket tcpSocket;

    public MessageSender(Vector<IPChannelPair<String, SocketChannel>> channels) {

    }

    public void sendMessage(InetAddress dest_ip, byte[] message) {
        // TODO
    }
}
