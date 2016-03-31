package agni.client.receiver;

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
		// Sender's name is the 6th bit
		String src = "" + (char) message[5];
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
	}
}
