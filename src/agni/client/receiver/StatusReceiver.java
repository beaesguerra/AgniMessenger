package agni.client.receiver;

import agni.client.view.AgniClientView;
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
		// how do we pass status here?1
//		notifyStatusReceived(src, status);
	}


}
