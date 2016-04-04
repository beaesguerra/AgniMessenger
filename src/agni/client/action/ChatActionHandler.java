package agni.client.action;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


import agni.client.communication.MessageSender;

public class ChatActionHandler {
    private MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x05;

    public ChatActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void requestChatAction(String message) {
        if(message == null)
            throw new NullPointerException("requestChatAction received a null message");
        if(!isAllAscii(message)) {
            throw new IllegalArgumentException("message wasn't all ascii");
        } else {
            byte[] messageBytes = message.getBytes(StandardCharsets.US_ASCII);
            int numBytes = HEADER_LENGTH_SIZE +
                           MESSAGE_TYPE_SIZE +
                           messageBytes.length;
            byte[] packedMessage = new byte[numBytes];
            // filling the first 4 bytes with message length
            System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4);
            // filling the message type in 4th byte
            Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
            // adding the messageBytes
            System.arraycopy(messageBytes, 0, packedMessage, 5 , message.length());
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
