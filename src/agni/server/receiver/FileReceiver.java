package agni.server.receiver;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

public class FileReceiver implements MessageParser {
    private Vector <FileListener> fileListeners = null;

    public FileReceiver() {
        fileListeners = new Vector<FileListener>();
    }

    private void notifyFileRequest(String ip, byte EOF, String fileName, byte[] file) {
        for ( FileListener  fListener : fileListeners )
            fListener.fileRequest(ip, EOF, fileName, file);
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
        String fileName = null;
        if(ip==null || message == null)
            throw new IllegalArgumentException();
        byte[] parsedMessage = parseMessage(message);
        byte EOF = parsedMessage[0];
        int filenameLength = parsedMessage[1];
        try {
            fileName = new String(Arrays.copyOfRange(parsedMessage, 2, (filenameLength + 2)), "us-ascii");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException while parsing Login info");
            e.printStackTrace();
        }
        byte[] file = Arrays.copyOfRange(parsedMessage, (filenameLength + 2), parsedMessage.length );

        notifyFileRequest(ip, EOF, fileName, file); 
    }

}
