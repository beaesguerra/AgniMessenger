package agni.server.receiver;

import java.nio.ByteBuffer;
import java.util.Vector;

public class HeartbeatReceiver implements MessageParser {

    private Vector <StatusListener> statusListeners = null;

    public HeartbeatReceiver() {

        statusListeners = new Vector<StatusListener>();
    }

    private void notifyHeartbeat(String ip, byte status) {
        for ( StatusListener  sListener : statusListeners )
            sListener.ReceivedHeartBeat(ip, status);
    }

    public void register(StatusListener sListener) {
        statusListeners.add(sListener);
    }

    /*
     * parse ByteBuffer into status byte
     * @requires ByteBuffer Message
     * @promises status as byte
     */
    private byte parseMessage(ByteBuffer message) {
        byte parsedMessage = message.get(5);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, ByteBuffer message) {
        byte parsedMessage = this.parseMessage(message);

        notifyHeartbeat(ip, parsedMessage);	
    }
}
