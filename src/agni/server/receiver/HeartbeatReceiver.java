package agni.server.receiver;

public class HeartbeatReceiver implements MessageParser {

    private Vector <StatusListener> statusListeners = null;
	
    public HeartbeatReceiver() {

        statusListeners = new Vector();	
    }
	
    private void notifyHeartbeat(String ip, byte[] message) {

        for( StatusListener  sListener: statusListeners )
        sListener.ReceivedHeartBeat(ip,status);	
    }
	
    public void register(StatusListener sListener) {
    	statusListeners.add(sListener);
	
    }

    private byte[] parseMessage(ByteBuffer message) {
    	
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length);
        return parsedMessage;
    }


    @Overide
    public void receiveMessage(InetAddress ip, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyHeartBeat(ip.toString(), message);	
    }
}
