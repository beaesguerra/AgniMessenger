package agni.server.receiver;

import java.util.Vector;

public class HeartbeatReceiver implements MessageParser {

    private Vector <StatusListener> statusListeners = null;

    public HeartbeatReceiver() {
        statusListeners = new Vector<StatusListener>();
    }

    private void notifyHeartbeat(String ip, byte status) {
        for ( StatusListener  sListener : statusListeners ) {
            sListener.receiveStatusChange(ip, status);
        }
    }

    public void register(StatusListener sListener) {
        statusListeners.add(sListener);
    }

    /*
     * parse byte[] into status byte
     * @requires byte[] Message
     * @promises status as byte
     */
    private byte parseMessage(byte[] message) {
        byte parsedMessage = message[5];
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, byte[] message) {
        if(ip==null || message == null)
            throw new NullPointerException();
        byte parsedMessage = this.parseMessage(message);
        notifyHeartbeat(ip, parsedMessage); 
    }
}
