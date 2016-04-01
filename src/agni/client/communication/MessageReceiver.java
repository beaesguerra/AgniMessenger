package agni.client.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import agni.client.receiver.ChatReceiver;
import agni.client.receiver.FileReceiver;
import agni.client.receiver.HeartbeatReceiver;
import agni.client.receiver.InformationReceiver;
import agni.client.receiver.StatusReceiver;

public class MessageReceiver implements Runnable {
    private Socket socket;
    private HeartbeatReceiver heartbeatReceiver;
    private InformationReceiver informationReceiver;
    private StatusReceiver statusReceiver;
    private ChatReceiver chatReceiver;
    private FileReceiver fileReceiver;

    /*
     * Every message that it receives, it checks its message type, 
     * then passes the entire message onto the corresponding Receiver.
     * This also means that the MessageReceiver needs reference to 
     * each of the receivers. It's constructor will need to be updated.
     * */
    
    public MessageReceiver(Socket clientSocket,
                           HeartbeatReceiver heartbeatReceiver,
                           InformationReceiver informationReceiver,
                           StatusReceiver statusReceiver,
                           ChatReceiver chatReceiver,
                           FileReceiver fileReceiver) {
        this.socket = clientSocket;
        this.heartbeatReceiver = heartbeatReceiver;
        this.informationReceiver = informationReceiver;
        this.statusReceiver = statusReceiver;
        this.chatReceiver = chatReceiver;
        this.fileReceiver = fileReceiver;
    }
    
    public enum MessageTypes {
        HEARTBEAT((byte)0x07),
        INFO((byte)0x08),
        CHAT((byte)0x09),
        FILE((byte)0x0A),
        STATUS((byte)0x0B);
        
        private final byte bytes;
        private MessageTypes(byte bytes) {
            this.bytes = bytes;
        }

        final public byte bytes() {
            return bytes;
        }
    }

    @Override
    public void run() {
        // Declaring incoming buffer and line to read to
        String line = null;
        BufferedReader inBuffer = null;
        byte[] lineBytes = null;
        for (;;) {
            // Initialize inputBuffer
            try {
                inBuffer =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));
                line = inBuffer.readLine();
                lineBytes = line.getBytes(StandardCharsets.US_ASCII);
            } catch (IOException e) {
                System.exit(1);
            }

            byte messageType = lineBytes[4];
            if (messageType == MessageTypes.HEARTBEAT.bytes()) {
                // Pass to heartbeatReceiver
                heartbeatReceiver.receiveMessage(lineBytes);
            } else if (messageType == MessageTypes.INFO.bytes()) {
                // Pass to informationReceiver
                informationReceiver.receiveMessage(lineBytes);
            } else if (messageType == MessageTypes.CHAT.bytes()) {
                // Pass to chatReceiver
                chatReceiver.receiveMessage(lineBytes);
            } else if (messageType == MessageTypes.FILE.bytes()) {
                // Pass to fileReceiver
                fileReceiver.receiveMessage(lineBytes);
            } else if (messageType == MessageTypes.STATUS.bytes()) {
                // Pass to statusReceiver
                statusReceiver.receiveMessage(lineBytes);
            } else {
                assert(false);
            }
        }
    }
}