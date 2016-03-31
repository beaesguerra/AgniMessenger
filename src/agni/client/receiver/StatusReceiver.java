package agni.client.receiver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import agni.server.sender.StatusSender.Status;

public class StatusReceiver extends MessageParser {

    public StatusReceiver() {

    }

    private void notifyStatusReceived(String user, Status status) {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.statusReaction(user, status);
    }

	@Override
	public void receiveMessage(byte[] message) {
		if(message == null)
			throw new IllegalArgumentException("receiveMessage got a null message");
		// Sender's name length -> 5th bit [4]
		int senderNameLength = (int) message[4];
		// Sender's name is the 6th bit
		String src;
		try {
			src = new String(Arrays.copyOfRange(message, 5, 5 + senderNameLength), "us-ascii");
			// Status is the 5th bit
			switch(message[4]) {
			case 0x00:
				notifyStatusReceived(src, Status.OFFLINE);
				break;
			case 0x01:
				notifyStatusReceived(src, Status.ONLINE);
				break;
			case 0x02:
				notifyStatusReceived(src, Status.AWAY);
				break;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
