package agni.client.receiver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ChatReceiver extends MessageParser {
	
    public ChatReceiver() {
    	
    }

    private void notifyChatReceived(String src, String message) {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.chatReaction(src, message);
    }
    
	@Override
	public void receiveMessage(byte[] message) {
		if(message == null)
			throw new NullPointerException("receiveMessage got a null message");
		// Sender's name length -> 5th bit [4]
		int senderNameLength = (int) message[4];
		// Message starts -> 7th bit [6]
		byte[] parsedMessage = Arrays.copyOfRange(message, 6+senderNameLength, message.length);
		try {
			// Sender's name starts -> 6th bit [5]
			String src = new String(Arrays.copyOfRange(message, 5, 5+senderNameLength), "us-ascii");
			notifyChatReceived(src, new String(parsedMessage, "us-ascii"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
