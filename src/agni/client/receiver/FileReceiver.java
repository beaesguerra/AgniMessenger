package agni.client.receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
	
		// End of file -> 5th bit [4]
		// 0x00 -> more message left for file
		// 0x01 -> file ends in this file
		byte fileEndsHere = message[4];
		
		// File name length -> 6th bit [5]
		int fileNameLength = message[5];
		// Sender's name length -> 8th bit [7]
		int senderNameLength = (int) message[7];
		try {
			// Sender's name starts -> 9th bit [8]
			// (9+file name length) to (9 + file name length + sender name length)
			String src = new String(Arrays.copyOfRange(message,
													   8 + fileNameLength,
													   8 + fileNameLength + senderNameLength),
													   "us-ascii");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// file content
		// (8 + file name length) 
		// to (8 + file name length + file content length)
		
		
//		FileOutputStream stream = new FileOutputStream(path);
//		try {
//		    stream.write(message);
//		} finally {
//		    stream.close();
//		}
//		notifyFileReceived(src, file);
	}

}
