package agni.server.receiver;
import java.io.UnsupportedEncodingException;
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
     * parse byte[] to byte array
     * @requires byte[] Message
     * @promises chat message as a byte array
     */
    private byte[] parseMessage(byte[] message) {
    	int length = message.length;
        byte[] parsedMessage = Arrays.copyOfRange(message,5,length);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, byte[] message) {
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
