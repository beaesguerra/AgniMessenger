package agni.client.receiver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class InformationReceiver extends MessageParser {

    public InformationReceiver() {

    }

    private void notifyInfoReceived(String info) {
    	for(ReceiverListener rListener : super.listeners) {
    		rListener.infoReaction(info);
    	}
    }

	@Override
	public void receiveMessage(byte[] message) {
		if(message == null)
			throw new NullPointerException("receiveMessage got a null message");
		int infoLength = (int) message[0] +
				         (int) message[1] +
				         (int) message[2];
		// Info starts in 5th bit
		String info = null;
		try {
			info = new String(Arrays.copyOfRange(message, 5,
					                 5 + infoLength),
					                 "us-ascii");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		notifyInfoReceived(info);
	}
}
