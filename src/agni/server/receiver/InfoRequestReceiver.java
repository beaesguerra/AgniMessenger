package agni.server.receiver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class InfoRequestReceiver implements MessageParser {
    private Vector <InfoListener> infoListeners = null;

    public InfoRequestReceiver() {
        infoListeners = new Vector<InfoListener>();
    }

    private void notifyInfoRequest(String ip, byte type) {
        for ( InfoListener  iListener : infoListeners )
            iListener.infoRequest(ip, type);
    }

    public void register(InfoListener iListener) {
        infoListeners.add(iListener);
    }

    /*
     * parse ByteBuffer into type byte
     * @requires ByteBuffer Message
     * @promises type as a byte
     */
    private byte parseMessage(ByteBuffer message) {
        byte parsedMessage = message.get(5);
        return parsedMessage;
    }

    @Override
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {
        String ip = null;
        byte parsedMessage = parseMessage(message);

        try {
            ip = channel.getRemoteAddress().toString();
        } catch (IOException e) {
            System.out.println("IOException unable to obtain channel's ip");
            e.printStackTrace();
        }

        notifyInfoRequest(ip, parsedMessage);
    }

}
