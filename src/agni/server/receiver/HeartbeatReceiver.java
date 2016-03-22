package agni.server.receiver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class HeartbeatReceiver implements MessageParser {

    private Vector <StatusListener> statusListeners = null;
	
    public HeartbeatReceiver() {

        statusListeners = new Vector<StatusListener>();	
    }
	
    private void notifyHeartbeat(String ip, byte status) {
        for( StatusListener  sListener: statusListeners )
        sListener.ReceivedHeartBeat(ip,status);	
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
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {
        String ip = null;
        byte parsedMessage = this.parseMessage(message);

        try {
            ip = channel.getRemoteAddress().toString();
       } catch (IOException e) {
          System.out.println("IOException unable to obtain channel's ip");
           e.printStackTrace();
       }

        notifyHeartbeat(ip, parsedMessage);	
    }
}
