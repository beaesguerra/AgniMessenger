package agni.client.communication;

import java.net.Socket;

public class MessageSender {

    private Socket tcpSocket;

    public MessageSender(Socket clientSocket) {
    	this.tcpSocket = clientSocket;
    }

    public void sendMessage(byte[] message) {

    }
}
