package agni.client.receiver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class InformationReceiver extends MessageParser {

    public InformationReceiver() {

    }

    private void notifyInfoReceived(String info) {
        for (ReceiverListener rListener : super.listeners) {
            rListener.infoReaction(info);
        }
    }

    @Override
    public void receiveMessage(byte[] message) {
        if (message == null){
            throw new NullPointerException("receiveMessage got a null message");
        }

        ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(message, 0, 4)); // big-endian by default
        int infoLength = wrapped.getInt(); // 1        
        // Info starts in 5th bit
        String info = null;
        info = new String(Arrays.copyOfRange(message, 5,
                                             5 + infoLength),
                          StandardCharsets.US_ASCII);

        notifyInfoReceived(info);
    }
}
