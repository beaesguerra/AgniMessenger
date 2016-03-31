package agni.client.receiver;

public class InformationReceiver extends MessageParser {

    public InformationReceiver() {

    }

    private void notifyInfoReceived(String info) {
    	for(ReceiverListener rListener : super.listeners)
    		rListener.infoReaction(info);
    }

	@Override
	public void receiveMessage(byte[] message) {
		// Info is the 5th bit
		String info = "" + (char) message[4];
		notifyInfoReceived(info);
	}
}
