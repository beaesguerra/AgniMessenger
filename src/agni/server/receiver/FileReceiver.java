package agni.server.receiver;

public class FileReceiver implements MessageParser {

    private Vector <FileListener> fileListeners = null;
	
    public FileReceiver() {
        fileListeners = new Vector();fileListeners = new Vector();

    }
	
    private void notifyFileRequest(SocketChannel channel, byte[] file) {
        for( FileListener  fListener: fileListeners )
            fListener.infoRequest(channel, file);
    }
	
    public void register(FileListener fListener) {
        fileListeners.add(fListener);
	
    }

    private byte[] parseMessage(ByteBuffer message) {
    	
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length);
        return parsedMessage;
    }


    @Overide
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyFileRequest(channel, message);	
    }
	
}
