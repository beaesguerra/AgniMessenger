package agni.server.receiver;

import java.util.Vector;

public class HeartbeatReceiver implements MessageParser {

    private Vector <HeartbeatListener> heartbeatListeners = null;

    public HeartbeatReceiver() {
        heartbeatListeners = new Vector<HeartbeatListener>();
    }

    private void notifyHeartbeat(String ip, byte status) {
        for ( HeartbeatListener  sListener : heartbeatListeners ) {
            sListener.receivedHeartBeat(ip, status);
        }
    }

    public void register(HeartbeatListener sListener) {
        heartbeatListeners.add(sListener);
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
