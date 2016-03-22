package agni.server.manager;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.sender.InfoSender;

public class UserManager {
    private InfoSender infoSender;
    private I_UserDataGuard userDataGuard;

    public UserManager(InfoSender infoSender,
                       UserDataGuard userDataGuard) {
        this.infoSender = infoSender;
        this.userDataGuard = userDataGuard;
    }
}
