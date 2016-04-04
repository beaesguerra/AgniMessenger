package agni.client.action;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import agni.client.communication.MessageSender;

public class UserActionHandler {
    private MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private int USER_ACTION_TYPE_LENGTH = 1;
    final private int EXTRA_ARGUMENT_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x04;

    public enum UserRequestTypes {
        JOIN_CHAT((byte)0x00),
        LEAVE_CHAT((byte)0x01),
        FRIEND_LIST((byte)0x02),
        FRIEND_STATUS((byte)0x03),
        ADD_FRIEND((byte)0x04),
        LOGOUT((byte)0x05),
        CREATE_CHAT((byte)0x06);
        
        private final byte bytes;
        private UserRequestTypes(byte bytes) {
            this.bytes = bytes;
        }

        final public byte bytes() {
            return bytes;
        }
    }


    public UserActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    
    public void requestUserAction(byte userActionType) {
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
        System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4);
        Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
        Arrays.fill(packedMessage, 5, 6, userActionType);
        messageSender.sendMessage(packedMessage);
    }

    public void requestUserAction(byte userActionType, String extraArg) {
    	byte[] extraArgBytes = extraArg.getBytes(StandardCharsets.US_ASCII);
    	int numBytes = HEADER_LENGTH_SIZE +
                       MESSAGE_TYPE_SIZE +
                       USER_ACTION_TYPE_LENGTH +
                       extraArgBytes.length;
        byte[] packedMessage = new byte[numBytes];
        System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4);
        Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
        Arrays.fill(packedMessage, 5, 6, userActionType);
        // one extra byte for the extra argument
        
        System.arraycopy(extraArgBytes, 0, packedMessage, 6, extraArgBytes.length);
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
