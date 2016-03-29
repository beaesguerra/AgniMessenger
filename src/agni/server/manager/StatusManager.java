package agni.server.manager;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.sender.StatusSender;

public class StatusManager {
    private StatusSender statusSender;
    private I_UserDataGuard userDataGuard;

    public StatusManager(StatusSender statusSender,
                         UserDataGuard userDataGuard) {
        this.statusSender = statusSender;
        this.userDataGuard = userDataGuard;
    }
}
