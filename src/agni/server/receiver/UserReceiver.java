package agni.server.receiver;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class UserReceiver implements MessageParser {
    private Vector <UserListener> userListeners = null;

    public UserReceiver() {
        userListeners = new Vector<UserListener>();
    }

    private void notifyUserRequest(String ip, byte type, String message) {
        for ( UserListener  uListener : userListeners ) {
            try {
				uListener.userRequest(ip, type, message);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public void register(UserListener uListener) {
        userListeners.add(uListener);
    }

    /*
     * parse byte[] type byte
     * @requires byte[] Message
     * @promises user request type as a byte
     */
    private byte[] parseMessage(byte[] message) {
        int length = message.length;
        byte[] parsedMessage = Arrays.copyOfRange(message,5,length);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, byte[] message) {
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
        System.out.println("USERRECIEVER usermessage is " + userMessage);
        notifyUserRequest(ip, type, userMessage);  
    }
}
