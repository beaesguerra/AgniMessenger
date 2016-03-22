package agni.server.receiver;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class ChatReceiver implements MessageParser {
    private Vector <ChatListener> chatListeners = null;
	
    public ChatReceiver() {
        chatListeners = new Vector<ChatListener>();
    }
	
    private void notifyChatRequest(String ip, byte[] message) {
        for( ChatListener  cListener: chatListeners )
            cListener.chatRequest(ip, message);
    }
	
    public void register(ChatListener cListener) {
        chatListeners.add(cListener);
    }

    /*
     * parse ByteBuffer to byte array
     * @requires ByteBuffer Message
     * @promises chat message as a byte array
     */
    private byte[] parseMessage(ByteBuffer message) {
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length));
        return parsedMessage;
    }

    @Override
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {
    	String ip = null;
        byte[] parsedMessage = this.parseMessage(message);

        try {
            ip = channel.getRemoteAddress().toString();
       } catch (IOException e) {
          System.out.println("IOException unable to obtain channel's ip");
           e.printStackTrace();
       }

        notifyChatRequest(ip, parsedMessage);	
    }


}
