package agni.client.action;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
    	if(username == null || password == null) {
    		throw new NullPointerException("requestLogin received a null username/password");
    	}
    	if(!isAllAscii(username) || !isAllAscii(password)) {
    		throw new IllegalArgumentException("requestLogin received non-Ascii username/passord");
    	} else {
    		byte[] usernameBytes = username.getBytes(StandardCharsets.US_ASCII);
        	byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
        	int numBytes = HEADER_LENGTH_SIZE +
        	               MESSAGE_TYPE_SIZE +
        	               USERNAME_LENGTH_SIZE +
        	               usernameBytes.length +
        	               passwordBytes.length;
        	byte[] packedMessage = new byte[numBytes];
        	System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4);
        	Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
        	Arrays.fill(packedMessage, 5, 6, (byte) usernameBytes.length);
        	System.arraycopy(usernameBytes, 0, packedMessage,
        			         6, (6 + usernameBytes.length));
        	System.arraycopy(passwordBytes, 0, packedMessage,
        			         8, (passwordBytes.length));
        	messageSender.sendMessage(packedMessage);
    	}
    	
    }
    
    private static boolean isAllAscii(String input) {
        boolean isAscii = true;
        for (int i = 0; i < input.length(); i++) {
            int c = input.charAt(i);
            if (c > 0x7F) {
                isAscii = false;
                break;
            }
        }
        return isAscii;
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
