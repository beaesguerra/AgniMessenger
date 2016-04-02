package agni.client.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendMessage(byte[] message) {
        int length = message.length;
        try {
            dataOut.write(message, 0, length);
            dataOut.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
