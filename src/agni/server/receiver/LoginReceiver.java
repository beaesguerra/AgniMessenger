package agni.server.receiver;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

public class LoginReceiver implements MessageParser {
    private Vector <LoginListener> loginListeners = null;

    public LoginReceiver() {
        loginListeners = new Vector<LoginListener>();
    }

    private void notifyLoginRequest(String ip, String username, String password) {
        for(LoginListener  lListener : loginListeners) {
            lListener.loginRequest (ip, username, password);
        }
    }

    public void register(LoginListener lListener) {
        loginListeners.add(lListener);
    }

    /*
     * parse ByteBuffer into strings
     * @requires ByteBuffer Message
     * @promises username and password as strings
     */
    private String[] parseMessage(ByteBuffer message) {
        message.flip();
        int length = message.remaining();
        byte[] byteArray = new byte[length];
        message.get(byteArray);
        byte[] messageArray = Arrays.copyOfRange(byteArray,5,length);

        String[] parsedMessage = new String[2];
        int usernameLength = messageArray[0];
        try {
            parsedMessage[0] = new String(Arrays.copyOfRange(messageArray, 1, (usernameLength + 1)), "us-ascii");
            parsedMessage[1] = new String(Arrays.copyOfRange(messageArray, (usernameLength + 1), messageArray.length), "us-ascii");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException while parsing Login info");
            e.printStackTrace();
        }
        return parsedMessage;
    }


    @Override
    public void receiveMessage(String ip, ByteBuffer message) {  
        if(ip==null || message == null)
            throw new NullPointerException();
        String[] parsedMessage = this.parseMessage(message);
        notifyLoginRequest(ip, parsedMessage[0], parsedMessage[1]);  
    }

}
