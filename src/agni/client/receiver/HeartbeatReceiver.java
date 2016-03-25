package agni.client.receiver;

import agni.client.view.AgniClientView;

public class HeartbeatReceiver extends MessageParser {

    public HeartbeatReceiver() {

    }

    private void notifyHeartbeatReceived() {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.heartbeatReaction();
    }

	@Override
	public void receiveMessage(String src, byte[] message) {
		notifyHeartbeatReceived();
	}



}
