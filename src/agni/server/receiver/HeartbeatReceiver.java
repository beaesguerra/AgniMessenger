package agni.server.receiver;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class HeartbeatReceiver implements MessageParser {

    private Vector <StatusListener> statusListeners = null;
	
    public HeartbeatReceiver() {

        statusListeners = new Vector<StatusListener>();	
    }
	
    private void notifyHeartbeat(SocketChannel channel, byte[] status) {

        for( StatusListener  sListener: statusListeners )
        sListener.ReceivedHeartBeat(channel,status);	
    }
	
    public void register(StatusListener sListener) {
    	statusListeners.add(sListener);
	
    }

    private byte[] parseMessage(ByteBuffer message) {
    	
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length));
        return parsedMessage;
    }


    @Override
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyHeartbeat(channel, parsedMessage);	
    }
}
