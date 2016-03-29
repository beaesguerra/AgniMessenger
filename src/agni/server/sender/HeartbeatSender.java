package agni.server.sender;

import java.util.Arrays;

import agni.server.communication.I_MessageSender;

public class HeartbeatSender {
    private I_MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int NUM_BYTES = 5;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x07;

    public HeartbeatSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendHeartbeat(String dest_ip) {
    	if(dest_ip == null){
    		throw new NullPointerException();
    	} else {
    		byte[] packedMessage = new byte[NUM_BYTES];
    		System.arraycopy(intToByteArray(NUM_BYTES), 0, packedMessage, 0, 4); // 4, number of bytes in int
    		Arrays.fill(packedMessage, 4, 5, MESSAGE_TYPE);

    		messageSender.sendMessage(dest_ip, packedMessage);
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
