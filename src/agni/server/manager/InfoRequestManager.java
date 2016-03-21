package agni.server.manager;

import agni.server.dataguard.FileDataGuard;
import agni.server.sender.InfoSender;

public class InfoRequestManager {
    private InfoSender infoSender;
    private I_FileDataGuard fileDataGuard;

    public InfoRequestManager(InfoSender infoSender,
                              FileDataGuard fileDataGuard) {
        this.infoSender = infoSender;
        this.fileDataGuard = fileDataGuard;
    }

}
