package agni.server.receiver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length));

        return parsedMessage;
    }

    @Override
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {
        String ip = null;
        byte[] parsedMessage = parseMessage(message);

        int filenameLength = parsedMessage[1];
        String EOF = Arrays.toString(Arrays.copyOfRange(parsedMessage, 0, 1));
        String filename = Arrays.toString(Arrays.copyOfRange(parsedMessage, 2, (filenameLength + 2)));
        byte[] file = Arrays.copyOfRange(parsedMessage, (filenameLength + 2), parsedMessage.length );

        try {
            ip = channel.getRemoteAddress().toString();
        } catch (IOException e) {
            System.out.println("IOException unable to obtain channel's ip");
            e.printStackTrace();
        }

        notifyFileRequest(ip, EOF, filename, file);
    }

}
