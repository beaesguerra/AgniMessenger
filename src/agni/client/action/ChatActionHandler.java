package agni.client.action;

import java.nio.charset.StandardCharsets;

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
}
