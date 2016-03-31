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
		// Sender's name -> 6th bit [5]
		String src = "" + (char) message[5];
		byte[] parsedMessage = Arrays.copyOfRange(message, 5, message.length);
		try {
			notifyChatReceived(src, new String(parsedMessage, "us-ascii"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
