package agni.client.communication;

import java.net.Socket;

public class MessageReceiver implements Runnable {
    private Socket tcpSocket;

    /*
     * Every message that it receives, it checks its message type, 
     * then passes the entire message onto the corresponding Receiver.
     * This also means that the MessageReceiver needs reference to 
     * each of the receivers. It's constructor will need to be updated.
     * */
    
    public MessageReceiver(Socket clientSocket) {
    	this.tcpSocket = clientSocket;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
