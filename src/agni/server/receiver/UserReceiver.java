package agni.server.receiver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class UserReceiver implements MessageParser {

    private Vector <UserListener> userListeners = null;

    public UserReceiver() {

        userListeners = new Vector<UserListener>();
    }

    private void notifyUserRequest(String ip, byte type) {
        for( UserListener  uListener: userListeners )
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
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {
        String ip = null;
        byte parsedMessage = this.parseMessage(message);

        try {
             ip = channel.getRemoteAddress().toString();
        } catch (IOException e) {
           System.out.println("IOException unable to obtain channel's ip");
            e.printStackTrace();
        }

        notifyUserRequest(ip, parsedMessage);  
    }

}
