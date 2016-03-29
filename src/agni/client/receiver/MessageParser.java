package agni.client.receiver;

import agni.client.view.*;

public abstract class MessageParser {
    private ReceiverListener[] listeners;

    public void register(AgniClientView listener) {
    	
    }
    
    public void deregister(AgniClientView listener) {
    	
    }
    
    public abstract void receiveMessage(byte[] message);
}
