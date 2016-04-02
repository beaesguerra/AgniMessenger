package agni.client.communication;

import java.net.Socket;

import agni.client.receiver.ChatReceiver;
import agni.client.receiver.FileReceiver;
import agni.client.receiver.HeartbeatReceiver;
import agni.client.receiver.InformationReceiver;
import agni.client.receiver.StatusReceiver;

public class MessageSender {
    private Socket tcpSocket;

    public MessageSender(Socket clientSocket) {

    }

    public void sendMessage(byte[] message) {

    }
}
