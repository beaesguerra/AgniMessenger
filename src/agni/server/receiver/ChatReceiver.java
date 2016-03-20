package agni.server.receiver;

public class ChatReceiver implements MessageParser {

    private Vector <ChatListener> chatListeners = null;
	
    public ChatReceiver() {

        chatListeners = new Vector();
    }
	
    private void notifyChatRequest(String ip, byte[] message) {

        for( ChatListener  cListener: chatListeners )
            cListener.chatRequest(ip, message);
    }
	
    public void register(ChatListener cListener) {

        chatListeners.add(cListener);
    }

    private byte[] parseMessage(ByteBuffer message) {
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length);
        return parsedMessage;
    }


    @Overide
    public void receiveMessage(InetAddress ip, ByteBuffer message) {

        byte[] parsedMessage = message.parseMessage(message); 
        notifyChatRequest(ip.toString(), message);	
    }
	
}
