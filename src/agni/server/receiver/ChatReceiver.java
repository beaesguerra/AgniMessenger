package agni.server.receiver;

public class ChatReceiver implements MessageParser {

    private Vector <ChatListener> chatListeners = null;
	
    public ChatReceiver() {

        chatListeners = new Vector();
    }
	
    private void notifyChatRequest(SocketChannel channel, byte[] message) {

        for( ChatListener  cListener: chatListeners )
            cListener.chatRequest(channel, message);
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
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = message.parseMessage(message); 
        notifyChatRequest(channel, message);	
    }
	
}
