package agni.server.receiver;

import java.io.UnsupportedEncodingException;
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
     * parse byte[] into strings
     * @requires byte[] Message
     * @promises username and password as strings
     */
    private String[] parseMessage(byte[] message) {
        int length = message.length;
        byte[] messageArray = Arrays.copyOfRange(message,5,length);
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
    public void receiveMessage(String ip, byte[] message) {  
        if(ip==null || message == null)
            throw new NullPointerException();
        String[] parsedMessage = this.parseMessage(message);
        notifyLoginRequest(ip, parsedMessage[0], parsedMessage[1]);  
    }

    private void notifyNewUserRequest() {
        
    }

}
