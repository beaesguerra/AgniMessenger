import java.util.Arrays;

public class SerHeartbeatSender {
    private SerMessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int NUM_BYTES = 5;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x07;

    public SerHeartbeatSender(SerMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendHeartbeat(String destIp) {
        if (destIp == null) {
            throw new NullPointerException();
        } else {
            byte[] packedMessage = new byte[NUM_BYTES];
            System.arraycopy(intToByteArray(NUM_BYTES), 0, packedMessage, 0, 4); // 4, number of bytes in int
            Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);

            System.out.println("SENDING HEARTBEAT");
            messageSender.sendMessage(destIp, packedMessage);
        }
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
