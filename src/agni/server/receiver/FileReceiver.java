package agni.server.receiver;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class FileReceiver implements MessageParser {

    private Vector <FileListener> fileListeners = null;
	
    public FileReceiver() {
        fileListeners = new Vector<FileListener>();

    }
	
    private void notifyFileRequest(SocketChannel channel, byte[] file) {
        for( FileListener  fListener: fileListeners )
            fListener.fileRequest(channel, file);
    }
	
    public void register(FileListener fListener) {
        fileListeners.add(fListener);
	
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
        notifyFileRequest(channel, parsedMessage);	
    }
	
}
