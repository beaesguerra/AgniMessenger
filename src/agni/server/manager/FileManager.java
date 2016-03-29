package agni.server.manager;

import agni.server.dataguard.FileDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.FileListener;
import agni.server.sender.FileSender;
import agni.server.sender.InfoSender;

public class FileManager implements FileListener {
    private InfoSender infoSender;
    private FileSender fileSender;
    private FileDataGuard fileDataGuard;

    public FileManager(InfoSender infoSender,
                       FileSender fileSender,
                       FileDataGuard fileDataGuard, 
                       UserDataGuard userDataGuard) {
        this.infoSender = infoSender;
        this.fileSender = fileSender;
        this.fileDataGuard = fileDataGuard;
    }

    @Override
    public void chatRequest(String ip, byte type) {
        // TODO Auto-generated method stub
        
    }
}
