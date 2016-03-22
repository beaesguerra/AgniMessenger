package agni.server.receiver;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
        InetSocketAddress address = null;
        byte[] parsedMessage = this.parseMessage(message);

        try {
             address = (InetSocketAddress)channel.getRemoteAddress();
        } catch (IOException e) {
           System.out.println("IOException unable to obtain channel's address");
            e.printStackTrace();
        }

        notifyChatRequest(channel, parsedMessage);	
    }




	
}
