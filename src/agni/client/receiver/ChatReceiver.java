package agni.client.receiver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ChatReceiver extends MessageParser {
	
    public ChatReceiver() {
    	
    }

    private void notifyChatReceived(String sender, String message) {
    	for(ReceiverListener rListener : super.listeners) {
    		rListener.chatReaction(sender, message);
    	}
    }
    
	@Override
	public void receiveMessage(byte[] message) {
		if(message == null)
			throw new NullPointerException("receiveMessage got a null message");
		// Sender's name length -> 5th bit
		int senderNameLength = (int) message[5];
		try {
			// Sender's name starts -> 6th bit
			String sender = new String(Arrays.copyOfRange(message, 6,
													   6 + senderNameLength),
													   "us-ascii");
			// Message starts -> 7th bit
			String parsedMessage = new String(Arrays.copyOfRange(message,
													  7 + senderNameLength,
													  message.length), "us-ascii");
			notifyChatReceived(sender, parsedMessage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
