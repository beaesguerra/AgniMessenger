package agni.server.receiver;

public class InfoRequestReceiver implements MessageParser {

    private Vector <InfoListener> infoListeners = null;

    public InfoRequestReceiver() {
        infoListeners = new Vector();
    }

    private void notifyInfoRequest(SocketChannel channel, byte type) {

        for( InfoListener  iListener: infoListeners )
            iListener.infoRequest(channel, type);
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
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyInfoRequest(channel, message);  
    }

}
