package agni.server.receiver;

import java.sql.SQLException;
import java.util.Vector;

public class InfoRequestReceiver implements MessageParser {
    private Vector <InfoListener> infoListeners = null;

    public InfoRequestReceiver() {
        infoListeners = new Vector<InfoListener>();
    }

    private void notifyInfoRequest(String ip, byte type) throws SQLException {
        for( InfoListener  iListener : infoListeners ) {
            iListener.infoRequest(ip, type);
        }
    }

    public void register(InfoListener iListener) {
        infoListeners.add(iListener);
    }

    /*
     * parse byte[] into type byte
     * @requires byte[] Message
     * @promises type as a byte
     */
    private byte parseMessage(byte[] message) {
        byte parsedMessage = message[5];
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, byte[] message) throws SQLException {
        if(ip==null || message == null)
            throw new NullPointerException();
        byte parsedMessage = parseMessage(message);
        notifyInfoRequest(ip, parsedMessage);  
    }

}
