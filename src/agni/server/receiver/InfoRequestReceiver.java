package agni.server.receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class InfoRequestReceiver implements MessageParser {

    private Vector <InfoListener> infoListeners = null;

    public InfoRequestReceiver() {
        infoListeners = new Vector<InfoListener>();
    }

    private void notifyInfoRequest(InetSocketAddress address, String username,String password) {

        for( InfoListener  iListener: infoListeners )
            iListener.infoRequest(address, username, password);
    }

    public void register(InfoListener iListener) {

        infoListeners.add(iListener);
    }
    
    //TODO parse message into username and password
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

		notifyInfoRequest(address, username, password);  
    }

}
