package agni.server.receiver;

public class InfoRequestReceiver implements MessageParser {

    private Vector <InfoListener> infoListeners = null;

    public InfoRequestReceiver() {
        infoListeners = new Vector();
    }

    private void notifyInfoRequest(String ip, byte type) {

        for( InfoListener  iListener: infoListeners )
            iListener.infoRequest(ip, type);
    }

    public void register(InfoListener iListener) {

        infoListeners.add(iListener);
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
        notifyInfoRequest(ip.toString(), message);  
    }

}
