package agni.client.action;

import java.util.Arrays;

import agni.client.communication.MessageSender;

public class UserActionHandler {
    private MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private int USER_ACTION_TYPE_LENGTH = 1;
    final private int EXTRA_ARGUMENT_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x04;

    public UserActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void requestUserAction(byte userActionType, byte extraArg, boolean extraArgUsed) {
        /*
        - byte representing the type of user action
        - Join Chat(0x00)
        - Leave Chat(0x01)
        - See friends list(0x02)
        - Check friend status(0x03)
        - Add friend(0x04)
        - Logout(0x05)
         */
        int numBytes = HEADER_LENGTH_SIZE +
                       MESSAGE_TYPE_SIZE +
                       USER_ACTION_TYPE_LENGTH;
        byte[] packedMessage = new byte[numBytes];
        // one extra byte for the extra argument
        if(extraArgUsed == true) {
        	numBytes += EXTRA_ARGUMENT_SIZE;
        	System.arraycopy(extraArg, 0, packedMessage, 6, 6 + EXTRA_ARGUMENT_SIZE);
        }
        System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, numBytes);
        Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
        System.arraycopy(userActionType, 0, packedMessage, 5, USER_ACTION_TYPE_LENGTH);
        messageSender.sendMessage(packedMessage);
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
