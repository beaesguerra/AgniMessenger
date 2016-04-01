package agni.client.action;

import agni.client.communication.MessageSender;

public class InfoRequestActionHandler {
    private MessageSender messageSender;
    final private int HEADER_LENGTH_SIZE = 4;
    final private int MESSAGE_TYPE_SIZE = 1;
    final private byte MESSAGE_TYPE = 0x03;
    
    public InfoRequestActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    
    public enum InfoTypes {
        SERVER_IP((byte)0x00),
        PORT((byte)0x01),
        NAME((byte)0x02),
        USERS_ONLINE((byte)0x03),
        CURRENT_CHATS((byte)0x04);
        
        private final byte bytes;
        private InfoTypes(byte bytes) {
            this.bytes = bytes;
        }

        final public byte bytes() {
            return bytes;
        }
    }
    
    /**
     * A message to send a request for Agni server information
     * @param infoType - the type of the information to be sent
     * */
    public void requestInfo(byte infoType) {
    	/*
		- byte representing the type of request information
		- Server IP (0x00) , Port (0x01), and Name (0x02)
		- Current users online (0x03)
		- Current chats (0x04)
    	 */
    	int numBytes = HEADER_LENGTH_SIZE +
    				   MESSAGE_TYPE_SIZE;
    	byte[] packedMessage = new byte[numBytes];
    	System.arraycopy(intToByteArray(numBytes), 0, packedMessage, 0, 4);
    	
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
