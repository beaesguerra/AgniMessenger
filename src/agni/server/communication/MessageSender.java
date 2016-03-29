package agni.server.communication;

import java.net.Socket;

public class MessageSender implements I_MessageSender {
    private Socket tcpSocket;

    public MessageSender() {

    }

    public void sendMessage(String ipAddress, byte[] message) {
        // TODO
    }
}
