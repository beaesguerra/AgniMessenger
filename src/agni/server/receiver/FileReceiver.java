package agni.server.receiver;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

public class FileReceiver implements MessageParser {
    private Vector <FileListener> fileListeners = null;

    public FileReceiver() {
        fileListeners = new Vector<FileListener>();
    }

    private void notifyFileRequest(String ip, String EOF, String fileName, byte[] file) {
        for ( FileListener  fListener : fileListeners )
            fListener.fileRequest(ip, file);
    }

    public void register(FileListener fListener) {
        fileListeners.add(fListener);
    }

    private byte[] parseMessage(ByteBuffer message) {
    	message.flip();
        int length = message.remaining();
        byte[] byteArray = new byte[length];
        message.get(byteArray);
        byte[] parsedMessage = Arrays.copyOfRange(byteArray,5,length);//buffer seems to add an extra 3 bytes
        return parsedMessage;
    }

    @Override
    public void receiveMessage(String ip, ByteBuffer message) {
        byte[] parsedMessage = parseMessage(message);

        int filenameLength = parsedMessage[1];
        String EOF = Arrays.toString(Arrays.copyOfRange(parsedMessage, 0, 1));
        String filename = Arrays.toString(Arrays.copyOfRange(parsedMessage, 2, (filenameLength + 2)));
        byte[] file = Arrays.copyOfRange(parsedMessage, (filenameLength + 2), parsedMessage.length );

        notifyFileRequest(ip, EOF, filename, file);	
    }

}
