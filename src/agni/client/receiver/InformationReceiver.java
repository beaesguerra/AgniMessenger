package agni.client.receiver;

import agni.client.view.AgniClientView;

public class InformationReceiver extends MessageParser {

    public InformationReceiver() {

    }

    private void notifyInfoReceived(String message) {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.infoReaction(message);
    }

	@Override
	public void receiveMessage(byte[] message) {
		// 5th bit
		String info = "" + (char) message[4];
		notifyInfoReceived(info);
	}
}
