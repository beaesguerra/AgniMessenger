package agni.client.action;

import agni.client.communication.MessageSender;

public class LoginActionHandler {
    private MessageSender messageSender;

    public LoginActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void requestLogin(String username, String password) {

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
