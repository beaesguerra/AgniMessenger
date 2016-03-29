package agni.server.sender;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import agni.server.communication.I_MessageSender;

public class StatusSender {
    private I_MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x0b;
    final private int STATUS_BYTE_SIZE = 1;

    public enum Status {
        OFFLINE((byte)0x00),
        ONLINE((byte)0x01),
        AWAY((byte)0x02);

        private final byte bytes;
        private Status(byte bytes) {
            this.bytes = bytes;
        }

        public byte bytes() {
            return bytes;
        }
    }

    public StatusSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendStatus(String destIp, Status status, String friendName) {
        if (destIp == null || status == null || friendName == null) {
            throw new NullPointerException();
        } else if (!isAllAscii(friendName)) {
            throw new IllegalArgumentException();
        } else {
            byte[] friendNameBytes = friendName.getBytes(StandardCharsets.US_ASCII);
            int numBytes = HEADER_LENGTH_SIZE +
                           MESSAGE_TYPE_SIZE +
                           STATUS_BYTE_SIZE +
                           friendNameBytes.length;
            byte[] packedMessage = new byte[numBytes];
            System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4); // 4, number of bytes in int
            Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);
            Arrays.fill(packedMessage, 5, 6, status.bytes);
            System.arraycopy(friendNameBytes, 0,
                             packedMessage, 6, friendNameBytes.length);

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
