package agni.server.sender;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import agni.server.communication.I_MessageSender;

public class ChatSender {
    private I_MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x09;
    final private int SENDER_LENGTH_SIZE = 1;

    public ChatSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendChat(String dest_ip, String srcName, String message) {
        if(dest_ip == null || srcName == null || message == null){
            throw new NullPointerException();
        }
        if (!isAllAscii(srcName) || !isAllAscii(message)) {
            throw new IllegalArgumentException();
        } else {
            byte[] srcNameBytes = srcName.getBytes(StandardCharsets.US_ASCII);
            byte[] messageBytes = message.getBytes(StandardCharsets.US_ASCII);

            int numBytes = HEADER_LENGTH_SIZE +
                           MESSAGE_TYPE_SIZE +
                           SENDER_LENGTH_SIZE +
                           srcNameBytes.length +
                           messageBytes.length;
            byte[] packedMessage = new byte[numBytes];

            System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4); // 4, number of bytes in int
            Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
            Arrays.fill(packedMessage, 5, 6, (byte)srcNameBytes.length);
            System.arraycopy(srcNameBytes, 0,
                             packedMessage, 6, srcNameBytes.length);
            System.arraycopy(messageBytes, 0,
                             packedMessage, 6 + srcNameBytes.length, messageBytes.length);

            messageSender.sendMessage(dest_ip, packedMessage);
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
