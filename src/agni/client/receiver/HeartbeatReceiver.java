package agni.client.receiver;

public class HeartbeatReceiver extends MessageParser {

    public HeartbeatReceiver() {

    }

    private void notifyHeartbeatReceived() {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.heartbeatReaction();
    }

	@Override
	public void receiveMessage(byte[] message) {
		notifyHeartbeatReceived();
	}
}
