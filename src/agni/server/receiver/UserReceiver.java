package agni.server.receiver;

public class UserReceiver implements MessageParser {

    private Vector <UserListener> userListeners = null;

    public UserReceiver() {

        userListeners = new Vector();
    }

    private void notifyUserRequest(SocketChannel channel, byte type) {

        for( UserListener  uListener: userListeners )
            uListener.infoRequest(channel, type);
    }

    public void register(UserListener uListener) {

        userListeners.add(uListener);
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
        notifyUserRequest(channel, message);  
    }

}
