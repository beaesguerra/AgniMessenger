package agni.server.receiver;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class ChatReceiver implements MessageParser {

    private Vector <ChatListener> chatListeners = null;
	
    public ChatReceiver() {

        chatListeners = new Vector<ChatListener>();
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
        message.get(parsedMessage, 5, (length));
        return parsedMessage;
    }


    @Override
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = parseMessage(message); 
        notifyChatRequest(channel, parsedMessage);	
    }




	
}
