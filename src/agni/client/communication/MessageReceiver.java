package agni.client.communication;

import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

import agni.client.receiver.*;;

public class MessageReceiver implements Runnable {
    private Socket tcpSocket;
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
    	this.tcpSocket = clientSocket;
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

        public byte bytes() {
            return bytes;
        }
    }

    @Override
    public void run() {
    	// Declaring incoming buffer and line to read to
    	String line = null;
    	BufferedReader inBuffer = null;
    	byte[] byteLine = null;
    	while (!line.equals("terminate")) {
    		// Initialize inputBuffer
    		try {
    			inBuffer =
    					new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
    			line = inBuffer.readLine();
    			byteLine = line.getBytes();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

    		// Need to fix this to work with enums directly 
    		// messageType = 5th bit
    		switch(byteLine[4]) {
    		case 0x07:
    			// Pass to heartbeatReceiver
    			heartbeatReceiver.receiveMessage(byteLine);
    			break;
    		case 0x08:
    			// Pass to informationReceiver
    			informationReceiver.receiveMessage(byteLine);
    			break;
    		case 0x09:
    			// Insert action
    			chatReceiver.receiveMessage(byteLine);
    			break;
    		case 0x0A:
    			// Insert action
    			fileReceiver.receiveMessage(byteLine);
    			break;
    		case 0x0B:
    			// Insert action
    			statusReceiver.receiveMessage(byteLine);
    			break;
    		}
    	}
    	// After termination functionality
    	System.out.println("MessageReceiver Terminated");
    	try {
			tcpSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}