package agni.server.receiver;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

public class UserReceiver implements MessageParser {
    private Vector <UserListener> userListeners = null;

    public UserReceiver() {
        userListeners = new Vector<UserListener>();
    }

    private void notifyUserRequest(String ip, byte type, String message) {
        for ( UserListener  uListener : userListeners ) {
            uListener.userRequest(ip, type, message);
        }
    }

    public void register(UserListener uListener) {
        userListeners.add(uListener);
    }

    /*
     * parse ByteBuffer type byte
     * @requires ByteBuffer Message
     * @promises user request type as a byte
     */
    private byte[] parseMessage(ByteBuffer message) {
        message.flip();
        int length = message.remaining();
        byte[] byteArray = new byte[length];
        message.get(byteArray);
        byte[] parsedMessage = Arrays.copyOfRange(byteArray,5,length);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, ByteBuffer message) {
        String userMessage = null;
        if(ip==null || message == null)
            throw new NullPointerException();
        byte[] parsedMessage = this.parseMessage(message);
        byte type = parsedMessage[0];
        try {
            userMessage = new String(Arrays.copyOfRange(parsedMessage,1,parsedMessage.length), "us-ascii");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        notifyUserRequest(ip, type, userMessage);  
    }
}
