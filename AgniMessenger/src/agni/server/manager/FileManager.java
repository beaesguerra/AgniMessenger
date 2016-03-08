package agni.server.manager;

import agni.server.dataguard.FileDataGuard;
import agni.server.sender.FileSender;
import agni.server.sender.InfoSender;

public class FileManager {
    private InfoSender infoSender;
    private FileSender fileSender;
    private FileDataGuard fileDataGuard;

    public FileManager(InfoSender infoSender,
                        FileSender fileSender,
                        FileDataGuard fileDataGuard) {
        this.infoSender = infoSender;
        this.fileSender = fileSender;
        this.fileDataGuard = fileDataGuard;
    }
}
