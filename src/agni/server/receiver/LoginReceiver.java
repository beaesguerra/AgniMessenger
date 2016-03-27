package agni.server.receiver;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

public class LoginReceiver implements MessageParser {
    private Vector <LoginListener> loginListeners = null;

    public LoginReceiver() {
        loginListeners = new Vector<LoginListener>();
    }

    private void notifyLoginRequest(String ip, String username, String password) {
        for (LoginListener  lListener : loginListeners)
            lListener.loginRequest (ip, username, password);
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
        int length = message.remaining();
        byte[] messageArray = new byte[length];
        message.get(messageArray, 5, (length));

        String[] parsedMessage = new String[2];
        int usernameLength = messageArray[0];
        parsedMessage[0] = Arrays.toString(Arrays.copyOfRange(messageArray, 1, (usernameLength + 1)));
        parsedMessage[1] = Arrays.toString(Arrays.copyOfRange(messageArray, (usernameLength + 1), messageArray.length ));

        return parsedMessage;
    }


    @Override
    public void receiveMessage(String ip, ByteBuffer message) {  	
        String[] parsedMessage = this.parseMessage(message);
        notifyLoginRequest(ip, parsedMessage[0], parsedMessage[1]);  
    }

}
