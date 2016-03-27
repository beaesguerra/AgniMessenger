package agni.server.receiver;

import java.nio.ByteBuffer;
import java.util.Vector;

public class InfoRequestReceiver implements MessageParser {
    private Vector <InfoListener> infoListeners = null;

    public InfoRequestReceiver() {
        infoListeners = new Vector<InfoListener>();
    }

    private void notifyInfoRequest(String ip, byte type) {
        for ( InfoListener  iListener : infoListeners )
            iListener.infoRequest(ip, type);
    }

    public void register(InfoListener iListener) {
        infoListeners.add(iListener);
    }

    /*
     * parse ByteBuffer into type byte
     * @requires ByteBuffer Message
     * @promises type as a byte
     */
    private byte parseMessage(ByteBuffer message) {
        byte parsedMessage = message.get(5);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, ByteBuffer message) {
        byte parsedMessage = parseMessage(message);

        notifyInfoRequest(ip, parsedMessage);  
    }

}
