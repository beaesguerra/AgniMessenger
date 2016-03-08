package agni.server.manager;

import agni.server.dataguard.UserDataGuard;
import agni.server.sender.StatusSender;

public class StatusManager {
    private StatusSender statusSender;
    private UserDataGuard userDataGuard;

    public StatusManager(StatusSender statusSender,
                            UserDataGuard userDataGuard) {
        this.statusSender = statusSender;
        this.userDataGuard = userDataGuard;
    }
}
