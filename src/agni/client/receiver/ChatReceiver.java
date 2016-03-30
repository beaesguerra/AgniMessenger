package agni.client.receiver;

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
		// TODO
		// need to parse src
		// notifyChatReceived(message.toString());
	}
    
    
}
