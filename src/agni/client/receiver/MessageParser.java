package agni.client.receiver;

import java.util.Vector;

import agni.client.view.*;

public abstract class MessageParser {
    protected Vector <ReceiverListener> listeners;

    public void register(AgniClientView listener) {
    	// Need to check if new-view exists in the vector
    	if(!listeners.contains(listener))
    		listeners.add(listener);
    }
    
    public void deregister(AgniClientView listener) {
    	// Need to check if new-view exists in the vector
    	if(listeners.contains(listener))
    		listeners.remove(listener);
    }
    
    public abstract void receiveMessage(byte[] message);
}
