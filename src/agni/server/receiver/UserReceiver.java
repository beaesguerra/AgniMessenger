package agni.server.receiver;

import java.nio.ByteBuffer;
import java.util.Vector;

public class UserReceiver implements MessageParser {
    private Vector <UserListener> userListeners = null;

    public UserReceiver() {
        userListeners = new Vector<UserListener>();
    }

    private void notifyUserRequest(String ip, byte type) {
        for ( UserListener  uListener : userListeners )
            uListener.infoRequest(ip, type);
    }

    public void register(UserListener uListener) {
        userListeners.add(uListener);
    }

    /*
     * parse ByteBuffer type byte
     * @requires ByteBuffer Message
     * @promises user request type as a byte
     */
    private byte parseMessage(ByteBuffer message) {
        byte parsedMessage = message.get(5);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, ByteBuffer message) {
        if(ip==null || message == null)
            throw new IllegalArgumentException();
        byte parsedMessage = this.parseMessage(message);
        notifyUserRequest(ip, parsedMessage);  
    }
}
