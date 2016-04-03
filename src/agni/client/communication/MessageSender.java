package agni.client.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import agni.client.receiver.ChatReceiver;
import agni.client.receiver.FileReceiver;
import agni.client.receiver.HeartbeatReceiver;
import agni.client.receiver.InformationReceiver;
import agni.client.receiver.StatusReceiver;

public class MessageSender {
    private Socket tcpSocket;
    private DataOutputStream dataOut = null;

    public MessageSender(Socket clientSocket) {
        tcpSocket = clientSocket;
        OutputStream out;
        try {
            out = tcpSocket.getOutputStream();
            dataOut = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendMessage(byte[] message) {
        int length = message.length;
        try {
            dataOut.write(message, 0, length);
            dataOut.flush();
        } catch (IOException e) {
            System.out.println("Error writing to the DataOutputStream")
            e.printStackTrace();
        }
    }
}
