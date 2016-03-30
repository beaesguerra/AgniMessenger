package agni.client.receiver;

import java.io.File;
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
//		notifyFileReceived(src, file);
	}

}
