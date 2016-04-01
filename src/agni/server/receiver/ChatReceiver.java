package agni.server.receiver;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

public class ChatReceiver implements MessageParser {
    private Vector <ChatListener> chatListeners = null;

    public ChatReceiver() {
        chatListeners = new Vector<ChatListener>();
    }

    private void notifyChatRequest(String ip, String message) {
        for ( ChatListener  cListener : chatListeners ) {
            cListener.chatRequest(ip, message);
        }
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
        message.flip();
        int length = message.remaining();
        byte[] byteArray = new byte[length];
        message.get(byteArray);
        byte[] parsedMessage = Arrays.copyOfRange(byteArray,5,length);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, ByteBuffer message) {
        if(ip==null || message == null)
            throw new NullPointerException();
        byte[] parsedArray = this.parseMessage(message);
        String parsedMessage;
        try {
            parsedMessage = new String(parsedArray, "us-ascii");
            notifyChatRequest(ip, parsedMessage); 
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }

}
