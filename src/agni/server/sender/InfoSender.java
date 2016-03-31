package agni.server.sender;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import agni.server.communication.I_MessageSender;

public class InfoSender {
    private I_MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x08;

    public InfoSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendInfo(String destIp, String message) {
        if (destIp == null || message == null) {
            throw new NullPointerException();
        } else if (!isAllAscii(message)) {
            throw new IllegalArgumentException();
        } else {
            byte[] messageBytes = message.getBytes(StandardCharsets.US_ASCII);
            int numBytes = HEADER_LENGTH_SIZE +
                           MESSAGE_TYPE_SIZE +
                           messageBytes.length;
            byte[] packedMessage = new byte[numBytes];
            System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4); // 4, number of bytes in int
            Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
            System.arraycopy(messageBytes, 0,
                             packedMessage, 5, messageBytes.length);

            messageSender.sendMessage(destIp, packedMessage);
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
