package agni.client.action;

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
