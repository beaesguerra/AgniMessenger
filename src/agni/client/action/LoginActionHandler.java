package agni.client.action;

import java.nio.charset.StandardCharsets;

import agni.client.communication.MessageSender;

public class LoginActionHandler {
    private MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private int USERNAME_LENGTH_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x02;

    public LoginActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    
    /**
     * packages the login message and passes it to MessageSender
     * @param username - the username of the client
     * @param passoed - the password corresponding to the username of the client
     * */
    public void requestLogin(String username, String password) {
    	if(username == null || password == null)
    		throw new NullPointerException("requestLogin received a null username/password");
    	byte[] usernameBytes = username.getBytes(StandardCharsets.US_ASCII);
    	byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
    	int numBytes = HEADER_LENGTH_SIZE +
    	               MESSAGE_TYPE_SIZE +
    	               USERNAME_LENGTH_SIZE +
    	               usernameBytes.length +
    	               passwordBytes.length;
    	
    }
    
    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                   (byte)(value >>> 24),
                   (byte)(value >>> 16),
                   (byte)(value >>> 8),
                   (byte)value
               };
    }
}
