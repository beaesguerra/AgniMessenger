package agni.client.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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

    @Override
    public void run() {
        // Initialize inputBuffer
    	try {
			BufferedReader inBuffer =
			        new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}