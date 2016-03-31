package agni.client.receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import agni.client.view.AgniClientView;

public class FileReceiver extends MessageParser {
	// Not sure if we use this or the array in MessageParser
//	private Vector <AgniClientView> views = null;
	
    public FileReceiver() {
//    	views = new Vector<AgniClientView>();
    }

    private void notifyFileReceived(String src, File file) {
    	int size = (int) file.getTotalSpace();
    	for(ReceiverListener rListener : super.listeners)
    		rListener.fileReaction(file.getName(), src, size);
    }
    
	@Override
	public void receiveMessage(byte[] message) {
		if(message == null)
			throw new IllegalArgumentException("receiveMessage got a null message");
		// Sender's name -> 6th bit [5]
		String src = "" + (char) message[5];
//		FileOutputStream stream = new FileOutputStream(path);
//		try {
//		    stream.write(message);
//		} finally {
//		    stream.close();
//		}
//		notifyFileReceived(src, file);
	}

}
