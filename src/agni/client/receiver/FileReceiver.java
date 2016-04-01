package agni.client.receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Vector;
import agni.client.view.AgniClientView;

public class FileReceiver extends MessageParser {
    
    public FileReceiver() {}

    private void notifyFileReceived(String src, File file) {
        int size = (int) file.getTotalSpace();
        for(ReceiverListener rListener : super.listeners)
            rListener.fileReaction(file.getName(), src, size);
    }
    
    @Override
    public void receiveMessage(byte[] message) {
        if(message == null)
            throw new NullPointerException("receiveMessage got a null message");
    
        // End of file -> 5th bit
        // 0x00 -> more message left for file
        // 0x01 -> file ends in this file
        byte fileEndsHere = message[5];
        
        // File name length -> 6th bit
        int fileNameLength = message[6];
        // Sender's name length -> 8th bit
        int senderNameLength = (int) message[8];
        try {
            // File name -> 7th bit
            String fileName = new String(Arrays.copyOfRange(message,
                                         7, 7 + fileNameLength),
                                         "us-ascii");
            // Sender's name starts -> 9th bit
            // (9+file name length) to (9 + file name length + sender name length)
            String src = new String(Arrays.copyOfRange(message,
                                                       9 + fileNameLength,
                                                       9 + fileNameLength + senderNameLength),
                                                       "us-ascii");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//      notifyFileReceived(src, file);
    }

}
