package agni.client.receiver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import agni.server.sender.StatusSender.Status;

public class StatusReceiver extends MessageParser {

    public StatusReceiver() {}

    private void notifyStatusReceived(String user, Status status) {
    	for(ReceiverListener rListener : super.listeners) {
    		rListener.statusReaction(user, status);
    	}
    }
    
	@Override
	public void receiveMessage(byte[] message) {
		if(message == null)
			throw new NullPointerException("receiveMessage got a null message");
		// Sender's name length -> 5th bit
		int senderNameLength = (int) message[5];
		// Sender's name is the 6th bit
		String src = null;
		try {
			src = new String(Arrays.copyOfRange(message, 6, 6 + senderNameLength), "us-ascii");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// Status is the 5th bit
		switch(message[5]) {
		case 0x00:
			notifyStatusReceived(src, Status.OFFLINE);
			break;
		case 0x01:
			notifyStatusReceived(src, Status.ONLINE);
			break;
		}
	}
}
