package agni.client.receiver;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

import agni.client.view.AgniClientView;

public class ChatReceiver extends MessageParser {
	// Not sure if we use this or the array in MessageParser
//	private Vector <AgniClientView> views = null; 
	
    public ChatReceiver() {
//    	views = new Vector<AgniClientView>();
    }

    private void notifyChatReceived(String src, String message) {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.chatReaction(src, message);
    }
    
	@Override
	public void receiveMessage(byte[] message) {
		// Sender's name -> 6th bit [5]
		String src = "" + (char) message[5];
		notifyChatReceived(src, message.toString());
	}
}
