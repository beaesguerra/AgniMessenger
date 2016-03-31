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
		// Sender's name -> 6th bit [5]
		int senderNameLength = (int) message[5];
		byte[] parsedMessage = Arrays.copyOfRange(message, 7+senderNameLength, message.length);
		try {
			String src = new String(Arrays.copyOfRange(message, 6, 6+senderNameLength), "us-ascii");
			notifyChatReceived(src, new String(parsedMessage, "us-ascii"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
