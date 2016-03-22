package agni.server.receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;
public class UserReceiver implements MessageParser {

    private Vector <UserListener> userListeners = null;

    public UserReceiver() {

        userListeners = new Vector();
    }

    private void notifyUserRequest(SocketChannel channel, byte type) {

        for( UserListener  uListener: userListeners )
            uListener.infoRequest(channel, type);
    }

    public void register(UserListener uListener) {

        userListeners.add(uListener);
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

        notifyUserRequest(channel, type);  
    }

}
